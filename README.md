# kneebay
A neworked marketplace whose knees won't buckle!

## Objectives
* You can develop a distributed application in Java that uses Java RMI or Java IDL (CORBA) for inter-process communication.
* You know how to define remote interfaces using java.rmi.Remote; how to implement the interface(s); how to develop a server that serves as a container for remote objects; you know to bind/lookup remote object by names using the RMI naming service rmiregistry; you know how to run an RMI application.
* You know how to define a remote interface in IDL; how to generate a Java interface from its IDL definition using idlj compiler; how to implement the interface and develop a server-container for remote CORBA objects; how to use COS naming service tnameserv; how to run a CORBA application developed with Java IDL.

## Tasks:
* You are to solve in Java the following problem. The homework can be done in a group of 2 students.
* Homework should be presented and demonstrated to a course instructor during a lab session on the date it is due.
* Bonus policy: If you report a homework on time and your solution is accepted, you will get 3% bonus on your first ID2212 exam. The amount of bonus  can be reduced for errors and/or poor design of the application.


## Necessary requirements (for full bonus and to pass)
1. Client remote interface for callbacks
** There must be RMI callbacks to clients through the client remote interface (when a "wish" item becomes available, and when an item, which the client is selling, is sold) as described in the problem specification. Note that the server should not have to look up the client in the rmi registry to be able to make the callback. Instead, the client should have sent the server a reference to itself.
Problem, A networked marketplace using RMI (Java RMI or Java IDL)

## Overview
Develop a client-server distributed application in Java for trading things (items) on a networked marketplace. Clients (traders) and a server (marketplace) communicate using Remote Method Invocations (implemented with Java RMI or Java IDL). A server represents the marketplace and provides a remote interface that allows clients to (un)register at the marketplace, to sell (i.e. to place items for sale) and to buy items, and to inspect what items are available on the marketplace. An item is identified by its name and price, e.g. "camera" for 3000 SEK. If a client buys an item, the seller should be notified by a call-back via the client's remote interface. The server also allows a client to place a "wish" to purchase a item for a specified price. When a matching item becomes available at the marketplace for the price not higher than the specified wished price, the interested client should be notified by a call-back via the client's remote interface.

Each client and the server have an access to the RMI bank server considered on Exercise 2 (Bank RMI or bankIDL), and Lab 2. Clients are identified by names. The client can open an account in the bank to be used when the client sells or buys things on the marketplace. The server deposits an amount to the client's account when the client's thing has been sold. The server withdraws an amount from the client's account when the client purchases an item. The purchase can be rejected if the client does not have sufficient balance to pay the price.
