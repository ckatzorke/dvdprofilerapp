package org.dvdprofilerapp.xml.jaxp;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.dvdprofilerapp.DvdProfilerRepository;
import org.dvdprofilerapp.model.DVD;
import org.dvdprofilerapp.xml.CollectionImporter;
import org.springframework.core.io.Resource;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XPathCollectionImporter implements CollectionImporter {

	private DvdProfilerRepository dvdProfilerRepository;

	private Resource collectionXmlResource;

	public void setCollectionXmlResource(Resource collectionXmlResource) {
		this.collectionXmlResource = collectionXmlResource;
	}

	public void setDvdProfilerRepository(
			DvdProfilerRepository dvdProfilerRepository) {
		this.dvdProfilerRepository = dvdProfilerRepository;
	}

	private int dvdCount;

	@Override
	public int getDvdCount() {
		return dvdCount;
	}

	@Override
	public void importCollection() throws Exception {
		XPath xPath = XPathFactory.newInstance().newXPath();
		XPathExpression xpExpr = xPath.compile("/Collection/DVD");
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
			importedDVD.setDescription(description.getTextContent().trim());
			System.err.print(" Persisting...");
			dvdProfilerRepository.writeDvd(importedDVD);
			System.err.println(" Done...");
		}
		dvdCount = dvds.getLength();
	}
}
