package com.capgemini.user.service.util;

import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.xml.transform.StringSource;

public class JaxbUtil {
	
	private static final JaxbUtil singleton = new JaxbUtil();
	private static final Map<String,JAXBContext> jaxbConextMap = new ConcurrentHashMap<>();
	
	private JaxbUtil(){}
	
	public static JaxbUtil getSingleton(){
		return singleton;
	}
	
	public <T> String marshall(T objectToMarshall){
		String packageName = objectToMarshall.getClass().getPackage().getName();
		JAXBContext jaxbContext = null;
		StringWriter sw = new StringWriter();
		if(jaxbConextMap.containsKey(packageName) && jaxbConextMap.get(packageName)!=null){
			jaxbContext = jaxbConextMap.get(packageName);
		}else{
			jaxbContext = JAXBContext.newInstance(packageName);
			jaxbConextMap.put(packageName, jaxbContext);
		}
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(objectToMarshall, sw);
		return sw.toString();
	}
	
	public <T> T unmarshall(String xml, Class<T> type){
		String packageName = type.getPackage().getName();
		JAXBContext jaxbContext = null;
		if(jaxbConextMap.containsKey(packageName) && jaxbConextMap.get(packageName)!=null){
			jaxbContext = jaxbConextMap.get(packageName);
		}else{
			jaxbContext = JAXBContext.newInstance(packageName);
			jaxbConextMap.put(packageName, jaxbContext);
		}
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		T object = unmarshaller.unmarshal(new StringSource(xml),type).getValue();
		return object;
	}
	
	/*public <T> T unmarshallIgnoreNamespace(String xml, Class<T> type){
		String packageName = type.getPackage().getName();
		JAXBContext jaxbContext = null;
		if(jaxbConextMap.containsKey(packageName) && jaxbConextMap.get(packageName)!=null){
			jaxbContext = jaxbConextMap.get(packageName);
		}else{
			jaxbContext = JAXBContext.newInstance(packageName);
			jaxbConextMap.put(packageName, jaxbContext);
		}
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		XMLReader reader = XMLReaderFactory.createXMLReader();
		XmlNamespaceFilter inFilter = new XmlNamespaceFilter("http://www.webserviceX.NET", false);
		inFilter.setParent(reader);
		InputSource is = new InputSource(new StringReader(xml));
		SAXSource source = new SAXSource(inFilter, is);
		T object = unmarshaller.unmarshal(source,type).getValue();
		return object;
	}*/

}
