package jp.or.med.orca.jma_tokutei.common.printer;

import java.io .*;
import java.awt.*;
import java.net.*;
import javax.print.*;
import javax.swing.*;
import java .awt  .image.*;
import java .awt  .print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;

// ----------------------------------------------------------------------------
/**
	class::name	JHtmlPrinter

	class::expl	HTML Printer Class
*/
// ----------------------------------------------------------------------------
public class JHtmlPrinter extends JEditorPane implements Printable
{
	private JFrame m_framePreview;

	/*
	 *  コンストラクタ
	 *
	 *		@param  none
	 *
	 *		@return none
	 *
	 */
	public JHtmlPrinter()
	{
		super( "text/html", "" );
	}

	/*
	 *  コンストラクタ
	 *
	 *		@param  印刷コンテンツ
	 *
	 *		@return none
	 *
	 */
	public JHtmlPrinter( String stContents )
	{
		super( "text/html", stContents );
	}

	/*
	 *  ＨＴＭＬ読込
	 *
	 *		@param  ファイルパス
	 *
	 *		@return none
	 *
	 *		@except IOExcepiton : ファイルが見つからなかった
	 *
	 */
	public void loadHtml( String stPath ) throws IOException
	{
		FileReader reader = null;

		try
		{
			StringBuffer buf = new StringBuffer();

			reader = new FileReader( stPath );

			for( int i=0; i != -1; )
			{
				i = reader.read();

				if( i != -1 )
				{
					buf.append( (char)(i) );
				}
			}

			setText( buf.toString() );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			reader.close();
		}
	}

	/*
	 *  ＨＴＭＬ設定
	 *
	 *		@param  印刷コンテンツ
	 *
	 *		@return none
	 *
	 */
	public void setHtmlContents( String stContents )
	{
		setText( stContents );
	}

	/*
	 *  印刷開始
	 *
	 *		@param  none
	 *
	 *		@return none
	 *
	 *		@except PrinterException : 印刷に失敗した
	 *
	 */
	public void beginPrint() throws PrinterException
	{
		preview();

		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();

		// Ａ４用紙
		aset.add( MediaSizeName.ISO_A4    );

		// 白黒印刷
		aset.add( Chromaticity.MONOCHROME );

		// プリンタ
		PrinterJob job = PrinterJob.getPrinterJob();

		job.setPrintable( this );

		PrintService[] services = PrinterJob.lookupPrintServices();

		// プリンタ設定
		if( services.length > 0 )
		{
			// 	最後に見つけたサービスを適用
			job.setPrintService( services[ services.length - 1 ] );

			if( job.printDialog( aset ) )
			{
				job.print( aset );
			}
		}
		else
		{
			throw new PrinterException( "Printer Not Found !!" );
		}

		previewExit();
	}

	/*
	 *  印刷プレビュー
	 *
	 *		@param  ...
	 *
	 *		@return ...
	 *
	 */
	private void preview()
	{
		m_framePreview = new JFrame("printer preview")
		{
			/*
			 *	@Override
			 */
			public void validate()
			{
				Rectangle rect = getBounds();
				
				super.validate();
						
				/*
				 *  レンダリング結果を印刷しているのでサイズ変更禁止
				 */
				if( 800 != rect.width || 600 != rect.height )
				{
					setBounds( rect.x, rect.y, 800, 600 );
				}
			}
		};

		m_framePreview.setSize( 800, 600 );

		m_framePreview.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );

		m_framePreview.setLocationRelativeTo( null );

		try
		{
			m_framePreview.getContentPane().add( new JScrollPane( this ) );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}

		m_framePreview.setVisible( true );
	}

	/*
	 *  印刷プレビュー
	 *
	 *		@param  ...
	 *
	 *		@return ...
	 *
	 */
	private void previewExit()
	{
		m_framePreview.dispose();
	}

	/*
	 *  @override
	 *
	 *		@param  ...
	 *
	 *		@return ...
	 *
	 */
	public int print( Graphics g, PageFormat pf, int pageIndex )
	{
		if( pageIndex == 0 )
		{
			Graphics2D g2d = (Graphics2D)( g );

            g2d.translate ( pf.getImageableX(), pf.getImageableY() );

			paintComponent( g2d );

			// 印刷ページが存在する
			return Printable.PAGE_EXISTS;
		}
		else
		{
			// 印刷ページが存在しない
			return Printable.NO_SUCH_PAGE;
		}
	}
}


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}


