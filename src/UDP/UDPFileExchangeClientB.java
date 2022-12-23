package UDP;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class UDPFileExchangeClientB {
    public static void main(String[] args) {
        System.out.println("<<Client B>>");

        // DatagramSocket 객체 생성
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket(20000); // 20000번 포트에 바인딩 됨
            ds.connect(new InetSocketAddress("localhost", 10000)); // 10000번 포트와 연결
        } catch (SocketException e) {
            System.out.println(e.getMessage());
        }

        // 파일 이름 수신
        byte[] receivedData = new byte[65508];
        DatagramPacket receivedPacket = new DatagramPacket(receivedData, receivedData.length);

        try {
            ds.receive(receivedPacket);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        String filename = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
        System.out.println("Receiving " + filename + " from port 10000");

        // 수신할 파일
        File file = new File("src/UDP/received_" + filename);


        // 파일을 수신할 스트림
        BufferedOutputStream bos =  null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        String startSign = "/start";
        String endSign = "/end";

        // 파일 수신을 목적으로 receivedData 와 receivedPacket 을 초기화함
        receivedData = new byte[65508];
        receivedPacket = new DatagramPacket(receivedData, receivedData.length);

        try {
            ds.receive(receivedPacket);
            if (new String(receivedPacket.getData(), 0, receivedPacket.getLength()).equals(startSign)) {
                while (true) {
                    ds.receive(receivedPacket);
                    if (new String(receivedPacket.getData(), 0, receivedPacket.getLength()).equals(endSign)) {
                        break;
                    }
                    bos.write(receivedPacket.getData(), 0, receivedPacket.getLength());
                    bos.flush();
                }
            }
            bos.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // 파일 수신을 완료했다는 메시지 전송
        byte[] sendData = "Successfully received data".getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length);
        try {
            ds.send(sendPacket);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
