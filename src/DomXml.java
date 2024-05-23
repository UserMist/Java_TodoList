import java.util.ArrayList;
import java.util.HashMap;

public class DomXml {
    //null for self-closing tags
    public DomXmlHeader header;
    public String name;
    public HashMap<String, String> fields;
    public boolean containsDirectText;
    public String directText;
    public ArrayList<DomXml> childrenNodes;
}
