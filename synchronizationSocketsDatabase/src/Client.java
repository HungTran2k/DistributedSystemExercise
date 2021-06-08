
import org.w3c.dom.Document;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.net.Socket;


public class Client {

    public static void main(String[] args) {
        String HOST = "localhost";
        int PORT = 9999;

        Socket socket = null;

        DB1 clientManagement = new DB1();
        // Create Student
        Student std = new Student();
        ArrayList<Student> students = DB1.getStudents();
        try {
            // Connect client with server
            socket = new Socket(HOST, PORT);



            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            //send data to server
            Client c = new Client();
            for(int i = 0; i < students.size(); i++){
                // send data to client

                String output = clientManagement.convertObject2Doc(students.get(i));
                out.writeUTF(output);
            }

            // read data from server
            String receive = "";
            while(receive != null){
                System.out.print("Data from server: ");
                String input = in.readUTF();
                System.out.println(input);

                Document doc = clientManagement.convertXmlString2Document(input);

                int id = Integer.parseInt(doc.getElementsByTagName("id").item(0).getTextContent());
                String name = doc.getElementsByTagName("name").item(0).getTextContent();
                double grade = Double.parseDouble(doc.getElementsByTagName("grade").item(0).getTextContent());

                //Student student = new Student(id, name, grade);
                Connection conn = clientManagement.connectToDatabase();
               if (clientManagement.updateDatabase(conn, id, name, grade)) {
                    System.out.println("Success update database");
                } else {
                  System.out.println("Cannot add student to database");
                }

            }





            // Get output Stream
            //ObjectInputStream in = new ObjectInputStream(socket.getInputStream());


            // read data from server
//            System.out.print("Data from server: ");
//            Student flag = new Student(0, "demo", 0);
//            while(flag != null){
//                Student recieve_std = (Student) in.readObject();
//                System.out.println(recieve_std.toString());
//                Connection conn = connectToDatabase();
//                if (updateDatabase(conn, recieve_std.getId(), recieve_std.getName(), recieve_std.getGrade())) {
//                    System.out.println("Success update database");
//                } else {
//                    System.out.println("Cannot add student to database");
//                }
//            }
//
//
//            // Get input stream
//            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
//            for(int i = 0; i < students.size(); i++){
//                // send data to server
//                out.writeObject(students.get(i));
//            }


            // close stream
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // close the socket
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
