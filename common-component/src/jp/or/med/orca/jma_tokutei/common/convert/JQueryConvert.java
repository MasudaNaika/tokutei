package jp.or.med.orca.jma_tokutei.common.convert;

/*
 * SQLにフィールドの値を投げる際に必要となる文字列の変換を行う
 */
public class JQueryConvert {
	private JQueryConvert()
	{
		
	}
	
	/*
	 *
	 */
	public static String queryConvert(String query)
	{
		String ret;
		
		if(query == null)
		{
			ret = new String("null ");
			return ret;
		}
		
		
		
		if(query.isEmpty())
		{
			ret = new String("null ");
			return ret;
		}
		
		ret = new String("'" + query + "' ");
		return ret;
	}

	public static String queryLikeConvert(String query)
	{
		String ret;
		
		if(query == null)
		{
			ret = new String("null ");
			return ret;
		}
		
		
		
		if(query.isEmpty())
		{
			ret = new String("null ");
			return ret;
		}
		
		ret = new String("'%" + query + "%' ");
		return ret;
	}
	
	/**
	 * 空文字とカンマを追加する。 
	 */
	public static String queryConvertAppendBlankAndComma(String query)
	{
		String ret;

		if(query == null)
		{
			ret = new String("'',");
			return ret;
		}
		
		if(query.isEmpty())
		{
			ret = new String("'',");
			return ret;
		}
		
		ret = new String("'" + query + "',");
		return ret;
	}
	public static String queryConvertAppendComma(String query)
	{
		String ret;

		if(query == null)
		{
			ret = new String("null,");
			return ret;
		}
		
		if(query.isEmpty())
		{
			ret = new String("null,");
			return ret;
		}
		
		ret = new String("'" + query + "',");
		return ret;
	}
}




//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

