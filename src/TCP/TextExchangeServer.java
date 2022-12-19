package TCP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TextExchangeServer {
    public static void main(String[] args) {
        System.out.println("<<Server>>");
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(10000);
        } catch (IOException e) {
            System.out.println("Port 10000 is already in use");
            System.exit(0);
        }
        System.out.println("Waiting for client to connect ...");
        try {
            // 연결을 수락하고 연결된 socket 객체를 반환함
            Socket socket = serverSocket.accept();
            System.out.println("Accepted connection!");
            System.out.println("Connected Client: " + socket.getInetAddress() + ":" + socket.getPort());

            // 속도의 개선과 다양한 입출력을 위해 BufferStream 과 DataStream 사용
            DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            String received = inputStream.readUTF();
            System.out.println("Client sent: " + received);

            outputStream.writeUTF("Welcome");
            outputStream.flush();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
