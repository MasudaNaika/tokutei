package jp.or.med.orca.jma_tokutei.dbfix;

import java.io.*;
import java.util.Vector;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import jp.co.ascsystem.lib.*;
import jp.co.ascsystem.util.*;
import jp.or.med.orca.jma_tokutei.dbfix.*;

public class ShcDBAdjust {
    public DngAppProperty Props;
    public String dbServer;
    public String dbPath0;
    public String dbPath;
    public String dbPort;
    public String dbUser;
    public String dbPass;
    public String dbUri;
    String fdbFile,dataVersion,schemaVersion,zipVersion;
    int rowID;
    String[][] updateSQLs;

    public ShcDBAdjust() {
      File pf = new File("property.xml");
      DngAppProperty props = new DngAppProperty("property.xml");
      dbPort = props.getProperty("doc/DBConfig/Port");
      dbUser = props.getProperty("doc/DBConfig/UserName");
      dbPass = props.getProperty("doc/DBConfig/Password");
      dbPath = pf.getAbsolutePath().replaceAll("property.xml$","")+props.getProperty("doc/DBConfig/Path");
      dbServer = props.getProperty("doc/DBConfig/Server");
      if (!dbServer.equals("localhost")) dbPath = props.getProperty("doc/DBConfig/AbsolutePath");
    }

    public void call(String str) {
      fdbFile = str+".fdb";
      dbUri = dbServer+"/"+dbPort+":"+dbPath+fdbFile;
      int stat = compareDBVersion(str);
      if (stat>0) execUpdate(stat);
      return;
    }

    int compareDBVersion(String str) {
      rowID = 0;
      int rows = 0;
      int rows1 = 0;
      int updateStatus = 0;
      dataVersion = "0.0.0";
      schemaVersion = "0.0.0";
      zipVersion = "9999-NOT-Available-yet";
      DngDBAccess dbm = new DngDBAccess("firebird",dbUri,dbUser,dbPass);
      if (dbm.connect()) {
        try {
          dbm.execQuery("select * from T_VERSION where ROW_ID=(select max(ROW_ID) from T_VERSION)");
          rows = dbm.Rows; 
          if (rows>0) {
            dataVersion = dbm.getData("DB_VERSION",0).toString();
            schemaVersion = dbm.getData("SCHEMA_VERSION",0).toString();
            rowID = Integer.parseInt(dbm.getData("ROW_ID",0).toString());
            System.out.println("version on "+str+".fdb: "+rowID+" Schema "+schemaVersion+" Data "+dataVersion);
          }
        } catch (Exception e) {
          rows = -1;
          System.out.println("WARNING: T_VERSION table not found. created it automatically.");
        }
        //System.out.println(rows);
        if (rows<0) {
          try {
            dbm.execUpdate("CREATE TABLE T_VERSION( ROW_ID Smallint NOT NULL, SCHEMA_VERSION Varchar(20) NOT NULL, DB_VERSION Varchar(20) NOT NULL, UPDATE_TIMESTAMP TIMESTAMP NOT NULL, PRIMARY KEY (ROW_ID) )");
            System.out.println("create T_VERSION table succeed");

          } catch ( Exception e ) {
            System.out.println("Could not create T_VERSION table..... why?. continue anyway......");
          }
        }
      }
      else {
        System.out.println("Could not connect "+dbUri);
        dataVersion = null;
        schemaVersion = null;
        zipVersion = null;
        return 0;
      }
  
      ShcUpdateDBInfo ins = new ShcUpdateDBInfo();
      String updateDataVersion,updateSchemaVersion,updateZipcode;
      if (str.equals("System")) {
        updateSchemaVersion = ins.getCurrentMaxVersion("schemaSystem");
        updateDataVersion = ins.getCurrentMaxVersion("dataSystem");
        if (dataVersion.compareTo("1.2.8")>=0) {
          dbm.execQuery("select max(LAST_TIME) from T_POST");
          rows1 = dbm.Rows; 
          if (rows1>0) {
             zipVersion=(dbm.getData("MAX",0).toString()).substring(0,10);
          }
        }
        else if (updateSchemaVersion.compareTo("1.2.8")>=0) {
          zipVersion = "2010-06-01";
        }
        updateZipcode = ins.getCurrentMaxVersion("dataZipup");
        System.out.println("Zipcode: last modify on System = "+zipVersion + " current distribution = "+ updateZipcode);
        if (zipVersion.compareTo(updateZipcode)<0) updateStatus = 1;
      } else {
        updateSchemaVersion = ins.getCurrentMaxVersion("schemaKikan");
        updateDataVersion = ins.getCurrentMaxVersion("dataKikan");
      }
      dbm.Close();
      if (dataVersion.compareTo(updateDataVersion)<0) updateStatus = 2;
      if (schemaVersion.compareTo(updateSchemaVersion)<0) updateStatus = 2;
      if (updateStatus>0) { 
        updateSQLs = ins.getProcessSQL(str,schemaVersion,dataVersion,zipVersion);
      }
      return updateStatus;
    }
  
