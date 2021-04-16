package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Statement;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(loader.load());

        primaryStage.setTitle("DSExercise");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        /*Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 700));
        primaryStage.show();*/
    }


    public static void main(String[] args) {
         String url2 = "jdbc:mysql://localhost:3306/hungdb";
         String username = "project_java";
         String password = "160817";
         Connection conn2 = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn2 = DriverManager.getConnection(url2, username, password);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        if (conn2 != null) {
            System.out.println("Connected to the database");
        }

        int port = 9999;
        ServerSocket socket = null;
        System.out.println("Server is running on port " + port);
        try {
            socket = new ServerSocket(port);
            while (true) {
                Socket client = socket.accept();
                ObjectInputStream in = new ObjectInputStream(client.getInputStream());

                Student std = (Student) in.readObject();
                System.out.println(std);

                String query = "INSERT INTO student (name, id, year, gender) VALUE ('" + std.getName() + "', '"
                        + std.getId() + "', '" + std.getYear() + "', '" + std.getGender() + "');";

                Statement statement = conn2.createStatement();
                statement.executeUpdate(query);

                client.close(); // close connecttion with server
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("run this");
                e.printStackTrace();
            }
        }
    }

}
