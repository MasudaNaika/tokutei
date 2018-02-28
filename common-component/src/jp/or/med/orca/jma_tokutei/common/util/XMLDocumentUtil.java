package jp.or.med.orca.jma_tokutei.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.OutputStream;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.filectrl.JFile;
import jp.or.med.orca.jma_tokutei.common.hl7.common.Utility;
import jp.or.med.orca.jma_tokutei.common.util.XMLDocumentUtil;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Text;

//import com.lowagie.text.Document;
import com.sun.org.apache.xml.internal.serializer.OutputPropertiesFactory;

public class XMLDocumentUtil {
	private static final String ELEMENT_PROPERTIES = "properties";
	private static final String ELEMENT_PROPERTY = "property";
	private static final String ATTRIBUTE_ID = "id";
	
	private Element m_elementRoot = null; // ルート要素
	private String m_strXmlFileName = ""; // XMLファイル名

	/**
	 *  コンストラクタ
	 *  
	 *    @param  ファイルパス
	 *  
	 *    @return none
	 */
	public XMLDocumentUtil(String strPath) throws Exception {
		try {
			// ファクトリ生成
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();

			// ビルダー生成
			DocumentBuilder builder = factory.newDocumentBuilder();

			// ルート要素取得
			m_elementRoot = builder.parse(strPath).getDocumentElement();
		} catch (Exception err) {
			throw err;
		}

		m_strXmlFileName = strPath;
	}
	
	/**
	 *  取込ドキュメントに対して XPath 発行
	 * 
	 *    @param  発行パス
	 *    
	 *    @return 返却値
	 */
	public String getNodeValue(String xpath) throws Exception {
		return getNodeValue(m_elementRoot, xpath);
	}

	/**
	 *  指定ノード値の取得
	 *
	 *    @param  実行ノード
	 *    @param  発行パス
	 *
	 *    @return 返却値
	 */
	private String getNodeValue(Node n, String xpath) throws Exception {
		String result = "";

		int index = xpath.indexOf("/");

		if (index != -1) {
			NodeList nodelist = ((Element) n).getElementsByTagName(xpath.substring(0, index));
			return getNodeValue(nodelist.item(0), xpath.substring(index + 1));
		}

		NodeList nodelist = ((Element) n).getElementsByTagName(xpath);
		Node node;

		for (int i = 0; i < nodelist.getLength(); i++) {
			node = nodelist.item(i);
			if (node.getFirstChild() != null) {
				result = node.getFirstChild().getNodeValue();
			}
		}

		return result;
	}

	/**
	 *  指定ノード値取得
	 */
	public String getNodeValue(String properities, String properity)
			throws Exception {
		String result = "";
		Node node = getNode(properities, properity);

		if (node != null) {
			result = node.getNodeValue();
		}

		return result;
	}

	/**
	 *  指定ノード値設定
	 */
	public void setNodeValue(String properities, String properity, String value)
			throws Exception {
		Node node = getNode(properities, properity);

		if (node != null) {
			node.setNodeValue(value);
		}
	}
	
	/** add s.inoue 20081205
	 *  指定ノード追加設定
	 */
	public void addNodeValue(XMLDocumentUtil xmlDoc,String properities, String properity)
			throws Exception {
		
		 try {
		        DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		        DocumentBuilder docbuilder = dbfactory.newDocumentBuilder(); // DocumentBuilderインスタンス
		        Document document =(Document)docbuilder.newDocument();		 // Documentの生成
		        
		        document= docbuilder.parse(PropertyUtil.getProperty("property.filename"));
		        
		         /*
		          * ノードを生成し、rootのノードの最初の子ノードとして追加
		          */
		         Element contents = document.createElement("property");
		         contents.setAttribute("id", "AbsolutePath");
		         
		         //org.w3c.dom.Text contents2 = document.createTextNode("\t"+ "\t");
		         
		         NodeList list = document.getElementsByTagName(ELEMENT_PROPERTIES);
		 		 Node n;
		 		 for (int i = 0; i < list.getLength(); i++) {
		 			n = list.item(i);
		 			NamedNodeMap map = n.getAttributes();
		 			
		 			if ("DBConfig".equals(map.getNamedItem(ATTRIBUTE_ID).getNodeValue())) {
		 				NodeList list2 = ((Element) n).getElementsByTagName(ELEMENT_PROPERTY);
		 				for (int j = 0; j < list2.getLength(); j++) {
		 					Node node = list2.item(j);
		 					NamedNodeMap map2 = node.getAttributes();
		 					if ("LoginTimeOut".equals(map2.getNamedItem(ATTRIBUTE_ID).getNodeValue())) {
		 						Node pnode= node.getFirstChild();
		 						
		 						Node rootNode =  pnode.getParentNode().getParentNode();
		 						rootNode.insertBefore(contents, node);
		 						break;
		 					}
		 				}
		 			}
		 		 }
		        
		         /*
		          * DOMオブジェクトをpropertyファイルへ出力
		          */
		         TransformerFactory tfactory = TransformerFactory.newInstance(); 
		         Transformer transformer = tfactory.newTransformer(); 
		         
		         transformer.setOutputProperty(OutputKeys.INDENT , "yes");
		         transformer.setOutputProperty(OutputPropertiesFactory.S_KEY_INDENT_AMOUNT,"2");

		         String outfile = PropertyUtil.getProperty("property.filename");

		         DOMSource source = new DOMSource(document);
		 		 File Xml = new File(outfile);
		 		 FileOutputStream stream = new FileOutputStream(Xml);
		 		 StreamResult result = new StreamResult(stream);
		 		 transformer.transform(source, result);

		 		 stream.close();
		 				         
		      } catch (Exception err) {
		    	  throw err;
		      }
		
	}
	
	/**
	 *  設定ファイルよりノード値を取得
	 */
	private Node getNode(String properties, String property) {
		NodeList list = m_elementRoot.getElementsByTagName(ELEMENT_PROPERTIES);
		Node n;
		for (int i = 0; i < list.getLength(); i++) {
			n = list.item(i);
			NamedNodeMap map = n.getAttributes();
			if (properties.equals(map.getNamedItem(ATTRIBUTE_ID).getNodeValue())) {
				NodeList list2 = ((Element) n).getElementsByTagName(ELEMENT_PROPERTY);
				for (int j = 0; j < list2.getLength(); j++) {
					Node node = list2.item(j);
					NamedNodeMap map2 = node.getAttributes();
					if (property.equals(map2.getNamedItem(ATTRIBUTE_ID).getNodeValue())) {
						return node.getFirstChild();
					}
				}
			}
		}
		return null;
	}

	/**
	 *  ファイル保存
	 */
	public void store() throws Exception {
		store(m_strXmlFileName);
	}

	/**
	 *  ファイル保存
	 */
	public void store(String fileName) throws Exception {
		File newXML = new File(fileName);
		Transformer tm;
		try {
			tm = TransformerFactory.newInstance().newTransformer();
			tm.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");

			/* Added 2008/04/08 若月  */
			/* --------------------------------------------------- */
			/* インデントの文字数を指定する。 */
			tm.setOutputProperty(OutputPropertiesFactory.S_KEY_INDENT_AMOUNT, "2");		
			/* --------------------------------------------------- */
			
			tm.transform(new DOMSource(m_elementRoot), new StreamResult(
					new FileOutputStream(newXML)));
		} catch (Exception err) {
			throw err;
		}

		newXML = null;
	}
}
