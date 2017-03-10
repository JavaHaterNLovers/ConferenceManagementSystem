package vendor;

import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Container
{
    /**
     * Instanta containerului.
     */
    private static Container instance = null;

    /**
     * Toate serviciile existente, citite din fisierul de servicii.
     */
    private HashMap<String, Object[]> existingServices;

    /**
     * Serviciile care au fost initializate.
     */
    private HashMap<String, Object> services;

    /**
     * Instanta configurarii.
     */
    private Config config;

    /**
     * Creeaza un nou container cu fiserul dat si configurarea data.
     *
     * @param fileName
     * @param config
     */
    private Container(String fileName, Config config) {
        this.services = new HashMap<>();
        this.existingServices = new HashMap<>();
        this.config = config;

        this.readServices(fileName);
    }

    /**
     * Initializeaza instanta folosind un fisier dat.
     *
     * @param fileName
     */
    public static void initialize(String fileName) {
        instance = new Container(fileName, Config.getConfig());
    }

    /**
     * Returneaza instanta.
     *
     * @return
     */
    public static Container getContainer() {
        return instance;
    }

    /**
     * Citeste serviciile dintr-un fisier si salveaza-le pentru a putea fi initializate mai tarziu.
     *
     * @param fileName
     */
    public void readServices(String fileName) {
        ArrayList<String> tempServices = new ArrayList<>();

        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new FileInputStream(fileName));
            NodeList nodes = doc.getDocumentElement().getChildNodes();
            for (int i = 0;i < nodes.getLength();i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) node;

                    String id = this.getServiceId(el);
                    String className = this.getServiceClass(el, id);

                    ArrayList<Object> argsList = new ArrayList<>();
                    argsList.add(className);

                    // Verifica daca serviciul va fi instantiat folosind un factory sau nu.
                    if (!this.setServiceFactoryCall(el, argsList, id)) {
                        this.setServiceArguments(el, argsList, tempServices);
                        this.setServiceCalls(el, argsList, id);
                    }

                    if (this.existingServices.containsKey(id)) {
                        throw new RuntimeException("A service with id " + id + " already exists.");
                    }

                    this.existingServices.put(id, argsList.toArray());
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException("There was an error loading the services.xml file.");
        }

        for (String s : tempServices) {
            if (!this.existingServices.containsKey(s)) {
                throw new RuntimeException("A service has a dependency on anoher service with id " + s + " which does not exists.");
            }
        }
    }

    /**
     * Returneaza id-ul unui serviciu sau arunca exceptie daca nu exista.
     *
     * @param el
     * @return
     */
    private String getServiceId(Element el) {
        Attr idAttr = el.getAttributeNode("id");

        if (idAttr == null) {
            throw new RuntimeException("Every service needs to have an id.");
        }

        return idAttr.getValue();
    }

    /**
     * Returneaza clasa unui serviciu sau arunca exceptie daca nu exista.
     *
     * @param el
     * @param id
     * @return
     */
    private String getServiceClass(Element el, String id) {
        Node classNode = el.getElementsByTagName("class").item(0);

        if (classNode == null) {
            throw new RuntimeException("The service with id " + id + " does not have a class.");
        }

        return classNode.getTextContent();
    }

    /**
     * Seteaza apelul la metoda unui serviciu factory daca acest serviciu provine dintr-un factory.
     *
     * @param el
     * @param argsList
     * @param id
     * @return
     */
    private boolean setServiceFactoryCall(Element el, ArrayList<Object> argsList, String id) {
        Node callNode = el.getElementsByTagName("call").item(0);

        if (callNode != null && callNode.getNodeType() == Node.ELEMENT_NODE) {
            Element callNodeEl = (Element) callNode;
            Attr method = callNodeEl.getAttributeNode("method");

            if (method == null) {
                throw new RuntimeException("The service with id " + id + " has a call section but does not have a method.");
            }

            ArrayList<Object> callArgs = new ArrayList<>();

            this.setServiceFactoryCallArguments(callNodeEl, callArgs);

            argsList.add(new Object[]{method.getValue(), callArgs.toArray()});

            return true;
        }

        return false;
    }

    /**
     * Seteaza argumentele pentru metoda factory-ului.
     *
     * @param el
     * @param callArgs
     */
    private void setServiceFactoryCallArguments(Element el, ArrayList<Object> callArgs) {
        NodeList list = el.getChildNodes();

        if (list != null) {
            ArrayList<Object> tempArgs = new ArrayList<>();

            boolean first = true;
            for (int j = 0;j < list.getLength();j++) {
                Node n = list.item(j);
                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) n;

                    String arg = e.getTextContent().trim();

                    if (arg.startsWith("%") && arg.endsWith("%")) {
                        arg = config.getParameter(arg.substring(1, arg.length() - 1));
                    }

                    if (first) {
                        callArgs.add(arg);
                        first = false;
                    } else {
                        tempArgs.add(arg);
                    }
                }
            }

            callArgs.add(tempArgs.toArray());
        }
    }

    /**
     * Seteaza argumentele serviciului si stocheaza serviciile gasite ca argument si seteaza orice parametrii gasiti.
     *
     * @param el
     * @param argsList
     * @param tempServices
     */
    private void setServiceArguments(Element el, ArrayList<Object> argsList, ArrayList<String> tempServices) {
        Node argsNode = el.getElementsByTagName("args").item(0);

        if (argsNode != null) {
            NodeList list = argsNode.getChildNodes();

            if (list != null) {
                for (int j = 0;j < list.getLength();j++) {
                    Node n = list.item(j);
                    if (n.getNodeType() == Node.ELEMENT_NODE) {
                        String s = n.getTextContent().trim();
                        if (s.startsWith("@") && !this.existingServices.containsKey(s.substring(1))) {
                            if (tempServices.contains(s)) {
                                tempServices.add(s);
                            }
                        } else if (s.startsWith("%") && s.endsWith("%")) {
                            s = config.getParameter(s.substring(1, s.length() - 1));
                        }
                        argsList.add(s);
                    }
                }
            }
        }
    }

    /**
     * Seteaza metodele care vor trebui apelate pe serviciu, daca ele exista.
     *
     * @param el
     * @param argsList
     * @param id
     */
    private void setServiceCalls(Element el, ArrayList<Object> argsList, String id) {
        Node callsNode = el.getElementsByTagName("calls").item(0);

        if (callsNode != null) {
            NodeList list = callsNode.getChildNodes();

            if (list != null) {
                for (int j = 0;j < list.getLength();j++) {
                    Node n = list.item(j);
                    if (n.getNodeType() == Node.ELEMENT_NODE) {
                        Element e = (Element) n;
                        Attr method = e.getAttributeNode("method");

                        if (method == null) {
                            throw new RuntimeException("The service with id " + id + " has a calls section but a call does not have a method.");
                        }

                        String m = method.getValue();
                        String arg = e.getTextContent().trim();

                        argsList.add(new Object[]{m, arg});
                    }
                }
            }
        }
    }

    /**
     * Returneaza un obiect dupa id din container.Daca obiectul exista deja el va fi returnat din bufferul intern, daca nu el va fi initializat.
     * Un serviciu nu este initializat pana nu este folosit prima data.
     *
     * @param id
     * @return
     */
    public Object get(String id) {
        if (!this.services.containsKey(id)) {
            return this.instantiateService(id);
        }

        return this.services.get(id);
    }

    /**
     * Initializeaza un serviciu dupa id daca acesta exista.
     *
     * @param id
     * @return
     */
    private Object instantiateService(String id) {
        if (!this.existingServices.containsKey(id)) {
            throw new RuntimeException("The service with id " + id + " does not exists.");
        }

        Object[] args = this.existingServices.get(id);
        String className = (String) args[0];

        // Verificam daca serviciul provine dintr-un factory.
        if (className.startsWith("@")) {
            return this.instantiateServiceFromFactory(id, className.substring(1), (Object []) args[1]);
        }

        Class<?> cls;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("The class " + className + " does not exists for service with id " + id);
        }
        args = Arrays.copyOfRange(args, 1, args.length);

        int index = this.instantiateServiceArguments(args);

        Object obj = this.instantiateServiceFromConstructor(cls, Arrays.copyOfRange(args, 0, index), id, className);

        this.callServiceMethods(index, obj, args, id, className);

        return obj;
    }

    /**
     * Initializeaza un serviciu dintr-un factory.
     *
     * @param id
     * @param className
     * @param method
     * @return
     */
    private Object instantiateServiceFromFactory(String id, String className, Object[] method) {
        Object service = this.get(className);
        Object[] arg = (Object[]) method[1];

        Object obj = null;
        for (Method m : service.getClass().getMethods()) {
            if (m.getName().equals(method[0])) {
                try {
                    obj = m.invoke(service, arg);
                    break;
                } catch (Exception ex) {
                }
            }
        }

        if (obj == null) {
            throw new RuntimeException("Service with id " + id + " could not be instantiated from factory service " + className);
        }

        this.services.put(id, obj);

        return obj;
    }

    /**
     * Initializeaza argumentele unui serviciu.Daca serviciul are ca si argument alte servicii, acestea vor fi initializate mai intai.
     * Returneaza index-ul la care s-a oprit instantierea daca argumentele trebuie injectate folosind setteri.
     *
     * @param args
     * @return
     */
    private int instantiateServiceArguments(Object[] args) {
        int index = 0;
        for (index = 0;index < args.length;index++) {
            Object o = args[index];

            if (!(o instanceof String)) {
                break;
            }

            String s = (String) o;
            if (s.startsWith("@")) {
                args[index] = this.get(s.substring(1));
            }
        }

        return index;
    }

    /**
     * Instantiaza un serviciu folosind constructorele lui.
     *
     * @param cls
     * @param ctargs
     * @param id
     * @param className
     * @return
     */
    private Object instantiateServiceFromConstructor(Class<?> cls, Object[] ctargs, String id, String className) {
        Object obj = null;

        for (Constructor<?> ctor : cls.getConstructors()) {
            try {
                obj = ctor.newInstance(ctargs);
                break;
            } catch (Exception ex) {
                obj = null;
            }
        }

        if (obj == null) {
            throw new RuntimeException("Service with id " + id + " and class " + className + " could not be instantiated.");
        } else {
            this.services.put(id, obj);
        }

        return obj;
    }

    /**
     * Apeleaza metodele necesare asupra serviciului dupa ce acesta a fost initializat.
     *
     * @param index
     * @param obj
     * @param args
     * @param id
     * @param className
     */
    private void callServiceMethods(int index, Object obj, Object[] args, String id, String className) {
        if (index != args.length) {
            for (int j = index;j < args.length;j++) {
                Object[] method = (Object[]) args[j];

                if (method[1] instanceof String) {
                    String s = (String) method[1];

                    if (s.startsWith("@")) {
                        method[1] = this.get(s.substring(1));
                    }
                }

                boolean ok = false;
                for (Method m : obj.getClass().getMethods()) {
                    if (m.getName().equals(method[0])) {
                        try {
                            m.invoke(obj, method[1]);
                            ok = true;
                            break;
                        } catch (Exception ex) {

                        }
                    }
                }

                if (!ok) {
                    throw new RuntimeException("Could not call method " + method[0] + " on service with id " + id + " and class " + className);
                }
            }
        }
    }
}
