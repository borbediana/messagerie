#  Messagerie

This is a Spring Boot application. It handles the processing of messages. The application has:
- API layer
- business/service layer

Usually, such an application would have also a repository layer. The simplicity of the application allowed me to skip over it.


## Architecture:
- Java web server, exposing REST API and WebSockets
- Redis to temporary store the messages
- Cassandra DB to persist the messages

## Flow
A user logs into application. He can send a message. The message is sent on server through REST call. All the other users logged in are receiving the new message through websockets.

When the message created from UI is first stored in Redis. A producer thread is reading from Redis and adding messages in queue. A consumer is taking messages from queue and persisting them to Cassandra DB.

## Execution

Prerequisites:
 - Docker version 18.01.0-ce
 - docker-compose version 1.13.0
 - Linux environment (in order to be able to execute run.sh)

Building the Docker image of the application (execute command from current directory):
>docker build . -f dockerfile-app -t messagerie:1

Starting the application and the services it needs (execute command from current directory):
> ./run.sh

**Why running a script instead starting the application also with docker-compose? The answer is because Cassandra. The official Cassandra image doesn't support script initialization in order to prepare the database for connections. So, I had to decouple the Cassandra initialization from the application startup.**

**The application becomes accessible at http://localhost:8080**


## REST endpoints
- PUT /create  - creates a new entry in Redis. It requires body, example:
	> {
        "type": "CHAT",
        "content": "Hello from here!",
        "sender": "Caroline"
    }
- GET /persist - removes the last entry from Redis and persists it to Cassandra
- GET /all - gets all the entries from Cassandra
- GET /all/redis - gets all the entries from Redis
- DELETE /clean/redis - removes all the entries from Redis

## WebSockets
 -     sendMessage - when a user sends a message, all the others are notified
	 - @MessageMapping("/chat.sendMessage")
	 - @SendTo("/topic/public")
 -     addUser - when a user logges in, all the others are notified
	- @MessageMapping("/chat.addUser")
	- @SendTo("/topic/public")


> Credit to https://github.com/callicoder/spring-boot-websocket-chat-demo for big part of the UI
