package interfaces;

public interface IEchoClient {
    public void login(String user);
    public void addObserver(IDataReady observer);
    public void connectToServer(String ipAddress, int port);
    public void sendMessage(String message);
    public void closeConnection();    
}
