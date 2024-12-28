package socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        // 서버 소켓을 포트 9999에서 생성
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println(serverSocket + ": 서버 소켓 생성");

        // 무한 루프로 클라이언트 연결을 대기
        while (true) {
            // 클라이언트 연결 대기
            Socket socket = serverSocket.accept();
            System.out.println(socket + ": 연결됨");

            // 클라이언트로부터 데이터를 받기 위한 입력 스트림 생성
            InputStream inputStream = socket.getInputStream();
            // 클라이언트로 데이터를 보내기 위한 출력 스트림 생성
            OutputStream outputStream = socket.getOutputStream();

            byte[] buf = new byte[1024]; // 데이터 버퍼
            int count = 0;

            // 클라이언트로부터 데이터를 읽고 다시 클라이언트로 전송
            while ((count = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, count);
                System.out.write(buf, 0 , count); // 서버 콘솔에 데이터 출력
            }
            // 출력 스트림 닫기
            outputStream.close();
            System.out.println(socket + ": 연결 종료");

            // 클라이언트 소켓 닫기
            socket.close();
        }
    }
}
