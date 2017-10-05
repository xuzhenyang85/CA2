package client;

import java.io.IOException;


public class EchoClientTester {
  
  //Start the Server on the PORT used below, before running
  public static void main(String[] args) throws IOException, InterruptedException {
    EchoClient client = new EchoClient();
    
    client.addObserver((msg) -> {
      System.out.println("Received a message from server: "+msg);
    });
    client.connectToServer("127.0.0.1",8081);
    
    client.sendMessage("LOGIN:AAA");
    client.sendMessage("LOGIN:BBB");
    client.sendMessage("MSG:BBB:Hello");
    Thread.sleep(100);
    client.closeConnection();
    
    System.out.println("DONE");
   
    
  }

  
}
