package jp.or.med.orca.jma_tokutei.common.sql.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jp.or.med.orca.jma_tokutei.common.sql.model.JShowResultFrameModel;

public interface JShowResultFrameDao extends Dao {

	/**
	 * 検査項目名と健診パターン名を取得する
	 * @param uketukeId 受付ID
	 * @param kensaNengapi 検査実施年月日
	 * @param centerKoumokuCd 検査センター項目コード
	 * @return JShowResultFrameModel
	 */
	JShowResultFrameModel selectKpNameCenterName(
			Long uketukeId,
			Integer kensaNengapi,
			Integer centerKoumokuCd) throws SQLException;

	// edit ver2 s.inoue 2009/08/26
	/**
	 * 検査項目名と健診パターン名を取得する
	 * @param uketukeId 受付ID
	 * @param kensaNengapi 検査実施年月日
	 * @param centerKoumokuCd 検査センター項目コード
	 * @return JShowResultFrameModel
	 */
	JShowResultFrameModel selectKpNameCenterName(
			Long uketukeId
			) throws SQLException;


	List<TreeMap<String, String>>
	selectTableRecord(Long uketukeId, Integer kensaNengapi, String hknjanum, Integer kenshinPatternNumber)
	throws SQLException;

	// edit s.inoue 2009/11/18
	List<TreeMap<String, String>>
	selectTableRecord(Long uketukeId, Integer kenshinPatternNumber, String hknjanum)
	throws SQLException;
}