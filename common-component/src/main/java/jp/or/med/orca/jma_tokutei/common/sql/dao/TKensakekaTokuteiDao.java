package jp.or.med.orca.jma_tokutei.common.sql.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import jp.or.med.orca.jma_tokutei.common.sql.model.TKensakekaTokutei;
import jp.or.med.orca.jma_tokutei.common.sql.model.TKojin;

public interface TKensakekaTokuteiDao extends Dao {

	/**
	 * 指定受付ID、検査年月日(YYYYMMDD)レコードを1件取得
	 * @param uketukeId	受付ID
	 * @param kensaNengapi 検査年月日
	 * @return TKensakekaTokutei
	 * @throws SQLException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	TKensakekaTokutei selectByPrimaryKey(Long uketukeId, Integer kensaNengapi)
		throws SQLException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException;

	// edit ver2 s.inoue 2009/08/26
	/**
	 * 指定受付ID、検査年月日(YYYYMMDD)レコードを1件取得
	 * @param uketukeId	受付ID
	 * @param kensaNengapi 検査年月日
	 * @return TKensakekaTokutei
	 * @throws SQLException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	TKensakekaTokutei selectByPrimaryKey(Long uketukeId)
		throws SQLException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException;

	/**
	 * レコード全件取得
	 * @return
	 * @throws SQLException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	List<TKensakekaTokutei> selectAll()
		throws SQLException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException;

	 /**
	 * プライマリーキー指定でレコード件数取得
	 * @param uketukeId 受付ID
	 * @param kensaNengapi 検査実施年月日
	 * @param koumokuCd 項目コード
	 * @return TKensakekaSonota
	 * @throws SQLException
	 */
	 Long selectByCountPrimaryKey(
			Long uketukeId,
			Integer kensaNengapi)
		throws SQLException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException;
	 // add s.inoue 2012/07/04
	 public abstract TKensakekaTokutei selectKekkaTokuteiByPrimaryKey(Long long1, Integer integer)
	 throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException;

	 // edit ver2 s.inoue 2009/08/26
	 /**
	 * プライマリーキー指定でレコード件数取得
	 * @param uketukeId 受付ID
	 * @param kensaNengapi 検査実施年月日
	 * @param koumokuCd 項目コード
	 * @return TKensakekaSonota
	 * @throws SQLException
	 */
	 Long selectByCountPrimaryKey(
			Long uketukeId)
		throws SQLException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException;

	/**
	 * レコード挿入
	 * @param tKensakekaTokutei
	 * @throws SQLException
	 */
	void insert(TKensakekaTokutei tKensakekaTokutei) throws SQLException;

	/**
	 * レコード更新
	 * @param tKensakekaTokutei
	 * @throws SQLException
	 */
	void update(TKensakekaTokutei tKensakekaTokutei,boolean flgInput) throws SQLException;

	// add ver2 s.inoue 2009/07/07
	/**
	 * レコード更新
	 * @param tKensakekaTokutei
	 * @throws SQLException
	 */
	void updatekey(Long newUketukeId,Long curUketukeID,Integer kensaNengapi) throws SQLException;

	// edit s.inoue 2009/10/04
	/**
	 * レコード更新
	 * @param tKensakekaTokutei
	 * @throws SQLException
	 */
	void updateKenshinDate(Integer InputKenshinDate,Long curUketukeID,Integer kensaNengapi) throws SQLException;

	/**
	 * レコード削除
	 * @param uketukeId
	 * @throws SQLException
	 */
	void delete(String uketukeId) throws SQLException;

	/**
	 * レコード削除
	 * @param uketukeId
	 * @throws SQLException
	 */
	void delete(String uketukeId, String jissibi) throws SQLException;
}
