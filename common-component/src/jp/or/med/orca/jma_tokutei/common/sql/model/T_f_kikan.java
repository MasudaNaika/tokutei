package jp.or.med.orca.jma_tokutei.common.sql.model;

import java.util.HashMap;

/**
 * T_KOJINレコードを表すクラス
 */
public class T_f_kikan implements RecordModel {
    private String TKIKAN_NO = null;
    private String KIKAN_NAME = null;
    private String NITI_RESE = null;
    
    public String getKIKAN_NAME() {
        return KIKAN_NAME;
    }
    public void setKIKAN_NAME(String kikan_name) {
        KIKAN_NAME = kikan_name;
    }
    public String getNITI_RESE() {
        return NITI_RESE;
    }
    public void setNITI_RESE(String niti_rese) {
        NITI_RESE = niti_rese;
    }
    public String getTKIKAN_NO() {
        return TKIKAN_NO;
    }
    public void setTKIKAN_NO(String tkikan_no) {
        TKIKAN_NO = tkikan_no;
    }
}
