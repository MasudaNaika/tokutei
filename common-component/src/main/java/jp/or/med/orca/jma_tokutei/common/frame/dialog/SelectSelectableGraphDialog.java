package jp.or.med.orca.jma_tokutei.common.frame.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JConstantString;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField;
import jp.or.med.orca.jma_tokutei.common.component.PlotCombinedCategory;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenComboboxControl;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.openswing.swing.domains.java.Domain;
// add s.inoue 2013/02/13
// IDialog継承
public class SelectSelectableGraphDialog extends ApplicationFrame
    implements ActionListener,IDialog
{
    private static Logger logger = Logger.getLogger("jp/or/med/orca/jma_tokutei/common/frame/dialog/SelectSelectableGraphDialog");
    protected JPanel jpanelOut;
    protected JFreeChart freeChart;
    protected ExtendedButton jButtonCancel;
    protected JPanel jPanelButtonArea;
    protected ExtendedOpenComboboxControl jComboBox_DokujiKoumokuCD;
    protected DynamicTimeSeriesCollection dataset;
    private static String GLAPH_TITLE = "健診項目種別経年データグラフ";
    private String uketukeid;
    private String hknjanum;
    private static String KENSIN_HIGHT = "9N001000000000001";
    // protected ExtendedTextField jLabel_Kijunti;
    private JPanel jPanel_KijuntiPanel;

    private void initialize()
    {
    	// eidt s.inoue 2012/07/06
        setSize(ViewSettings.getFrameCommonSize());
        // setDefaultCloseOperation(3);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        java.awt.Image image = (new ImageIcon(JPath.Ico_Common_METABO)).getImage();
        setIconImage(image);
    }

    SelectSelectableGraphDialog(ArrayList arraylist)
    {
        super(GLAPH_TITLE);
        jpanelOut = null;
        freeChart = null;
        jButtonCancel = null;
        jPanelButtonArea = null;
        jComboBox_DokujiKoumokuCD = null;
        dataset = null;
        uketukeid = "";
        hknjanum = "";
//        jLabel_Kijunti = null;
        jPanel_KijuntiPanel = null;
        initialize();
        Hashtable hashtable = (Hashtable)arraylist.get(0);
        uketukeid = (String)hashtable.get("UKETUKE_ID");
        hknjanum = (String)hashtable.get("HKNJANUM");

        // add s.inoue 2012/07/06
        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);

        // move s.inoue 2012/07/05
        // add(getJPanel_RadioPanel(), "South");
        add(getJPanelGraphArea(),  BorderLayout.CENTER);
        freeChart = createChart(JConstantString.codesGraphShintaisokutei);
        ChartPanel chartpanel = new ChartPanel(freeChart);
        chartpanel.setFillZoomRectangle(true);
        chartpanel.setMouseWheelEnabled(true);
        chartpanel.setPreferredSize(new Dimension(900, 500));
        chartpanel.setFillZoomRectangle(true);
        chartpanel.setZoomAroundAnchor(true);

//        jpanelOut = new JPanel();
//        jpanelOut.add(chartpanel);
//        jpanelOut.setPreferredSize(ViewSettings.getFrameCommonSize());

		// add s.inoue 2012/11/12
		jLabel_Title = new TitleLabel("tokutei.keinendatagraf.frame.title","tokutei.keinendatagraf.frame.guidance");
		jLabel_Title.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));

		// add s.inoue 2012/11/14
		this.setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());

        add(getJPanelButtonArea(),  BorderLayout.NORTH);

        // add(getJPanelGraphArea(chartpanel), "Center");
        GridBagConstraints gridbagconstraints1 = new GridBagConstraints();
        gridbagconstraints1.gridx = 0;
        gridbagconstraints1.gridy = 0;
        gridbagconstraints1.insets = new Insets(5, 5, 5, 5);
        // eidt s.inoue 2012/07/05
        jPanel_KijuntiPanel.add(chartpanel,gridbagconstraints1);

        // add s.inoue 2012/07/11
        chartpanel.repaint();
        jPanel_KijuntiPanel.repaint();

        setComboboxDokujiKenshin();
        setLocationRelativeTo(null);
    }

    private JPanel getJPanelButtonArea()
    {
        if(jPanelButtonArea == null)
        {
            GridBagConstraints gridbagconstraints4 = new GridBagConstraints();
            gridbagconstraints4.gridx = 0;
            gridbagconstraints4.gridy = 0;
            gridbagconstraints4.insets = new Insets(5, 5, 0, 0);
            // eidt s.inoue 2012/11/13
            gridbagconstraints4.anchor = GridBagConstraints.WEST;
            gridbagconstraints4.gridwidth = 3;

            GridBagConstraints gridbagconstraints = new GridBagConstraints();
            gridbagconstraints.gridx = 1;
            gridbagconstraints.gridy = 1;
            gridbagconstraints.insets = new Insets(5, 5, 5, 0);
            GridBagConstraints gridbagconstraints1 = new GridBagConstraints();
            gridbagconstraints1.gridx = 3;
            gridbagconstraints1.gridy = 1;
            gridbagconstraints1.insets = new Insets(5, 5, 5, 5);
            gridbagconstraints1.weightx = 1.0D;
            gridbagconstraints1.anchor = 17;
            GridBagConstraints gridbagconstraints2 = new GridBagConstraints();
            gridbagconstraints2.gridx = 2;
            gridbagconstraints2.gridy = 1;
            gridbagconstraints2.insets = new Insets(5, 30, 5, 5);
            GridBagConstraints gridbagconstraints3 = new GridBagConstraints();
            gridbagconstraints3.gridx = 4;
            gridbagconstraints3.gridy = 1;
            gridbagconstraints3.insets = new Insets(5, 30, 5, 5);
            // eidt s.inoue 2012/11/13
            gridbagconstraints3.anchor = GridBagConstraints.WEST;

            jPanelButtonArea = new JPanel();
            jPanelButtonArea.setLayout(new GridBagLayout());
            jPanelButtonArea.add(getJButtonCancel(), gridbagconstraints);
            JLabel jlabel = new JLabel("健診項目種別");
            jlabel.setFont(new Font("ＭＳ ゴシック", 0, 12));
            jPanelButtonArea.add(jlabel, gridbagconstraints2);
            jPanelButtonArea.add(getJComboBox_DokujiKoumokuCD(), gridbagconstraints1);

            // add s.inoue 2012/11/13
            jPanelButtonArea.add(getJPanel_TitleArea(), gridbagconstraints4);
        }
        return jPanelButtonArea;
    }

	/**
	 * This method initializes jPanel_TitleArea
	 *
	 * @return javax.swing.JPanel
	 */
	protected JPanel jPanel_TitleArea = null;
	protected ExtendedLabel jLabel_Title = null;

	private JPanel getJPanel_TitleArea() {
		if (jPanel_TitleArea == null) {
			GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
			gridBagConstraints26.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints26.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints26.gridy = 1;
			gridBagConstraints26.gridx = 0;

			GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
			gridBagConstraints25.gridy = 0;
//			gridBagConstraints25.ipady = 10;
			gridBagConstraints25.anchor = GridBagConstraints.WEST;
			// gridBagConstraints25.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints25.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints25.weightx = 1.0D;
			gridBagConstraints25.gridx = 0;

			jPanel_TitleArea = new JPanel();
			jPanel_TitleArea.setLayout(new GridBagLayout());
			jPanel_TitleArea.setName("jPanel2");
			jPanel_TitleArea.add(jLabel_Title, gridBagConstraints25);
		}
		return jPanel_TitleArea;
	}

    private JFreeChart createChart(String as[])
    {
        CategoryDataset categorydataset = createDataset(as);
        NumberAxis numberaxis = new NumberAxis("Value");
        numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
        lineandshaperenderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
        CategoryPlot categoryplot = new CategoryPlot(categorydataset, null, numberaxis, lineandshaperenderer);
        categoryplot.setDomainGridlinesVisible(true);
        CategoryAxis categoryaxis = new CategoryAxis("健診年月日");
        PlotCombinedCategory plotcombinedcategory = new PlotCombinedCategory(categoryaxis, new NumberAxis("単位"));
        plotcombinedcategory.add(categoryplot, 1);
        JFreeChart jfreechart = new JFreeChart(GLAPH_TITLE, new Font("Dialog", 1, 18), plotcombinedcategory, true);
        return jfreechart;
    }

    private CategoryDataset createDataset(String as[])
    {
    	// eidt s.inoue 2012/07/10
        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
//        StringBuilder stringbuilder = new StringBuilder();
//        stringbuilder.append("※基準値： ");
        for(int i = 0; i < as.length; i++)
        {
            ArrayList arraylist = getTeikeiMaster(as[i]);
            for(int j = 0; j < arraylist.size(); j++)
            {
                Hashtable hashtable1 = (Hashtable)arraylist.get(j);
                String k = ((String)hashtable1.get("KENSA_NENGAPI")).substring(2, 4);
                int l = Integer.parseInt(((String)hashtable1.get("KENSA_NENGAPI")).substring(4, 6));
                int i1 = Integer.parseInt(((String)hashtable1.get("KENSA_NENGAPI")).substring(6, 8));
                float f = Float.parseFloat(String.valueOf(hashtable1.get("KEKA_TI")));
                // String s2 = (new StringBuilder()).append(k).append("年").append(l).append("月").append(i1).append("日").toString();
                String s2 = (new StringBuilder()).append(k).append("'").append(l).append(".").append(i1).toString();

                // add s.inoue 2012/07/11
                // 基準値処理
                StringBuilder stringbuilder = new StringBuilder();
                // stringbuilder.append("※基準値： ");
                if(arraylist.size() != 0){
	                Hashtable hashtable = (Hashtable)arraylist.get(0);
	                // eidt s.inoue 2012/07/11
	                // stringbuilder.append((new StringBuilder()).append("[").append((String)hashtable.get("KOUMOKU_NAME")).append("：").toString());
	                stringbuilder.append("[");
	                if(((String)hashtable.get("SEX")).equals("1"))
	                {
	                    if(!((String)hashtable.get("DS_KAGEN")).equals(""))
	                        stringbuilder.append((new StringBuilder()).append((String)hashtable.get("DS_KAGEN")).append("～").toString());
	                    if(!((String)hashtable.get("DS_JYOUGEN")).equals(""))
	                    {
	                        String s = stringbuilder.substring(stringbuilder.length() - 1, stringbuilder.length());
	                        if(!s.equals("～"))
	                            stringbuilder.append((new StringBuilder()).append("～").append((String)hashtable.get("DS_JYOUGEN")).toString());
	                        else
	                            stringbuilder.append((String)hashtable.get("DS_JYOUGEN"));
	                    }
	                } else
	                {
	                    if(!((String)hashtable.get("JS_KAGEN")).equals(""))
	                        stringbuilder.append((new StringBuilder()).append("上限値|").append((String)hashtable.get("JS_KAGEN")).append("～").toString());
	                    if(!((String)hashtable.get("JS_JYOUGEN")).equals(""))
	                    {
	                        String s1 = stringbuilder.substring(stringbuilder.length() - 1, stringbuilder.length());
	                        if(!s1.equals("～"))
	                            stringbuilder.append((new StringBuilder()).append("～").append((String)hashtable.get("JS_JYOUGEN")).toString());
	                        else
	                            stringbuilder.append((String)hashtable.get("JS_JYOUGEN"));
	                    }
	                }
	                if(stringbuilder.substring(stringbuilder.length() - 1, stringbuilder.length()).equals("："))
	                    stringbuilder.delete(stringbuilder.lastIndexOf("["), stringbuilder.length());
	                else
	                    stringbuilder.append("]");
	            }
                String s3 = (new StringBuilder()).append(
                		(String)hashtable1.get("KOUMOKU_NAME")).append(" ").append(
                				hashtable1.get("KENSA_HOUHOU") != null ? (String)hashtable1.get("KENSA_HOUHOU") : "").append(stringbuilder.toString().equals("[]")?"":stringbuilder.toString()).toString();
                defaultcategorydataset.addValue(f, s3, s2);
            }

//            if(arraylist.size() == 0)
//                continue;
//            Hashtable hashtable = (Hashtable)arraylist.get(0);
//            stringbuilder.append((new StringBuilder()).append("[").append((String)hashtable.get("KOUMOKU_NAME")).append("：").toString());
//            if(((String)hashtable.get("SEX")).equals("1"))
//            {
//                if(!((String)hashtable.get("DS_KAGEN")).equals(""))
//                    stringbuilder.append((new StringBuilder()).append((String)hashtable.get("DS_KAGEN")).append("～").toString());
//                if(!((String)hashtable.get("DS_JYOUGEN")).equals(""))
//                {
//                    String s = stringbuilder.substring(stringbuilder.length() - 1, stringbuilder.length());
//                    if(!s.equals("～"))
//                        stringbuilder.append((new StringBuilder()).append("～").append((String)hashtable.get("DS_JYOUGEN")).toString());
//                    else
//                        stringbuilder.append((String)hashtable.get("DS_JYOUGEN"));
//                }
//            } else
//            {
//                if(!((String)hashtable.get("JS_KAGEN")).equals(""))
//                    stringbuilder.append((new StringBuilder()).append("上限値|").append((String)hashtable.get("JS_KAGEN")).append("～").toString());
//                if(!((String)hashtable.get("JS_JYOUGEN")).equals(""))
//                {
//                    String s1 = stringbuilder.substring(stringbuilder.length() - 1, stringbuilder.length());
//                    if(!s1.equals("～"))
//                        stringbuilder.append((new StringBuilder()).append("～").append((String)hashtable.get("JS_JYOUGEN")).toString());
//                    else
//                        stringbuilder.append((String)hashtable.get("JS_JYOUGEN"));
//                }
//            }
//            if(stringbuilder.substring(stringbuilder.length() - 1, stringbuilder.length()).equals("："))
//                stringbuilder.delete(stringbuilder.lastIndexOf("["), stringbuilder.length());
//            else
//                stringbuilder.append("]");
        }
        // add s.inoue 2012/07/11
//        jLabel_Kijunti.setText(stringbuilder.toString());
        return defaultcategorydataset;
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        if(actionevent.getSource() == jButtonCancel)
            dispose();
        setVisible(false);
    }

