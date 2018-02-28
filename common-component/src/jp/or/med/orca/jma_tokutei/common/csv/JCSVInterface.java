package jp.or.med.orca.jma_tokutei.common.csv;

import java.io  .*;
import java.util.*;

// ----------------------------------------------------------------------------
/**
	class::name	JCSVInterface

	class::expl	this is csv interface class.
*/
// ----------------------------------------------------------------------------
abstract class JCSVInterface
{
	protected Vector<Vector<String>> m_vectTable;

	/*
	 *  コンストラクタ
	 *
	 *		@param  none
	 *
	 *		@return none
	 */
	public JCSVInterface()
	{
		m_vectTable = new Vector<Vector<String>>();
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

