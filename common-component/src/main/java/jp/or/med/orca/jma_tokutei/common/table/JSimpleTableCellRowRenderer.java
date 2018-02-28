package jp.or.med.orca.jma_tokutei.common.table;

import java.awt.*;
import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

public class JSimpleTableCellRowRenderer implements TableCellRenderer {
	private static final DotBorder dotBorder = new DotBorder(2,2,2,2);
    private static final Border emptyBorder  = BorderFactory.createEmptyBorder(2,2,2,2);
    private final DefaultTableCellRenderer delegate;

    private static final String[] COLUMN_ARIGNMENT_CENTER = {
    "年度","生年月日","性別","入力","健診実施日","判定日","結果通知日",
    "更新日付","必須フラグ", "H/L","更新日時","バックアップ日付","有効/無効","有効期限開始","有効期限終了","日次"};
    private static final String[] COLUMN_ARIGNMENT_RIGHT = {
    "基準値下限（男性）","基準値上限（男性）","基準値下限（女性）","基準値上限（女性）","入力下限値", "入力上限値","バックアップサイズ","履歴番号"};
    private static final String[] COLUMN_ARIGNMENT_LEFT = {
    "受診券整理番号","氏名（カナ）","氏名（漢字）","被保険者証等記号","被保険者証等番号","保険者番号","代行機関番号",
    "判定結果","保健指導レベル","総合コメント","項目番号","項目名","項目コード","検査方法","基準値範囲","単位",
    "備考","保険者名称","郵便番号","所在地","電話番号","住所","項目番号","項目名","健診パターンNo","健診パターン名称","受診者紐付けID",
    "支払代行機関番号","支払代行機関名称","結果（コード）", "結果（文字列）" ,"実施区分", "判定", "コメント","ユーザ名","権限","所見種別","所見No","所見","委託単価区分","所見種別No"};
    private static final String[] COLUMN_ARIGNMENT_RIGHT_CUNMA = {
    "単価合計","窓口負担合計","請求金額合計","単価（円）","結果（数値）",
    "単価（基本的な健診）","単価（貧血検査）","単価（心電図検査）","単価（眼底検査）","単価（人間ドック）"};
    private static final String[] COLUMN_ARIGNMENT_CENTEER_INPUT = {"必須フラグ[入力]"};
    private static final String[] COLUMN_ARIGNMENT_LEFT_INPUT = {"備考[入力]"};
    private static final String[] COLUMN_ARIGNMENT_RIGHT_INPUT = {
    	"基準値下限（男性）[入力]","基準値上限（男性）[入力]","基準値下限（女性）[入力]","基準値上限（女性）[入力]","単価（円）[入力]","備考[入力]"};

    private final Color COLOR_ABLE = ViewSettings.getAbleItemBgColor();
    private final Color COLOR_DEFAULT = Color.WHITE;

    private Vector<JSimpleTableCellRendererData> cellColors = new Vector<JSimpleTableCellRendererData>();

    public JSimpleTableCellRowRenderer(DefaultTableCellRenderer delegate) {
        this.delegate = delegate;
    }

    public Component getTableCellRendererComponent(JTable jtable, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    	// edit s.inoue 2009/11/18 フィールド値が無い場合にも空値に置き換える
    	if (value == null)
    		value ="";

        Component component = delegate.getTableCellRendererComponent(jtable,value,isSelected,hasFocus,row,column);
        if(component instanceof JComponent) {
            int lsi = jtable.getSelectionModel().getLeadSelectionIndex();

            // edit s.inoue 2010/07/12
	        ((JComponent)component).setBorder(row==lsi?dotBorder:emptyBorder);
	        dotBorder.setLastCellFlag(row==lsi&&column==jtable.getColumnCount()-1);

            String headString = (String)jtable.getColumnModel().getColumn(column).getHeaderValue();

            // edit s.inoue 2009/11/16
            setJSimpleTableCellSettings(component,headString,isSelected);

            if(!isSelected)
            {
                if(jtable.getColumnCount() < 4)
                    return component;
                String s1 = (String)jtable.getValueAt(row, 3);
                if(s1 != null)
                {
                    ((JComponent)component).setBackground(isSelected ? new Color(153, 204, 255) : jtable.getBackground());
                    if(s1.equals("基本"))
                    	component.setBackground(ViewSettings.getKihonItemBgColor());
                    else
                    if(s1.equals("詳細"))
                        component.setBackground(ViewSettings.getSyosaiItemBgColor());
                    else
                    if(s1.equals("追加"))
                        component.setBackground(ViewSettings.getTuikaItemBgColor());
                }
                for(int l = 0; l < jtable.getSelectedRowCount(); l++)
                    if(row == l)
                        jtable.setSelectionForeground(Color.GREEN);

            }

        }
        return component;
    }

