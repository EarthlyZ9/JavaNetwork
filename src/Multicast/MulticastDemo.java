package Multicast;

import java.io.IOException;
import java.net.*;

public class MulticastDemo {
    public static void main(String[] args) {
        // MulticastSocket 객체 생성
        MulticastSocket ms1 = null;
        MulticastSocket ms2 = null;
        MulticastSocket ms3 = null;

        try {
            ms1 = new MulticastSocket(); // 비어 있는 포트로 자동 바인딩
            ms2 = new MulticastSocket(10000);
            ms3 = new MulticastSocket(new InetSocketAddress(InetAddress.getByName("localhost"), 10000));
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("ms1 is bound to: " + ms1.getLocalSocketAddress());
        System.out.println("ms2 is bound to: " + ms2.getLocalSocketAddress());
        System.out.println("ms3 is bound to: " + ms3.getLocalSocketAddress());
        System.out.println();

        // 주요 메소드
        try {
            System.out.println("TTL: " + ms1.getTimeToLive()); // default = 1
            ms1.setTimeToLive(50);
            System.out.println("TTL: " + ms1.getTimeToLive()); // 50
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        try {
            ms1.joinGroup(InetAddress.getByName("239.0.0.1"));
            ms2.joinGroup(InetAddress.getByName("239.0.0.1"));
            ms3.joinGroup(InetAddress.getByName("239.0.0.1"));

            // ms1 이 전송함
            byte[] sendData = "Hello".getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("239.0.0.1"), 10000);
            ms1.send(sendPacket);

            byte[] receivedData;
            DatagramPacket receivedPacket;

            // 같은 Multicast Group 의 ms2 가 받음
            receivedData = new byte[65508];
            receivedPacket = new DatagramPacket(receivedData, receivedData.length);
            ms2.receive(receivedPacket);
            System.out.println("ms2 received: " + new String(receivedPacket.getData()).trim());
            System.out.println("from: " + receivedPacket.getSocketAddress());

            // 같은 Multicast Group 의 ms3 도 받음
            receivedData = new byte[65508];
            receivedPacket = new DatagramPacket(receivedData, receivedData.length);
            ms3.receive(receivedPacket);
            System.out.println("ms3 received: " + new String(receivedPacket.getData()).trim());
            System.out.println("from: " + receivedPacket.getSocketAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
