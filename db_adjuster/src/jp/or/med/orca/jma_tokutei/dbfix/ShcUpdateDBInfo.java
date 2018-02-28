package jp.or.med.orca.jma_tokutei.dbfix;

import java.io.*;
import java.util.Arrays;
import java.util.regex.*;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Vector;

import jp.co.ascsystem.lib.*;
import jp.co.ascsystem.util.*;

public class ShcUpdateDBInfo {

    Hashtable<String,Vector> procDBVersion = new Hashtable<String,Vector>();
    int maxSchemaSystemVersionID = -1;
    int maxDataSystemVersionID = -1;
    int maxSchemaKikanVersionID = -1;
    int maxDataKikanVersionID = -1;
    int maxDataZipVersionID = -1;
    String maxSchemaSystemVersion = null;
    String maxDataSystemVersion = null;
    String maxSchemaKikanVersion = null;
    String maxDataKikanVersion = null;
    String maxDataZipVersion = null;
    Vector<String> schemaSystemVersion = new Vector<String>();
    Vector<String> dataSystemVersion = new Vector<String>();
    Vector<String> schemaKikanVersion = new Vector<String>();
    Vector<String> dataKikanVersion = new Vector<String>();
    Vector<String> dataZipVersion = new Vector<String>();

    DngAppProperty Props,ZProps;

    public ShcUpdateDBInfo() {
      init();
    }

    public String getCurrentMaxVersion(String str) {
      String ver = null; 
        if (str.equals("schemaSystem")) ver = maxSchemaSystemVersion;
        if (str.equals("dataSystem")) ver = maxDataSystemVersion;
        if (str.equals("schemaKikan")) ver = maxSchemaKikanVersion;
        if (str.equals("dataKikan")) ver = maxDataKikanVersion;
        if (str.equals("dataZipup")) ver = maxDataZipVersion;
      return ver;
    }
  
    public String[][] getProcessSQL(String str,String schemaver,String dataver,String zipver) {
      Vector<Hashtable> task1= new Vector<Hashtable>();
      Vector<Hashtable> task2= new Vector<Hashtable>();
      Vector<Hashtable> task3= new Vector<Hashtable>();
      int total = 0;
      if (str.equals("System")) {
        for (int i=0; i<schemaSystemVersion.size(); i++) {
          String procVersion = schemaSystemVersion.get(i); 
          if (schemaver.compareTo(procVersion)<0) {
            task1 = getSchemaSystemTask(i);
            total += task1.size();
            break;
          }
        }
        for (int i=0; i<dataSystemVersion.size(); i++) {
          String procVersion = dataSystemVersion.get(i); 
          if (dataver.compareTo(procVersion)<0) {
            task2 = getDataSystemTask(i);
            total += task2.size();
            break;
          }
        }
        for (int i=0; i<dataZipVersion.size(); i++) {
          String procVersion = dataZipVersion.get(i); 
          if (zipver.compareTo(procVersion)<0) {
            task3 = getDataZipTask(i);
            total += task3.size();
            break;
          }
        }
      } else {
        for (int i=0; i<schemaKikanVersion.size(); i++) {
          String procVersion = schemaKikanVersion.get(i); 
          if (schemaver.compareTo(procVersion)<0) {
            task1 = getSchemaKikanTask(i);
            total += task1.size();
            break;
          }
        }
        for (int i=0; i<dataKikanVersion.size(); i++) {
          String procVersion = dataKikanVersion.get(i); 
          if (dataver.compareTo(procVersion)<0) {
            task2 = getDataKikanTask(i);
            total += task2.size();
            break;
          }
        }
      }
      int t1count = (task1.equals(null)) ? 0: task1.size();
      int t2count = (task2.equals(null)) ? 0: task2.size();
      int t3count = (task3.equals(null)) ? 0: task3.size();

      String[][] sqls = new String[total][4];
      for (int i=0; i<total; i++) {
        Hashtable hash = (i<t1count) ? task1.get(i) : ( (i<t1count+t2count) ? task2.get(i-t1count) : task3.get(i-t2count-t1count) );
        //System.out.println(hash);
        if (hash.containsKey("pre_query")) {
          String preQuery = hash.get("pre_query").toString();
          sqls[i][1] = (preQuery.length()>0) ? preQuery : null;
        } else {
          sqls[i][1] = null;
        }
        if (hash.containsKey("restart")) {
          sqls[i][0] = "RESTART";
        }
        if (hash.containsKey("VER")) {
          String[] cont = hash.get("VER").toString().split("_");
          sqls[i][0] = "VERSION";
          sqls[i][2] = cont[1];
          sqls[i][3] = cont[0];
        }
        if (hash.containsKey("query")) {
          sqls[i][0] = "query";
          sqls[i][2] = hash.get("query").toString();
          sqls[i][3] = null;
        }
        if (hash.containsKey("param_query")) {
          sqls[i][0] = "param_query";
          sqls[i][2] = hash.get("param_query").toString();
          sqls[i][3] = hash.get("data_url").toString();
        }
        if (hash.containsKey("zip_del_url")) {
          sqls[i][0] = "zip_delete";
          sqls[i][2] = "DELETE FROM T_POST WHERE POST_CD = ? AND ADDRESS = ? AND LAST_TIME < ? "; 
          sqls[i][3] = hash.get("zip_del_url").toString();
        }
        if (hash.containsKey("zip_add_url")) {
          sqls[i][0] = "zip_append";
          sqls[i][2] = "INSERT INTO T_POST (POST_CD, ADDRESS, LAST_TIME) VALUES (?,?,?)"; 
          sqls[i][3] = hash.get("zip_add_url").toString();
        }
      }
      return sqls;
    }

