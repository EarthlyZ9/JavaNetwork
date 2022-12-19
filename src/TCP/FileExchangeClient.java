package TCP;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class FileExchangeClient {
    public static void main(String[] args) {
        System.out.println("<<Client>>");
        try {
            Socket socket = new Socket(InetAddress.getByName("localhost"), 10000);
            System.out.println("Connected to Server");
            System.out.println("Server Address: " + socket.getInetAddress() + ":" + socket.getPort());

            DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            File file = new File("src/TCP/image.jpeg");

            // BufferInputStream 으로 파일을 읽어서 DataOutputStream 에 write 해줌
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            System.out.println("Send file: " + file.getName());

            // 1. 파일 이름 전송
            outputStream.writeUTF(file.getName());

            // 2. 파일 데이터 전송 (2048 바이트 씩)
            byte[] data = new byte[2048];
            int len;
            while ((len=bufferedInputStream.read(data)) != -1) {
                outputStream.writeInt(len);
                outputStream.write(data, 0, len);
                outputStream.flush();
            }

            // 3. 파일의 마지막임을 암시하는 -1 전송
            outputStream.writeInt(-1);
            outputStream.flush();

            // 4. 파일수신을 완료했다는 서버의 메시지를 받음
            String received = inputStream.readUTF();
            System.out.println("Server sent: " + received);
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
