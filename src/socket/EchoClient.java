package socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class EchoClient {
    public static void main(String[] args) throws IOException {
        Socket socket = null;
//        socket = new Socket(args[0], Integer.parseInt(args[1]));
        // 소켓을 초기화
        socket = new Socket();
        // 서버에 연결 (localhost:9999)
        socket.connect(new InetSocketAddress("localhost", 9999));
        System.out.println(socket + ": 연결됨");

        // 서버로 데이터를 보내기 위한 출력 스트림 생성
        OutputStream outputStream = socket.getOutputStream();
        // 서버로부터 데이터를 받기 위한 입력 스트림 생성
        InputStream inputStream = socket.getInputStream();

        byte[] buf = new byte[1024]; // 데이터 버퍼
        int count = 0;

        // 표준 입력으로부터 데이터를 읽고 서버에 전송, 서버의 응답을 출력
        while ((count = System.in.read(buf)) != -1) {
            outputStream.write(buf, 0, count); // 서버에 데이터 전송
            count = inputStream.read(buf); // 서버로부터 응답 읽기
            System.out.write(buf, 0, count); // 응답을 콘솔에 출력
        }
        outputStream.close(); // 출력 스트림 닫기

        System.out.println(socket + ": 연결 종료");
        socket.close(); // 소켓 닫기
    }
}
