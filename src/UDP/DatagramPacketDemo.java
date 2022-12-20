package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class DatagramPacketDemo {
    public static void main(String[] args) throws IOException {
        // 송신할 데이터
        byte[] buf = "UDP-데이터그램패킷".getBytes();

        // DatagramPacket 객체 생성 - 수신지 미포함
        DatagramPacket dp1 = new DatagramPacket(buf, buf.length); // offset 0
        DatagramPacket dp2 = new DatagramPacket(buf, 4, buf.length - 4); // offset 4 -> "데이터그램패킷"

        // DatagramPacket 객체 생성 - 수신지 포함: InetAddress
        DatagramPacket dp3 = null, dp4 =null;

        try {
            dp3 = new DatagramPacket(buf, buf.length, InetAddress.getByName("localhost"), 10000);
            dp4 = new DatagramPacket(buf, 4, buf.length -4, InetAddress.getByName("localhost"), 10000);
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        }

        // DatagramPacket 객체 생성 - 수신지 포함: SocketAddress
        DatagramPacket dp5 = null, dp6 = null;
        dp5 = new DatagramPacket(buf, buf.length, new InetSocketAddress("localhost", 10000));
        dp5 = new DatagramPacket(buf, 4, buf.length - 4, new InetSocketAddress("localhost", 10000));

        // 주요 메소드
        System.out.println("dp1 의 원격지 IP: " + dp1.getAddress()); // null
        System.out.println("dp1 의 원격지 Port: " + dp1.getPort()); // -1

        // System.out.println("dp1 의 SocketAddress: " + dp1.getSocketAddress()); // IllegalArgumentException

        System.out.println("dp3 의 원격지 IP: " + dp3.getAddress());
        System.out.println("dp3 의 원격지 Port: " + dp3.getPort());
        System.out.println("dp3 의 원격지 SocketAddress: " + dp3.getSocketAddress());
        System.out.println();

        System.out.println("dp1 원본 데이터: " + new String(dp1.getData())); // "UDP-데이터그램패킷"
        System.out.println("dp2 원본 데이터: " + new String(dp2.getData())); // "UDP-데이터그램패킷"
        System.out.println("dp2 실제 사용 데이터: " + new String(dp2.getData(), dp2.getOffset(), dp2.getLength())); // "데이터그램패킷"
        System.out.println();

        // 데이터 재설정
        dp1.setData("안녕하세요".getBytes());
        System.out.println("dp1 새 데이터: " + new String(dp1.getData()));

    }
}
