import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatServer {
    private int port = 7290;

    public ChatServer() {
    }

    public ChatServer(int port) {
        this.port = port;
    }

    public void server() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();

            try {
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                Scanner scanner = new Scanner(System.in);

                while (true) {
                    String accept = inputStream.readUTF();
                    System.out.println(accept);
                    String send = scanner.nextLine();
                    System.out.println("服务器：" + send);
                    outputStream.writeUTF("服务器：" + send);
                }
            } finally {
                socket.close();
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new ChatServer().server();
    }
}