    void execUpdate(int stat) {
      boolean RESTART=false;
      JDialog fr = new JDialog();
      fr.setTitle("日医特定健康診査システム");
      Container contentPane = fr.getContentPane();
      contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

      JProgressBar pb = new JProgressBar();
      JLabel tit;
      if (stat==1) 
        tit = new JLabel("データベースを更新しています。");
      else
        tit = new JLabel("....バージョン整合のため、データベースを更新しています。");
      pb.setStringPainted(true);
      pb.setMinimum(0);
      pb.setMaximum(updateSQLs.length);
      pb.setString("0/"+String.valueOf(updateSQLs.length));
      pb.setValue(0);
      contentPane.add(tit);
      contentPane.add(pb);
      fr.setSize(500,120);
      Dimension sc = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension sz = fr.getSize();
      fr.setLocation((sc.width-sz.width)/2,(sc.height-sz.height)/2);
      fr.setVisible(true);

      DngDBAccess dbm = new DngDBAccess("firebird",dbUri,dbUser,dbPass);
      String updateDataVersion = dataVersion;
      String updateSchemaVersion = schemaVersion;
      String updateZipVersion = zipVersion;
      if (dbm.connect()) {
        for (int i=0; i<updateSQLs.length; i++) {

          System.out.println(" type: "+updateSQLs[i][0]);
          System.out.println("    pre: "+updateSQLs[i][1]);
          System.out.println("    que: "+updateSQLs[i][2]);
          System.out.println("    url: "+updateSQLs[i][3]);

          if (updateSQLs[i][0].equals("RESTART")) {
             RESTART=true;
             break;
          }
          if (updateSQLs[i][0].equals("VERSION")) {
            if(updateSQLs[i][3].equals("schema")) updateSchemaVersion=updateSQLs[i][2];
            if(updateSQLs[i][3].equals("data")) updateDataVersion=updateSQLs[i][2];
            if(updateSQLs[i][3].equals("zipcode")) updateZipVersion=updateSQLs[i][2];
            dbm.Close();
            dbm.connect();
            System.out.println("mod for "+updateSQLs[i][3]+updateSQLs[i][2]+" start");
          }
          else if (updateSQLs[i][0].equals("query")) {
            dbm.begin();
            dbm.execUpdate(updateSQLs[i][2]);
            dbm.commit();
          }
          else {
            dbm.begin();
            if (updateSQLs[i][1]!=null) dbm.execUpdate(updateSQLs[i][1]);
            try {
              BufferedReader in = new BufferedReader(new FileReader(updateSQLs[i][3])); 
              String line;
              Vector<String> paramline = new Vector<String>();
              while ( (line = in.readLine()) != null ) {
                paramline.add(line);
              }
              in.close();
              dbm.setPrepareStatement(updateSQLs[i][2]);

              JProgressBar pbp = new JProgressBar();
              JLabel titp;
              if (stat==1)
                titp = new JLabel("[ task "+i+" ] 郵便番号辞書を更新しています。");
              else if (updateSQLs[i][3].indexOf("data1282")>0) 
                titp = new JLabel("[ task "+i+" ] 郵便番号辞書を構築しています。");
              else
                titp = new JLabel("[ task "+i+" ] データレコードを追加/削除/更新しています。");
              pbp.setStringPainted(true);
              pbp.setMinimum(0);
              pbp.setMaximum(paramline.size());
              pbp.setString("0/"+String.valueOf(paramline.size()));
              pbp.setValue(0);
              contentPane.add(titp);
              contentPane.add(pbp);
              contentPane.setVisible(false);
              contentPane.setVisible(true);
              for (int j=0;j<paramline.size();j++) {
                String[] param = (paramline.get(j)).split("\",\"");
                param[0]=param[0].replaceAll("^\"","");
                param[param.length-1]=param[param.length-1].replaceAll("\"$","");
                dbm.execParamUpdate(param);
                pbp.setValue(j+1);
                pbp.paintImmediately(pbp.getVisibleRect());
                pbp.setString(String.valueOf(j+1)+"/"+String.valueOf(paramline.size())+" (record)");
              }
              dbm.closePrepareStatement();
              dbm.commit();
              contentPane.remove(titp);
              contentPane.remove(pbp);
            } catch (IOException ie) {
              System.out.println(ie);
            }
          }
          pb.setValue(i+1);
          pb.paintImmediately(pb.getVisibleRect());
          pb.setString(String.valueOf(i+1)+"/"+String.valueOf(updateSQLs.length) + " (task)");
        }
        fr.dispose();
        if (stat>1) {
          if (updateDataVersion.equals("0.0.0")) updateDataVersion="1.0.0";
          StringBuffer buf = new StringBuffer();
          buf.append("insert into T_VERSION (ROW_ID,SCHEMA_VERSION,DB_VERSION,");
          buf.append("UPDATE_TIMESTAMP) values (");
          buf.append(rowID+1);
          buf.append(",'");
          buf.append(updateSchemaVersion);
          buf.append("','");
          buf.append(updateDataVersion);
          buf.append("',CURRENT_TIME)");
          dbm.begin();
          dbm.execUpdate(buf.toString());
          dbm.commit();
          dbm.Close();
        }
      }
      if (schemaVersion.compareTo(updateSchemaVersion)<0) System.out.println("schema version on \""+fdbFile+"\" modified "+schemaVersion+" => "+updateSchemaVersion);
      if (dataVersion.compareTo(updateDataVersion)<0) System.out.println("data version on \""+fdbFile+"\"  modified "+dataVersion+" => "+updateDataVersion);
      if (zipVersion.compareTo(updateZipVersion)<0) System.out.println("Zipcode  on \""+fdbFile+"\"  modified "+zipVersion+" => "+updateZipVersion);
      if (RESTART) {
         JOptionPane.showMessageDialog(
           null, "更新反映のためにアプリケーションの再起動が必要なDB更新を行いました。\n一旦「日医特定健康診査システム」を終了します。\n「OK」を押下後、再度、起動してください。", "日医健康診査システムDB更新",
           JOptionPane.INFORMATION_MESSAGE
         ) ;
         System.exit(0);
      }
    }

    public static void main(String[] args) {
      ShcDBAdjust in = new ShcDBAdjust();
      in.call(args[0]);
      System.exit(0);
    }
}
