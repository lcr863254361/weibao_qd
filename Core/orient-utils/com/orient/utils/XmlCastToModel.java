package com.orient.utils;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;

import java.io.*;

public class XmlCastToModel<T> {

    private T t = null;

    public T castfromXML(Class<T> objectClass, String xmlContent,
                         String definitionXmlPath) {
        t = null;
        try {
            StringReader reader = new StringReader(xmlContent);
            File mapFile = new File(definitionXmlPath);
            Mapping mapping = new Mapping();
            mapping.loadMapping(mapFile.getAbsolutePath());
            Unmarshaller unmar = new Unmarshaller(objectClass);
            unmar.setMapping(mapping);
            t = (T) unmar.unmarshal(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MappingException e) {
            e.printStackTrace();
        } catch (MarshalException e) {
            e.printStackTrace();
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        return t;
    }

    public String beanToXml(T bean, String definitionXmlPath) {
        try {
            Mapping map = new Mapping();
            map.loadMapping(definitionXmlPath);
            StringWriter write = new StringWriter();
            Marshaller marshaller = new Marshaller(write);
            marshaller.setEncoding("UTF-8");
            marshaller.setMapping(map);
            marshaller.marshal(bean);

            String value = write.toString();
            System.out.println(value);
            return value;
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            return "";
        }
    }

}
