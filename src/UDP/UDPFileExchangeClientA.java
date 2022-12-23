package UDP;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class UDPFileExchangeClientA {
    public static void main(String[] args) {
        System.out.println("<<Client A>>");

        // DatagramSocket 객체 생성
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket(10000); // 10000번 포트에 바인딩 됨
            ds.connect(new InetSocketAddress("localhost", 20000)); // 20000번 포트와 연결
        } catch (SocketException e) {
            System.out.println(e.getMessage());
        }

        // 전송할 파일
        File file = new File("src/UDP/image.jpeg");

        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // DatagramPacket 전송
        DatagramPacket sendPacket = null;

        String filename = file.getName();
        sendPacket = new DatagramPacket(filename.getBytes(), filename.getBytes().length); // packet 에는 수신지 정보 없읍

        // 파일 이름 전송
        try {
            ds.send(sendPacket);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // 파일 전송 시작 알림
        String startSign = "/start";
        sendPacket = new DatagramPacket(startSign.getBytes(), startSign.getBytes().length); // packet 에는 수신지 정보 없읍

        try {
            ds.send(sendPacket);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // 실제 파일 데이터 전송
        int len;
        byte[] fileData = new byte[2048]; // 2048 byte 씩 전송
        try {
            // buffered input stream 을 통해 file 을 읽어 2048 바이트씩 전송함
            while ((len=bis.read(fileData)) != -1) {
                sendPacket = new DatagramPacket(fileData, len);
                ds.send(sendPacket);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // 파일 전송 완료 알림
        String endSign = "/end";
        sendPacket = new DatagramPacket(endSign.getBytes(), endSign.getBytes().length);

        try {
            ds.send(sendPacket);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // 파일 전송이 성공적이었다는 메시지를 다른 클라이언트로부터 수신
        byte[] receivedData = new byte[65508];
        DatagramPacket receivedPacket = new DatagramPacket(receivedData, receivedData.length);

        try {
            ds.receive(receivedPacket);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(new String(receivedPacket.getData()).trim());
    }
}
