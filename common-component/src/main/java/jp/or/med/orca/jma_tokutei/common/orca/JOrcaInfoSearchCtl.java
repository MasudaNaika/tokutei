// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi

package jp.or.med.orca.jma_tokutei.common.orca;

import java.io.*;
import java.net.*;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import org.apache.log4j.Logger;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

public class JOrcaInfoSearchCtl
{

    public JOrcaInfoSearchCtl()
    {
        user = "";
        pass = "";
        host = "";
        port = "";
        patientId = "";
        simeiKana = "";
        simeiKanji = "";
        sex = "";
        birthDate = "";
        address1 = "";
        address2 = "";
        postCode = "";
        phoneNumber1 = "";
        phoneNumber2 = "";
        personSymbol = "";
        personNumber = "";
        startDate = "";
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String s)
    {
        user = s;
    }

    public String getPass()
    {
        return pass;
    }

    public void setPass(String s)
    {
        pass = s;
    }

    public String getHost()
    {
        return host;
    }

    public void setHost(String s)
    {
        host = s;
    }

    public String getPort()
    {
        return port;
    }

    public void setPort(String s)
    {
        port = s;
    }

    public String getPatientId()
    {
        return patientId;
    }

    public void setPatientId(String s)
    {
        patientId = s;
    }

    public String getSimeiKana()
    {
        return simeiKana;
    }

    public void setSimeiKana(String s)
    {
        simeiKana = s;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String s)
    {
        sex = s;
    }

    public String getSimeiKanji()
    {
        return simeiKanji;
    }

    public void setSimeiKanji(String s)
    {
        simeiKanji = s;
    }

    public String getBirthDay()
    {
        return birthDate;
    }

    public void setBirthDay(String s)
    {
        birthDate = s;
    }

    public String getAddress1()
    {
        return address1;
    }

    public void setAddress1(String s)
    {
        address1 = s;
    }

    public String getAddress2()
    {
        return address2;
    }

    public void setAddress2(String s)
    {
        address2 = s;
    }

    public String getPostCode()
    {
        return postCode;
    }

    public void sePostCode(String s)
    {
        postCode = s;
    }

    public String getPhoneNumber1()
    {
        return phoneNumber1;
    }

    public void sePhoneNumber1(String s)
    {
        phoneNumber1 = s;
    }

    public String getPhoneNumber2()
    {
        return phoneNumber2;
    }

    public void sePhoneNumber2(String s)
    {
        phoneNumber2 = s;
    }

    public String getPersonSymbol()
    {
        return personSymbol;
    }

    public void setPersonSymbol(String s)
    {
        personSymbol = s;
    }

    public String getPersonNumber()
    {
        return personNumber;
    }

    public void setPersonNumber(String s)
    {
        personNumber = s;
    }

    public String getstartDate()
    {
        return startDate;
    }

    public void setstartDate(String s)
    {
        startDate = s;
    }

    public void setURL()
        throws IOException, URISyntaxException
    {
        String s = null;
        // eidt s.inoue 2013/03/25
        // String s1 = (new StringBuilder()).append("http://").append(getHost()).append(":").append(getPort()).append("/api01r/patientget?id=").append(getPatientId()).toString();
        String s1 = "";

        String patient = getPatientId();
        if (patient.equals("")){
        	s1 = (new StringBuilder()).append("http://").append(getHost()).append(":").append(getPort()).append("/api01r/patientget").toString();
        }else{
        	s1 = (new StringBuilder()).append("http://").append(getHost()).append(":").append(getPort()).append("/api01r/patientget?id=").append(getPatientId()).toString();
        }


        URL url = (new URI(s1)).toURL();
        s = getContents(url);
        readXMLPatientParser(s);
    }

    private Document string2Document(String s)
    {
        Document document = null;
        try
        {
            DocumentBuilderFactory documentbuilderfactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentbuilder = documentbuilderfactory.newDocumentBuilder();
            StringReader stringreader = new StringReader(s);
            document = documentbuilder.parse(new InputSource(stringreader));
            documentbuilder = documentbuilderfactory.newDocumentBuilder();
        }
        catch(Exception exception)
        {
            logger.error(exception.getMessage());
        }
        return document;
    }

