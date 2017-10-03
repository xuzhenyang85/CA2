package server;

import java.io.*;
import java.net.*;
import java.util.Date;

public class ServerClientThread extends Thread {

    private Socket clientSocket;

    public ServerClientThread(Socket s) {
        clientSocket = s;
        start();
    }

    @Override
    public void run() {
        System.out.println("Server log: Client connected...");

        try {
            PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            toClient.println("Welcome...");

            String clientInput;

            while ((clientInput = fromClient.readLine()) != null) {
                System.out.println("Server log: Received input - " + clientInput);

                if (clientInput.startsWith("UPPER#")) {
                    System.out.println("Server log: Asked for uppercased...");

                    toClient.println("UPPERCASED..." + clientInput.substring(6, clientInput.length()).toUpperCase());
                } else if (clientInput.startsWith("LOWER#")) {
                    System.out.println("Server log: Asked for lowercased...");

                    toClient.println("LOWERCASED..." + clientInput.substring(6, clientInput.length()).toLowerCase());
                } else if (clientInput.startsWith("REVERSE#")) {
                    System.out.println("Server log: Asked for REVERSE...");
                    String result = clientInput.substring(6, clientInput.length()).toLowerCase();
                    String newresult = "";
                    for (int i = (result.length() - 1); i >= 0; i--) {
                        newresult += result.charAt(i);
                    }
                    toClient.println(newresult);
                } else if (clientInput.startsWith("TRANSLATE#")) {
                    System.out.println("Server log: Asked for translate...");
                    
                    if ((clientInput.substring(10, clientInput.length())).equals("hund")) {
                        toClient.println("TRANSLATE... dog");
                    }
                    if ((clientInput.substring(10, clientInput.length())).equals("kat")) {
                        toClient.println("TRANSLATE... cat");
                    }
                    if ((clientInput.substring(10, clientInput.length())).equals("fisk")) {
                        toClient.println("TRANSLATE... fish");
                    }
                    
                } else if (clientInput.equals("TIME#")) {
                    System.out.println("Server log: Asked for the time...");

                    toClient.println("THE TIME IS " + new Date() + "...");
                }
                else{
                    toClient.println("Wrong word");
                }
            }
        } catch (Exception e) {
            System.out.println("Server log: Problem with Communication Server...");
        }

    }
}
