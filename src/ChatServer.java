import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    public static void main(String[] args) throws Exception {
//        ServerSocket serverSocket = new ServerSocket(7290, 100, InetAddress.getLocalHost());
        ServerSocket serverSocket = new ServerSocket(7290);
        System.out.println("Server started at: " + serverSocket);

        Socket client = null;
        boolean checked = true;
        while(checked) {
            client = serverSocket.accept();
            System.out.println("connect with client......");
            new Thread(new ServerThread(client)).start();
        }
        serverSocket.close();
    }

    static class ServerThread implements Runnable {

        private Socket client = null;

        public ServerThread(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                PrintStream out = new PrintStream(client.getOutputStream());
                BufferedReader buf = new BufferedReader(new InputStreamReader(
                        client.getInputStream()
                ));
                boolean flag = true;
                while (flag) {
                    String str = buf.readLine();
                    if (str == null || "".equals(str)) {
                        flag = false;
                    } else {
                        if ("bye".equals(str)) {
                            flag = false;
                        } else {
                            out.println("echo:" + str);
                            System.out.println("echo:" + str);
                        }
                    }
                }
                out.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
