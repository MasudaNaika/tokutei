package jp.or.med.orca.jma_tokutei.common.execlocker;

// ----------------------------------------------------------------------------
/**
	class::name	JExecLockerConfig

	class::expl	起動制限定数宣言クラス
*/
// ----------------------------------------------------------------------------
public interface JExecLockerConfig
{
	// 起動制限ファイル
	final public static String LOCK_FILENAME_BASE = ".parentlock";
	
	/* Added 2008/03/09 若月  */
	/* --------------------------------------------------- */
	public static final String ADMIN_LOCK_FILENAME = LOCK_FILENAME_BASE + ".admin";
//	public static final String KIKAN_LOCK_FILENAME_PATTERN = "\\\\.parentlock\\\\.\\d{10}"; 
	public static final String KIKAN_LOCK_FILENAME_PATTERN = "\\.parentlock\\.\\d{10}"; 
	/* --------------------------------------------------- */
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

