package sample;

import java.net.*;
import java.io.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    @FXML
    Button submitButton;
    @FXML
    TextField name;
    @FXML
    TextField id;
    @FXML
    TextField year;
    @FXML
    TextField gender;


    //Student std = new Student(name.getText(), id.getText(), year.getText());


    public static void sendServer(Student student) {

        int serverPort = 9998;
        Socket socket = null;
        ObjectOutputStream toServer = null;
        ObjectInputStream fromServer = null;
        BufferedReader br = null;

        try {
            InetAddress serverHost = InetAddress.getByName("localhost");
            System.out.println("Connecting to server on port " + serverPort);
            socket = new Socket(serverHost, serverPort);
            System.out.println("Just connected to " + socket.getRemoteSocketAddress());

            toServer = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            Message msgToSend = new Message(student);
            toServer.writeObject(msgToSend);
            toServer.flush();


            // Read data froms server
            // BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // String line;
            // while ((line = in.readLine()) != null)
            //     System.out.println(line);
            fromServer = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            Message msgFromReply = (Message) fromServer.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                System.out.println("ALready closed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

