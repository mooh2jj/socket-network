package socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadEchoServer extends Thread {

    private Socket socket = null;

    // 생성자: 클라이언트 소켓을 받아 초기화
    public MultiThreadEchoServer(Socket socket) {
        this.socket = socket;
    }

    // 스레드가 실행되는 메서드
    public void run() {
        System.out.println(socket + ": 연결됨"); // 클라이언트 연결 로그 출력
        try {
            InputStream fromClient = socket.getInputStream(); // 클라이언트에서 데이터 수신
            OutputStream toClient = socket.getOutputStream(); // 클라이언트로 데이터 송신

            byte[] buf = new byte[1024]; // 데이터 버퍼
            int count = 0;
            while ((count = fromClient.read(buf)) != -1) { // 데이터 읽기
                toClient.write(buf, 0, count); // 클라이언트로 데이터 전송
                System.out.write(buf, 0, count); // 서버 콘솔에 데이터 출력
            }

        } catch (IOException e) {
            System.out.println(socket + ": 연결종료 (" + e + ")"); // 연결 종료 로그 출력
        } finally {
            if (socket != null) { // 소켓이 null이 아닌 경우
                try {
                    socket.close(); // 소켓 닫기
                    socket = null; // 소켓을 null로 설정
                } catch (IOException e) {
                    e.printStackTrace(); // 예외 스택 트레이스 출력
                }
            }
        }
    }

    public static void main(String[] args) throws NumberFormatException, IOException {
        ServerSocket serverSocket = new ServerSocket(5001); // 서버 소켓 생성
        System.out.println(serverSocket + ": 서버 소켓 생성"); // 서버 소켓 생성 로그 출력
        while (true) {
            Socket client = serverSocket.accept(); // 클라이언트 연결 대기
            MultiThreadEchoServer myServer = new MultiThreadEchoServer(client); // 새 스레드 생성
            myServer.start(); // 스레드 시작
        }
    }
}
