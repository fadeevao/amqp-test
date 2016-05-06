# amqp-test
Simple ticket-request application that uses AMQP to request/issue journey tickets

This is my try to use AMQP protocol is RabbitMQ's implementation. 
The controller receives a journey ticket request, puts it on the queue where it gets picked up and response ticket is generated and sent back. 

Technologies used:
Spring
RabbitMQ
Mockito
MockMVC