//    private ExtendedTextField getJTextField_Kijunti()
//    {
//        if(jLabel_Kijunti == null)
//            jLabel_Kijunti = new ExtendedTextField();
//        return jLabel_Kijunti;
//    }

    private JPanel getJPanel_RadioPanel()
    {
        if(jPanel_KijuntiPanel == null)
        {
            GridBagConstraints gridbagconstraints = new GridBagConstraints();
            gridbagconstraints.gridx = 0;
            gridbagconstraints.gridy = 1;
            gridbagconstraints.ipadx = 5;
            gridbagconstraints.insets = new Insets(10, 10, 10, 10);
            jPanel_KijuntiPanel = new JPanel();
            jPanel_KijuntiPanel.setLayout(new GridBagLayout());
            jPanel_KijuntiPanel.setOpaque(false);
            // jPanel_KijuntiPanel.add(getJTextField_Kijunti(), gridbagconstraints);
        }
        return jPanel_KijuntiPanel;
    }

    // add s.inoue 2012/07/05
    // グラフパネル(中央)
    private JPanel getJPanelGraphArea()
    {
        if(jpanelOut == null)
        {
            GridBagConstraints gridbagconstraints = new GridBagConstraints();
            gridbagconstraints.gridx = 1;
            gridbagconstraints.gridy = 0;
//            GridBagConstraints gridbagconstraints1 = new GridBagConstraints();
//            gridbagconstraints1.gridx = 2;
//            gridbagconstraints1.gridy = 0;
//            gridbagconstraints1.insets = new Insets(5, 5, 5, 5);
            jpanelOut = new JPanel();
            jpanelOut.setLayout(new GridBagLayout());

//            jpanelOut.add(chartpanel, gridbagconstraints);
            jpanelOut.add(getJPanel_RadioPanel(), gridbagconstraints);
        }
        return jpanelOut;
    }

    private ExtendedButton getJButtonCancel()
    {
        if(jButtonCancel == null)
        {
            ExtendedImageIcon extendedimageicon = new ExtendedImageIcon(JPath.Ico_Common_Back);
            ImageIcon imageicon = extendedimageicon.setStrechIcon(this, JPath.CONST_FIX_ICON);
            jButtonCancel = new ExtendedButton("Return", "戻る(R)", "戻る(ALT+R)", 82, imageicon);
            jButtonCancel.addActionListener(this);
        }
        return jButtonCancel;
    }

    private ExtendedOpenComboboxControl getJComboBox_DokujiKoumokuCD()
    {
        if(jComboBox_DokujiKoumokuCD == null)
        {
            jComboBox_DokujiKoumokuCD = new ExtendedOpenComboboxControl(jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode.IME_OFF);
            jComboBox_DokujiKoumokuCD.setCanCopy(true);
            setComboKoumokuCode();
            jComboBox_DokujiKoumokuCD.setAttributeName("T_KENSHINMASTER");
            jComboBox_DokujiKoumokuCD.setDomainId("T_KENSHINMASTER");
        }
        return jComboBox_DokujiKoumokuCD;
    }

    private void setComboKoumokuCode()
    {
        String as[] = JConstantString.codeGraphTokuteiKey;
        JApplication.graphDomain = new Domain("T_KENSHINMASTER");
        for(int i = 0; i < as.length; i++)
            JApplication.graphDomain.addDomainPair(as[i], as[i]);

        JApplication.domains.put(JApplication.graphDomain.getDomainId(), JApplication.graphDomain);
    }

    private void setComboboxDokujiKenshin()
    {
        jComboBox_DokujiKoumokuCD.setSelectedIndex(0);
        jComboBox_DokujiKoumokuCD.getComboBox().addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent itemevent)
            {
                ItemEvent _tmp = itemevent;
                if(itemevent.getStateChange() == 1)
                {
                   	// eidt s.inoue 2012/07/10
                	// jLabel_Kijunti.setText("");
                    String s = jComboBox_DokujiKoumokuCD.getValueAt(jComboBox_DokujiKoumokuCD.getSelectedIndex());
                    HashMap hashmap = JConstantString.getGraphTokuteiKensa();
                    String as[] = (String[])hashmap.get(s);
                    freeChart = createChart(as);
                    freeChart.fireChartChanged();
                    ChartPanel chartpanel = new ChartPanel(freeChart);
                    chartpanel.setFillZoomRectangle(true);
                    chartpanel.setMouseWheelEnabled(true);
                    chartpanel.setPreferredSize(new Dimension(900, 500));
                    jpanelOut.removeAll();
                    jpanelOut.add(chartpanel);
                    jpanelOut.invalidate();
                    jpanelOut.validate();
                    invalidate();
                    validate();
                }
            }

