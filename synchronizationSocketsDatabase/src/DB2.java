import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.*;
import java.util.ArrayList;

public class DB2 {
    public static Connection connectToDatabase() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hungdb", "project_java",
                    "160817");
            System.out.println("Connect to database!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    public static ArrayList<Student> getStudents(){
        ArrayList<Student> students = new ArrayList<>();
        Connection connection = connectToDatabase();
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * from student");
            while(rs.next()){
                students.add(new Student(rs.getInt("id"), rs.getString("name"), rs.getDouble("grade")));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return students;
    }

    public static boolean updateDatabase(Connection conn,int id, String name, double grade) {
        String query = "INSERT INTO student(id, name, grade) VALUE (?, ?, ?)";
        try {
            PreparedStatement st = conn.prepareStatement(query); // create prepared statement
            st.setInt(1, id);
            st.setString(2, name);
            st.setDouble(3, grade);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    public String convertObject2Doc(Student student) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element root = doc.createElement("students");
        doc.appendChild(root);

        root.appendChild(createUser(doc, student.getId(), student.getName(), student.getGrade()));

        // test
        String xmlString = convertDoc2XmlString(doc);
        System.out.println(xmlString);
        return xmlString;
    }

    public static Document convertXmlString2Document(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Node createUser(Document doc, int id, String name, double grade) {
        Element student = doc.createElement("student");

        Element idNode = doc.createElement("id");
        idNode.appendChild(doc.createTextNode(id+""));
        student.appendChild(idNode);

        Element nameNode = doc.createElement("name");
        nameNode.appendChild(doc.createTextNode(name));
        student.appendChild(nameNode);

        Element ageNode = doc.createElement("grade");
        ageNode.appendChild(doc.createTextNode(grade+""));
        student.appendChild(ageNode);

        return student;
    }

    public static String convertDoc2XmlString(Document doc) {
        String output = "";

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = tf.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            output = writer.getBuffer().toString();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return output;
    }

}
