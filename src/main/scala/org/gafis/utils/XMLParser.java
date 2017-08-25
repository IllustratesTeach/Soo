package org.gafis.utils;

import org.gafis.config.WebConfig;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

/**
 * Created by yuchen on 2017/8/17.
 */
public class XMLParser {

    /**
     * obj to XML
     * @param obj
     * @param load
     * @return
     * @throws JAXBException
     */
    public static String beanToXml(Object obj,Class<?> load) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(load);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        StringWriter writer = new StringWriter();
        marshaller.marshal(obj,writer);
        return writer.toString();
    }



    /**
      * xml文件配置转换为对象
      * @param file  xml文件file对象
      * @param load    java对象.Class
      * @return    java对象
      * @throws JAXBException
      * @throws IOException
     */
        public static Object xmlToBean(File file,Class<?> load) throws JAXBException, IOException {
            JAXBContext context = JAXBContext.newInstance(load);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Object object = unmarshaller.unmarshal(file);
            return object;
        }

    public static void main(String args[]){
        try{
            String xmlPath = "D:/testConfig.xml";
            BufferedWriter bfw = new BufferedWriter(new FileWriter(new File(xmlPath)));
            String str = beanToXml(new WebConfig(),WebConfig.class);
            bfw.write(str);
            bfw.close();
        }catch(Exception ex){

        }
    }
}
