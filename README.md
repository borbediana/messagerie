#  Messagerie

This is a Spring Boot application. It handles the processing of messages.

## Architecture:
- Java web server, exposing REST API and WebSockets
- Redis to temporary store the messages
- Cassandra DB to persist the messages

## Flow
A user logs into application. Then he can send a message. The message is send on server through REST call. All the other users logged in are receiving the new message through websockets.

When calling create REST enpoint, the message is stored in Redis. A producer thread is reading from Redis and adding messages in queue. A consumer is taking messages from queue and persisting them to Cassandra DB.

## Execution

Prerequisites:
 - Docker version 18.01.0-ce
 - docker-compose version 1.13.0
 - Linux environment (in order to be able to execute run.sh)

Building the Docker image of the application (execute command from current directory):
>docker build . -f dockerfile-app -t messagerie:1

Starting the application and the services it needs (execute command from current directory):
> ./run.sh

**The application becomes accessible at http://localhost:8080**


## REST endpoints
- PUT /create  - creates a new entry in Redis.  It requires a request body, example:
	> {
        "type": "CHAT",
        "content": "Hello from here!",
        "sender": "Caroline"
    }
- GET /persist - removed the last entry from Redis and persists it to Cassandra
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
