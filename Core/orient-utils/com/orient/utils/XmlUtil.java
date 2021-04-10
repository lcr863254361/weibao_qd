package com.orient.utils;

import org.jbpm.api.JbpmException;
import org.jbpm.pvm.internal.xml.Parse;
import org.w3c.dom.*;

import javax.xml.namespace.QName;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.*;

/**
 * convenience methods to make reading org.w3c.dom models easier.
 */
public class XmlUtil {

    private XmlUtil() {
        // hide default constructor to prevent instantiation
    }

    public static List<Element> elements(Element element, String tagName) {
        if (element == null) {
            return Collections.emptyList();
        }
        NodeList activityList = element.getChildNodes();
        if ((activityList == null)
                || (activityList.getLength() == 0)
                ) {
            return Collections.emptyList();
        }
        List<Element> elements = new ArrayList<>();
        for (int i = 0; i < activityList.getLength(); i++) {
            Node child = activityList.item(i);
            if (Element.class.isAssignableFrom(child.getClass())) {
                Element childElement = (Element) child;
                String childTagName = getTagLocalName(childElement);
                if (childTagName.equals(tagName)) {
                    if (elements == null) {
                        elements = new ArrayList<>();
                    }
                    elements.add(childElement);
                }
            }
        }
        return elements;
    }

    public static List<Element> elements(Element element, Set<String> allowedTagNames) {
        if (element == null) {
            return Collections.emptyList();
        }
        NodeList activityList = element.getChildNodes();
        if ((activityList == null)
                || (activityList.getLength() == 0)
                ) {
            return Collections.emptyList();
        }
        List<Element> elements = new ArrayList<>();
        for (int i = 0; i < activityList.getLength(); i++) {
            Node child = activityList.item(i);
            if (Element.class.isAssignableFrom(child.getClass())) {
                Element childElement = (Element) child;
                String childTagName = getTagLocalName(childElement);
                if (allowedTagNames.contains(childTagName)) {
                    if (elements == null) {
                        elements = new ArrayList<>();
                    }
                    elements.add(childElement);
                }
            }
        }
        return elements;
    }

    public static Element element(Element element, String tagName) {
        return element(element, tagName, false, null);
    }

    public static Element element(Element element, String tagName, boolean required, Parse parse) {
        if (element == null) {
            return null;
        }
        NodeList activityList = element.getChildNodes();
        for (int i = 0; (i < activityList.getLength()); i++) {
            Node child = activityList.item(i);
            if ((Element.class.isAssignableFrom(child.getClass())) && (getTagLocalName((Element) child)).equals(tagName)) {
                return (Element) child;
            }
        }

        if (required && (parse != null)) {
            parse.addProblem("nested element <" + XmlUtil.getTagLocalName(element) + "><" + tagName + " ... />... is required", element);
        }
        return null;
    }


    public static List<Element> elements(Element element) {
        if (element == null) {
            return Collections.emptyList();
        }
        NodeList activityList = element.getChildNodes();
        if ((activityList == null)
                || (activityList.getLength() == 0)
                ) {
            return Collections.emptyList();
        }
        List<Element> elements = new ArrayList<>();
        if ((activityList != null) && (activityList.getLength() > 0)) {
            elements = new ArrayList<>();
            for (int i = 0; i < activityList.getLength(); i++) {
                Node activity = activityList.item(i);
                if (activity instanceof Element) {
                    elements.add((Element) activity);
                }
            }
        }
        return elements;
    }

