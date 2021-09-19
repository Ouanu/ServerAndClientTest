import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ChatClient {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket(InetAddress.getLocalHost(), 7290);
        System.out.println("Started client socket at" +
                socket.getLocalSocketAddress());
        BufferedReader socketReader = new BufferedReader(new InputStreamReader(
                socket.getInputStream()
        ));
        BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(
                socket.getOutputStream()
        ));
        BufferedReader consoleReader = new BufferedReader(
                new InputStreamReader(System.in)
        );

        String promptMsg = "Please enter a message (Bye to quit):";
        String outMsg = null;

        System.out.println(promptMsg);
        while ((outMsg = consoleReader.readLine()) != null) {
            if (outMsg.equalsIgnoreCase("bye")) {
                break;
            }

            socketWriter.write(outMsg);
            socketWriter.write("\n");
            socketWriter.flush();

            String inMsg = socketReader.readLine();
            System.out.println("Server: " + inMsg);
            System.out.println();
            System.out.println(promptMsg);
        }
        socket.close();
    }
}
