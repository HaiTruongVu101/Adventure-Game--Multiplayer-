package Characters;

import java.io.*;
import java.net.Socket;
import javax.swing.JOptionPane;

public class javaClient implements Runnable {
    private Socket client;
    private DataOutputStream out;
    private DataInputStream in;
    private volatile String lastReceived = "";
    private String id;

    public String getID() {
        return id;
    }

    public static javaClient createOrNull() {
        String[] options = {"Single Player", "Multiplayer"};
        int mode = JOptionPane.showOptionDialog(
            null,
            "Choose game mode:",
            "Game Mode",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );
        if (mode == 1) {                // Multiplayer
            try {

                // You can prompt for IP/port here if you want
                return new javaClient("LocalHost", 6784);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Could not connect to server. Closing the app");
                System.exit(0);
                return null;
            }
        } else {
            return null;                // Single player mode
        }
    }
    

    public javaClient(String serverIp, int port) throws IOException {
        client = new Socket(serverIp, port);
        out = new DataOutputStream(client.getOutputStream());
        in = new DataInputStream(client.getInputStream());
        id = String.valueOf(client.getLocalPort());
        new Thread(this).start();
    }
    
    public void sendMessage(String msg) throws IOException {
        out.writeUTF(msg);
    }

        public String getLastReceived() {
        return lastReceived;
    }

@Override
public void run() {
    try {
        while (true) {
            String msg = in.readUTF();
            lastReceived = msg;
        }
    } catch (IOException e) {
        
    }
}

    public void close() throws IOException {
        client.close();
    }
}