    Vector<Hashtable> getSchemaSystemTask(int init) {
      int total=0;
      Vector<Hashtable> task = new Vector<Hashtable>();
      for (int i=init; i<schemaSystemVersion.size(); i++) {
        String ver = schemaSystemVersion.get(i);
        Hashtable hash = Props.getHash("root/data/schema_system_version_"+ver);
        total += hash.size();
        Hashtable<String,String> hash1 = new Hashtable<String,String>();
        hash1.put("VER","schema_"+ver);
        task.add(hash1);
        for (int j=0; j<hash.size(); j++) {
          String key = "task"+ ((j>0) ? (new Integer(j)).toString() : "");
          task.add((Hashtable)hash.get(key));
        }
      }
      return task;
    }

    Vector<Hashtable> getDataSystemTask(int init) {
      int total=0;
      Vector<Hashtable> task = new Vector<Hashtable>();
      for (int i=init; i<dataSystemVersion.size(); i++) {
        String ver = dataSystemVersion.get(i);
        Hashtable hash = Props.getHash("root/data/data_system_version_"+ver);
        total += hash.size();
        Hashtable<String,String> hash1 = new Hashtable<String,String>();
        hash1.put("VER","data_"+ver);
        task.add(hash1);
        for (int j=0; j<hash.size(); j++) {
          String key = "task"+ ((j>0) ? (new Integer(j)).toString() : "");
          task.add((Hashtable)hash.get(key));
        }
      }
      return task;
    }

    Vector<Hashtable> getSchemaKikanTask(int init) {
      int total=0;
      Vector<Hashtable> task = new Vector<Hashtable>();
      for (int i=init; i<schemaKikanVersion.size(); i++) {
        String ver = schemaKikanVersion.get(i);
        Hashtable hash = Props.getHash("root/data/schema_kikan_version_"+ver);
        total += hash.size();
        Hashtable<String,String> hash1 = new Hashtable<String,String>();
        hash1.put("VER","schema_"+ver);
        task.add(hash1);
        for (int j=0; j<hash.size(); j++) {
          String key = "task"+ ((j>0) ? (new Integer(j)).toString() : "");
          task.add((Hashtable)hash.get(key));
        }
      }
      return task;
    }

    Vector<Hashtable> getDataKikanTask(int init) {
      int total=0;
      Vector<Hashtable> task = new Vector<Hashtable>();
      for (int i=init; i<dataKikanVersion.size(); i++) {
        String ver = dataKikanVersion.get(i);
        Hashtable hash = Props.getHash("root/data/data_kikan_version_"+ver);
        total += hash.size();
        Hashtable<String,String> hash1 = new Hashtable<String,String>();
        hash1.put("VER","data_"+ver);
        task.add(hash1);
        for (int j=0; j<hash.size(); j++) {
          String key = "task"+ ((j>0) ? (new Integer(j)).toString() : "");
          task.add((Hashtable)hash.get(key));
        }
      }
      return task;
    }

