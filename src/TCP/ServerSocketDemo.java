package TCP;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ServerSocketDemo {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket1 = null;
        ServerSocket serverSocket2 = null;

        try {
            serverSocket1 = new ServerSocket();
            serverSocket2 = new ServerSocket(20000);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Is serverSocket1 bound to a port? " + serverSocket1.isBound());
        System.out.println("Is serverSocket2 bound to a port? " + serverSocket2.isBound());
        System.out.println();

        // bind
        try {
            serverSocket1.bind(new InetSocketAddress("127.0.0.1", 10000));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Is serverSocket1 bound to a port? " + serverSocket1.isBound());
        System.out.println("Is serverSocket2 bound to a port? " + serverSocket2.isBound());
        System.out.println();

        // 사용중인 포트 확인하기 65536
        for(int i=0; i<10000; i++) {
            try {
                ServerSocket serverSocket = new ServerSocket(i);
            } catch(IOException e) {
                System.out.println(i+"번째 포트 사용중 ...");
            }
        }

        System.out.println();

        // client 로 부터 TCP 접속 대기
        try {
            serverSocket1.setSoTimeout(2000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        try {
            // 반환받은 소켓의 inputstream 과 outputstream 을 이용
            Socket socket = serverSocket1.accept();
        } catch (IOException e) {
            try {
                System.out.println(serverSocket1.getSoTimeout() + "ms 시간이 지나 listening 종료");
            } catch (IOException e1) {
                System.out.println(e1.getMessage());
            }
        }
    }
}
