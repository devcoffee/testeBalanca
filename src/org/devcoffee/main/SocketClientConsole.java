package org.devcoffee.main;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SocketClientConsole {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String serverAddress = "localhost";
    private int port = 12345;

    public SocketClientConsole() {

        try {
            socket = new Socket(serverAddress, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String response;
            while (true) {
                response = in.readLine();
                if (response == null) {
                    break;
                }
                System.out.println(response);
                writeToFile(response);
                Thread.sleep(1000); // Sleep for 1 second
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeToFile(String data) {
        String fileName = "output.txt";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = sdf.format(new Date());
        try (FileWriter fw = new FileWriter(fileName, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(timestamp + " - " + data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new SocketClientConsole();
    }
}