    public static List<Element> elements(Element element, String ns, String localName) {
        if (element == null) {
            return Collections.emptyList();
        }
        NodeList activityList = element.getChildNodes();
        if ((activityList == null)
                || (activityList.getLength() == 0)
                ) {
            return Collections.emptyList();
        }
        List<Element> matchingElements = new ArrayList<>();
        NodeList nl = element.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node n = nl.item(i);
            if (n instanceof Element && n.getLocalName() != null && n.getLocalName().equals(localName) && n.getNamespaceURI() != null && n.getNamespaceURI().equals(ns)) {
                matchingElements.add((Element) n);
            }
        }
        return matchingElements;
    }

    public static List<Element> elementsQName(Element element, Set<QName> allowedTagNames) {
        if (element == null) {
            return Collections.emptyList();
        }
        NodeList activityList = element.getChildNodes();
        if ((activityList == null)
                || (activityList.getLength() == 0)
                ) {
            return Collections.emptyList();
        }
        List<Element> elements = new ArrayList<>();
        if (activityList != null) {
            for (int i = 0; i < activityList.getLength(); i++) {
                Node child = activityList.item(i);
                if (Element.class.isAssignableFrom(child.getClass())) {
                    Element childElement = (Element) child;
                    QName childElementQName = new QName(childElement.getNamespaceURI(), childElement.getLocalName());
                    if (allowedTagNames.contains(childElementQName)) {
                        if (elements == null) {
                            elements = new ArrayList<>();
                        }
                        elements.add(childElement);
                    }
                }
            }
        }
        return elements;
    }

    public static Element element(Element element) {
        Element onlyChild = null;
        List<Element> elements = elements(element);
        if (!elements.isEmpty()) {
            onlyChild = elements.get(0);
        }
        return onlyChild;
    }

    public static String toString(Node node) {
        if (node == null) return "null";

        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

            StringWriter stringWriter = new StringWriter();
            transformer.transform(new DOMSource(node), new StreamResult(stringWriter));
            return stringWriter.toString();
        } catch (TransformerException e) {
            throw new JbpmException("could not transform dom node to string", e);
        }
    }

    public static String getContentText(Element element) {
        return element.getTextContent();
    }

    public static boolean isTextOnly(Element element) {
        for (Node child = element.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child.getNodeType() == Node.ELEMENT_NODE)
                return false;
        }
        return true;
    }

    public static List<Attr> attributes(Element element) {
        NamedNodeMap attributeMap = element.getAttributes();
        if ((attributeMap == null) || (attributeMap.getLength() == 0)) {
            return Collections.emptyList();
        }

        List<Attr> attributes = new ArrayList<>();
        for (int i = 0; i < attributeMap.getLength(); i++) {
            attributes.add((Attr) attributeMap.item(i));
        }

        return attributes;
    }

    public static List<Node> contents(Element element) {
        NodeList activityList = element.getChildNodes();
        if ((activityList == null) || (activityList.getLength() == 0)) {
            return Collections.emptyList();
        }

        List<Node> contents = new ArrayList<>();
        for (int i = 0; i < activityList.getLength(); i++) {
            contents.add(activityList.item(i));
        }

        return contents;
    }

    public static String getTagLocalName(Element element) {
        if (element == null) {
            return null;
        }
        String localName = element.getLocalName();
        if (localName != null) {
            return localName;
        }
        return element.getTagName();
    }

    /**
     * the attribute value or null if the attribute is not present
     */
    public static String attribute(Element element, String attributeName) {
        if (element.hasAttribute(attributeName)) {
            return element.getAttribute(attributeName);
        } else {
            return null;
        }
    }

    /**
     * convenience method to combine extraction of a string attribute value.
     * <p>
     * If the attribute exists, it is returned.  If the attribute is not present, null
     * is returned.  The attribute is not present and it is required,
     * a problem will be added to the parse.
     */
    public static String attribute(Element element, String attributeName, boolean required, Parse parse) {
        return attribute(element, attributeName, required, parse, null);
    }

    /**
     * convenience method to combine extraction of a string attribute value.
     * <p>
     * If the attribute exists, it is returned.  If the attribute is not present, the
     * defaultValue is returned.  The attribute is not present and it is required,
     * a problem will be added to the parse.
     */
    public static String attribute(Element element, String attributeName, boolean required, Parse parse, String defaultValue) {
        if (element.hasAttribute(attributeName)) {
            String value = element.getAttribute(attributeName);
            if (required && "".equals(value)) {
                parse.addProblem("attribute <" + XmlUtil.getTagLocalName(element) + " " + attributeName + "=\"\" is empty", element);
            }
            return value;
        }

        if (required) {
            parse.addProblem("attribute <" + XmlUtil.getTagLocalName(element) + " " + attributeName + "=\"...\" is required", element);
        }

        return defaultValue;
    }


    /**
     * parse an attribute as an integer.
     */
    public static Integer attributeInteger(Element element, String attributeName, boolean required, Parse parse) {
        String valueText = attribute(element, attributeName, required, parse);

        if (valueText != null) {
            try {
                return Integer.parseInt(valueText);
            } catch (NumberFormatException e) {
                parse.addProblem(errorMessageAttribute(element, attributeName, valueText, "value not parsable as integer"), element);
            }
        }

        return null;
    }

    /**
     * parse an attribute as an boolean.
     */
    public static Boolean attributeBoolean(Element element, String attributeName, boolean required, Parse parse) {
        return attributeBoolean(element, attributeName, required, parse, null);
    }

    /**
     * parse an attribute as an boolean.
     */
    public static Boolean attributeBoolean(Element element, String attributeName, boolean required, Parse parse, Boolean defaultValue) {
        String valueText = attribute(element, attributeName, required, parse);
        if (valueText != null) {
            Boolean value = parseBooleanValue(valueText);
            if (value == null) {
                parse.addProblem(errorMessageAttribute(element, attributeName, valueText, "value not in {true, enabled, on, false, disabled, off}"), element);
            }
            return value;
        }
        return defaultValue;
    }

    public static Boolean parseBooleanValue(String valueText) {
        if (valueText != null) {
            // if we have to check for value true
            if (("true".equals(valueText))
                    || ("enabled".equals(valueText))
                    || ("on".equals(valueText))
                    ) {
                return Boolean.TRUE;

            } else if (("false".equals(valueText))
                    || ("disabled".equals(valueText))
                    || ("off".equals(valueText))
                    ) {
                return Boolean.FALSE;
            }
        }

        return null;
    }

    public static String errorMessageAttribute(Element element, String attributeName, String attributeValue, String message) {
        return "attribute <" + XmlUtil.getTagLocalName(element) + " " + attributeName + "=\"" + attributeValue + "\" " + message;
    }

    public static List<String> parseList(Element element, String singularTagName) {
        // a null value for text represents a wildcard
        String text = XmlUtil.attribute(element, singularTagName + "s");
        // so next we'll convert a '*' into the text null value, which indicates a
        // wildcard
        if ("*".equals(text)) {
            text = null;
        }
        if (element.hasAttribute(singularTagName)) {
            String eventText = element.getAttribute(singularTagName);
            text = (text == null ? eventText : text + "," + eventText);
        }
        List<String> eventNames = parseCommaSeparatedList(text);
        return eventNames;
    }

    /**
     * parses comma or space separated list. A null return value means a wildcard.
     *
     * @return List of tokens or null if the commaSeparatedListText is null, '*',
     * or empty
     */
    public static List<String> parseCommaSeparatedList(String commaSeparatedListText) {
        List<String> entries = null;
        if (commaSeparatedListText != null) {
            if (!"*".equals(commaSeparatedListText)) {
                StringTokenizer tokenizer = new StringTokenizer(commaSeparatedListText, ", ");
                while (tokenizer.hasMoreTokens()) {
                    if (entries == null) {
                        entries = new ArrayList<String>();
                    }
                    entries.add(tokenizer.nextToken());
                }
            }
        }
        return entries;
    }

    public static class NamespaceValue {

        public String prefix;
        public String localPart;

        public NamespaceValue(String prefix, String localPart) {
            this.prefix = prefix;
            this.localPart = localPart;
        }
    }

    public static NamespaceValue attributeNamespaceValue(Element element, String attributeName) {
        NamespaceValue namespaceValue = null;
        String text = attribute(element, attributeName);
        if (text != null) {
            int colonIndex = text.indexOf(':');
            if (colonIndex == -1) {
                namespaceValue = new NamespaceValue(null, text);
            } else {
                String prefix = text.substring(0, colonIndex);
                String localPart = null;
                if (text.length() > colonIndex + 1) {
                    localPart = text.substring(colonIndex + 1);
                }
                namespaceValue = new NamespaceValue(prefix, localPart);
            }
        }
        return namespaceValue;
    }

    public static QName attributeQName(Element element, String attributeName) {
        QName qname = null;

        NamespaceValue namespaceValue = attributeNamespaceValue(element, attributeName);
        String text = attribute(element, attributeName);
        if (namespaceValue != null) {
            if (namespaceValue.prefix == null) {
                qname = new QName(text);
            } else {
                String uri = element.lookupNamespaceURI(namespaceValue.prefix);
                if (uri == null) {
                    throw new JbpmException("unknown prefix in qname " + text);
                } else if (namespaceValue.localPart == null) {
                    throw new JbpmException("no local part in qname " + text);
                } else {
                    qname = new QName(uri, namespaceValue.localPart, namespaceValue.prefix);
                }
            }
        }
        return qname;
    }

    public static QName getQNameFromString(Element element, String qnameAsString) {
        if (qnameAsString == null || element == null) {
            return null;
        }
        int colonIndex = qnameAsString.indexOf(":");
        String prefix = qnameAsString.substring(0, colonIndex);
        String localName = qnameAsString.substring(colonIndex + 1);
        String ns = getNamespaceURI(element, prefix);
        return new QName(ns, localName, prefix);
    }

    public static String getNamespaceURI(final org.w3c.dom.Node n, final String prefix) {
        Node prefixDeclaration = n.getAttributes().getNamedItem("xmlns:" + prefix);
        if (prefixDeclaration != null) {
            // we have found the good NameSpace
            return prefixDeclaration.getNodeValue();
        }
        // we have found the good NameSpace
        // we look for the NameSpace in the parent Node
        return getNamespaceURI(n.getParentNode(), prefix);
    }

}