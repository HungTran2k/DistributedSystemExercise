package sample;

import java.net.*;
import java.io.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller{

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


    public void submit() {
        Student std = new Student(name.getText(), id.getText(), year.getText(), gender.getText());
        System.out.println(std);

        Socket socket = null;
        int serverPort = 9999;
        try {
            socket = new Socket("127.0.0.1", serverPort);

            // Read data froms server
            // BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // String line;
            // while ((line = in.readLine()) != null)
            //     System.out.println(line);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(std);

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