//            final SelectSelectableGraphDialog this$0;
//
//
//            {
//                this$0 = SelectSelectableGraphDialog.this;
//                super();
//            }
        });
    }

    private ArrayList getTeikeiMaster(String s)
    {
        try
        {
            JApplication.hokenjyaDatabase = JConnection.ConnectHokenjyaDatabase();
        }
        catch(Exception exception)
        {
            System.out.println(exception.getMessage());
            JErrorMessage.show("M1000", null);
            System.exit(1);
        }
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("SELECT DISTINCT ");
        stringbuilder.append("TS.KENSA_NENGAPI,TK.KOUMOKU_CD,TK.KOUMOKU_NAME,TK.KENSA_HOUHOU, TK.TANI, TK.DS_JYOUGEN, TK.DS_KAGEN, ");
        stringbuilder.append("TK.JS_JYOUGEN, TK.JS_KAGEN, TS.JISI_KBN, TS.H_L, TK.HISU_FLG, TK.DATA_TYPE,TS.KEKA_TI,KN.SEX ");
        stringbuilder.append("FROM T_KOJIN KN,T_KENSAKEKA_SONOTA TS,T_KENSHINMASTER TK, ");
        stringbuilder.append("  (SELECT TN.UKETUKE_ID,TK.KENSA_NENGAPI ");
        stringbuilder.append("  FROM T_NAYOSE TN,T_KENSAKEKA_TOKUTEI TK ");
        stringbuilder.append("  WHERE TN.NAYOSE_NO = ");
        stringbuilder.append("   (SELECT NAYOSE_NO FROM T_NAYOSE TN ");
        stringbuilder.append("   WHERE TN.UKETUKE_ID =  ");
        stringbuilder.append(JQueryConvert.queryConvert(uketukeid));
        stringbuilder.append("  )AND TN.UKETUKE_ID = TK.UKETUKE_ID) TN_KEY ");
        stringbuilder.append("WHERE TS.UKETUKE_ID = TN_KEY.UKETUKE_ID ");
        stringbuilder.append("AND TS.UKETUKE_ID = KN.UKETUKE_ID ");
        stringbuilder.append("AND TS.KENSA_NENGAPI = TN_KEY.KENSA_NENGAPI ");
        stringbuilder.append("AND TS.KEKA_TI <> '' ");
        stringbuilder.append("AND TS.KOUMOKU_CD = TK.KOUMOKU_CD ");
        stringbuilder.append("AND TK.HKNJANUM = ");
        stringbuilder.append(JQueryConvert.queryConvert(hknjanum));
        stringbuilder.append("AND TK.DATA_TYPE = '1'");
        if(s.equals(""))
        {
            stringbuilder.append("AND TS.KOUMOKU_CD = ");
            stringbuilder.append(JQueryConvert.queryConvert(KENSIN_HIGHT));
        } else
        {
            stringbuilder.append("AND TS.KOUMOKU_CD = ");
            stringbuilder.append(JQueryConvert.queryConvert(s));
        }
        stringbuilder.append("ORDER BY KENSA_NENGAPI,XMLITEM_SEQNO ");
        ArrayList arraylist = null;
        try
        {
            arraylist = JApplication.kikanDatabase.sendExecuteQuery(stringbuilder.toString());
        }
        catch(SQLException sqlexception)
        {
            logger.error(sqlexception.getMessage());
        }
        return arraylist;
    }

	@Override
	public RETURN_VALUE getStatus() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public String getFilePath() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public String getKenshinDate() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Integer getPrintSelect() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public String getK_PNo() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public String getK_PName() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public String getK_PNote() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void setText(String text) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void setMessageTitle(String title) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void setDialogSelect(boolean enabled) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void setDialogTitle(String title) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void setSaveFileName(String title) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void setShowCancelButton(boolean isShowCancel) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public Vector getDataSelect() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}






