package jp.or.med.orca.jma_tokutei.common.hl7;

import org.w3c.dom.*;

import com.sun.org.apache.xml.internal.serializer.OutputPropertiesFactory;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import jp.or.med.orca.jma_tokutei.common.hl7.clinicaldocument.Author;
import jp.or.med.orca.jma_tokutei.common.hl7.clinicaldocument.ClinicalDocument;
import jp.or.med.orca.jma_tokutei.common.hl7.clinicaldocument.Custodian;
import jp.or.med.orca.jma_tokutei.common.hl7.clinicaldocument.DocumentationOf;
import jp.or.med.orca.jma_tokutei.common.hl7.clinicaldocument.Participant;
import jp.or.med.orca.jma_tokutei.common.hl7.clinicaldocument.RecordTarget;
import jp.or.med.orca.jma_tokutei.common.hl7.clinicaldocument.RepresentedOrganization;
import jp.or.med.orca.jma_tokutei.common.hl7.clinicaldocument.component.Component;
import jp.or.med.orca.jma_tokutei.common.hl7.clinicaldocument.component.Observation;
import jp.or.med.orca.jma_tokutei.common.hl7.common.Utility;

/**
 * Modified 2008/05/03 若月
 * Java コーディング規約に従って、一部をリファクタリングした。
 */
public class ClinicalDocumentWriter {

	private RecordTarget patientRole = null;
	private RepresentedOrganization assignedAuthor = null;
	private DocumentationOf documentationOf = null;
	private Participant participant = null;
	private List<Observation> tKenshinObservationList = null;
	private List<Observation> otherObservationList = null;

	public ClinicalDocumentWriter() {
		tKenshinObservationList = new ArrayList<Observation>();
		otherObservationList = new ArrayList<Observation>();
	}

	public void createXml(String fileName) throws Exception {
		if (check() == false) {
			throw new Exception();
		}
		DOMImplementation impl;
		Transformer transformer;

		// 初期化処理
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			impl = builder.getDOMImplementation();
		} catch (Exception e) {
			throw new RuntimeException();
		}

		// XML作成
		Document document = impl.createDocument("urn:hl7-org:v3",
				"ClinicalDocument", null);
		Element root = document.getDocumentElement();
		root.setAttribute("xmlns:xsi",
				"http://www.w3.org/2001/XMLSchema-instance");

		// edit s.inoue 2010/07/05
		// edit s.inoue 2009/12/09 XSD相対パス回避
		// root.setAttribute("xsi:schemaLocation","urn:hl7-org:v3 ./XSD/hc08_V08.xsd");
		root.setAttribute("xsi:schemaLocation","urn:hl7-org:v3 ../XSD/hc08_V08.xsd");

		// 要素の追加
		ClinicalDocument.appendChild(document, root);

		// add s.inoue 2009/12/29
		root.appendChild(document.createComment("受診者情報"));
		root.appendChild(patientRole.getElement(document));
		root.appendChild(Author.GetElement(document, assignedAuthor));
		root.appendChild(Custodian.getElement(document));

		String seiriNumberExtension = participant.getSeiriNumberExtension();
		if (seiriNumberExtension != null && !seiriNumberExtension.isEmpty()) {
			root.appendChild(participant.getElement(document));
		}

		root.appendChild(documentationOf.getElement(document));
		root.appendChild(Component.getElement(document,
				tKenshinObservationList, otherObservationList));

		// XML出力
		TransformerFactory TransFactory = TransformerFactory.newInstance();
		try {
			transformer = TransFactory.newTransformer();
		} catch (Exception e) {
			throw new RuntimeException();
		}
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		/* Added 2008/04/06 若月 */
		/* --------------------------------------------------- */
		/* インデントの文字数を指定する。 */
		transformer.setOutputProperty(
				OutputPropertiesFactory.S_KEY_INDENT_AMOUNT, "2");

		/* 以下の処理は機能しないため、コメントアウトする。 */
		//		/* スタンドアロンのドキュメント宣言出力を抑制する。 */
		//		transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
		/* --------------------------------------------------- */

		DOMSource source = new DOMSource(document);
		File xmlFile = new File(fileName);
		FileOutputStream stream = new FileOutputStream(xmlFile);
		StreamResult result = new StreamResult(stream);
		transformer.transform(source, result);

		stream.close();

		// XMLスキーマの検証
		// edit s.inoue 2010/07/05
		Utility.checkXmlSchema(fileName, "../XSD/hc08_V08.xsd");
		// Utility.checkXmlSchema(fileName, "./XSD/hc08_V08.xsd");
	}

	public boolean check() {
		if (patientRole == null || assignedAuthor == null
				|| documentationOf == null || participant == null) {
			return false;
		}

		if (patientRole.Check() == false || assignedAuthor.Check() == false
				|| documentationOf.Check() == false
				|| participant.Check() == false) {
			return false;
		}

		for (int i = 0; i < tKenshinObservationList.size(); i++) {
			if (tKenshinObservationList.get(i).check() == false) {
				return false;
			}
		}

		for (int i = 0; i < otherObservationList.size(); i++) {
			if (otherObservationList.get(i).check() == false) {
				System.out.println(otherObservationList.get(i).getCodeDisplayName());
				return false;
			}
		}

		return true;
	}

	public void setPatientRole(RecordTarget role) {
		patientRole = role;
	}

	public void setAssignedAuthor(RepresentedOrganization author) {
		assignedAuthor = author;
	}

	public void setDocumentationOf(DocumentationOf document) {
		documentationOf = document;
	}

	public void setAssociatedEntity(Participant part) {
		participant = part;
	}
	public void addTKenshinObservation(Observation obs) {
		tKenshinObservationList.add(obs);
	}

	public void addOtherObservation(Observation obs) {
		otherObservationList.add(obs);
	}
}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

