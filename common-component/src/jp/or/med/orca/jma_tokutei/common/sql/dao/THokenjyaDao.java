package jp.or.med.orca.jma_tokutei.common.sql.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import jp.or.med.orca.jma_tokutei.common.sql.model.TKojin;
import jp.or.med.orca.jma_tokutei.common.sql.model.TNayose;

public interface THokenjyaDao extends Dao {

	/**
	 * ユニークキー指定でレコード件数取得
	 * @param hknjanum
	 * @return kojinCnt
	 */
	int selectByCountUniqueKey(String hknjanum)
		throws SQLException, NoSuchMethodException,
		IllegalAccessException, InvocationTargetException;

	/**
	 * ユニークキー指定でレコード件数取得
	 * @param hknjanum,historyId
	 * @return kojinCnt
	 */
	int selectByCountUniqueKey(String hknjanum,String historyId,String itakuKubun)
		throws SQLException, NoSuchMethodException,
		IllegalAccessException, InvocationTargetException;

	/**
	 * レコード削除
	 * @param
	 * @throws SQLException
	 */
	void delete()throws SQLException;

	/**
	 * レコード削除
	 * @param hknjanum
	 * @throws SQLException
	 */
	void delete(String hknjanum,String historyId,String itakuKubun) throws SQLException;

	/**
	 * 履歴ID採番
	 * @return 履歴ID
	 * @throws SQLException
	 */
	long selectNewHistoryId(String hknjanum) throws SQLException;

}
