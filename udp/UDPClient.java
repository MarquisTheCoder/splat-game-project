package udp;

import players.Player;

import java.io.IOException;
import java.net.*;

public class UDPClient {

    private int port;
    private InetAddress address;
    private DatagramSocket socket;

    public static void main(String [] args) throws IOException {

        Player player = new Player(2,6,99,10);
        player.setxPosition(2);

        DatagramSocket client = new DatagramSocket();
        InetAddress addr = InetAddress.getByName("localhost");

        byte[] buffer = new byte[4];
        int i = 0;
        System.out.println(player.playerData());
        for(Float data: player.playerData()){
            if(i <= 4){
                buffer[i] = data.byteValue();
                i++;
            }
        }

        DatagramPacket toServer = new DatagramPacket(buffer, buffer.length, addr, 27000);

        client.send(toServer);
        byte[] recvBuffer = new byte[1500];
        DatagramPacket fromServer = new DatagramPacket(recvBuffer, recvBuffer.length);
        client.receive(fromServer);
        System.out.format("Enemy state[UPDATE]: %d, %d, %d, %d",
                recvBuffer[0],
                recvBuffer[1],
                recvBuffer[2],
                recvBuffer[3]);
        client.close();
    }
}
