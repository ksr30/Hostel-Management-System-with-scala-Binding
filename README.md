# Hostel Mangement System

The objective of the Proposed System is to improve Hostel Services provided by 3rd Party Service Provider by using the Immutable Smart Contracts. 
     Before transferring money to Service Provider, Manager initiates Confirmation-Contact with hostlers as a party to know their feedback. If everyone approves only then Manager can initiate Money-Transfer Contract with Service Provider.

## Approach            

![DAML-Use-Case-Page-1 (1)](https://user-images.githubusercontent.com/40355376/78567284-142cb080-783e-11ea-985c-7660ac069cc6.jpg)



## Building
To Compile the project:
```bash
daml build
```
## Generate Scala classes representing DAML contract templates
```
$ daml codegen scala
```
## Start Sandbox
This examples requires a running sandbox. To start it, run the following command:
```
$ daml sandbox ./.daml/dist/quickstart-0.0.1.dar
```
where `./.daml/dist/quickstart-0.0.1.dar` is the DAR file created in the previous step.

## Compile and run Scala example
Run the following command from the `quickstart-scala` folder:
```
$ sbt "application/runMain com.knoldus.IouMain localhost 6865"
```
If example completes successfully, the above process should terminate and the output should look like this:
```
<Sent and received messages>
...
11:54:03.865 [run-main-0] INFO - Shutting down...
...
[success] Total time: 7 s, completed Sep 12, 2019, 11:54:04 AM
```

## Test

```bash
daml damlc -- test --files daml/Tests/MainTest.daml

```
## Running Scenarios
Open visual studio
Click on Scenario.daml
Now click on:
```bash
Scenario results
```



## Scenario Covered

1. Manager initiates and submits Confirmation-Contract.
2. Each involved Party Approves the Confirmation-Contract.
3. After everyone's approval, the Manager can initiate Money-Transfer Contract with Service Provider.  