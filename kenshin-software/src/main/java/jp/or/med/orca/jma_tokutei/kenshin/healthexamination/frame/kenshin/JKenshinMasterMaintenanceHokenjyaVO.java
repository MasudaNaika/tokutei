package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshin;

import org.openswing.swing.message.receive.java.ValueObjectImpl;

/**
 * 一覧Ctl画面
 * @author s.inoue
 * @version 2.0
 */
public class JKenshinMasterMaintenanceHokenjyaVO extends ValueObjectImpl {

  private String hokenjyaNumber;
  private String hokenjyaName;

  public JKenshinMasterMaintenanceHokenjyaVO() {
  }

  public String getHokenjyaNumber() {
    return hokenjyaNumber;
  }

  public String getHokenjyaName() {
	return hokenjyaName;
  }
}