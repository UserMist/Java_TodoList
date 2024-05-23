package main.todolist.serialization;
import main.*;
import main.todolist.TodoList;
import main.todolist.TodoTask;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.Date;

public class XmlConverter implements RepresentationConverter<TodoList> {
    private static final SimpleDateFormat defaultDateParser = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void toRepresentation(TodoList in, StringBuilder out) throws Exception {
        var xmlBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        var xmlDoc = xmlBuilder.newDocument();
        var xmlRoot = xmlDoc.createElement("TodoList");
        xmlDoc.appendChild(xmlRoot);


        in.tasks.forEach((taskId, task) -> {
            var taskXml = xmlDoc.createElement("Task");
            taskXml.setAttribute("id", taskId.toString());
            taskXml.setAttribute("title", task.getTitle());
            taskXml.setAttribute("description", task.getDescription());
            xmlRoot.appendChild(taskXml);

            var priority = xmlDoc.createElement("Priority");
            priority.setTextContent(String.valueOf(task.getPriority()));
            taskXml.appendChild(priority);

            var deadline = xmlDoc.createElement("Deadline");
            deadline.setTextContent(defaultDateParser.format(task.getDeadline()));
            taskXml.appendChild(deadline);

            var status = xmlDoc.createElement("Status");
            switch(task.getStatus()) {
                case Invalid: status.setTextContent("invalid"); break;
                case New: status.setTextContent("new"); break;
                case InProgress: status.setTextContent("in_progress"); break;
                case Done: status.setTextContent("done"); break;
            }
            taskXml.appendChild(status);

            if(task.getStatus() == TodoTask.Status.Done && task.getCompletionDate() != null) {
                var completionDate = xmlDoc.createElement("CompletionDate");
                completionDate.setTextContent(defaultDateParser.format(task.getCompletionDate()));
                taskXml.appendChild(completionDate);
            }
        });

        var transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        var stringAcc = new StringWriter();
        transformer.transform(new DOMSource(xmlDoc), new StreamResult(stringAcc));

        var output = stringAcc.getBuffer().toString();
        out.append(output);
    }

    @Override
    public void fromRepresentation(String in, TodoList out) throws Exception {
        var inStream = new ByteArrayInputStream(in.getBytes(StandardCharsets.UTF_8));
        var xmlBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        var xmlRoot = xmlBuilder.parse(inStream).getChildNodes().item(0);

        if(!xmlRoot.getNodeName().equals("TodoList"))
            throw new Exception("Элемент main.TodoList.main.TodoList не найден");

        out.tasks.clear();
        var xmlChildren = xmlRoot.getChildNodes();
        var xmlRootChildCount = xmlChildren.getLength();

        for(var i = 0; i < xmlRootChildCount; i++) {
            var taskXml = xmlChildren.item(i);
            if(!taskXml.getNodeName().equals("Task"))
                continue;

            var xmlChildAttributes = taskXml.getAttributes();
            var taskId = Integer.parseInt(xmlChildAttributes.getNamedItem("id").getNodeValue());

            var titleNode = xmlChildAttributes.getNamedItem("title");
            var title = titleNode != null? titleNode.getNodeValue() : "unknown";

            var descriptionNode = xmlChildAttributes.getNamedItem("description");
            var description = descriptionNode != null ? descriptionNode.getNodeValue() : "";

            var task = new TodoTask(title, description, 0, new Date());

            var taskXmlChildren = taskXml.getChildNodes();
            var taskXmlChildCount = taskXmlChildren.getLength();
            for(var j = 0; j < taskXmlChildCount; j++) {
                var taskFieldXml = taskXmlChildren.item(j);
                switch(taskFieldXml.getNodeName()) {
                    case "Description":
                        task.setDescription(taskFieldXml.getTextContent());
                        break;
                    case "Priority":
                        task.setPriority(Integer.parseInt(taskFieldXml.getTextContent()));
                        break;
                    case "Deadline":
                        task.setDeadline(defaultDateParser.parse(taskFieldXml.getTextContent()));
                        break;
                    case "Status":
                        switch(taskFieldXml.getTextContent()) {
                            case "new": task.setStatus(TodoTask.Status.New); break;
                            case "in_progress": task.setStatus(TodoTask.Status.InProgress); break;
                            case "done": task.setStatus(TodoTask.Status.Done); break;
                        }
                        break;
                    case "CompletionDate":
                        task.setCompletionDate(defaultDateParser.parse(taskFieldXml.getTextContent()));
                        break;
                }
            }

            out.tasks.put(taskId, task);
        }
    }
}
