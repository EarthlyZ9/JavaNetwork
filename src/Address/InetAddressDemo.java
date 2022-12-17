package Address;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;

public class InetAddressDemo {
    public static void main(String[] args) throws IOException {

        // 원격지 IP 의 InetAddress 객체 생성
        InetAddress ia1 = InetAddress.getByName("www.google.com");
        InetAddress ia2 = InetAddress.getByAddress(new byte[] {(byte) 172, (byte) 217, (byte) 161, (byte) 132});
        InetAddress ia3 = InetAddress.getByAddress("www.google.com", new byte[] {(byte) 172, (byte) 217, (byte) 161, (byte) 132});

        System.out.println("ia1: " + ia1);
        System.out.println("ia2: " + ia2);
        System.out.println("ia3: " + ia3);

        // 로컬 루프백 IP
        InetAddress ia4 = InetAddress.getLocalHost();
        InetAddress ia5 = InetAddress.getLoopbackAddress();

        System.out.println("ia4: " + ia4);
        System.out.println("ia5: " + ia5);

        // 하나의 호스트가 여러개의 IP 를 가지고 있는 경우
        InetAddress[] ia6 = InetAddress.getAllByName("www.naver.com");
        System.out.println("ia6: " + Arrays.toString(ia6));
        System.out.println();

        // InetAddress 메소드
        byte[] googleAddressInBytes = ia1.getAddress();
        System.out.println("google address in bytes: " + Arrays.toString(googleAddressInBytes));
        System.out.println("google host address in String: " + ia1.getHostAddress());
        System.out.println("google host name: " + ia1.getHostName());
        System.out.println();

        System.out.println("Is google address reachable? " + ia2.isReachable(2000));
        System.out.println("Is google address a loopback ip? " + ia1.isLoopbackAddress());
        System.out.println("Is google address a multicast ip? " + ia1.isMulticastAddress());

        System.out.println("Is 127.0.0.1 a loopback ip? " + InetAddress.getByAddress(new byte[] {127, 0, 0, 1}).isLoopbackAddress());
        System.out.println("Is 255.255.255.255 a multicast ip? " + InetAddress.getByAddress(new byte[] {(byte) 255, (byte) 255, (byte) 255, (byte) 255}).isMulticastAddress());
    }
}
