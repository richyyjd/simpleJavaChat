import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args){
        final ServerSocket serverSocket ;
        final Socket clientSocket ;
        final BufferedReader in;
        final PrintWriter out;
        final Scanner sc=new Scanner(System.in);
        System.out.println("... Starting Server....................... [ OK ]");

        try {
            serverSocket = new ServerSocket(7000);
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));

            Thread sender= new Thread(new Runnable() {
                String msg; //variable that will contains the data writter by the user
                @Override   // annotation to override the run method
                public void run() {
                    while(true){
                        System.out.println("############### Server listening on port: 7000 #####################");
                        msg = sc.nextLine(); //reads data from user's keybord
                        out.println(msg);    // write data stored in msg in the clientSocket
                        out.flush();   // forces the sending of the data
                    }
                }
            });
            sender.start();

            Thread receive= new Thread(new Runnable() {
                String msg ;
                @Override
                public void run() {
                    try {
                        msg = in.readLine();
                        while(msg!=null){
                            System.out.println("-------------------------------------");
                            System.out.println("Client : "+msg);
                            msg = in.readLine();
                        }

                        System.out.println("Client disconected!!");

                        out.close();
                        clientSocket.close();
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            receive.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
