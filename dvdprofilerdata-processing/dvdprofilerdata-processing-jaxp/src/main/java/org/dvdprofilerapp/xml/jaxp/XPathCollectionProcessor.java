package org.dvdprofilerapp.xml.jaxp;

import java.io.IOException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.dvdprofilerapp.model.DVD;
import org.dvdprofilerapp.xml.AbstractCollectionProcessor;
import org.springframework.core.io.Resource;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XPathCollectionProcessor extends AbstractCollectionProcessor {

	private Resource collectionXmlResource;

	public void setCollectionXmlResource(Resource collectionXmlResource) {
		this.collectionXmlResource = collectionXmlResource;
	}

	@Override
	public void process() {
		XPath xPath = XPathFactory.newInstance().newXPath();
		XPathExpression xpExpr;
		try {
			xpExpr = xPath.compile("/Collection/DVD");
			NodeList dvds = (NodeList) xpExpr.evaluate(new InputSource(
					this.collectionXmlResource.getInputStream()),
					XPathConstants.NODESET);
			System.err.println("Importing " + dvds.getLength() + " dvds.");
			XPathExpression xpId = xPath.compile("ID");
			for (int i = 0; i < dvds.getLength(); i++) {
				System.err.print((i + 1) + "/" + dvds.getLength() + "...");
				DVD importedDVD = new DVD();
				Node dvd = dvds.item(i);
				Node id = (Node) xPath.evaluate("ID", dvd, XPathConstants.NODE);
				importedDVD.setId(id.getTextContent().trim());
				Node title = (Node) xPath.evaluate("Title", dvd,
						XPathConstants.NODE);
				importedDVD.setTitle(title.getTextContent().trim());
				Node description = (Node) xPath.evaluate("Overview", dvd,
						XPathConstants.NODE);
				importedDVD.setOverview(description.getTextContent().trim());
				sendEvent(importedDVD);
				System.err.println(" Done...");
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
