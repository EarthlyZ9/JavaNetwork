package TCP;

import java.io.IOException;
import java.net.*;

public class SocketDemo {
    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket socket1 = new Socket();
        Socket socket2 = null;
        Socket socket3 = null;
        Socket socket4 = null;
        Socket socket5 = null;

        System.out.println(InetAddress.getLocalHost().toString().split("/")[1]);

        InetAddress local = InetAddress.getByName("127.0.0.1");

        socket2 = new Socket("www.naver.com", 80);
        socket3 = new Socket("www.google.com", 80, local, 5001);
        socket4 = new Socket(InetAddress.getByName("www.naver.com"), 80);
        socket5 = new Socket(InetAddress.getByName("www.naver.com"), 80, local, 5001);

        // 연결 없는 소켓 connect 하기
        System.out.println("socket1: " + socket1.getInetAddress() + ":" + socket1.getInetAddress());
        try {
            socket1.connect(new InetSocketAddress("www.naver.com", 80));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("socket1: " + socket1.getInetAddress() + ":" + socket1.getPort());
        System.out.println("socket2: " + socket2.getInetAddress() + ":" + socket2.getPort());
        System.out.println();

        // 로컬 주소 정보를 지정한 경우와 지정하지 않은 경우
        System.out.println("socket2: " + socket2.getLocalAddress() + ":" + socket2.getLocalPort());
        System.out.println("socket2: " + socket2.getLocalSocketAddress());
        System.out.println("socket3: " + socket3.getLocalAddress() + ":" + socket3.getLocalPort());
        System.out.println("socket3: " + socket3.getLocalSocketAddress());

        // 버퍼 사이즈
        try {
            System.out.println(socket2.getSendBufferSize() + ", " + socket2.getReceiveBufferSize());
        } catch (SocketException e) {
            System.out.println(e.getMessage());
        }
    }
}
