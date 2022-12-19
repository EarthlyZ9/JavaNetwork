package Stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOStream {
    public static void main(String[] args) {
        InputStream in = System.in;
        OutputStream out = System.out;

        int inputData = 0;

        try {
           inputData = in.read();
           out.write(inputData);
           out.flush();
           out.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