    private String getContents(URL url)
    {
        StringBuffer stringbuffer = new StringBuffer();
        try
        {
            Authenticator.setDefault(new Authenticator() {

                protected PasswordAuthentication getPasswordAuthentication()
                {
                    String s1 = getUser();
                    String s2 = getPass();
                    System.out.println((new StringBuilder()).append("user:").append(s1).append(" pass:").append(s2).toString());
                    return new PasswordAuthentication(s1, s2.toCharArray());
                }

//                final JOrcaInfoSearchCtl this$0;
//            {
//                this$0 = JOrcaInfoSearchCtl.this;
//                super();
//            }
            });
            URLConnection urlconnection = url.openConnection();
            BufferedInputStream bufferedinputstream = new BufferedInputStream(urlconnection.getInputStream());
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(bufferedinputstream, "UTF-8"));
            do
            {
                String s;
                if((s = bufferedreader.readLine()) == null)
                    break;
                if(s.indexOf("version") <= 0)
                    stringbuffer.append((new StringBuilder()).append(s).append("\n").toString());
            } while(true);
        }
        catch(Exception exception)
        {
            logger.error(exception.getMessage());
        }
        return stringbuffer.toString();
    }

    private void readXMLPatientParser(String s)
    {
        Document document = string2Document(s);
        Element element = document.getDocumentElement();
        System.out.println((new StringBuilder()).append("ルート：").append(element.getTagName()).toString());
        for(Node node = element.getFirstChild().getFirstChild(); node != null; node = node.getFirstChild())
        {
            System.out.println((new StringBuilder()).append("項目：").append(node.getNodeName()).toString());
            if(!getElementValueByTags(node,"record"))
            {
                JErrorMessage.show("M3337", null);
                return;
            }
        }

//        // add s.inoue 2013/03/25
//        XPathEvaluator xpe = null;
//        xpe = new XPathEvaluator(hl7path);
//
//        Iterable<Node> chdPatient = xpe.getNodeList("//checkupClaim/settlement/*");
//
//		for (Node chdNodeCd : chdPatient) {
//			if (chdNodeCd.getNodeName().equals("serviceEventType")){
//			}
//		}

    }

    private boolean getElementValueByTags(Node node,String s)
    {
    	// eidt s.inoue 2013/03/25
        // String s = "record";
        String s1 = "";
        if(node.getNodeName().equals(s))
        {
            NamedNodeMap namednodemap = node.getAttributes();
            if(namednodemap != null)
            {
                Node node1 = namednodemap.getNamedItem("name");
                if(node1 != null)
                    s1 = node1.getNodeValue();
                if(s1.equals("patientinfores"))
                    setNodeValue(node);
            }
        }
        return true;
    }

    private void setNodeValue(Node node)
    {
        NamedNodeMap namednodemap = node.getAttributes();
        if(namednodemap != null)
        {
            String s = namednodemap.getNamedItem("name").getNodeValue();
            if(s.equals("patientinfores") || s.equals("Patient_Information") || s.equals("Home_Address_Information") || s.equals("HealthInsurance_Information"))
                for(node = node.getFirstChild(); node.getNodeName().equals("string");)
                {
                    String s1 = "";
                    NamedNodeMap namednodemap1 = node.getAttributes();
                    if(namednodemap1 != null)
                    {
                        s1 = namednodemap1.getNamedItem("name").getNodeValue();
                        if(s1.equals("Patient_ID"))
                        {
                            System.out.println(node.getTextContent());
                            patientId = node.getTextContent();
                        } else
                        if(s1.equals("WholeName"))
                        {
                            System.out.println(node.getTextContent());
                            simeiKanji = node.getTextContent();
                        } else
                        if(s1.equals("WholeName_inKana"))
                        {
                            System.out.println(node.getTextContent());
                            simeiKana = node.getTextContent();
                        } else
                        if(s1.equals("BirthDate"))
                        {
                            System.out.println(node.getTextContent());
                            birthDate = node.getTextContent().replace("-", "");
                        } else
                        if(s1.equals("Sex"))
                        {
                            System.out.println(node.getTextContent());
                            sex = node.getTextContent();
                        } else
                        if(s1.equals("Address_ZipCode"))
                        {
                            System.out.println(node.getTextContent());
                            postCode = node.getTextContent();
                        } else
                        if(s1.equals("WholeAddress1"))
                        {
                            System.out.println(node.getTextContent());
                            address1 = node.getTextContent();
                        } else
                        if(s1.equals("WholeAddress2"))
                        {
                            System.out.println(node.getTextContent());
                            address2 = node.getTextContent();
                        } else
                        if(s1.equals("PhoneNumber1"))
                        {
                            System.out.println(node.getTextContent());
                            phoneNumber1 = node.getTextContent();
                        } else
                        if(s1.equals("PhoneNumber2"))
                        {
                            System.out.println(node.getTextContent());
                            phoneNumber2 = node.getTextContent();
                        } else
                        if(s1.equals("HealthInsuredPerson_Symbol"))
                        {
                            System.out.println(node.getTextContent());
                            personSymbol = node.getTextContent();
                        } else
                        if(s1.equals("HealthInsuredPerson_Number"))
                        {
                            System.out.println(node.getTextContent());
                            personNumber = node.getTextContent();
                        } else
                        if(s1.equals("Certificate_StartDate"))
                        {
                            System.out.println(node.getTextContent());
                            startDate = node.getTextContent();
                        }
                    }
// edit s.inoue 2013/03/25
//                    if(s1.equals("PhoneNumber2"))
//                    {
//                        node = node.getParentNode().getNextSibling().getFirstChild().getFirstChild();
//                    	return;
//                    } else
//                    {
                    if(s1.equals("PhoneNumber2")){
                    	getXMLsearch(node);
                    }
                        if(s1.equals("Certificate_StartDate"))
                            return;
                        node = node.getNextSibling();
                        // add s.inoue 2013/03/25
                        if (node == null)return;
//                    }
                }

        }
        setNodeValue(node);
    }

    // add s.inoue 2013/03/25
	private void getXMLsearch(Node child) {

		NamedNodeMap attrsCode = null;
		Node node = child.getParentNode();

		while (node != null) {
			// String nodeNm = node.getNodeName();
			attrsCode = node.getAttributes();
			String nodeNm = attrsCode.getNamedItem("name").getNodeValue();
			System.out.println(attrsCode.getNamedItem("name"));
			// 実施機関番号
			Node claimeChild = null;
			if (nodeNm.equals("HealthInsurance_Information")){
				claimeChild=node.getFirstChild().getFirstChild();
				while (claimeChild != null) {
					NamedNodeMap namednodemap1 = claimeChild.getAttributes();
					String claimeChildnodeNm = namednodemap1.getNamedItem("name").getNodeValue();

	                if(claimeChildnodeNm.equals("HealthInsuredPerson_Symbol"))
	                {
	                    System.out.println(claimeChild.getTextContent());
	                    personSymbol = claimeChild.getTextContent();
	                } else
	                if(claimeChildnodeNm.equals("HealthInsuredPerson_Number"))
	                {
	                    System.out.println(claimeChild.getTextContent());
	                    personNumber = claimeChild.getTextContent();
	                }
	                claimeChild = claimeChild.getNextSibling();
				}
			}
			node = node.getNextSibling();
		}
	}

    private String user;
    private String pass;
    private String host;
    private String port;
    private String patientId;
    private String simeiKana;
    private String simeiKanji;
    private String sex;
    private String birthDate;
    private String address1;
    private String address2;
    private String postCode;
    private String phoneNumber1;
    private String phoneNumber2;
    private String personSymbol;
    private String personNumber;
    private String startDate;
    private static Logger logger = Logger.getLogger("jp/or/med/orca/jma_tokutei/common/orca/JOrcaInfoSearchCtl");

}
