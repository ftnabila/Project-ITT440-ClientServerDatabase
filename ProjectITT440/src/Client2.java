/**
 * @FATIN NABILA RAMLI - PROJECT ITT440 (CLIENT/SERVER DATABASE)
 * 
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {
   private static Socket socket;
   public static void main(String args[]){
    try{
        String host = "localhost"; //172.168.13.250
        int port = 9090;
        InetAddress address = InetAddress.getByName(host);
        socket = new Socket(address, port);
        
        System.out.println("Just connected to " + socket.getRemoteSocketAddress());
        
        Scanner sc = new Scanner(System.in);
        
        //Process of sending message to server
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);
        
        System.out.println("Enter ID: ");
        String msg = sc.nextLine();
        int num = Integer.parseInt(msg);
        
        bw.write(num);
        bw.flush();
        
        System.out.println("Message sent to the server is: " + msg);
        
        //Getting the message replied by server
        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String reply = br.readLine();
        
        System.out.println("-------STUDENT DETAILS------");
        System.out.println(reply + "\t");
    }
    catch (Exception e){
        e.printStackTrace();
    }
    finally{
        //closing the socket
        try{
            socket.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
   }   
}
