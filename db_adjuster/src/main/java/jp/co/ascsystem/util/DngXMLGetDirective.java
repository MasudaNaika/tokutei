package jp.co.ascsystem.util;

import java.io.*;
import java.util.regex.*;

public class DngXMLGetDirective {

  String strTag="";
  String strValue="";
  public DngXMLGetDirective(String contents) { 
    tagParse(contents);
  }

  public void tagParse(String contents) {
    contents = Pattern.compile("<!--.*?-->",Pattern.DOTALL).matcher(contents).replaceAll("");
    contents = contents.replaceAll("\\(",":LEFT:");
    contents = contents.replaceAll("\\)",":RIGHT:");
    contents = contents.replaceAll("\\?",":QUEST:");
    contents = contents.replaceAll("\\|",":PIPE:");
    contents = contents.replaceAll("\\+",":PLUS:");
    contents = contents.replaceAll("\\*",":STAR:");
    contents = contents.replaceAll("\\$",":DOLL:");
    Pattern pattern = Pattern.compile("<([^!>/?:]+?)>");
    Matcher matcher = pattern.matcher(contents);
    while (matcher.find()) {
      String str=matcher.group(1);
      String strTagp[] = str.split("[ \t\n\f\r]+");
      Pattern pattern2 = Pattern.compile("<"+str+">(.*?)</"+strTagp[0]+">",Pattern.DOTALL);
      Matcher matcher2 = pattern2.matcher(contents);
      if (matcher2.find()) {
        contents = contents.replaceFirst(matcher2.group(0),"");
        if (strTag!="") strTag += ":_"; 
        strTag += str;
        if (strValue!="") strValue += ":_"; 
        String val = matcher2.group(1);
        val = val.replaceAll(":LEFT:","(");
        val = val.replaceAll(":RIGHT:",")");
        val = val.replaceAll(":QUEST:","?");
        val = val.replaceAll(":PIPE:","|");
        val = val.replaceAll(":PLUS:","+");
        val = val.replaceAll(":STAR:","*");
        val = val.replaceAll(":DOLL:","\\$");
        strValue += val;
        //tagParse(val);
        matcher = matcher.reset();
        matcher = pattern.matcher(contents);
      }
    }
    return;
  }
 
  public String[] getTag() {
    return strTag.split(":_");
  }

  public String[] getValue() {
    return strValue.split(":_");
  }
}
