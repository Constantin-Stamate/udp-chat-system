package org.udpchat;

import java.net.*;
import java.util.Scanner;

public class UDPChat {

    private static final String BROADCAST_ADDRESS = "255.255.255.255";
    private static final int MESSAGE_PORT = 65000;
    private static DatagramSocket socket;
    private static volatile boolean running = true;
    private static String userName;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        userName = scanner.nextLine();

        System.out.print("Enter the virtual IP of this client (e.g., 127.0.0.2): ");
        String localIP = scanner.nextLine();

        System.out.println("\nInstructions:");
        System.out.println("- Private messages: private:IP:Message");
        System.out.println("  Example: private:127.0.0.3:Hello!");
        System.out.println("- Normal messages will be sent to the general channel.");
        System.out.println("- Type 'exit' to close the client.\n");

        try {
            InetAddress localAddress = InetAddress.getByName(localIP);
            socket = new DatagramSocket(MESSAGE_PORT, localAddress);
            socket.setBroadcast(true);

            Thread receiveThread = new Thread(() -> {
                byte[] buffer = new byte[1024];
                while (running) {
                    try {
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                        socket.receive(packet);
                        String msg = new String(packet.getData(), 0, packet.getLength());

                        if (msg.startsWith("[private]")) {
                            System.out.println("(Privat) " + msg.substring(9));
                        } else {
                            System.out.println("(General) " + msg);
                        }
                    } catch (SocketException se) {
                        break;
                    } catch (Exception e) {
                        if (running) {
                            System.out.println("Error receiving message: " + e.getMessage());
                        }
                    }
                }
            });
            receiveThread.start();

            while (running) {
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("exit")) {
                    running = false;
                    socket.close();
                    break;
                }

                if (input.startsWith("private:")) {
                    String[] parts = input.split(":", 3);
                    if (parts.length == 3) {
                        String targetIP = parts[1];
                        String message = parts[2];
                        sendPrivateMessage(targetIP, message);
                    } else {
                        System.out.println("Invalid private message format. Use: private:IP:Message");
                    }
                } else {
                    sendBroadcastMessage(userName + ": " + input);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void sendBroadcastMessage(String message) {
        try {
            byte[] buffer = message.getBytes();
            InetAddress address = InetAddress.getByName(BROADCAST_ADDRESS);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, MESSAGE_PORT);
            socket.send(packet);
        } catch (Exception e) {
            System.out.println("Error sending general message: " + e.getMessage());
        }
    }

    private static void sendPrivateMessage(String targetIP, String message) {
        try {
            InetAddress address = InetAddress.getByName(targetIP);
            String privateMsg = "[private]" + userName + ": " + message;
            DatagramPacket packet = new DatagramPacket(privateMsg.getBytes(), privateMsg.length(), address, MESSAGE_PORT);
            socket.send(packet);
        } catch (Exception e) {
            System.out.println("Error sending private message: " + e.getMessage());
        }
    }
}