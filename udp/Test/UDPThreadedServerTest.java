package udp.Test;

import udp.UDPThreadedServer;

import java.io.IOException;
import java.net.SocketException;

public class UDPThreadedServerTest {
    public static void main(String[] args) throws IOException {
        UDPThreadedServer udpThreadedServer = new UDPThreadedServer(27000);
        udpThreadedServer.init();
    }
}
