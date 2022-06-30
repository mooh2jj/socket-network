package socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadEchoServer extends Thread {

    private Socket socket = null;

    public MultiThreadEchoServer(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        System.out.println(socket + ": 연결됨");
        try {
            InputStream fromClient = socket.getInputStream();
            OutputStream toClient = socket.getOutputStream();

            byte[] buf = new byte[1024];
            int count = 0;
            while ((count = fromClient.read(buf)) != -1) {
                toClient.write(buf, 0, count);
                System.out.write(buf, 0, count);
            }

        } catch (IOException e) {
            System.out.println(socket + ": 연결종료 (" + e + ")");
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                    socket = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws NumberFormatException, IOException {
        ServerSocket serverSocket = new ServerSocket(5001);
        System.out.println(serverSocket + ": 서버 소켓 생성");
        while (true) {
            Socket client = serverSocket.accept();
            MultiThreadEchoServer myServer = new MultiThreadEchoServer(client);
            myServer.start();
        }
    }
}
