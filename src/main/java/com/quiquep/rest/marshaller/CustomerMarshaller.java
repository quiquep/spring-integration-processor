package com.quiquep.rest.marshaller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.stereotype.Component;
import org.springframework.xml.transform.StringSource;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.quiquep.rest.domain.CustomerAccount;
import com.quiquep.rest.domain.CustomerAccountRequest;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;

@Component
public class CustomerMarshaller implements Marshaller, Unmarshaller {

	private static final Log LOG = LogFactory.getLog(CustomerMarshaller.class);
	
	public Object unmarshal(Source source) throws IOException, XmlMappingException {
		
		final NodeList nodeList = ((DOMSource)source).getNode().getChildNodes();

		LOG.debug("Receiving request " + nodeList + " length " + nodeList.getLength());
		
		final List<CustomerAccount> customers = new ArrayList<CustomerAccount>();
		if (nodeList.getLength() > 1) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				final Node node = nodeList.item(i);
				LOG.debug("Nodename " + node.getNodeName());
				if (node.getLocalName().equals("customerAccountInfo")) {
					customers.add(createCustomer(node));
				}
			}
		}		
		return customers;
	}

	private CustomerAccount createCustomer(final Node node) {
		final NodeList childs = node.getChildNodes();
		String id = "";
		String name = "";
		for (int i=0;i<childs.getLength(); i++) {
			final Node child = childs.item(i);
			if (child.getLocalName().equals("customerId")) {
				id = child.getTextContent();
			} else if (child.getLocalName().equals("customerFullName")) {
				name = child.getTextContent();
			}
		}
		return new CustomerAccount(id, name);
	}
	
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	public void marshal(Object object, Result result) throws IOException, XmlMappingException {

		final CustomerAccountRequest customer = (CustomerAccountRequest) object;
		
		final String xmlString = "<schemas:customer xmlns:schemas=\"http://www.globallogic.com/spring/ws/schemas\">"
				+ "<schemas:passportId>" + customer.getPassportId() + "</schemas:passportId></schemas:customer>";

		try {
			final Transformer transformer = new TransformerFactoryImpl().newTransformer();
			transformer.transform(new StringSource(xmlString), result);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}	

}
