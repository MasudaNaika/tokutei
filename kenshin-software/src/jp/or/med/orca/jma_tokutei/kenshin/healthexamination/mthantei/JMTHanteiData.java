package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.mthantei;

import java.io .*;
import java.net.*;

// ----------------------------------------------------------------------------
/***
	class::name	JMTHanteiData

	class::expl	ＭＴ判定データ格納クラス
*/
// ----------------------------------------------------------------------------
public class JMTHanteiData
{
	private Integer m_iGender            = null;					// 性別 (男性、女性)
	
	private Float   m_fStomach[]         = { null, null, null };	// 腹囲（実際測定、自己測定、自己申告）

	private Float   m_fNaturalFat        = null;					// 中性脂肪

	private Float   m_fInternalFatArea   = null;					// 内蔵脂肪面積

	private Float   m_fHDL               = null;					// ＨＤＬ

	private Float   m_fMaxBloodPressre[] = { null, null, null };	// 血圧（拡張時）(その他、２回目、１回目)

	private Float   m_fMinBloodPressre[] = { null, null, null };	// 血圧（収縮時）(その他、２回目、１回目)
	
	private Float   m_fDrawBloodTime     = null;					// 採血時間

	private Float   m_fBloodSugar        = null;					// 血糖値
	
	private Float   m_fHbA1c             = null;					// HbA1c
	
	private Boolean m_isQuestion1        = null;					// 服薬質問１（血圧）
	
	private Boolean m_isQuestion2        = null;					// 服薬質問２（血糖）
	
	private Boolean m_isQuestion3        = null;					// 服薬質問３（脂質）

	/***
	 *  性別設定
	 *
	 *    @param  性別
	 *
	 *    @return none
	 */
	public void setGender( String stGender )
	{
		m_iGender = stGender.isEmpty() ? null : Integer.valueOf( stGender );
	}

	/***
	 *  性別取得
	 *
	 *    @param  none
	 *
	 *    @return Integer
	 */
	public Integer getGender()
	{
		return m_iGender;
	}

	/***
	 *  腹囲設定
	 *
	 *    @param  胸囲（実測値）
	 *    @param  胸囲（測定値）
	 *    @param  胸囲（申告値）
	 *
	 *    @return none
	 */
	public void setStomach( String stActual, String stMeasure, String stReport )
	{
		m_fStomach[ 0 ] = stActual .isEmpty() ? null : Float.valueOf( stActual  );
		
		m_fStomach[ 1 ] = stMeasure.isEmpty() ? null : Float.valueOf( stMeasure );
		
		m_fStomach[ 2 ] = stReport .isEmpty() ? null : Float.valueOf( stReport  );
	}

	/***
	 *  腹囲取得
	 *
	 *    @param  none
	 *
	 *    @return Float[]
	 */
	public Float[] getStomach()
	{
		return m_fStomach;
	}

	/**
	 *  中性脂肪
	 *
	 *    @param  中性脂肪値
	 *
	 *    @return none
	 */
	public void setNaturalFat( String stNaturalFat )
	{
		m_fNaturalFat = stNaturalFat.isEmpty() ? null : Float.valueOf( stNaturalFat );
	}

	/**
	 *  中性脂肪取得
	 *
	 *    @param  none
	 *
	 *    @return Float
	 */
	public Float getNaturalFat()
	{
		return m_fNaturalFat;
	}
	
	/**
	 *  内臓脂肪面積
	 *
	 *    @param  内臓脂肪面積値
	 *
	 *    @return none
	 */
	public void setInternalFatArea( String stInternalFatArea )
	{
		m_fInternalFatArea = stInternalFatArea.isEmpty() ? null : Float.valueOf( stInternalFatArea );
	}

	/**
	 *  内臓脂肪面積取得
	 *
	 *    @param  none
	 *
	 *    @return Float
	 */
	public Float getInternalFatArea()
	{
		return m_fInternalFatArea;
	}

	/**
	 *  ＨＤＬ設定
	 *
	 *    @param  ＨＤＬ
	 *
	 *    @return none
	 */
	public void setHDL( String stHDL )
	{
		m_fHDL = stHDL.isEmpty() ? null : Float.valueOf( stHDL );
	}

	/**
	 *  ＨＤＬ取得
	 *
	 *    @param  none
	 *
	 *    @return Float
	 */
	public Float getHDL()
	{
		return m_fHDL;
	}

	/**
	 *  血圧（拡張時）設定
	 *
	 *    @param  血圧（その他）
	 *    @param  血圧（２回目）
	 *    @param  血圧（１回目）
	 *
	 *    @return none
	 */
	public void setMaxBloodPressre( String stOther, String stSecond, String stFirst )
	{
		m_fMaxBloodPressre[ 0 ] = stOther .isEmpty() ? null : Float.valueOf( stOther  );
			
		m_fMaxBloodPressre[ 1 ] = stSecond.isEmpty() ? null : Float.valueOf( stSecond );

		m_fMaxBloodPressre[ 2 ] = stFirst .isEmpty() ? null : Float.valueOf( stFirst  );
	}

