package jp.or.med.orca.jma_tokutei.common.sql.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import jp.or.med.orca.jma_tokutei.common.sql.model.TKojin;
import jp.or.med.orca.jma_tokutei.common.sql.model.TNayose;

public interface TShiharaiDao extends Dao {

	/**
	 * ユニークキー指定でレコード件数取得
	 * @param shiharainum
	 * @return kojinCnt
	 */
	int selectByCountUniqueKey(String shiharainum)
		throws SQLException, NoSuchMethodException,
		IllegalAccessException, InvocationTargetException;

	/**
	 * レコード削除
	 * @param
	 * @throws SQLException
	 */
	void delete(String shiharainum)throws SQLException;
}
