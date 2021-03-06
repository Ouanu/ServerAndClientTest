import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ChatClient {
    public static void main(String[] args) throws Exception{
        Socket client = new Socket(InetAddress.getLocalHost(), 7290);
        client.setSoTimeout(10000);
        System.out.println("Started client socket at" +
                client.getLocalSocketAddress());
        BufferedReader buf = new BufferedReader(new InputStreamReader(
                client.getInputStream()
        ));
        PrintStream out = new PrintStream(client.getOutputStream());
        BufferedReader input = new BufferedReader(
                new InputStreamReader(System.in)
        );
        boolean flag = true;
        while (flag) {
            System.out.println("Enter message: ");
            String str = input.readLine();

            out.println(str);
            if ("bye".equals(str)) {
                flag = false;
            } else {
                try {
                    String echo = buf.readLine();
                    System.out.println(echo);
                } catch (SocketTimeoutException e) {
                    System.out.println("Time out, no response");
                }
            }
        }
        input.close();
        if (client != null) {
            client.close();
        }
    }
}
