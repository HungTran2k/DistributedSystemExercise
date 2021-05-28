import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Staff3 {
    public static void main(String[] args) {
        // Instantiate the Factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File("staff3.xml"));
            NodeList nl = doc.getElementsByTagName("staff");

            System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
            System.out.println("------");

            for (int i = 0; i < nl.getLength(); i++) {
                Node n = nl.item(i);
                System.out.println("Current element: " + n.getNodeName());

                Element element = (Element) n;
                System.out.println("Staff ID: " + element.getAttribute("id"));

                NodeList nodeListFirstName = doc.getElementsByTagName("firstname");
                Node nodeFirstName = nodeListFirstName.item(0);
                Element elementFirstName = (Element) nodeFirstName;
                System.out.println("First name: " + elementFirstName.getTextContent());

                NodeList nodeListLastName = doc.getElementsByTagName("lastname");
                Node nodeLastName = nodeListLastName.item(0);
                Element elementLastName = (Element) nodeLastName;
                System.out.println("Last name: " + elementLastName.getTextContent());

                NodeList nodeListNickName = doc.getElementsByTagName("nickname");
                Node nodeNickName = nodeListNickName.item(0);
                Element elementNickName = (Element) nodeNickName;
                System.out.println("Nickname: " + elementNickName.getTextContent());

                NodeList nodeListSalary = doc.getElementsByTagName("salary");
                Node nodeSalary = nodeListSalary.item(0);
                Element elementSalary = (Element) nodeSalary;
                System.out.println("Salary and Currency: " + elementSalary.getTextContent() + " - " + elementSalary.getAttribute("currency"));

            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

}
