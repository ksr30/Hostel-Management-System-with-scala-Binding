// Copyright (c) 2020 The DAML Authors. All rights reserved.
// SPDX-License-Identifier: Apache-2.0

package com.knoldus

import java.time.Instant

import akka.actor.ActorSystem
import akka.stream.Materializer
import com.digitalasset.api.util.TimeProvider
import com.digitalasset.grpc.adapter.AkkaExecutionSequencerPool
import com.digitalasset.ledger.api.refinements.ApiTypes.{ApplicationId, WorkflowId}
import com.digitalasset.ledger.api.v1.ledger_offset.LedgerOffset
import com.digitalasset.ledger.client.LedgerClient
import com.digitalasset.ledger.client.binding.Contract
import com.digitalasset.ledger.client.configuration.{
  CommandClientConfiguration,
  LedgerClientConfiguration,
  LedgerIdRequirement
}
import com.knoldus.ClientUtil.workflowIdFromParty
import com.knoldus.DecodeUtil.{decodeAllCreated, decodeArchived, decodeCreated}
import com.knoldus.FutureUtil.toFuture
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Success}


import com.digitalasset.ledger.client.binding.{Primitive => P}
import com.knoldus.{Main => M}


object IouMain extends App with StrictLogging {

  if (args.length != 2) {
    logger.error("Usage: LEDGER_HOST LEDGER_PORT")
    System.exit(-1)
  }

  private val ledgerHost = args(0)
  private val ledgerPort = args(1).toInt


// Creating Parties
  private val hostler1 = P.Party("U1")
  private val hostler2 = P.Party("U2")
  private val hostler3 = P.Party("U3")
  private val hostler4 = P.Party("U4")
  private val manager1 = P.Party("Manager")
  private val service_Provider1 = P.Party("XYZ Service Provider")

  val listOfHostlers = List(hostler1,hostler2,hostler3,hostler4)
// TODO why?
  private val asys = ActorSystem()
  private val amat = Materializer(asys)
  private val aesf = new AkkaExecutionSequencerPool("clientPool")(asys)

  private def shutdown(): Unit = {
    logger.info("Shutting down...")
    Await.result(asys.terminate(), 10.seconds)
    ()
  }

  private implicit val ec: ExecutionContext = asys.dispatcher

  private val applicationId = ApplicationId("IOU Example")

  private val timeProvider = TimeProvider.Constant(Instant.EPOCH)

  // Configure client's end which is going to interact with ledger
  private val clientConfig = LedgerClientConfiguration(
    applicationId = ApplicationId.unwrap(applicationId),
    ledgerIdRequirement = LedgerIdRequirement("", enabled = false),
    commandClient = CommandClientConfiguration.default,
    sslContext = None,
    token = None
  )


  private val clientF: Future[LedgerClient] =
    LedgerClient.singleHost(ledgerHost, ledgerPort, clientConfig)(ec, aesf)

  private val clientUtilF: Future[ClientUtil] =
    clientF.map(client => new ClientUtil(client, applicationId, 30.seconds, timeProvider))

  private val offset0F: Future[LedgerOffset] = clientUtilF.flatMap(_.ledgerEnd)

// Set workflowId of each party which is going to interact with ledger

  private val hostler1WorkflowId: WorkflowId = workflowIdFromParty(hostler1)
  private val hostler2WorkflowId: WorkflowId = workflowIdFromParty(hostler2)
  private val hostler3WorkflowId: WorkflowId = workflowIdFromParty(hostler3)
  private val hostler4WorkflowId: WorkflowId = workflowIdFromParty(hostler4)
  private val managerWorkflowId: WorkflowId = workflowIdFromParty(manager1)
  private val service_ProviderWorkflowId: WorkflowId = workflowIdFromParty(service_Provider1)


// final contract between manager and service provider

  val finalContract1 = M.Money_Transfer_Agreement(
    issuer = manager1,
    owner = service_Provider1,
    money = 0,
    signatories = manager1 :: listOfHostlers
  )

// Confirmation Contract after service is provided

  val confirmationAgreement = M.Confirmation_Agreement(
    manager = manager1,
    service_Provider = service_Provider1,
    money = 10000,
    finalContract = finalContract1,
    alreadySigned = List(manager1),


    )



