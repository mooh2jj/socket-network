package socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println(serverSocket + ": 서버 소켓 생성");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println(socket + ": 연결됨");

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            byte[] buf = new byte[1024];
            int count = 0;

            while ((count = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, count);
                System.out.write(buf, 0 , count);
            }
            outputStream.close();
            System.out.println(socket + ": 연결 종료");

            socket.close();
        }
    }
}
