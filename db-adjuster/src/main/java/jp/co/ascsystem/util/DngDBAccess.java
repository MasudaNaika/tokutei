/*           Db_Access.java
             Database access class 
             2003/02/24 Coded by Takaaki Yoshida <yoshida@saias.co.jp>
             2008/12/20 modified for NITTOKU DB Adjuster 
         Saias Co., Ltd.
*/
package jp.co.ascsystem.util;

import java.sql.*;
import java.util.*;
import java.io.*;

public class DngDBAccess {

    public int Cols;
    public int Rows;
    public Vector<String> fieldName;
    public Vector<Object> usrValue;

    private Connection con;
    private String drv[] = { 
                             "org.firebirdsql.jdbc.FBDriver", 
                             "org.postgresql.Driver", 
                             "sun.jdbc.odbc.jdbcodbcDriver"}; 
      
    private String dbPrefix[] = {
                   "jdbc:firebirdsql:",
                   "jdbc:postgresql:",
                   "jdbc:odbc:"
    };

    private int drv_no;
    private String url,driver,user,passwd;
    private int fetchCount = 0;
    private PreparedStatement pstmt = null;

    public DngDBAccess(String rdbms,String uri,String usr,String pass) {
      try {         
	 drv_no = 0;
         if (rdbms=="firebird") drv_no = 0; 
         if (rdbms=="postgresql") drv_no = 1; 
         if (rdbms=="msaccess") drv_no = 2; 

	 driver = drv[drv_no];
         url = dbPrefix[drv_no] + uri;
         this.user = usr;
         this.passwd = pass;
      } catch (Exception e) {
      }
    }
    
    public boolean connect() {
      try {
        Class.forName(driver);
        con = DriverManager.getConnection(url ,user, passwd);
        return true;
      } catch (Exception e) {
        System.err.println("[WARN] DB Connect Error "+e);
	return false; 
      }
    }
  
    public void begin() {
      try {
        if (con.isClosed()) connect();
        con.setAutoCommit(false);
      } catch (Exception e) {
      }
      return;
    }

    public void commit() {
      try {
        con.commit();
      } catch (Exception e) {
      }
      return;
    }

    public void rollback() {
      try {
        con.rollback();
      } catch (Exception e) {
      }
      return;
    }

    public Object[] getFieldNames() {
      return fieldName.toArray();
    }

    public Object[] fetchRow() {
      Vector rdat = (Vector) usrValue.elementAt(fetchCount++);
      return rdat.toArray();
    }

    public Object getData(int col,int row) {
      Vector rdat = (Vector) usrValue.elementAt(row);
      return rdat.elementAt(col);
    }

    public Object getData(String colname,int row) {
      int col = fieldName.indexOf(colname);
      Vector rdat = (Vector) usrValue.elementAt(row);
      return rdat.elementAt(col);
    }

    public int execUpdate(String sqlStmt) {
      int num;
      try {
           Statement stmt = con.createStatement();
           String query = sqlStmt;
           num = stmt.executeUpdate(query);
           stmt.close();
      }
      catch(Exception e) {
        System.err.println("[WARN] Query has been ignored\n  ["+sqlStmt+"]\n  "+e);
        num = -1;
      }
      return num;
    }

    public int setPrepareStatement(String sqlStmt) {
      int num=0;
      try {
         pstmt = con.prepareStatement(sqlStmt);
         //System.out.println(sqlStmt);
      }
      catch(Exception e) {
        System.out.println("[WARN] PrepareStatment not compiled "+e);
        num = -1;
      }
      return num;
    }

    public void closePrepareStatement() {
      try {
        pstmt.close();
      } catch (Exception e) {
      }
    }

    public int execParamUpdate(String[] param) {
      if (pstmt==null) {
        return -2;
      }
      int num;
      java.sql.Date today = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
      try {
        for (int i=0; i<param.length; i++) {
           String p = param[i];
           if (p.equals("")) pstmt.setNull(i+1, Types.OTHER);
           else if (p.equals("CURRENT_TIME")) pstmt.setDate(i+1, today);
           else pstmt.setObject(i+1, p);
        }
        num = pstmt.executeUpdate();
      }
      catch(Exception e) {
        System.out.println("[WARN] Param Query has been ignored "+e);
        num = -1;
      }
      return num;
    }

    public void execQuery(String sqlStmt) {
      try {
        Statement stmt = con.createStatement();
        String query = sqlStmt;
        ResultSet rs = stmt.executeQuery(query);
        fieldName = new Vector<String>();
        usrValue = new Vector<Object>();

        //initialize results
        fieldName.clear(); 
        usrValue.clear(); 
        fetchCount=0;

        ResultSetMetaData result = rs.getMetaData();
        this.Cols = result.getColumnCount();
        int cnt=0;
        while (rs.next()) {
          Vector<Object> rdat = new Vector<Object>();
          for (int i=1;i<=this.Cols;i++) {
              if (cnt==0) this.fieldName.addElement(result.getColumnName(i));
	      rdat.addElement(rs.getObject(i));
          }
	  this.usrValue.addElement(rdat);
          cnt++;
        }
        this.Rows = cnt;
        rs.close();
        stmt.close();
      } catch (Exception e) {
        this.Rows = -1;
        System.out.println("Select Query Error"+e);
    }
    return;
  }

    public void Close() {
       try {
	  con.close();
       } catch (Exception e) {
       System.err.println("close Error "+e);
       }
       return;
    }
   
}
