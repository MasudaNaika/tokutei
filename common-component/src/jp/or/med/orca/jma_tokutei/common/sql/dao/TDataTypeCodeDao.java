package jp.or.med.orca.jma_tokutei.common.sql.dao;

import java.sql.SQLException;
import java.util.List;

import jp.or.med.orca.jma_tokutei.common.sql.model.TDataTypeCode;

public interface TDataTypeCodeDao extends Dao {

	TDataTypeCode selectByPrimaryKey(String koumokuCd, Integer codeNum) throws SQLException;
	List<TDataTypeCode> selectByAll() throws SQLException;
	
}
