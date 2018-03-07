package jp.or.med.orca.jma_tokutei.common.qr;

import java .awt  .*;
import javax.swing.*;
import java .awt  .event.*;

/**
 *  JQRreader
 *
 *    ＱＲコード文字列読込クラス
 */
public class JQRReader extends JFrame implements ActionListener
{
	/**
	 *  最大項目数
	 */
	final static int MAX_ITEMS = 42;

	/**
	 *  コンストラクタ
	 *
	 *    @param  none
	 *
	 *    @return none
	 */
	public JQRReader()
	{
		super("QRCode Reader");

		JTextArea textarea = new JTextArea("");
		JButton   button   = new JButton  ("取込");

		// 書体設定
		textarea.setFont( new Font("Dialog", Font.PLAIN, 12) );
		button  .setFont( new Font("Dialog", Font.PLAIN, 12) );

		// UI 設定
		textarea.setLineWrap      ( true );
		button  .addActionListener( this );
		
		// UI 追加
		getContentPane().setLayout( new BorderLayout() );

		add( textarea, BorderLayout.CENTER );
		add( button,   BorderLayout.SOUTH  );

		/* Modified 2008/04/21 若月  */
		/* --------------------------------------------------- */
//		// フレーム作成
//		setSize( 384, 256 );
//
//		setVisible( true );
//
//		setLocationRelativeTo( null );
//
//		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		/* --------------------------------------------------- */
		// フレーム作成
		setSize( 384, 256 );
		setLocationRelativeTo( null );
		setLocationRelativeTo( null );
		setVisible( true );
		/* --------------------------------------------------- */
	}

	/**
	 *  アクションイベント
	 *
	 *    @param  イベント
	 *
	 *    @return none
	 */
	public void actionPerformed( ActionEvent event )
	{
		dispose();
	}

	/**
	 *  受診券収納項目取得
	 *
	 *    @param  文字列
	 *
	 *    @return 項目配列<br>項目配列には受診券ＱＲコード収録項目が先頭から順に入っている.
	 */
	public static String[] getList( String strInput ) throws Exception
	{
		int iBufAddress = 0;

		// 項目データ長
		int[] aryItemsLen = { 9, 20, 1, 7, 8, 11, 8, 8, 1, 1, 1, 6, 1, 6, 1, 6, 1, 6, 6, 1, 1, 8, 1, 8, 1, 8, 1, 8, 1, 8, 1, 8, 1, 8, 1, 8 };

		// 項目データ配列
		String[] aryItems = new String[ MAX_ITEMS ];

		// 固定長・可変長で分割
		String[] arySplit = strInput.split(",");

		if( arySplit == null && arySplit.length != 7 )
		{
			throw new Exception();
		}

		/**
		 *  項目 01 ～ 項目 36　受診者情報・保険者情報・受診券情報
		 */
		for( int i=0; i<aryItemsLen.length; ++i )
		{
			StringBuffer strbuf = new StringBuffer();

			for( int j=0; j<aryItemsLen[ i ]; ++j )
			{
				strbuf.append( arySplit[ 0 ].charAt( iBufAddress ++ ) );
			}

			aryItems[ i ] = strbuf.toString();
		}

		/**
		 *  項目 37 ～ 項目 39　保険者情報
		 */
		aryItems[ 36 ] = arySplit[ 1 ];
		aryItems[ 37 ] = arySplit[ 2 ];
		aryItems[ 38 ] = arySplit[ 3 ];

		/**
		 *  項目 40 ～ 項目 42　受診券情報
		 */
		aryItems[ 39 ] = arySplit[ 4 ];
		aryItems[ 40 ] = arySplit[ 5 ];
		aryItems[ 41 ] = arySplit[ 6 ];

		return aryItems;
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

