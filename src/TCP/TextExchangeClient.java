package TCP;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TextExchangeClient {
    public static void main(String[] args) {
        System.out.println("<<Client>>");
        Socket socket = null;
        try {
            // 서버 IP 와 포트에 연결 요청 - 객체 생성하자마자 연결 요청
            socket = new Socket(InetAddress.getByName("localhost"), 10000);
            System.out.println("Connected to Server");

            // 서버 주소 및 포트 출력
            System.out.println("Server Address: " + socket.getInetAddress() + ":" + socket.getPort());
            System.out.println();

            // 속도의 개선과 다양한 입출력을 위해 BufferStream 과 DataStream 사용
            DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            outputStream.writeUTF("Hi");
            outputStream.flush();

            String received = inputStream.readUTF();

            System.out.println("Server sent: " + received);
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
