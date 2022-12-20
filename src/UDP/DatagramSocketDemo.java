package UDP;

import java.io.IOException;
import java.net.*;

public class DatagramSocketDemo {
    public static void main(String[] args) throws IOException {
        // 객체 생성
        DatagramSocket ds1 = null, ds2 = null, ds3 = null, ds4 = null;

        try {
            ds1 = new DatagramSocket(); // 가용가능한 포트로 자동 바인딩
            ds2 = new DatagramSocket(10000); // 10000번 포트에 바인드

            // 송신지 정보를 포함하고 있는 객체, 원격지 정보는 포함하고 있지 않음
            ds3 = new DatagramSocket(10001, InetAddress.getByName("localhost"));
            ds4 = new DatagramSocket(new InetSocketAddress("localhost", 10002));
        } catch (UnknownHostException | SocketException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("ds1 is bound to: " + ds1.getLocalAddress() + ":" + ds1.getLocalPort());
        System.out.println("ds2 is bound to: " + ds2.getLocalAddress() + ":" + ds2.getLocalPort());
        System.out.println("ds3 is bound to: " + ds3.getLocalAddress() + ":" + ds3.getLocalPort());
        System.out.println("ds4 is bound to: " + ds4.getLocalAddress() + ":" + ds4.getLocalPort());

        // 원격지 주소 정보 출력
        System.out.println("ds4 is connected to: " + ds4.getInetAddress() + ":" + ds4.getPort()); // null:-1

        // connect
        try {
            ds4.connect(new InetSocketAddress("localhost", 10003));
        } catch (SocketException e) {
            System.out.println(e.getMessage());
        }

        // 원격지 주소 정보 출력
        System.out.println("ds4 is connected to: " + ds4.getInetAddress() + ":" + ds4.getPort()); // connect 되었으므로 원격지 주소 출력됨

        // disconnect
        ds4.disconnect();
        System.out.println();

        byte[] data1 = "수신지 주소가 없는 데이터그램 패킷".getBytes();
        byte[] data2 = "수신지 주소가 있는 데이터그램 패킷".getBytes();

        // 수신지 주소가 없는 DatagramPacket
        DatagramPacket dp1 = new DatagramPacket(data1, data1.length);

        // 수신지 주소가 있는 DatagramPacket
        DatagramPacket dp2 = new DatagramPacket(data2, data2.length, new InetSocketAddress("localhost", 10002));

        // 데이터 보내기
        try {
            // DatagramSocket 에 원격지 주소가 없고 보내는 DatagramPacket 에도 수신지 주소가 없읍 => 보낼 수 없음
            // ds1.send(dp1); -> NullPointerException
            // ds2.send(dp1); -> NullPointerException
            // ds3.send(dp1); -> NullPointerException

            InetSocketAddress destination = new InetSocketAddress("localhost", 10002);
            ds1.connect(destination);
            ds2.connect(destination);
            ds3.connect(destination);

            // DatagramPacket 에 수신지 주소가 없어도 DatagramSocket 에 원격지 주소가 있기 때문에 전송 가능
            ds1.send(dp1);
            ds2.send(dp1);
            ds3.send(dp1);
            ds1.disconnect();
            ds2.disconnect();
            ds3.disconnect();

            // DatagramSocket 에는 원격지 주소가 없으나 DatagramPacket 에 수신지 주소가 있음 -> 전송 가능
            ds1.send(dp2);
            ds2.send(dp2);
            ds3.send(dp2);

            ds3.connect(destination);
            ds3.send(dp2); // DatagramSocket 의 원격지 주소 정보와 DatagramPacket 의 수신지 주소가 같아야만 보낼 수 있음 (아니라면 IllegalArgumentException)
            ds3.disconnect();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // 보낸 데이터 받아보기 - 현재까지 7번 send 함
        byte[] receivedData = new byte[65508];
        DatagramPacket dp = new DatagramPacket(receivedData, receivedData.length); // 데이터가 없는 빈 packet
        try {
            for (int i = 0; i < 7; i++) {
                // 포트 10002 에 바인딩 되어 있는 ds4 객체를 이용해서 받음
                ds4.receive(dp);
                System.out.println("Received data from: " + dp.getAddress() + ":" + dp.getPort() + " -> " + new String(dp.getData()).trim());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println();

        // 송수신 버퍼 크기 관련
        try {
            System.out.println("Send buffer size " + ds1.getSendBufferSize() + ", Receive buffer size " + ds1.getReceiveBufferSize());
        } catch (SocketException e) {
            System.out.println(e.getMessage());
        }

    }
}