  val issuerFlow: Future[Unit] = for {
    clientUtil <- clientUtilF
    offset0 <- offset0F
    _ = logger.info(s"Client API initialization completed, Ledger ID: ${clientUtil.toString}")

    // Manager creates confirmation contract with all hostelers as observer.

    createCmd = confirmationAgreement.create

    // Send created command to ledger

    _ <- clientUtil.submitCommand(manager1, managerWorkflowId, createCmd)
    _ = logger.info(s"$manager1 created Confirmation Agreement: $confirmationAgreement")
    _ = logger.info(s"$manager1 sent create command: $createCmd")

   // To confirm, command executed properly on ledger
    // nextTransaction :- Fetches transaction created by manager from ledger                             // contact != transaction ; contract = subset(transaction)

    tx0 <- clientUtil.nextTransaction(manager1, offset0)(amat)
    _ = logger.info(s"$manager1 received transaction: $tx0")

    // decodeCreated :- Decodes contract created of manager of 'Confirmation_Agreement' type from fetched transaction.

    tempConfirmationAgreement <- toFuture(decodeCreated[M.Confirmation_Agreement](tx0))
    _ = logger.info(s"$manager1 received contract: $tempConfirmationAgreement")

    offset1 <- clientUtil.ledgerEnd

// hostler1 approves contract by exercising approve choice

    exerciseCmd = tempConfirmationAgreement.contractId.exerciseApprove(actor = hostler1,signer = hostler1)

    _ <- clientUtil.submitCommand(hostler1, hostler1WorkflowId, exerciseCmd)
    _ = logger.info(s"$hostler1 sent exercise command: $exerciseCmd ")


    tx1 <- clientUtil.nextTransaction(hostler1, offset1)(amat)
    _ = logger.info(s"$hostler1 received transaction: $tx1")
    tempConfirmationAgreement1 <- toFuture(decodeAllCreated[M.Confirmation_Agreement](tx1).headOption)
    _ = logger.info(s"$hostler1 received contract: $tempConfirmationAgreement1")


    offset2 <- clientUtil.ledgerEnd
    exerciseCmd = tempConfirmationAgreement1.contractId.exerciseApprove(actor = hostler2,signer = hostler2)
    _ <- clientUtil.submitCommand(hostler2, hostler2WorkflowId, exerciseCmd)
    _ = logger.info(s"$hostler2 sent exercise command: $exerciseCmd")
    tx2 <- clientUtil.nextTransaction(hostler2, offset2)(amat)
    _ = logger.info(s"$hostler2 received transaction : $tx2")
   tempConfirmationAgreement2 <- toFuture(decodeAllCreated[M.Confirmation_Agreement](tx2).headOption)
    _ = logger.info(s"$hostler2 received confirmation : $tempConfirmationAgreement2")

    offset3 <- clientUtil.ledgerEnd
    exerciseCmd1 = tempConfirmationAgreement2.contractId.exerciseApprove(actor = hostler3,signer = hostler3)
    _ <- clientUtil.submitCommand(hostler3, hostler3WorkflowId, exerciseCmd1)
    _ = logger.info(s"$hostler3 sent exercise command: $exerciseCmd1 ")
    tx3 <- clientUtil.nextTransaction(hostler3, offset3)(amat)
    _ = logger.info(s"$hostler3 received transaction: $tx3")
    tempConfirmationAgreement3 <- toFuture(decodeAllCreated[M.Confirmation_Agreement](tx3).headOption)
    _ = logger.info(s"$hostler3 received confirmation: $tempConfirmationAgreement3")

    offset4 <- clientUtil.ledgerEnd
    exerciseCmd1 = tempConfirmationAgreement3.contractId.exerciseApprove(actor = hostler4,signer = hostler4)
    _ <- clientUtil.submitCommand(hostler4, hostler4WorkflowId, exerciseCmd1)
    _ = logger.info(s"$hostler4 sent exercise command: $exerciseCmd1 ")
    tx4 <- clientUtil.nextTransaction(hostler4, offset4)(amat)
    _ = logger.info(s"$hostler4 received transaction: $tx4")
    tempConfirmationAgreement4 <- toFuture(decodeAllCreated[M.Confirmation_Agreement](tx4).headOption)
    _ = logger.info(s"$hostler4 received confirmation: $tempConfirmationAgreement4")

    offset5 <- clientUtil.ledgerEnd

    // DoPay choice exercised by manager

    exerciseCmd1 = tempConfirmationAgreement4.contractId.exerciseDoPay(actor = manager1,newMoney = 10000)
    _ <- clientUtil.submitCommand(manager1, managerWorkflowId, exerciseCmd1)
    _ = logger.info(s"$manager1 sent exercise command: $exerciseCmd1 ")
    tx5 <- clientUtil.nextTransaction(manager1, offset5)(amat)
    _ = logger.info(s"$manager1 received transaction: $tx5")
    tempConfirmationAgreement5 <- toFuture(decodeAllCreated[M.Money_Transfer_Agreement](tx5).headOption)
    _ = logger.info(s"$manager1 received confirmation: $tempConfirmationAgreement5")

  } yield ()

  val returnCodeF: Future[Int] = issuerFlow.transform {
    case Success(_) =>
      logger.info("IOU flow completed.")
      Success(0)
    case Failure(e) =>
      logger.error("IOU flow completed with an error", e)
      Success(1)
  }

  val returnCode: Int = Await.result(returnCodeF, 10.seconds)
  shutdown()
  System.exit(returnCode)
}