    Vector<Hashtable> getDataZipTask(int init) {
      int total=0;
      Vector<Hashtable> task = new Vector<Hashtable>();
      for (int i=init; i<dataZipVersion.size(); i++) {
        String ver = dataZipVersion.get(i);
        Hashtable hash = ZProps.getHash("root/data/data_zipcode_version_"+ver);
        total += hash.size();
        Hashtable<String,String> hash1 = new Hashtable<String,String>();
        hash1.put("VER","zipcode_"+ver);
        task.add(hash1);
        for (int j=0; j<hash.size(); j++) {
          String key = "task"+ ((j>0) ? (new Integer(j)).toString() : "");
          task.add((Hashtable)hash.get(key));
        }
      }
      return task;
    }

    public void init() {
      Props = new DngAppProperty("work/version/update.xml");
      Hashtable hash = Props.getHash("root/data");
      Enumeration e = hash.keys();
      String[] ver = new String[hash.size()];
      int i=0;
      while (e.hasMoreElements()){
        ver[i++] = e.nextElement().toString();
      }
      Arrays.sort(ver);
      for (int j=0;j<hash.size();j++) {
        Matcher m1 = Pattern.compile("^(schema_system_version)_(.+)$").matcher(ver[j]);
        Matcher m2 = Pattern.compile("^(data_system_version)_(.+)$").matcher(ver[j]);
        Matcher m3 = Pattern.compile("^(schema_kikan_version)_(.+)$").matcher(ver[j]);
        Matcher m4 = Pattern.compile("^(data_kikan_version)_(.+)$").matcher(ver[j]);
        String val = null;
        if (m1.find()) schemaSystemVersion.add(m1.group(2));
        else if (m2.find()) dataSystemVersion.add(m2.group(2));
        else if (m3.find()) schemaKikanVersion.add(m3.group(2));
        else if (m4.find()) dataKikanVersion.add(m4.group(2));
      }
      ZProps = new DngAppProperty("work/version/zipcode.xml");
      hash = ZProps.getHash("root/data");
      e = hash.keys();
      ver = new String[hash.size()];
      i=0;
      while (e.hasMoreElements()){
        ver[i++] = e.nextElement().toString();
      }
      Arrays.sort(ver);
      for (int j=0;j<hash.size();j++) {
        Matcher m5 = Pattern.compile("^(data_zipcode_version)_(.+)$").matcher(ver[j]);
        String val = null;
        if (m5.find()) dataZipVersion.add(m5.group(2));
      }

      maxSchemaSystemVersionID =  schemaSystemVersion.size()-1;
      maxDataSystemVersionID =  dataSystemVersion.size()-1;
      maxSchemaKikanVersionID =  schemaKikanVersion.size()-1;
      maxDataKikanVersionID =  dataKikanVersion.size()-1;
      maxDataZipVersionID =  dataZipVersion.size()-1;

      maxSchemaSystemVersion=schemaSystemVersion.get(maxSchemaSystemVersionID);
      if (maxDataSystemVersionID>=0)
        maxDataSystemVersion = dataSystemVersion.get(maxDataSystemVersionID);
      else maxDataSystemVersion = "0.0.0";
      maxSchemaKikanVersion = schemaKikanVersion.get(maxSchemaKikanVersionID);
      maxDataKikanVersion = dataKikanVersion.get(maxDataKikanVersionID);
      if (maxDataZipVersionID>=0)
        maxDataZipVersion = dataZipVersion.get(maxDataZipVersionID);
      else maxDataZipVersion = "2010-04-01";
    }

    public static void main(String[] args) {
      ShcUpdateDBInfo ins = new ShcUpdateDBInfo();
      System.out.println("schema_system : "+ins.getCurrentMaxVersion("schemaSystem"));
      System.out.println("data_system : "+ins.getCurrentMaxVersion("dataSystem"));
      System.out.println("schema_kikan : "+ins.getCurrentMaxVersion("schemaKikan"));
      System.out.println("data_kikan : "+ins.getCurrentMaxVersion("dataKikan"));
  
      String[][] sql = ins.getProcessSQL(args[0],args[1],args[2],args[3]);
      for (int i=0; i<sql.length; i++ ) {
        System.out.println("type: "+sql[i][0]);
        System.out.println("    pre: "+sql[i][1]);
        System.out.println("    que: "+sql[i][2]);
        System.out.println("    url: "+sql[i][3]);
      }
      System.exit(0);
    }
}
