package Address;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class SocketAddressDemo {
    public static void main(String[] args) throws IOException {
        InetAddress ia = InetAddress.getByName("www.google.com");
        int port = 10000;

        // 생성자로 객체 생성
        InetSocketAddress isa1 = new InetSocketAddress(port);
        InetSocketAddress isa2 = new InetSocketAddress("www.google.com", port);
        InetSocketAddress isa3 = new InetSocketAddress(ia, port);

        System.out.println("isa1: " + isa1);
        System.out.println("isa2: " + isa2);
        System.out.println("isa3: " + isa3);
        System.out.println();

        System.out.println(isa2.getAddress());
        System.out.println(isa2.getHostName());
        System.out.println(isa2.getPort());
    }
}
