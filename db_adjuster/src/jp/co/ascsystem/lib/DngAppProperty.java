package jp.co.ascsystem.lib;

import java.util.Hashtable;
import jp.co.ascsystem.util.DngXMLParse;

public class DngAppProperty {
  private DngXMLParse xml;

  public DngAppProperty(String uri) {
    xml = new DngXMLParse(uri);
  }
  public String getProperty(String key) {
    String[] keys = key.split("/");
    String val = xml.getValue(keys);
    //if (val=="" || val.matches(".*?<.*>.*")) val=null;
    return val;
  }
  public Hashtable getHash(String key) {
    String[] keys = key.split("/");
    Hashtable hash = xml.getHash(keys);
    //if (val=="" || val.matches(".*?<.*>.*")) val=null;
    return hash;
  }
}
