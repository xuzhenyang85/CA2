package server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler extends Thread {

    Scanner reader;
    PrintWriter writer;
    Socket socket;
    EchoServer master;
    String clientName = "Anonym Bruger";

    public ClientHandler(Socket socket, EchoServer master) throws IOException {
        this.socket = socket;
        this.master = master;
        reader = new Scanner(socket.getInputStream());
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        this.master.addClientHandler(this);
        this.master.PrintUserList();
        while (true) 
        {
            //når loppet kører for evigt, vil vi gerne have reader.nextLine() skal blokkere, indtil der kommer en besked ind via socket'en
            String message = reader.nextLine(); //IMPORTANT blocking call
            
            //hvis beskeden indeholder STOP, vil vi gerne ødelægge loopet
            if (message.equals("STOP")) {
                break;
            }
            
            //hvis beskeden indeholder LOGIN, vil vi gerne sætte klientens navn
            if (message.startsWith("LOGIN:") && clientName == "Anonym Bruger") 
            {
                clientName = message.substring(6, message.length());
                writer.println(clientName + " er netop logget ind.");
            } 
            
            //Opgave 2, hvis klientens besked indeholder MSG, vil klienten gerne sende besked til specifikke andre klienter
            else if (message.startsWith("MSG:")) 
            {
                // klienterne der skal sendes en besked til sorteret fra MSG
                String receivers = message.split(":")[1];
                // alle klienter der skal sendes en besked til
                String[] clientNames = receivers.split(",");
                // beskeden der skal sendes til alle klienter
                String messageToSend = message.split(":")[2];
                // send beskeden til klienterne
                SendToClients(messageToSend, clientNames);
                
            } else if (message.startsWith("LOGOUT:")) 
            {
                String clientToLogout = message.split(":")[1];
                // vi mangler en metode i master, der kan hente en klient via klientns navn, og herefter close connection og remove.
            } else
            {
                master.echoMessageToAll(clientName, message);
                message = reader.nextLine();
            }
//            System.out.println("Received " + message);
//            String[] MsgParts = message.split(":");
//
//            System.out.println(MsgParts[0]);

//            if (MsgParts[0].toUpperCase().equals("LOGIN")) {
//                if (clientName.equals("Anonym Bruger")) {
//                    clientName = MsgParts[1];
//                    this.master.PrintUserList();
//                }
//            }
//
//            if (checkLogin()) {
//                if (MsgParts[0].toUpperCase().equals("MSG") & MsgParts.length == 3) {
//                    if (MsgParts[1].equals("*")) {
//                        master.echoMessageToAll(MsgParts[2], "");
//                    }
//                    if (clientName.equals(MsgParts[1])) { // messege to other user
//
//                    }
//                }
//            }
//            message = reader.nextLine();
//        }
//        try {
//            this.master.removeClientHandler(this);
//            socket.close();
//        } catch (IOException ex) {
//            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
        }
    }

    void sendMessage(String fromClient, String msg) 
    {
        // vi ønsker at printe klientens navn ud i alle beskeder
        writer.println(fromClient + ": " + msg);
    }
    // denne metode sender en besked til klienter der indeholder i array'et clientNames
    private void SendToClients(String msg, String[] clientNames)
    {
        for (String clientName : clientNames) 
        {
            // denne metode sender besked til en klient ad gangen
            master.echoMessageToClient(msg, clientName, this.clientName);
        }
    }

    public boolean checkLogin() {
        if (!clientName.equals("Anonym Bruger")) {
            return true;
        } else {
            sendMessage("You must login first");
            return false;
        }
    }

    void sendMessage(String msg)
    {
        writer.println(msg);
    }

}
