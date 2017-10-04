package client;

import java.io.IOException;


public class EchoClientTester {
  
  //Start the Server on the PORT used below, before running
  public static void main(String[] args) throws IOException, InterruptedException {
    EchoClient client = new EchoClient();
    
    client.addObserver((msg) -> {
      System.out.println("Received a message from server: "+msg);
    });
    client.connectToServer("localhost",8000);
    
    client.sendMessage("Hello", "");
    client.sendMessage("Hello World", "");
    client.sendMessage("Hello Wonderfull World", "");
    Thread.sleep(100);
    client.closeConnection();
    
    System.out.println("DONE");
   
    
  }

  
}
