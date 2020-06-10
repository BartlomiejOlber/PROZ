# Checkers Game Multiplayer
## Authors: Adrian Prorok, Bartlomiej Olber

This is a classic multiplayer checkers game written in Java and created as the final project of the "Event driven programming" subject 
at Warsaw University of Technology. The rules are very simple. The goal of each player is to beat all the opponent's pawns. 
When the opponent has no pawn left, the player wins the game. To move a pawn, click on it with the left mouse button, and then select the 
target square to which you want to move.

### Screenshots from the game:
![Screenshot_number_1](https://github.com/BartlomiejOlber/PROZ/blob/master/docs/doc-files/Screenshot_number_1.PNG)
![Screenshot_number_2](https://github.com/BartlomiejOlber/PROZ/blob/master/docs/doc-files/Screenshot_number_2.PNG)
![Screenshot_number_3](https://github.com/BartlomiejOlber/PROZ/blob/master/docs/doc-files/Screenshot_number_3.PNG)
![Screenshot_number_4](https://github.com/BartlomiejOlber/PROZ/blob/master/docs/doc-files/Screenshot_number_4.PNG)

Peer-to-peer game. Written using Model-View-Controller pattern. Communication between server and client sides is based on java.NIO
	tools. User's interface is designed as event-driven.
      The event listener is waiting for user's actions and allows to handle them. Thanks to this, the other components of the program can obtain, 
        process and update game status information.
       The use of the Swing library enabled us to graphically present our entire application.
	Both sides are multithreaded. For client side threads synchronization BlockingQueues provided in Java Concurrent library. 
	The sequence diagram below shows briefly the way Controllers'
        threads communicate with each other.
        
### Sequence diagram:        
![Sequence Diagram](https://github.com/BartlomiejOlber/PROZ/blob/master/docs/doc-files/sequence_diagram.png)  
   	   
In the project we also use Jackson ObjectMapper class as serializer/deserializer of Java objects to JSON format text and vice versa.
	The Mapper is included as dependency in pom.xml file.
	The communication between server and client sides is based on Message subsclasses. Polymorphic Requests and Responses are sent/received between the communicating sides. Below is shown Message class diagram. 
	
### Messages class diagram:	
![Messages class diagram](https://github.com/BartlomiejOlber/PROZ/blob/master/docs/doc-files/edu.proz.checkers.infrastructure.png)
	 

Following the principles taken from the MVC software design pattern, we divided our application into three cooperating modules.
        Below is a diagram that presents them, shows the classes contained in each module and describes the cooperation between the modules.
        
### MVC diagram        
![MVC Diagram](https://github.com/BartlomiejOlber/PROZ/blob/master/docs/doc-files/MVC_diagram.PNG)

## Usage 
### INet address and port configuration
  src/main/resources json files
### Build
 ```
mvn clean install -U
 ```
### Starting the game

1. Start server
```
./run
```
2. Start the first player
```
./client
```
3. Start the second player
```
./client
```
4. To start another game simultaneously or over again repeat 2 and 3

### Documentation and brief overview 
Open docs/index.html in web browser
	


