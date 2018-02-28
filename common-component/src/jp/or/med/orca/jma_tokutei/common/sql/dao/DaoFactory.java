package jp.or.med.orca.jma_tokutei.common.sql.dao;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import jp.or.med.orca.jma_tokutei.common.sql.dao.Dao;
import jp.or.med.orca.jma_tokutei.common.sql.dao.impl.JShowResultFrameDaoImpl;
import jp.or.med.orca.jma_tokutei.common.sql.dao.impl.TDataTypeCodeDaoImpl;
import jp.or.med.orca.jma_tokutei.common.sql.dao.impl.THokenjyaDaoImpl;
import jp.or.med.orca.jma_tokutei.common.sql.dao.impl.TKensakekaSonotaDaoImpl;
import jp.or.med.orca.jma_tokutei.common.sql.dao.impl.TKensakekaTokuteiDaoImpl;
import jp.or.med.orca.jma_tokutei.common.sql.dao.impl.TKojinDaoImpl;
import jp.or.med.orca.jma_tokutei.common.sql.dao.impl.TNayoseDaoImpl;
import jp.or.med.orca.jma_tokutei.common.sql.dao.impl.TShiharaiDaoImpl;
import jp.or.med.orca.jma_tokutei.common.sql.model.JShowResultFrameModel;
import jp.or.med.orca.jma_tokutei.common.sql.model.RecordModel;
import jp.or.med.orca.jma_tokutei.common.sql.model.TDataTypeCode;
import jp.or.med.orca.jma_tokutei.common.sql.model.THokenjya;
import jp.or.med.orca.jma_tokutei.common.sql.model.TKensakekaSonota;
import jp.or.med.orca.jma_tokutei.common.sql.model.TKensakekaTokutei;
import jp.or.med.orca.jma_tokutei.common.sql.model.TKojin;
import jp.or.med.orca.jma_tokutei.common.sql.model.TNayose;
import jp.or.med.orca.jma_tokutei.common.sql.model.TShiharai;

/**
 * 各テーブルのデータアクセスオブジェクトを生成するクラス
 * @author nishiyama
 *
 */
public class DaoFactory {

	// レコードモデルとDAOの関連を定義するマップ
	private static Map<Class, Class> daoMap;

	// レコードモデルとDAOの関連を定義
	static {
		daoMap = new HashMap<Class, Class>();
		daoMap.put(TKojin.class, TKojinDaoImpl.class);
		daoMap.put(TKensakekaTokutei.class, TKensakekaTokuteiDaoImpl.class);
		daoMap.put(TKensakekaSonota.class, TKensakekaSonotaDaoImpl.class);
		daoMap.put(JShowResultFrameModel.class, JShowResultFrameDaoImpl.class);
		daoMap.put(TDataTypeCode.class, TDataTypeCodeDaoImpl.class);
		// add ver2 s.inoue 2009/05/19
		daoMap.put(TNayose.class, TNayoseDaoImpl.class);
		// add s.inoue 2010/01/12
		daoMap.put(THokenjya.class, THokenjyaDaoImpl.class);
		// add s.inoue 2010/12/01
		daoMap.put(TShiharai.class, TShiharaiDaoImpl.class);
	}

	/**
	 * DAOを作成する
	 * @param con コネクション
	 * @param model レコードモデル(各テーブルのレコードを表すモデル)
	 * @return 各テーブルDAO
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 */
	public static Dao createDao(Connection con, RecordModel model)
		throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

		Class daoCls = daoMap.get(model.getClass());
		if (daoCls != null) {
			Constructor constructor = daoCls.getConstructor(new Class[]{Connection.class});
			return (Dao)constructor.newInstance(con);
		}
		return null;
	}
}