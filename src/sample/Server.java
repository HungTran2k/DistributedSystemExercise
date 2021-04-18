package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class Server {
    public static void main(String[] args) {
        int serverPort = 9998;
        ServerSocket serverSocket = null;
        ObjectOutputStream toClient = null;
        ObjectInputStream fromClient = null;
        String url2 = "jdbc:mysql://localhost:3306/hungdb";
        String username = "project_java";
        String password = "160817";

        try{
            serverSocket = new ServerSocket(serverPort);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Just connected to " + socket.getRemoteSocketAddress());

                toClient = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                fromClient = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                Message msgRequest = (Message) fromClient.readObject();
                Student student = msgRequest.getStudent();
                try {
                    String insertQuery = "INSERT INTO student (name, id, year, gender) VALUES (?, ?, ?, ?) ";
                    Connection connection = DriverManager.getConnection(url2, username, password);
                    if(connection != null) {
                        System.out.println("Connected");
                    }
                    PreparedStatement statement = connection.prepareStatement(insertQuery);
                    statement.setString(1, student.getName());
                    statement.setString(2, student.getId());
                    statement.setString(3, student.getYear());
                    statement.setString(4, student.getGender());
                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
