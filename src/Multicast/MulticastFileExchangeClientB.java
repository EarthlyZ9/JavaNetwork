package Multicast;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class MulticastFileExchangeClientB {
    public static void main(String[] args) {
        System.out.println("<<Client B>>");

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

        // 데이터 수신을 위해 멀티캐스트 그룹에 조인
        try {
            ms.joinGroup(multicastAddress);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // 파일 데이터 수신
        byte[] receivedData;
        DatagramPacket receivedPacket;

        // 파일 이름 수신
        receivedData = new byte[65508];
        receivedPacket = new DatagramPacket(receivedData, receivedData.length, multicastAddress, multicastPort);
        try {
            ms.receive(receivedPacket);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        String fileName = new String(receivedPacket.getData()).trim();
        System.out.println("Start receiving: " + fileName);

        // 파일 저장을 위한 파일 출력 스트림
        File file = new File("src/Multicast/received_" + fileName);
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // 데이터 패킷의 내용을 파일에 write
        String startSign = "/start";
        String endSign = "/end";

        receivedData = new byte[65508];
        receivedPacket = new DatagramPacket(receivedData, receivedData.length);
        try {
            ms.receive(receivedPacket);
            if (new String(receivedPacket.getData(), 0, receivedPacket.getLength()).equals(startSign)) {
                while (true) {
                    ms.receive(receivedPacket);
                    if (new String(receivedPacket.getData() ,0, receivedPacket.getLength()).equals(endSign)) {
                        break;
                    }
                    bos.write(receivedPacket.getData(), 0, receivedPacket.getLength());
                    bos.flush();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {
            bos.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("File received successfully");

        // 멀티캐스팅 그룹 나가기
        try {
            ms.leaveGroup(multicastAddress);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // 파일 수신을 완료했다는 메시지 전송
        byte[] sendData = "File received successfully by Client B".getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, multicastAddress, multicastPort);
        try {
            ms.send(sendPacket);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // 소켓 닫기
        ms.close();
    }
}
