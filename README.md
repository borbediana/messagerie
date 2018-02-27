#  Messagerie

This is a Spring Boot application. It handles the processing of messages.

## Architecture:
- Java web server, exposing REST API and WebSockets
- Redis to temporary store the messages
- Cassandra DB to persist the messages

## Flow
A user logs into application. Then he can send a message. The message is send on server through REST call. All the other users logged in are receiving the new message through websockets.

When calling create REST enpoint, the message is stored in Redis. A producer thread is reading from Redis and adding messages in queue. A consumer is taking messages from queue and persisting them to Cassandra DB.
