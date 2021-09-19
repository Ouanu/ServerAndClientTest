import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    public static void main(String[] args) throws Exception {
//        ServerSocket serverSocket = new ServerSocket(7290, 100, InetAddress.getLocalHost());
        ServerSocket serverSocket = new ServerSocket(7290);
        System.out.println("Server started at: " + serverSocket);


        while (true) {
            System.out.println("Waiting for a connection......");

            final Socket activeSocket = serverSocket.accept();

            System.out.println("Received a connection from " + activeSocket);
            Runnable runnable = () -> handleClientRequest(activeSocket);
            new Thread(runnable).start();
        }
    }

    public static void handleClientRequest(Socket socket) {
        try {
            BufferedReader socketReader = null;
            BufferedWriter socketWriter = null;
            BufferedReader consoleReader = null;
            socketReader = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()
            ));
            socketWriter = new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream()
            ));
            consoleReader = new BufferedReader(new InputStreamReader(
                    System.in
            ));

            String inMsg = null;

            while ((inMsg = socketReader.readLine()) != null) {
                System.out.println("Received from client: " + inMsg);

                String outMsg = inMsg;
                socketWriter.write(outMsg);
                socketWriter.write("\n");
                socketWriter.flush();
            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
