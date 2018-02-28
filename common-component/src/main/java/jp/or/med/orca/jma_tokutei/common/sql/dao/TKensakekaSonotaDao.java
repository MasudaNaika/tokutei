package jp.or.med.orca.jma_tokutei.common.sql.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import jp.or.med.orca.jma_tokutei.common.sql.model.TKensakekaSonota;

/**
 * 検査結果データその他データアクセスオブジェクト
 * インタフェース
 * @author nishiyama
 *
 */
public interface TKensakekaSonotaDao extends Dao {

	/**
	 * プライマリーキー指定でレコード1件取得
	 * @param uketukeId 受付ID
	 * @param kensaNengapi 検査実施年月日
	 * @param koumokuCd 項目コード
	 * @return TKensakekaSonota
	 * @throws SQLException
	 */
	 TKensakekaSonota selectByPrimaryKey(
			Long uketukeId,
			Integer kensaNengapi,
			String koumokuCd)
		throws SQLException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException;

	 // edit ver2 s.inoue 2009/08/26
	/**
	 * プライマリーキー指定でレコード1件取得
	 * @param uketukeId 受付ID
	 * @param kensaNengapi 検査実施年月日
	 * @param koumokuCd 項目コード
	 * @return TKensakekaSonota
	 * @throws SQLException
	 */
	 TKensakekaSonota selectByPrimaryKey(
			Long uketukeId,
			String koumokuCd)
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
			Integer kensaNengapi,
			String koumokuCd)
		throws SQLException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException;


		/**
		 * 受付ID,検査実施年月日指定でレコード取得
		 * @param uketukeId 受付ID
		 * @param kensaNengapi 検査実施年月日
		 * @return TKensakekaSonota
		 * @throws SQLException
		 */
		 List<TKensakekaSonota> selectByUketukeIdKensaNengapi(
				Long uketukeId,
				Integer kensaNengapi)
			throws SQLException, NoSuchMethodException,
					IllegalAccessException, InvocationTargetException;

	/**
	 * 全件レコード取得
	 * @return 検査結果データ特定レコード全件
	 */
	List<TKensakekaSonota> selectAll() throws SQLException,
		NoSuchMethodException, IllegalAccessException,
		InvocationTargetException;

	/**
	 * 検査結果データその他レコード1件挿入
	 * @param kensakekaSonota 検査結果データその他レコード
	 * @throws SQLException
	 */
	void insert(TKensakekaSonota kensakekaSonota) throws SQLException;

	/**
	 * 検査結果データその他レコード更新
	 * @param kensakekaSonota 更新対象検査結果データその他レコード
	 * @throws SQLException
	 */
	void update(TKensakekaSonota kensakekaSonota) throws SQLException;

	// add ver2 s.inoue 2009/07/07
	/**
	 * レコード更新
	 * @param tKensakekaTokutei
	 * @throws SQLException
	 */
	void updatekey(Long newUketukeId,Long curUketukeID,Integer kensaNengapi) throws SQLException;

	// add s.inoue 2009/10/08
	/**
	 * レコード更新
	 * @param tKensakekaTokutei
	 * @throws SQLException
	 */
	void updatekey(Integer newKensaNengapi,Long curUketukeID,Integer kensaNengapi) throws SQLException;

	/**
	 * キー指定でレコード1件削除
	 * @param uketukeId 受付ID
	 * @param kensaNengapi 検査実施年月日
	 * @param koumokuCd 項目コード
	 * @throws SQLException
	 */
	public void delete(
			Long uketukeId,
			Integer kensaNengapi,
			String koumokuCd) throws SQLException;

	/**
	 * 指定受付ID,検査実施年月日のレコードを削除する
	 * @param uketukeId 受付ID
	 * @param kensaNengapi 検査実施年月日
	 * @throws SQLException
	 */
	public void deleteByUketukeIdKensaNengapi(
			Long uketukeId, Integer kensaNengapi) throws SQLException;
}
