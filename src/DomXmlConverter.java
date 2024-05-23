import java.util.Stack;

public class DomXmlConverter implements RepresentationConverter<DomXml> {
    private static StringBuilder indent(StringBuilder out, int depth) {
        return out.append("\t".repeat(depth));
    }
    private static void stringToRawString(String str, StringBuilder out) {
        out.append('"');
        out.append(str);
        out.append('"');
    }

    private static void toRepresentationRecursive(DomXml in, StringBuilder out, int depth) {
        if(in.header != null) {
            indent(out, depth).append("<?xml version=");
            stringToRawString(in.header.version, out);
            out.append(" encoding=");
            stringToRawString(in.header.encoding, out);
            out.append("/>\n");
        }

        indent(out, depth).append('<').append(in.name);
        in.fields.forEach((name,val) -> {
            out.append(' ').append(name).append(" = ");
            stringToRawString(val, out);
        });

        if(in.containsDirectText) {
            out.append('>').append(in.directText).append("</").append(in.name).append(">\n");
            return;
        }

        var children = in.childrenNodes;
        if(children == null) {
            out.append("/>");
            return;
        }

        out.append('>');
        for(DomXml child : children)
            toRepresentationRecursive(child, out, depth);
        out.append("</").append(in.name).append(">\n");
    }

    private static int fromRepresentationRecursive(String in, int pos, DomXmlHeader out) {
        var newPos = pos;

        if(in.startsWith("<?", pos)) {
            pos += 2;

        }
        else if(in.startsWith("<", pos)) {
            pos += 1;
        }
        return pos;
    }

    @Override
    public void toRepresentation(DomXml in, StringBuilder out) {
        toRepresentationRecursive(in, out, 0);
    }

    @Override
    public void fromRepresentation(String in, DomXml out) throws Exception {
    }
}
