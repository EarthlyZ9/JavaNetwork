package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class UDPTextExchangeClientB {
    public static void main(String[] args) {
        System.out.println("<<Client B>>");

        // DatagramSocket 생성 후 바인딩
        DatagramSocket ds = null;
        try { ds = new DatagramSocket(20000); } catch (SocketException e) {
            System.out.println(e.getMessage());
        }

        // 데이터 수신
        byte[] receivedData = new byte[65508];
        DatagramPacket receivedPacket = new DatagramPacket(receivedData, receivedData.length); // 수신을 위한 빈 Packet
        try {
            ds.receive(receivedPacket);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Received data: " + new String(receivedPacket.getData()).trim());

        // 데이터 생성 + 수신지 정보를 가진 DatagramPacket
        byte[] sendData = "Welcome".getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, new InetSocketAddress("127.0.0.1", 10000));

        // 데이터 전송
        try {
            System.out.println("Sending data: " + new String(sendPacket.getData()).trim());
            ds.send(sendPacket);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
