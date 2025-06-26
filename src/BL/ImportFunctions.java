package BL;

import DA.DatabaseQueries;
import PL.WorkplaceGoups.WorkplaceXMLRecord;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.sql.SQLException;

import java.util.*;

public class ImportFunctions {
    public static final String WORKPLACE_FILE_PATH = "Kapstellen.xml"; ;

    private ImportFunctions() {}

    public static List<WorkplaceGroup> createGroups() throws SQLException {
        List<WorkplaceGroup> workplaceGroups = new ArrayList<>();
        TreeMap<Integer,Workplace> workplaces=importDatabaseRecords();
        try {
            List<WorkplaceXMLRecord> workplaceXMLRecords = importWorkplacesXMLFromFile(WORKPLACE_FILE_PATH);
            // Initialize workplace names and groups from XML records
            for (WorkplaceXMLRecord record : workplaceXMLRecords) {
                String groupName = record.getGroupName();
                if (workplaceGroups.stream().noneMatch(group -> group.getName().equals(groupName))) {
                    workplaceGroups.add(new WorkplaceGroup(groupName, new ArrayList<>()));
                }
            }

            for (Map.Entry<Integer,Workplace> entry : workplaces.entrySet()){
                int workplaceId=entry.getKey();
                Workplace workplace = entry.getValue();
                workplace.setSumRelevant(workplaceXMLRecords.stream()
                        .filter(record -> record.getId() == workplaceId)
                        .map(WorkplaceXMLRecord::isSumRelevant)
                        .findFirst()
                        .orElse(false));

                workplace.setName(workplaceXMLRecords.stream()
                        .filter(record -> record.getId() == workplaceId)
                        .map(WorkplaceXMLRecord::getName)
                        .findFirst()
                        .orElse("Brak w bazie"));

                String groupName = workplaceXMLRecords.stream()
                        .filter(record -> record.getId() == workplaceId)
                        .map(WorkplaceXMLRecord::getGroupName)
                        .findFirst()
                        .orElse("0. Nieprzypisane");
                if(groupName.equals("0. Nieprzypisane") && workplaceGroups.stream().noneMatch(group -> group.getName().equals("0. Nieprzypisane"))) {
                    WorkplaceGroup workplaceGroup = new WorkplaceGroup("0. Nieprzypisane", new ArrayList<>());
                    workplaceGroups.add(workplaceGroup);
                }
                WorkplaceGroup workplaceGroup=workplaceGroups.stream()
                            .filter(group -> group.getName().equals(groupName))
                            .findFirst()
                            .orElse(null);

                workplaceGroup.getWorkplaces().add(workplace);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        workplaceGroups.sort(Comparator.comparing(WorkplaceGroup::getName));
        return workplaceGroups;
    }

    private static TreeMap<Integer,Workplace> importDatabaseRecords() throws SQLException {
        List<QueryRecord> queryRecords = DatabaseQueries.importData();
        TreeMap<Integer,Workplace> workplaces = new TreeMap<>();

        for(QueryRecord record : queryRecords) {
            if(!workplaces.containsKey(record.getWorkplaceID())){
                workplaces.put(record.getWorkplaceID(), new Workplace(record.getWorkplaceID()));
            }
            workplaces.get(record.getWorkplaceID()).addProductionOrder(record);
        }
        return workplaces;
    }

    public static  List<WorkplaceXMLRecord> importWorkplacesXMLFromFile(String filePath){
        List<WorkplaceXMLRecord> workplaceXMLRecords = new ArrayList<>();
        try {
            // Parse the XML file
            File xmlFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();

            // Get all Workplace nodes
            NodeList nodeList = document.getElementsByTagName("Workplace");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    int id = Integer.parseInt(element.getAttribute("Id"));
                    String name = element.getAttribute("Name");
                    String group = element.getAttribute("Group");
                    boolean sumRelevant = Boolean.parseBoolean(element.getAttribute("SumRelevant"));
                    WorkplaceXMLRecord workplaceXMLRecord = new WorkplaceXMLRecord(id, name, group, sumRelevant);
                    workplaceXMLRecords.add(workplaceXMLRecord);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return workplaceXMLRecords;
    }

    public static void addWorkplaceToFile(String filePath, int id, String name, String group, boolean sumRelevant) {
        try {
            // Load the XML file
            File xmlFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            // Create a new Workplace element
            Element newWorkplace = document.createElement("Workplace");
            newWorkplace.setAttribute("Id", String.valueOf(id));
            newWorkplace.setAttribute("Name", name);
            newWorkplace.setAttribute("Group", group);
            newWorkplace.setAttribute("SumRelevant", String.valueOf(sumRelevant));

            // Append the new element to the root
            document.getDocumentElement().appendChild(newWorkplace);

            // Save changes to the XML file
            saveXMLDocument(document, filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateWorkplaceInFile(String filePath,int oldId,  int newId, String newName, String newGroup, boolean sumRelevant) {
        try {
            // Load the XML file
            File xmlFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            // Find the Workplace element by Id
            NodeList nodeList = document.getElementsByTagName("Workplace");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (Integer.parseInt(element.getAttribute("Id")) == oldId) {
                        // Update attributes
                        element.setAttribute("Id", String.valueOf(newId));
                        element.setAttribute("Name", newName);
                        element.setAttribute("Group", newGroup);
                        element.setAttribute("SumRelevant", String.valueOf(sumRelevant));
                        break;
                    }
                }
            }

            // Save changes to the XML file
            saveXMLDocument(document, filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeWorkplaceFromFile(String filePath, int id) {
        try {
            // Load the XML file
            File xmlFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            // Find the Workplace element by Id
            NodeList nodeList = document.getElementsByTagName("Workplace");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (Integer.parseInt(element.getAttribute("Id")) == id) {
                        // Remove the element from its parent
                        element.getParentNode().removeChild(element);
                        break;
                    }
                }
            }

            // Save changes to the XML file
            saveXMLDocument(document, filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveXMLDocument(Document document, String filePath) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(filePath));
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);
    }


}
