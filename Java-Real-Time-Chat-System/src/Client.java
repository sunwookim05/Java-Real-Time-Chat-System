import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.Buffer;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private Scanner sc;
    private BufferedReader reader;
    private PrintWriter writer;

    public Client(){
        init();
    }

    public void init(){
        try {
            socket = new Socket("localhost", 5000);
            System.out.println("Client is running...");
            sc = new Scanner(System.in);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());

            readerExecute();
            String input = "";
            while(true){
                System.out.print("to other >> ");
                input = sc.nextLine();
                if(input.contains("disconnet")) socket.close();
                writer.println(input);
                writer.flush();
            }
        }catch (Exception e){
            System.out.println("Client init error");
            System.exit(-1);
        }
    }

    public void readerExecute(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                String temp;
                while(true){
                    try {
                        temp = reader.readLine();
                        if(temp.isEmpty()) continue;
                        System.out.println("from other >> " + temp);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public static void main(String[] args){
        new Client();
    }
}
