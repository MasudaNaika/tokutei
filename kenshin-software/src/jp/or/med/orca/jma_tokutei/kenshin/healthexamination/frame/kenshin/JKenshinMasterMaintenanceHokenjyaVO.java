package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshin;

import org.openswing.swing.message.receive.java.ValueObjectImpl;

/**
 * ˆê——Ctl‰æ–Ê
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