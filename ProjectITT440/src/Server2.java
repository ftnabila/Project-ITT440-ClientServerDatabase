/**
 *
 * @FATIN NABILA RAMLI - PROJECT ITT440 (CLIENT/SERVER DATABASE)
 */
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import java.io.*;
import java.net.*;


public class Server2 {
    private static Socket socket;
    public static void main(String args[]){ // throws IOException
        try{
            int port = 9090;
            //int num = 0;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server Started and listening to port " + port);
            
            //Server is running always. This is done using this while(true) loop
            while(true){
                Database db = new Database();
                
                //Reading message sent from client
                socket = serverSocket.accept();
                //System.out.println("TESTING");
               
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                int message = br.read();
                System.out.println("Message sent from Client is: " + message);

                //Connect to MongoDB server
                MongoClient mongo = new MongoClient("localhost",27017);

                /*** Get Database ***/
                // if the database doesn't exists, MongoDB will create it
                MongoDatabase mydatabase = mongo.getDatabase("ITT440");

                /*** Get collection/table from ITT440 ***/
                //if collection doesn't exists, MongoDB will create it
                FindIterable<Document> mydatabaserecords = mydatabase.getCollection("Student").find();
                MongoCursor<Document> iterator = mydatabaserecords.iterator();


                while(iterator.hasNext()){
                    Document doc = iterator.next();
                    int id = doc.getInteger("id");
                    String name = doc.getString("name");
                    String state = doc.getString("state");
                    if (message == id){
                        db = new Database(id, name, state);
                    }
                   
                }

                int ID = db.getId();
                String nama = db.getName();
                String st = db.getState();
                
                System.out.println("Student ID: " + ID);
                System.out.println("Name: " + nama);
                System.out.println("State: " + st );
                
                String reply = ID + " " + nama + " " + st;
                
                //Sending the response back to the client.
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);
                bw.write(reply);
                bw.flush();
                break;
            }      
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
