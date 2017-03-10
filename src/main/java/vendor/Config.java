package vendor;

import java.io.FileInputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Config
{
    /**
     * Instanta obiectului de configurari.
     */
    public static Config instance = null;

    /**
     * Toti parametrii.
     */
    public HashMap<String, String> params;

    /**
     * Creeaza o noua instanta folosind fisierul dat.
     *
     * @param fileName
     */
    private Config(String fileName) {
        params = new HashMap<>();

        this.readParameters(fileName);
    }

    /**
     * Initializeaza instanta folosind un fisier dat.
     *
     * @param fileName
     */
    public static void initialize(String fileName) {
        instance = new Config(fileName);
    }

    /**
     * Returneaza instanta.
     *
     * @return
     */
    public static Config getConfig() {
        return instance;
    }

    /**
     * Citeste parametrii din fisier si stocheaza-i in obiect.
     *
     * @param fileName
     */
    private void readParameters(String fileName) {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new FileInputStream(fileName));

            NodeList nodes = doc.getElementsByTagName("params").item(0).getChildNodes();

            for (int i = 0;i < nodes.getLength();i++) {
                Node node = nodes.item(i);

                String name = node.getNodeName();

                NodeList children = node.getChildNodes();

                for (int j = 0;j < children.getLength();j++) {
                    Node child = children.item(j);

                    if (child.getNodeType() == Node.ELEMENT_NODE) {
                        String value = child.getTextContent();

                        params.put(name + "." + child.getNodeName(), value);
                    }
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException("There was an error reading the config.xml file.");
        }
    }

    /**
     * Returneaza un parametru dupa nume.
     *
     * @param param
     * @return Parametrul sau null daca nu exista.
     */
    public String getParameter(String param) {
        if (!params.containsKey(param)) {
            return null;
        }

        return params.get(param);
    }
}
