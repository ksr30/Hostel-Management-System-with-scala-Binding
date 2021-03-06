/*
 * THIS FILE WAS AUTOGENERATED BY THE DIGITAL ASSET DAML SCALA CODE GENERATOR
 * DO NOT EDIT BY HAND!
 */
import _root_.com.digitalasset.ledger.client.{binding=>$u0020lfdomainapi}
import _root_.com.digitalasset.ledger.api.v1.{value=>$u0020rpcvalue}
package com.knoldus {
  package Main {
    final case class Confirmation_Agreement(manager: ` lfdomainapi`.Primitive.Party, service_Provider: ` lfdomainapi`.Primitive.Party, money: ` lfdomainapi`.Primitive.Int64, finalContract: com.knoldus.Main.Money_Transfer_Agreement, alreadySigned: ` lfdomainapi`.Primitive.List[` lfdomainapi`.Primitive.Party]) extends ` lfdomainapi`.Template[Confirmation_Agreement] {
      override protected[this] def templateCompanion(implicit ` d` : _root_.scala.Predef.DummyImplicit) = Confirmation_Agreement
    }

    object Confirmation_Agreement extends ` lfdomainapi`.TemplateCompanion[Confirmation_Agreement] with _root_.scala.Function5[` lfdomainapi`.Primitive.Party, ` lfdomainapi`.Primitive.Party, ` lfdomainapi`.Primitive.Int64, com.knoldus.Main.Money_Transfer_Agreement, ` lfdomainapi`.Primitive.List[` lfdomainapi`.Primitive.Party], _root_.com.knoldus.Main.Confirmation_Agreement] {
      import _root_.scala.language.higherKinds;
      trait view[` C`[_]] extends ` lfdomainapi`.encoding.RecordView[` C`, view] { $u0020view =>
        val manager: ` C`[` lfdomainapi`.Primitive.Party];
        val service_Provider: ` C`[` lfdomainapi`.Primitive.Party];
        val money: ` C`[` lfdomainapi`.Primitive.Int64];
        val finalContract: ` C`[com.knoldus.Main.Money_Transfer_Agreement];
        val alreadySigned: ` C`[` lfdomainapi`.Primitive.List[` lfdomainapi`.Primitive.Party]];
        final override def hoist[` D`[_]](` f` : _root_.scalaz.~>[` C`, ` D`]): view[` D`] = {
          final class $anon extends _root_.scala.AnyRef with view[` D`] {
            override val manager = ` f`(` view`.manager);
            override val service_Provider = ` f`(` view`.service_Provider);
            override val money = ` f`(` view`.money);
            override val finalContract = ` f`(` view`.finalContract);
            override val alreadySigned = ` f`(` view`.alreadySigned)
          };
          new $anon()
        }
      };
      override val id = ` templateId`(packageId = `Package IDs`.Main, moduleName = "Main", entityName = "Confirmation_Agreement");
      final implicit class `Confirmation_Agreement syntax`[+` ExOn`](private val id: ` ExOn`) extends _root_.scala.AnyVal {
        def exerciseArchive(actor: ` lfdomainapi`.Primitive.Party, choiceArgument: com.knoldus.DA.Internal.Template.Archive)(implicit ` exOn` : ` lfdomainapi`.encoding.ExerciseOn[` ExOn`, Confirmation_Agreement]): ` lfdomainapi`.Primitive.Update[` lfdomainapi`.Primitive.Unit] = ` exercise`(id, "Archive", _root_.scala.Some(` lfdomainapi`.Value.encode(choiceArgument)));
        def exerciseArchive(actor: ` lfdomainapi`.Primitive.Party)(implicit ` exOn` : ` lfdomainapi`.encoding.ExerciseOn[` ExOn`, Confirmation_Agreement]): ` lfdomainapi`.Primitive.Update[` lfdomainapi`.Primitive.Unit] = exerciseArchive(actor, _root_.com.knoldus.DA.Internal.Template.Archive());
        def exerciseDoPay(actor: ` lfdomainapi`.Primitive.Party, choiceArgument: com.knoldus.Main.DoPay)(implicit ` exOn` : ` lfdomainapi`.encoding.ExerciseOn[` ExOn`, Confirmation_Agreement]): ` lfdomainapi`.Primitive.Update[` lfdomainapi`.Primitive.ContractId[com.knoldus.Main.Money_Transfer_Agreement]] = ` exercise`(id, "DoPay", _root_.scala.Some(` lfdomainapi`.Value.encode(choiceArgument)));
        def exerciseDoPay(actor: ` lfdomainapi`.Primitive.Party, newMoney: ` lfdomainapi`.Primitive.Int64)(implicit ` exOn` : ` lfdomainapi`.encoding.ExerciseOn[` ExOn`, Confirmation_Agreement]): ` lfdomainapi`.Primitive.Update[` lfdomainapi`.Primitive.ContractId[com.knoldus.Main.Money_Transfer_Agreement]] = exerciseDoPay(actor, _root_.com.knoldus.Main.DoPay(newMoney));
        def exerciseApprove(actor: ` lfdomainapi`.Primitive.Party, choiceArgument: com.knoldus.Main.Approve)(implicit ` exOn` : ` lfdomainapi`.encoding.ExerciseOn[` ExOn`, Confirmation_Agreement]): ` lfdomainapi`.Primitive.Update[` lfdomainapi`.Primitive.ContractId[com.knoldus.Main.Confirmation_Agreement]] = ` exercise`(id, "Approve", _root_.scala.Some(` lfdomainapi`.Value.encode(choiceArgument)));
        def exerciseApprove(actor: ` lfdomainapi`.Primitive.Party, signer: ` lfdomainapi`.Primitive.Party)(implicit ` exOn` : ` lfdomainapi`.encoding.ExerciseOn[` ExOn`, Confirmation_Agreement]): ` lfdomainapi`.Primitive.Update[` lfdomainapi`.Primitive.ContractId[com.knoldus.Main.Confirmation_Agreement]] = exerciseApprove(actor, _root_.com.knoldus.Main.Approve(signer))
      };
      override val consumingChoices: Set[` lfdomainapi`.Primitive.ChoiceId] = ` lfdomainapi`.Primitive.ChoiceId.subst(Set("Archive", "DoPay", "Approve"));
      override def toNamedArguments(` self` : Confirmation_Agreement) = ` arguments`(scala.Tuple2("manager", ` lfdomainapi`.Value.encode(` self`.manager)), scala.Tuple2("service_Provider", ` lfdomainapi`.Value.encode(` self`.service_Provider)), scala.Tuple2("money", ` lfdomainapi`.Value.encode(` self`.money)), scala.Tuple2("finalContract", ` lfdomainapi`.Value.encode(` self`.finalContract)), scala.Tuple2("alreadySigned", ` lfdomainapi`.Value.encode(` self`.alreadySigned)));
      override def fromNamedArguments(` r` : ` rpcvalue`.Record) = if (` r`.fields.length.==(5))
        ` r`.fields(0) match {
          case ` rpcvalue`.RecordField((""| "manager"), _root_.scala.Some(zv0)) => (` lfdomainapi`.Value.decode[` lfdomainapi`.Primitive.Party](zv0) match {
            case _root_.scala.Some(z0) => (` r`.fields(1) match {
              case ` rpcvalue`.RecordField((""| "service_Provider"), _root_.scala.Some(zv1)) => (` lfdomainapi`.Value.decode[` lfdomainapi`.Primitive.Party](zv1) match {
                case _root_.scala.Some(z1) => (` r`.fields(2) match {
                  case ` rpcvalue`.RecordField((""| "money"), _root_.scala.Some(zv2)) => (` lfdomainapi`.Value.decode[` lfdomainapi`.Primitive.Int64](zv2) match {
                    case _root_.scala.Some(z2) => (` r`.fields(3) match {
                      case ` rpcvalue`.RecordField((""| "finalContract"), _root_.scala.Some(zv3)) => (` lfdomainapi`.Value.decode[com.knoldus.Main.Money_Transfer_Agreement](zv3) match {
                        case _root_.scala.Some(z3) => (` r`.fields(4) match {
                          case ` rpcvalue`.RecordField((""| "alreadySigned"), _root_.scala.Some(zv4)) => (` lfdomainapi`.Value.decode[` lfdomainapi`.Primitive.List[` lfdomainapi`.Primitive.Party]](zv4) match {
                            case _root_.scala.Some(z4) => Some(Confirmation_Agreement(z0, z1, z2, z3, z4))
                            case _root_.scala.None => _root_.scala.None
                          })
                          case _ => _root_.scala.None
                        })
                        case _root_.scala.None => _root_.scala.None
                      })
                      case _ => _root_.scala.None
                    })
                    case _root_.scala.None => _root_.scala.None
                  })
                  case _ => _root_.scala.None
                })
                case _root_.scala.None => _root_.scala.None
              })
              case _ => _root_.scala.None
            })
            case _root_.scala.None => _root_.scala.None
          })
          case _ => _root_.scala.None
        }
      else
        _root_.scala.None;
      override def fieldEncoding(lte: ` lfdomainapi`.encoding.LfTypeEncoding): view[lte.Field] = {
        object `view ` extends view[lte.Field] {
          val manager = lte.field("manager", ` lfdomainapi`.encoding.LfEncodable.encoding[` lfdomainapi`.Primitive.Party](lte));
          val service_Provider = lte.field("service_Provider", ` lfdomainapi`.encoding.LfEncodable.encoding[` lfdomainapi`.Primitive.Party](lte));
          val money = lte.field("money", ` lfdomainapi`.encoding.LfEncodable.encoding[` lfdomainapi`.Primitive.Int64](lte));
          val finalContract = lte.field("finalContract", ` lfdomainapi`.encoding.LfEncodable.encoding[com.knoldus.Main.Money_Transfer_Agreement](lte));
          val alreadySigned = lte.field("alreadySigned", ` lfdomainapi`.encoding.LfEncodable.encoding[` lfdomainapi`.Primitive.List[` lfdomainapi`.Primitive.Party]](lte))
        };
        `view `
      };
      override def encoding(lte: ` lfdomainapi`.encoding.LfTypeEncoding)(`view `: view[lte.Field]): lte.Out[_root_.com.knoldus.Main.Confirmation_Agreement] = {
        val `recordFields `: lte.RecordFields[_root_.com.knoldus.Main.Confirmation_Agreement] = lte.RecordFields.xmapN(lte.fields(`view `.manager), lte.fields(`view `.service_Provider), lte.fields(`view `.money), lte.fields(`view `.finalContract), lte.fields(`view `.alreadySigned))({
          case scala.Tuple5(manager, service_Provider, money, finalContract, alreadySigned) => _root_.com.knoldus.Main.Confirmation_Agreement(manager, service_Provider, money, finalContract, alreadySigned)
        })({
          case _root_.com.knoldus.Main.Confirmation_Agreement(manager, service_Provider, money, finalContract, alreadySigned) => scala.Tuple5(manager, service_Provider, money, finalContract, alreadySigned)
        });
        lte.record(` dataTypeId`, `recordFields `)
      }
    }
  }
}
