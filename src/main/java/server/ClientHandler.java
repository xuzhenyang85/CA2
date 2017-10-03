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
      //  String clientName;

        public ClientHandler(Socket socket, EchoServer master) throws IOException {
            this.socket = socket;
            this.master = master;
            reader = new Scanner(socket.getInputStream());
            writer = new PrintWriter(socket.getOutputStream(), true);
        }

        @Override
        public void run() {
            this.master.addClientHandler(this); 
            this.master.UpdateUserList();
            String message = reader.nextLine(); //IMPORTANT blocking call
            System.out.println("Received " + message);
            while (!message.equals("stop")) {
                master.echoMessageToAll(message);
                message = reader.nextLine();
            }
//            if(message.startsWith("LOGIN:")){
//               if (clientName != null){
//                 clientName = .
//               }
//            }
            try {
                this.master.removeClientHandler(this);
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        void sendMessage(String msg) {
            writer.println(msg);
        }

}
