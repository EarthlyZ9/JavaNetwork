package Stream;

import java.io.*;

public class IOStreamReaderWriter {
    public static void main(String[] args) {
        InputStream in = System.in;
        // byte 대신 char 형태로 읽을 수 있음
        InputStreamReader reader = new InputStreamReader(in);

        OutputStream out = System.out;
        OutputStreamWriter writer = new OutputStreamWriter(out);

        // 한 개 이상의 값을 받아올 수 있음
        // 하지만, 고정된 길이의 배열을 통해 받아오기 때문에 공간의 낭비가 생기거나 공간이 부족할 수 있음
        char[] cdata = new char[2];
        try {
            reader.read(cdata);
            int intData = cdata[0] - '0'; // 아스키코드로 반환되기 때문에 0 (48) 을 빼줌

            writer.write("입력받은 값: ");
            writer.write(cdata);
            writer.write("\n");
            writer.write("입력받은 첫번째 값 + 10 = ");
            writer.write(intData + 10 + "\n");

            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
