import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {
    private static final ArrayList<User> userList = new ArrayList<User>();

    public static ArrayList<User> getUserList() {
        return userList;
    }

    private ServerSocket sSocket;

    public Server(){
        init();
    }

    public void init(){
        try{
            sSocket = new ServerSocket(5000);
            System.out.println("Server is running...");
            while(true){
                userList.add(new User(sSocket.accept()));
            }
        }catch (Exception e){
            System.out.println("Server init error");
            System.exit(-1);
        }
    }

    public static void main(String[] args){
        new Server();
    }
}
