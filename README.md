# amqp-test
Simple ticket-request application that puts requests on the queue and then processes them

This is my attempt to use RabbbitMQ to deal with the requests coming from the API. The setup is quite simple - there is one queue to which, using different routing keys, ticket requests and payments are sent. 

The flow:
1.  Ticket request is coming in, it's processed and a response containing a ticket price is sent back
2.  Each price information object is generated with an id which is then stored in an in-memory (hsqldb) database to later check whether the payment is sufficient
3.  Payment is supplied and the client is given back a ticket. At that point that ticket entry is removed from the in-memory database. If payment amount is not enough, request for more money is sent back. If payment is greater than required then change is sent back along with the ticket


Technologies used:
Spring, ,RabbitMQ, Mockito,MockMVC

