import org.w3c.dom.Document;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;

public class Server {


    public static void main(String[] args) {
        ServerSocket server = null;

        DB2 serverManagement = new DB2();
        // Create Object Student
        Student std = new Student();
        ArrayList<Student> students = serverManagement.getStudents();
        try {
            // init and config server run port 9999
            int PORT = 9999;
            server = new ServerSocket(PORT);
            System.out.println("Server runing on port " + PORT);

            while (true) {
                Socket client = server.accept(); // accept connect from client
                System.out.println(std);

                // Get input stream of Client

                DataInputStream in = new DataInputStream(client.getInputStream());
                DataOutputStream out = new DataOutputStream(client.getOutputStream());



//                ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
//
//                // write data to client
//                for(int i = 0; i < students.size(); i++){
//                    // send data to client
//                    out.writeObject(students.get(i));
//                }
//                //out.writeObject(std);
//
//                // Get output stream
//                ObjectInputStream in = new ObjectInputStream(client.getInputStream());
//                // recice data from client
//
//                System.out.print("Object from client: ");
//                Student flag = new Student(0, "demo", 0);
//                while(flag != null){
//                    Student recieve_std = (Student) in.readObject();
//                    System.out.println(recieve_std.toString());
//                    Connection conn = connectToDatabase();
//                    if (updateDatabase(conn, recieve_std.getId(), recieve_std.getName(), recieve_std.getGrade())) {
//                        System.out.println("Success update database");
//                    } else {
//                        System.out.println("Cannot add student to database");
//                    }
//                }

                //send data to client
                Server s = new Server();
                for(int i = 0; i < students.size(); i++){
                    // send data to client

                    String output = serverManagement.convertObject2Doc(students.get(i));
                    out.writeUTF(output);
                }


                //receive data from client
                String receive = "";
                while(receive != null){
                    System.out.print("Data from client: ");
                    String input = in.readUTF();
                    System.out.println(input);

                    Document doc = serverManagement.convertXmlString2Document(input);

                    int id = Integer.parseInt(doc.getElementsByTagName("id").item(0).getTextContent());
                    String name = doc.getElementsByTagName("name").item(0).getTextContent();
                    double grade = Double.parseDouble(doc.getElementsByTagName("grade").item(0).getTextContent());

                    Student student = new Student(id, name, grade);
                    Connection conn = serverManagement.connectToDatabase();
                    if (serverManagement.updateDatabase(conn, id, name, grade)) {
                        System.out.println("Success update database");
                    } else {
                        System.out.println("Cannot add student to database");
                    }

                }

                // close connect with client
                client.close();

                // close stream
                in.close();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // close connect with servewr
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
