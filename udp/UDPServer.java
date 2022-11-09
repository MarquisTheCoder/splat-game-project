package udp;
import java.net.*;
import java.io.*;

public class UDPServer {
    public static void main(String[] args) throws IOException{
        DatagramSocket server = new DatagramSocket(4160);
        byte[] buffer = new byte[256];
        DatagramPacket fromClient = new DatagramPacket(buffer, buffer.length);
        server.receive(fromClient);
        String clientResponse = new String(fromClient.getData());
        System.out.println("Client response: " + clientResponse);
        server.close();

    }
}
