package TCP;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class FileExchangeServer {
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

            DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            // 전송 받을 파일 이름
            String receivedFileName = inputStream.readUTF();
            // 전송 받을 위치
            File file = new File("src/TCP/" + "received_" + receivedFileName);

            // 파일을 써야하기 때문에 FileOutputStream 사용
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            // 속도의 개선과 다양한 입출력을 위해 BufferedOutputStream 사용
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

            byte[] data = new byte[2048];
            int len;
            while ((len=inputStream.readInt()) != -1) {
                // data input stream 에서 파일 데이터를 읽어와,
                inputStream.read(data, 0, len);

                // 버퍼 스트림에 write 해주고 flush() 함
                bufferedOutputStream.write(data, 0, len);
                bufferedOutputStream.flush();
            }

            System.out.println("Done receiving file");
            outputStream.writeUTF("File transmission complete");
            outputStream.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
