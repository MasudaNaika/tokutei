package jp.or.med.orca.jma_tokutei.common.sql.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import jp.or.med.orca.jma_tokutei.common.sql.model.TKojin;
import jp.or.med.orca.jma_tokutei.common.sql.model.TNayose;

public interface TNayoseDao extends Dao {

	/**
	 * ユニークキー指定でレコード件数取得
	 * @param jyushinSeiriNo 受診券整理番号
	 * @return kojinCnt 該当レコード件数
	 * @throws SQLException
	 */
	Long selectByCountUniqueKey(String uketukeId)
		throws SQLException, NoSuchMethodException,
		IllegalAccessException, InvocationTargetException;

	/**
	 * レコード削除
	 * @param uketukeId
	 * @throws SQLException
	 */
	void delete(String uketukeId) throws SQLException;

	/**
	 * 名寄せID採番
	 * @return 受付ID
	 * @throws SQLException
	 */
	long selectNewNayoseNo() throws SQLException;

	/**
	 * 名寄せNo取得
	 * @return 名寄せNo
	 * @throws SQLException
	 */
	long selectOldNayoseNo(long uketukeId) throws SQLException;

//	/**
//	 * レコード全件取得
//	 * @return
//	 * @throws SQLException
//	 * @throws NoSuchMethodException
//	 * @throws IllegalAccessException
//	 * @throws InvocationTargetException
//	 */
//	List<TKojin> selectAll()
//		throws SQLException, NoSuchMethodException,
//			IllegalAccessException, InvocationTargetException;
//	/**
//	 * レコード挿入
//	 * @param tKojin
//	 * @throws SQLException
//	 */
//	void insert(TKojin tKojin) throws SQLException;
//
//	/**
//	 * レコード更新
//	 * @param tKojin
//	 * @throws SQLException
//	 */
//	void update(TKojin tKojin) throws SQLException;
//
//	/**
//	 * 指定受付IDのレコードを1件取得
//	 * @param uketukeId
//	 * @return
//	 * @throws SQLException
//	 * @throws NoSuchMethodException
//	 * @throws IllegalAccessException
//	 * @throws InvocationTargetException
//	 */
//	TKojin selectByPrimaryKey(String uketukeId)
//		throws SQLException, NoSuchMethodException,
//				IllegalAccessException, InvocationTargetException;

}
