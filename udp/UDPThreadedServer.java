package udp;

import players.Player;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
class GlobalState{
    public static Player enemy = new Player(0,0,100,0);
}
public class UDPThreadedServer{
    private static final int DEFAULT_PORT = 27000;
    /*setting the packet the MTU(Maximum packet size) for any given packet
    * is very useful for multiplayer games and/or anything receiving real-time
    * data because the packets will be broken up depending on this constraint
    * if not MTU is determined the packet may be too large. If one too small is
    * determined the packets will be broken up and held by the server or client too
    * frequently*/

    private static final short MTU = 4;

    private int port;
    private DatagramSocket server;
    private InetAddress serverIp;
    private String hostName;


    public UDPThreadedServer() throws SocketException {
        this.port = DEFAULT_PORT;
        this.server = new DatagramSocket(DEFAULT_PORT);
    }
    public UDPThreadedServer(int port) throws SocketException{
        this.port = port;
        this.server = new DatagramSocket(this.port);
    }



    public void init() throws IOException {
        //getting the localhost of the current server machine
        serverIp = InetAddress.getLocalHost();
        hostName = serverIp.getHostName();
        System.out.format("Server started on [%s:%d]%n", hostName, port);

        while(true){
            //creating a buffer to hold client data
            byte[] dataBuffer = new byte[MTU];
            //creating a packet in the space of the buffer
            DatagramPacket fromClient = new DatagramPacket(dataBuffer, dataBuffer.length);
            //putting client response into the data packet
            server.receive(fromClient);

            //passing client data to the client handler to be formatted and
            //have its data manipulated and read
            ClientHandler clientHandler = new ClientHandler(fromClient);
            new Thread(clientHandler).start();

        }
    }
}
class ClientHandler implements Runnable{

    private DatagramPacket client;
    public ClientHandler(DatagramPacket client){
        this.client = client;
    }

    private void outputBytes(DatagramPacket fromClient){
        //converting byte data from client into human
        //readable string format and outputting the data
        byte[] buffer = fromClient.getData();
        System.out.format("from-client=> {%d, %d, %d, %d} %n", buffer[0], buffer[1], buffer[2], buffer[3]);
    }

    @Override
    public void run() {
        outputBytes(client);

        GlobalState.enemy.setHealth(GlobalState.enemy.getHealth() - client.getData()[3]);

        byte[] clientBuffer = new byte[4];
        int i = 0;

        for(Float data: GlobalState.enemy.playerData()){
            clientBuffer[i++] = data.byteValue();
        }
        DatagramPacket toClient = new DatagramPacket(clientBuffer, clientBuffer.length, client.getAddress(), client.getPort());
        try {
            DatagramSocket fromServer = new DatagramSocket();
            fromServer.send(toClient);
            fromServer.close();

        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