//package jp.or.med.orca.jma_tokutei.common.frame.dialog;
//
//import java.awt.*;
//import java.awt.event.*;
//import java.io.PrintStream;
//import java.sql.SQLException;
//import java.util.*;
//import javax.swing.*;
//import jp.or.med.orca.jma_tokutei.common.app.*;
//import jp.or.med.orca.jma_tokutei.common.component.*;
//import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
//import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
//import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
//import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenComboboxControl;
//import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
//import org.apache.log4j.Logger;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.axis.CategoryAxis;
//import org.jfree.chart.axis.NumberAxis;
//import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
//import org.jfree.chart.plot.CategoryPlot;
//import org.jfree.chart.renderer.category.LineAndShapeRenderer;
//import org.jfree.data.category.CategoryDataset;
//import org.jfree.data.category.DefaultCategoryDataset;
//import org.jfree.data.time.DynamicTimeSeriesCollection;
//import org.jfree.ui.ApplicationFrame;
//import org.openswing.swing.domains.java.Domain;
//
//public class SelectSelectableGraphDialog extends ApplicationFrame
//    implements ActionListener
//{
//
//    private void initialize()
//    {
//        setSize(ViewSettings.getFrameCommonSize());
//        setDefaultCloseOperation(3);
//        java.awt.Image image = (new ImageIcon(JPath.Ico_Common_METABO)).getImage();
//        setIconImage(image);
//    }
//
//    SelectSelectableGraphDialog(ArrayList arraylist)
//    {
//        super(GLAPH_TITLE);
//        jpanelOut = null;
//        freeChart = null;
//        jButtonCancel = null;
//        jPanelButtonArea = null;
//        jComboBox_DokujiKoumokuCD = null;
//        dataset = null;
//        uketukeid = "";
//        hknjanum = "";
//        jLabel_Kijunti = null;
//        jPanel_KijuntiPanel = null;
//        initialize();
//        Hashtable hashtable = (Hashtable)arraylist.get(0);
//        uketukeid = (String)hashtable.get("UKETUKE_ID");
//        hknjanum = (String)hashtable.get("HKNJANUM");
//
//        // move s.inoue 2012/07/05
//        // add(getJPanel_RadioPanel(), "South");
//        freeChart = createChart(JConstantString.codesGraphShintaisokutei);
//        ChartPanel chartpanel = new ChartPanel(freeChart);
//        chartpanel.setFillZoomRectangle(true);
//        chartpanel.setMouseWheelEnabled(true);
//        chartpanel.setPreferredSize(new Dimension(900, 500));
//        jpanelOut = new JPanel();
//        jpanelOut.add(chartpanel);
//        jpanelOut.setPreferredSize(ViewSettings.getFrameCommonSize());
//        add(getJPanelButtonArea(), "North");
//        add(jpanelOut, "Center");
//        setComboboxDokujiKenshin();
//        setLocationRelativeTo(null);
//    }
//
//    private JPanel getJPanelButtonArea()
//    {
//        if(jPanelButtonArea == null)
//        {
//            GridBagConstraints gridbagconstraints = new GridBagConstraints();
//            gridbagconstraints.gridx = 1;
//            gridbagconstraints.gridy = 0;
//            gridbagconstraints.insets = new Insets(5, 10, 5, 0);
//            GridBagConstraints gridbagconstraints1 = new GridBagConstraints();
//            gridbagconstraints1.gridx = 3;
//            gridbagconstraints1.gridy = 0;
//            gridbagconstraints1.insets = new Insets(5, 5, 5, 5);
//            gridbagconstraints1.weightx = 1.0D;
//            gridbagconstraints1.anchor = 17;
//            GridBagConstraints gridbagconstraints2 = new GridBagConstraints();
//            gridbagconstraints2.gridx = 2;
//            gridbagconstraints2.gridy = 0;
//            gridbagconstraints2.insets = new Insets(5, 30, 5, 5);
//            GridBagConstraints gridbagconstraints3 = new GridBagConstraints();
//            gridbagconstraints3.gridx = 4;
//            gridbagconstraints3.gridy = 0;
//            gridbagconstraints3.insets = new Insets(5, 30, 5, 5);
//            jPanelButtonArea = new JPanel();
//            jPanelButtonArea.setLayout(new GridBagLayout());
//            jPanelButtonArea.add(getJButtonCancel(), gridbagconstraints);
//            JLabel jlabel = new JLabel("健診項目セット");
//            jlabel.setFont(new Font("ＭＳ ゴシック", 0, 12));
//            jPanelButtonArea.add(jlabel, gridbagconstraints2);
//            jPanelButtonArea.add(getJComboBox_DokujiKoumokuCD(), gridbagconstraints1);
//        }
//        return jPanelButtonArea;
//    }
//
//    private JFreeChart createChart(String as[])
//    {
//        CategoryDataset categorydataset = createDataset(as);
//        NumberAxis numberaxis = new NumberAxis("Value");
//        numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//        LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
//        lineandshaperenderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
//        CategoryPlot categoryplot = new CategoryPlot(categorydataset, null, numberaxis, lineandshaperenderer);
//        categoryplot.setDomainGridlinesVisible(true);
//        CategoryAxis categoryaxis = new CategoryAxis("健診年月日");
//        PlotCombinedCategory plotcombinedcategory = new PlotCombinedCategory(categoryaxis, new NumberAxis("単位"));
//        plotcombinedcategory.add(categoryplot, 1);
//        JFreeChart jfreechart = new JFreeChart(GLAPH_TITLE, new Font("Dialog", 1, 18), plotcombinedcategory, true);
//        return jfreechart;
//    }
//
//    private CategoryDataset createDataset(String as[])
//    {
//        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
//        StringBuilder stringbuilder = new StringBuilder();
//        stringbuilder.append("※基準値： ");
//        for(int i = 0; i < as.length; i++)
//        {
//            ArrayList arraylist = getTeikeiMaster(as[i]);
//            for(int j = 0; j < arraylist.size(); j++)
//            {
//                Hashtable hashtable1 = (Hashtable)arraylist.get(j);
//                int k = Integer.parseInt(((String)hashtable1.get("KENSA_NENGAPI")).substring(0, 4));
//                int l = Integer.parseInt(((String)hashtable1.get("KENSA_NENGAPI")).substring(5, 6));
//                int i1 = Integer.parseInt(((String)hashtable1.get("KENSA_NENGAPI")).substring(7, 8));
//                float f = Float.parseFloat(String.valueOf(hashtable1.get("KEKA_TI")));
//                String s2 = (new StringBuilder()).append(k).append("年").append(l).append("月").append(i1).append("日").toString();
//                String s3 = (new StringBuilder()).append((String)hashtable1.get("KOUMOKU_NAME")).append(" ").append(hashtable1.get("KENSA_HOUHOU") != null ? (String)hashtable1.get("KENSA_HOUHOU") : "").toString();
//                defaultcategorydataset.addValue(f, s3, s2);
//            }
//
//            if(arraylist.size() == 0)
//                continue;
//            Hashtable hashtable = (Hashtable)arraylist.get(0);
//            stringbuilder.append((new StringBuilder()).append("[").append((String)hashtable.get("KOUMOKU_NAME")).append("：").toString());
//            if(((String)hashtable.get("SEX")).equals("1"))
//            {
//                if(!((String)hashtable.get("DS_JYOUGEN")).equals(""))
//                    stringbuilder.append((new StringBuilder()).append((String)hashtable.get("DS_JYOUGEN")).append("～").toString());
//                if(!((String)hashtable.get("DS_KAGEN")).equals(""))
//                {
//                    String s = stringbuilder.substring(stringbuilder.length() - 1, stringbuilder.length());
//                    if(!s.equals("～"))
//                        stringbuilder.append((new StringBuilder()).append("～").append((String)hashtable.get("DS_KAGEN")).toString());
//                    else
//                        stringbuilder.append((String)hashtable.get("DS_KAGEN"));
//                }
//            } else
//            {
//                if(!((String)hashtable.get("JS_JYOUGEN")).equals(""))
//                    stringbuilder.append((new StringBuilder()).append("上限値|").append((String)hashtable.get("JS_JYOUGEN")).append("?").toString());
//                if(!((String)hashtable.get("JS_KAGEN")).equals(""))
//                {
//                    String s1 = stringbuilder.substring(stringbuilder.length() - 1, stringbuilder.length());
//                    if(!s1.equals("～"))
//                        stringbuilder.append((new StringBuilder()).append("～").append((String)hashtable.get("JS_KAGEN")).toString());
//                    else
//                        stringbuilder.append((String)hashtable.get("JS_KAGEN"));
//                }
//            }
//            if(stringbuilder.substring(stringbuilder.length() - 1, stringbuilder.length()).equals("："))
//                stringbuilder.delete(stringbuilder.lastIndexOf("["), stringbuilder.length());
//            else
//                stringbuilder.append("]");
//        }
//
//        jLabel_Kijunti.setText(stringbuilder.toString());
//        return defaultcategorydataset;
//    }
//
//    public void actionPerformed(ActionEvent actionevent)
//    {
//        if(actionevent.getSource() == jButtonCancel)
//            dispose();
//        setVisible(false);
//    }
//
//    private ExtendedLabel getJTextField_Kijunti()
//    {
//        if(jLabel_Kijunti == null)
//            jLabel_Kijunti = new ExtendedLabel();
//        return jLabel_Kijunti;
//    }
//
//    private JPanel getJPanel_RadioPanel()
//    {
//        if(jPanel_KijuntiPanel == null)
//        {
//            GridBagConstraints gridbagconstraints = new GridBagConstraints();
//            gridbagconstraints.gridx = 0;
//            gridbagconstraints.gridy = 0;
//            gridbagconstraints.ipadx = 5;
//            gridbagconstraints.insets = new Insets(10, 10, 10, 10);
//            jPanel_KijuntiPanel = new JPanel();
//            jPanel_KijuntiPanel.setLayout(new GridBagLayout());
//            jPanel_KijuntiPanel.setOpaque(false);
//            jPanel_KijuntiPanel.add(getJTextField_Kijunti(), gridbagconstraints);
//        }
//        return jPanel_KijuntiPanel;
//    }
//
//    private ExtendedButton getJButtonCancel()
//    {
//        if(jButtonCancel == null)
//        {
//            ExtendedImageIcon extendedimageicon = new ExtendedImageIcon(JPath.Ico_Common_Back);
//            ImageIcon imageicon = extendedimageicon.setStrechIcon(this, JPath.CONST_FIX_ICON);
//            jButtonCancel = new ExtendedButton("Return", "戻る(R)", "戻る(ALT+R)", 82, imageicon);
//            jButtonCancel.addActionListener(this);
//        }
//        return jButtonCancel;
//    }
//
//    private ExtendedOpenComboboxControl getJComboBox_DokujiKoumokuCD()
//    {
//        if(jComboBox_DokujiKoumokuCD == null)
//        {
//            jComboBox_DokujiKoumokuCD = new ExtendedOpenComboboxControl(jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode.IME_OFF);
//            jComboBox_DokujiKoumokuCD.setCanCopy(true);
//            setComboKoumokuCode();
//            jComboBox_DokujiKoumokuCD.setAttributeName("T_KENSHINMASTER");
//            jComboBox_DokujiKoumokuCD.setDomainId("T_KENSHINMASTER");
//        }
//        return jComboBox_DokujiKoumokuCD;
//    }
//
//    private void setComboKoumokuCode()
//    {
//        String as[] = JConstantString.codeGraphTokuteiKey;
//        JApplication.graphDomain = new Domain("T_KENSHINMASTER");
//        for(int i = 0; i < as.length; i++)
//            JApplication.graphDomain.addDomainPair(as[i], as[i]);
//
//        JApplication.domains.put(JApplication.graphDomain.getDomainId(), JApplication.graphDomain);
//    }
//
//    private void setComboboxDokujiKenshin()
//    {
//        jComboBox_DokujiKoumokuCD.setSelectedIndex(0);
//        jComboBox_DokujiKoumokuCD.getComboBox().addItemListener(new ItemListener() {
//
//            public void itemStateChanged(ItemEvent itemevent)
//            {
//                ItemEvent _tmp = itemevent;
//                if(itemevent.getStateChange() == 1)
//                {
//                    String s = jComboBox_DokujiKoumokuCD.getValueAt(jComboBox_DokujiKoumokuCD.getSelectedIndex());
//                    HashMap hashmap = JConstantString.getGraphTokuteiKensa();
//                    String as[] = (String[])hashmap.get(s);
//                    freeChart = createChart(as);
//                    freeChart.fireChartChanged();
//                    ChartPanel chartpanel = new ChartPanel(freeChart);
//                    chartpanel.setFillZoomRectangle(true);
//                    chartpanel.setMouseWheelEnabled(true);
//                    chartpanel.setPreferredSize(new Dimension(900, 500));
//                    jpanelOut.removeAll();
//                    jpanelOut.add(chartpanel);
//                    jpanelOut.invalidate();
//                    jpanelOut.validate();
//                    invalidate();
//                    validate();
//                }
//            }
//
////            final SelectSelectableGraphDialog this$0;
////
////
////            {
////                this$0 = SelectSelectableGraphDialog.this;
////                super();
////            }
//        });
//    }
//
//    private ArrayList getTeikeiMaster(String s)
//    {
//        try
//        {
//            JApplication.hokenjyaDatabase = JConnection.ConnectHokenjyaDatabase();
//        }
//        catch(Exception exception)
//        {
//            System.out.println(exception.getMessage());
//            JErrorMessage.show("M1000", null);
//            System.exit(1);
//        }
//        StringBuilder stringbuilder = new StringBuilder();
//        stringbuilder.append("SELECT DISTINCT ");
//        stringbuilder.append("TS.KENSA_NENGAPI,TK.KOUMOKU_CD,TK.KOUMOKU_NAME,TK.KENSA_HOUHOU, TK.TANI, TK.DS_JYOUGEN, TK.DS_KAGEN, ");
//        stringbuilder.append("TK.JS_JYOUGEN, TK.JS_KAGEN, TS.JISI_KBN, TS.H_L, TK.HISU_FLG, TK.DATA_TYPE,TS.KEKA_TI,KN.SEX ");
//        stringbuilder.append("FROM T_KOJIN KN,T_KENSAKEKA_SONOTA TS,T_KENSHINMASTER TK, ");
//        stringbuilder.append("  (SELECT TN.UKETUKE_ID,TK.KENSA_NENGAPI ");
//        stringbuilder.append("  FROM T_NAYOSE TN,T_KENSAKEKA_TOKUTEI TK ");
//        stringbuilder.append("  WHERE TN.NAYOSE_NO = ");
//        stringbuilder.append("   (SELECT NAYOSE_NO FROM T_NAYOSE TN ");
//        stringbuilder.append("   WHERE TN.UKETUKE_ID =  ");
//        stringbuilder.append(JQueryConvert.queryConvert(uketukeid));
//        stringbuilder.append("  )AND TN.UKETUKE_ID = TK.UKETUKE_ID) TN_KEY ");
//        stringbuilder.append("WHERE TS.UKETUKE_ID = TN_KEY.UKETUKE_ID ");
//        stringbuilder.append("AND TS.UKETUKE_ID = KN.UKETUKE_ID ");
//        stringbuilder.append("AND TS.KENSA_NENGAPI = TN_KEY.KENSA_NENGAPI ");
//        stringbuilder.append("AND TS.KEKA_TI <> '' ");
//        stringbuilder.append("AND TS.KOUMOKU_CD = TK.KOUMOKU_CD ");
//        stringbuilder.append("AND TK.HKNJANUM = ");
//        stringbuilder.append(JQueryConvert.queryConvert(hknjanum));
//        stringbuilder.append("AND TK.DATA_TYPE = '1'");
//        if(s.equals(""))
//        {
//            stringbuilder.append("AND TS.KOUMOKU_CD = ");
//            stringbuilder.append(JQueryConvert.queryConvert(KENSIN_HIGHT));
//        } else
//        {
//            stringbuilder.append("AND TS.KOUMOKU_CD = ");
//            stringbuilder.append(JQueryConvert.queryConvert(s));
//        }
//        stringbuilder.append("ORDER BY KENSA_NENGAPI,XMLITEM_SEQNO ");
//        ArrayList arraylist = null;
//        try
//        {
//            arraylist = JApplication.kikanDatabase.sendExecuteQuery(stringbuilder.toString());
//        }
//        catch(SQLException sqlexception)
//        {
//            logger.error(sqlexception.getMessage());
//        }
//        return arraylist;
//    }
//
//    private static Logger logger = Logger.getLogger("jp/or/med/orca/jma_tokutei/common/frame/dialog/SelectSelectableGraphDialog");
//    protected JPanel jpanelOut;
//    protected JFreeChart freeChart;
//    protected ExtendedButton jButtonCancel;
//    protected JPanel jPanelButtonArea;
//    protected ExtendedOpenComboboxControl jComboBox_DokujiKoumokuCD;
//    protected DynamicTimeSeriesCollection dataset;
//    private static String GLAPH_TITLE = "健診項目別経年データグラフ";
//    private String uketukeid;
//    private String hknjanum;
//    private static String KENSIN_HIGHT = "9N001000000000001";
//    protected ExtendedLabel jLabel_Kijunti;
//    private JPanel jPanel_KijuntiPanel;
//
//
//}

