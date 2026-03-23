# UDP Chat System

This repository contains a complete Java-based UDP Chat System developed for laboratory exercises in
the **Network Programming Course** at **UTM (Technical University of Moldova)**. The project implements
a single-class UDP chat application demonstrating real-time message exchange, private messages, broadcast messages,
and basic concurrency using threads.

## System Architecture

The application works as a simple peer-to-peer chat system using UDP sockets:

- Each client runs the application and binds to a virtual IP and port.
- Messages can be sent either as **general broadcast** to all clients or as **private messages** to a specific client.
- A dedicated thread listens for incoming messages while the main thread handles user input.

Communication is implemented using Java `DatagramSocket` and `DatagramPacket`.

## Application Features

- UDP socket initialization on port **65000**
- Broadcast messages to all participants in the chat segment
- Private messages to a specific IP address
- Real-time message receiving using a dedicated thread
- Username registration for participants
- Graceful shutdown with proper socket cleanup
- Exception handling for message sending and receiving

## Concurrency & Synchronization

- Dedicated receiver thread for incoming messages
- Main thread handles user input simultaneously
- Volatile control flag to manage the application lifecycle
- Proper handling of network and I/O exceptions

## Installation

1. **Clone the repository**

```bash
   git clone https://github.com/Constantin-Stamate/udp-chat-system
```

2. **Navigate to the project directory**

```bash
   cd udp-chat-system
```

3. **Build the server project using Maven**

```bash
   mvn clean install
```

4. **Run the UDP chat application**

```bash
   java -cp target/classes org.udpchat.UDPChat
```

## User Guide

To test and use the **UDP Chat System**, follow these steps:

- Start the application on multiple terminal windows (each simulating a client).
- When prompted, enter a different **username** for each client.
- Enter a virtual **IP** for each client (e.g., `127.0.0.2`, `127.0.0.3`).

## Sending Messages

- To send a **private message**, use the format:
  > `private:<IP>:<Message>`

- To send a **general broadcast message**, just type the message and press **Enter**:
  > `Hello everyone!`

- To exit the chat, type:
  > `exit`

## Message Flow

> ```
> [Client A] ---> Broadcast ---> [All Clients]
> [Client B] ---> Private  ---> [Client C only]
> ```

- General messages are visible to **all participants**.
- Private messages are delivered **only to the specified client**.

## Technologies Used

- Programming Language: Java
- Networking: UDP Sockets
- Concurrency: Java Threads
- Build Tool: Maven
- IDE: IntelliJ IDEA

## Resources

For deeper understanding of the User Datagram Protocol (UDP):

- [RFC 768 – User Datagram Protocol (UDP)](https://datatracker.ietf.org/doc/html/rfc768) — official specification describing UDP’s connectionless model.
- [Cloudflare Learning – What is UDP?](https://www.cloudflare.com/learning/ddos/glossary/udp/) — explanation of UDP features and differences from TCP.

## Author

This project was developed as part of the **Network Programming Course** at **UTM (Technical University of Moldova)**.

- GitHub: [Constantin-Stamate](https://github.com/Constantin-Stamate)
- Email: constantinstamate.r@gmail.com