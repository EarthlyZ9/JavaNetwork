package Multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class MulticastTextExchangeClientA {
    public static void main(String[] args) {
        System.out.println("<<Client A>>");

        // 멀티캐스팅 주소지
        InetAddress multicastAddress = null;
        try {
            multicastAddress = InetAddress.getByName("234.234.234.234");
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        }

        // 멀티캐스팅 포트
        int multicastPort = 10000;

        // MulticastSocket 생성
        MulticastSocket ms = null;
        try {
            ms = new MulticastSocket(multicastPort);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // 멀티캐스트 그룹 조인
        try {
            ms.joinGroup(multicastAddress);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // 전송할 데이터를 담을 데이터그램 패킷 생성 + 전송
        byte[] sendData = "Hello from Client A".getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, multicastAddress, multicastPort);

        try {
            ms.send(sendPacket);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // 데이터 그램 패킷 수신 - 멀티캐스트 그룹에 송신된 데이터 받기
        receiveMessage(ms); // 본인이 보낸 메시지 수신
        receiveMessage(ms); // Client B 가 보낸 메시지 수신

        // 멀티캐스트 그룹 나가기
        try {
            ms.leaveGroup(multicastAddress);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // 소켓 닫기
        ms.close();
    }

    static void receiveMessage(MulticastSocket ms) {
        byte[] receivedData;
        DatagramPacket receivedPacket;
        receivedData = new byte[65508];
        receivedPacket = new DatagramPacket(receivedData, receivedData.length);

        try {
            ms.receive(receivedPacket);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Sent from: " + receivedPacket.getSocketAddress());
        System.out.println("Got: " + new String(receivedPacket.getData()).trim());
    }
}