	/**
	 *  血圧（拡張時）取得
	 *
	 *    @param  none
	 *
	 *    @return Float[]
	 */
	public Float[] getMaxBloodPressre()
	{
		return m_fMaxBloodPressre;
	}

	/**
	 *  血圧（縮小時）設定
	 *
	 *    @param  血圧（その他）
	 *    @param  血圧（２回目）
	 *    @param  血圧（１回目）
	 *
	 *    @return none
	 */
	public void setMinBloodPressre( String stOther, String stSecond, String stFirst )
	{
		m_fMinBloodPressre[ 0 ] = stOther .isEmpty() ? null : Float.valueOf( stOther  );
			
		m_fMinBloodPressre[ 1 ] = stSecond.isEmpty() ? null : Float.valueOf( stSecond );

		m_fMinBloodPressre[ 2 ] = stFirst .isEmpty() ? null : Float.valueOf( stFirst  );
	}

	/**
	 *  血圧（縮小時）取得
	 *
	 *    @param  none
	 *
	 *    @return Float[]
	 */
	public Float[] getMinBloodPressre()
	{
		return m_fMinBloodPressre;
	}

	/**
	 *　採血時間
	 *
	 *    @param  採血時間
	 *
	 *    @return none
	 */
	public void setDrawBloodTime( String stDrawBloodTime )
	{
		m_fDrawBloodTime = stDrawBloodTime.isEmpty() ? null : Float.valueOf( stDrawBloodTime );
	}
	
	/**
	 *　採血時間取得
	 *
	 *    @param  none
	 *
	 *    @return Float
	 */
	 public Float getDrawBloodTime()
	 {
	 	 return m_fDrawBloodTime;
	 }

	/**
	 *  血糖値設定
	 *
	 *    @param  血糖値
	 *
	 *    @return none
	 */
	public void setBloodSugar( String stBloodSugar )
	{
		m_fBloodSugar = stBloodSugar.isEmpty() ? null : Float.valueOf( stBloodSugar );
	}

	/**
	 *  血糖値取得
	 *
	 *    @param  血糖値
	 *
	 *    @return Float
	 */
	public Float getBloodSugar()
	{
		return m_fBloodSugar;
	}
	
	/**
	 *  ＨｂＡ１ｃ
	 *
	 *    @param  ＨｂＡ１ｃ
	 *
	 *    @return none
	 */
	public void setHbA1c( String stHbA1c )
	{
		m_fHbA1c = stHbA1c.isEmpty() ? null : Float.valueOf( stHbA1c );
	}
	
	/**
	 *  ＨｂＡ１ｃ取得
	 *
	 *    @param  none
	 *
	 *    @return Float
	 */
	public Float getHbA1c()
	{
		return m_fHbA1c;
	}
	
	/**
	 *  質問事項１（血圧）
	 *
	 *    @param  質問事項１（血圧）
	 *
	 *    @return none
	 */
	public void setQuestion1( String stAnswer )
	{
		if     ( stAnswer.equals("1") )
		{
			m_isQuestion1 = true;
		}
		else if( stAnswer.equals("2") )
		{
			m_isQuestion1 = false;
		}
	}
	
	/**
	 *  質問事項１（血圧）取得
	 *
	 *    @param  none
	 *
	 *    @return Boolean
	 */
	public Boolean getQuestion1()
	{
		return m_isQuestion1;
	}
	
	/**
	 *  質問事項２（血糖）
	 *
	 *    @param  質問事項２（血圧）
	 *
	 *    @return none
	 */
	public void setQuestion2( String stAnswer )
	{
		if     ( stAnswer.equals("1") )
		{
			m_isQuestion2 = true;
		}
		else if( stAnswer.equals("2") )
		{
			m_isQuestion2 = false;
		}
	}
	
	/**
	 *  質問事項２（血糖）取得
	 *
	 *    @param  none
	 *
	 *    @return Boolean
	 */
	public Boolean getQuestion2()
	{
		return m_isQuestion2;
	}
	
	/**
	 *  質問事項３（脂質）
	 *
	 *    @param  質問事項（脂質）
	 *
	 *    @return none
	 */
	public void setQuestion3( String stAnswer )
	{
		if     ( stAnswer.equals("1") )
		{
			m_isQuestion3 = true;
		}
		else if( stAnswer.equals("2") )
		{
			m_isQuestion3 = false;
		}
	}
	
	/**
	 *  質問事項３（脂質）取得
	 *
	 *    @param  none
	 *
	 *    @return Boolean
	 */
	public Boolean getQuestion3()
	{
		return m_isQuestion3;
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