    // フィールド左右寄せ設定
    private Component setJSimpleTableCellSettings(Component c,String headString,boolean isSelected){

    	for (String modelhead : COLUMN_ARIGNMENT_CENTER){
        	if(headString.equals(modelhead)){
        		delegate.setHorizontalAlignment(SwingConstants.CENTER);
        		if (!isSelected)
        			delegate.setBackground(COLOR_DEFAULT);
        		return c;
        	}
        }

        for (String modelhead : COLUMN_ARIGNMENT_RIGHT){
        	if(headString.equals(modelhead)){
        		delegate.setHorizontalAlignment(SwingConstants.RIGHT);
        		if (!isSelected)
        			delegate.setBackground(COLOR_DEFAULT);
        		return c;
        	}
        }
        for (String modelhead : COLUMN_ARIGNMENT_LEFT){
        	if(headString.equals(modelhead)){
        		delegate.setHorizontalAlignment(SwingConstants.LEFT);
        		if (!isSelected)
        			delegate.setBackground(COLOR_DEFAULT);
        		return c;
        	}
        }
        for (String modelhead : COLUMN_ARIGNMENT_RIGHT_CUNMA){
        	if(headString.equals(modelhead)){
    			delegate.setHorizontalAlignment(SwingConstants.RIGHT);
    			return c;
        	}
        }
        // 入力フィールド
        for (String modelhead : COLUMN_ARIGNMENT_CENTEER_INPUT){
        	if(headString.equals(modelhead)){
        		delegate.setHorizontalAlignment(SwingConstants.CENTER);
        		if (!isSelected)
    			delegate.setBackground(COLOR_ABLE);
    			return c;
        	}
        }
        for (String modelhead : COLUMN_ARIGNMENT_LEFT_INPUT){
        	if(headString.equals(modelhead)){
        		delegate.setHorizontalAlignment(SwingConstants.LEFT);
        		if (!isSelected)
    			delegate.setBackground(COLOR_ABLE);
    			return c;
        	}
        }
        for (String modelhead : COLUMN_ARIGNMENT_RIGHT_INPUT){
        	if(headString.equals(modelhead)){
        		delegate.setHorizontalAlignment(SwingConstants.RIGHT);
        		if (!isSelected)
    			delegate.setBackground(COLOR_ABLE);
    			return c;
        	}
        }
        return c;
    }
}

//class DotBorder extends EmptyBorder {
//    private static final BasicStroke dashed = new BasicStroke(
//        1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
//        10.0f, (new float[] {1.0f}), 0.0f);
//    private static final Color dotColor = new Color(200,150,150);
//    public DotBorder(int top, int left, int bottom, int right) {
//        super(top, left, bottom, right);
//    }
//    private boolean isLastCell = false;
//    public void setLastCellFlag(boolean flag) {
//        isLastCell = flag;
//    }
//    @Override
//    public boolean isBorderOpaque() {
//        return true;
//    }
//    @Override
//    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
//        Graphics2D g2 = (Graphics2D)g;
//        g2.translate(x,y);
//        g2.setPaint(dotColor);
//        g2.setStroke(dashed);
//        int cbx = c.getBounds().x;
//        if(cbx==0) {
//            g2.drawLine(0,0,0,h);
//        }
//        if(isLastCell) {
//            g2.drawLine(w-1,0,w-1,h);
//        }
//        if(cbx%2==0) {
//            g2.drawLine(0,0,w,0);
//            g2.drawLine(0,h-1,w,h-1);
//        }else{
//            g2.drawLine(1,0,w,0);
//            g2.drawLine(1,h-1,w,h-1);
//        }
//        g2.translate(-x,-y);
//    }
//}
