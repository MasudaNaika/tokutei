package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.mthantei;

import java.io .*;
import java.net.*;

// ----------------------------------------------------------------------------
/**
	class::name	JMTHantei

	class::expl	ＭＴ判定クラス
*/
// ----------------------------------------------------------------------------
public abstract class JMTHantei
{
	final private static int GENDER_MAN   = 1;  // 男性

	final private static int GENDER_WOMAN = 2;  // 女性

	final public static int  RESULT_STANDARD_CORRESPOND = 1;  // 基準該当

	final public static int  RESULT_RESERVE_CORRESPOND  = 2;  // 予備該当

	final public static int  RESULT_NOT_CORRESPOND      = 3;  // 非該当

	final public static int  RESULT_INABILLITY          = 4;  // 判定不能

	/**
	 *  ＭＴ判定
	 *
	 *    @param  none
	 *
	 *    @return none
	 */
	// eidt s.inoue 2012/09/14
	public static int checkMT( JMTHanteiData data,boolean newHbA1c)
	{
		boolean isFirstStep = false;

		int iCategory1Lost = 0;
		boolean isCategory1 = false;

		int iCategory2Lost = 0;
		boolean isCategory2 = false;

		int iCategory3Lost = 0;
		boolean isCategory3 = false;

		/**
		 *  ステップ１
		 */
		if( data.getInternalFatArea() != null )
		{
			// 内臓脂肪面積 100 cm 以上
			if( data.getInternalFatArea().intValue() >= 100 )
			{
				isFirstStep = true;
			}
		}

		if( !isFirstStep )
		{
			if( data.getGender() == null )
			{
				// 判定不能
				return RESULT_INABILLITY;
			}

			switch( (int)data.getGender() )
			{
				/**
				 *  男性
				 */
				case GENDER_MAN:
					{
						Float iStomach[] = data.getStomach();

						if( iStomach[ 0 ] == null && iStomach[ 1 ] == null && iStomach[ 2 ] == null )
						{
							// 判定不能
							return RESULT_INABILLITY;
						}
						else
						{
							for( int i=0; i<iStomach.length; ++i )
							{
								// 欠損値検索
								if( iStomach[ i ] != null )
								{
									// 腹囲 85 cm 以上
									if( iStomach[ i ].intValue() >= 85 )
									{
										isFirstStep = true;
									}

									break;
								}
							}
						}
					}
					break;

				/**
				 *  女性
				 */
				case GENDER_WOMAN:
					{
						Float iStomach[] = data.getStomach();

						if( iStomach[ 0 ] == null && iStomach[ 1 ] == null && iStomach[ 2 ] == null )
						{
							// 判定不能
							return RESULT_INABILLITY;
						}
						else
						{
							for( int i=0; i<iStomach.length; ++i )
							{
								// 欠損値検索
								if( iStomach[ i ] != null )
								{
									// 腹囲 90 cm 以上
									if( iStomach[ i ].intValue() >= 90 )
									{
										isFirstStep = true;
									}

									break;
								}
							}
						}
					}
					break;

				default:
					{
						// 判定不能
					}
					return RESULT_INABILLITY;
			}
		}

		/**
		 *  ステップ２
		 */
		if( isFirstStep )
		{
			/**
			 *  カテゴリ１
			 */
			if( data.getQuestion2() != null )
			{
				// 血糖服薬有り
				if( data.getQuestion2() )
				{
					isCategory1 = true;
				}
			}

			if( data.getBloodSugar() != null )
			{
				// 空腹時血糖値 110 以上
				if( data.getBloodSugar().intValue() >= 110 )
				{
					isCategory1 = true;
				}
			}
			else if( data.getHbA1c() != null )
			{
				// HbA1c 5.5 以上
				// eidt s.inoue 2012/09/14
				double dblHbA1c = 0;
				if (newHbA1c)
					dblHbA1c = 0.5;

				if( data.getHbA1c().floatValue() >= (5.5 + dblHbA1c))
				{
					isCategory1 = true;
				}
			}

			if( data.getQuestion2() == null || data.getBloodSugar() == null && data.getHbA1c() == null )
			{
				// 欠損項目存在
				iCategory1Lost ++;
			}

			/**
			 *  カテゴリ２
			 */
			if( data.getQuestion1() != null )
			{
				// 血圧服薬有り
				if( data.getQuestion1() )
				{
					isCategory2 = true;
				}
			}

			Float iMaxBlood[] = data.getMaxBloodPressre();
			Float iMinBlood[] = data.getMinBloodPressre();

			for( int i=0; i<iMinBlood.length; ++i )
			{
				// 欠損値検索
				if( iMinBlood[ i ] != null )
				{
					// 収縮血圧 130 以上
					if( iMinBlood[ i ].intValue() >= 130 )
					{
						isCategory2 = true;
					}

					break;
				}
			}

			for( int i=0; i<iMaxBlood.length; ++i )
			{
				// 欠損値検索
				if( iMaxBlood[ i ] != null )
				{
					// 拡張血圧 85 以上
					if( iMaxBlood[ i ].intValue() >= 85 )
					{
						isCategory2 = true;
					}

					break;
				}
			}

			if( data.getQuestion1() == null ||
				(iMinBlood[ 0 ]     == null && iMinBlood[ 1 ] == null && iMinBlood[ 2 ] == null) ||
				(iMaxBlood[ 0 ]     == null && iMaxBlood[ 1 ] == null && iMaxBlood[ 2 ] == null) )
			{
				// 欠損項目存在
				iCategory2Lost ++;
			}

			/**
			 *  カテゴリ３
			 */
			if( data.getQuestion3() != null )
			{
				// 脂質服薬有り
				if( data.getQuestion3() )
				{
					isCategory3 = true;
				}
			}

			if( data.getNaturalFat() != null )
			{
				// 中性脂肪 150 以上
				if( data.getNaturalFat().intValue() >= 150 )
				{
					isCategory3 = true;
				}
			}

			if( data.getHDL() != null )
			{
				// HDL コレステロール 40 未満
				if( data.getHDL().intValue() < 40 )
				{
					isCategory3 = true;
				}
			}

			if( data.getQuestion3() == null || data.getNaturalFat() == null || data.getHDL() == null )
			{
				// 欠損項目存在
				iCategory3Lost ++;
			}
		}

		/**
		 *  判定処理
		 */
		int iResult    = 0;
		int iRiskPoint = 0;

		iRiskPoint += isCategory1 ? 1 : 0;
		iRiskPoint += isCategory2 ? 1 : 0;
		iRiskPoint += isCategory3 ? 1 : 0;

		if( !isFirstStep )
		{
			// 非該当
			iResult = RESULT_NOT_CORRESPOND;
		}
		else
		{
			if( iRiskPoint == 0 )
			{
				// 非該当
				iResult = RESULT_NOT_CORRESPOND;
			}
			else if( iRiskPoint == 1 )
			{
				// 予備群
				iResult = RESULT_RESERVE_CORRESPOND;
			}
			else if( iRiskPoint >= 2 )
			{
				// 該当
				iResult = RESULT_STANDARD_CORRESPOND;
			}
		}

		// 欠損値を考慮した判定
		if( iResult >= RESULT_RESERVE_CORRESPOND && (iCategory1Lost > 0 || iCategory2Lost > 0 || iCategory3Lost > 0) )
		{
			// 判定不能
			iResult =  RESULT_INABILLITY;
		}

		return iResult;
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

