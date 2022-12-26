package Multicast;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class MulticastFileExchangeClientA {
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

        // 파일 로딩
        File file = new File("src/Multicast/image.jpeg");
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // 파일 데이터 전송
        DatagramPacket sendPacket = null;

        // 파일 이름 전송
        String fileName = file.getName();
        sendPacket = new DatagramPacket(fileName.getBytes(), fileName.length(), multicastAddress, multicastPort);
        try {
            ms.send(sendPacket);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Start sending " + fileName);

        // 파일 전송 시작 태그 전송
        String startSign = "/start";
        sendPacket = new DatagramPacket(startSign.getBytes(), startSign.length(), multicastAddress, multicastPort);
        try {
            ms.send(sendPacket);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // 실제 파일 데이터 전송
        int len;
        byte[] fileData = new byte[2048];
        try {
            while((len=bis.read(fileData)) != -1) {
                sendPacket = new DatagramPacket(fileData, len, multicastAddress, multicastPort);
                ms.send(sendPacket);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // 파일 전송 완료 태그 전송
        String endSign = "/end";
        sendPacket = new DatagramPacket(endSign.getBytes(), endSign.length(),multicastAddress, multicastPort);
        try {
            ms.send(sendPacket);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // 데이터 보낸 후 멀티캐스트 그룹에 조인
        try {
            ms.joinGroup(multicastAddress);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // Client B 가 보낸 데이터 수신
        MulticastDemo.receiveMessage(ms);

        // 멀티캐스티 그룹 나가기
        try {
            ms.leaveGroup(multicastAddress);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // 소켓 닫기
        ms.close();
    }
}
