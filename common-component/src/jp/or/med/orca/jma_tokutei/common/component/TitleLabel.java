package jp.or.med.orca.jma_tokutei.common.component;

import java.awt.Font;

import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

/**
 * 画面タイトル用ラベル
 */
public class TitleLabel extends ExtendedLabel {
	/**
	 * コンストラクタ
	 */
	public TitleLabel() {
		initializeTitleLabel();
	}
	public TitleLabel(String key,String keymess) {
		this();

		if (key != null && ! key.isEmpty()) {
			this.setText(ViewSettings.getUsingValueString(key));
		}
		// add s.inoue 2012/11/13
		if (keymess != null && ! keymess.isEmpty()) {
			this.setToolTipText(ViewSettings.getUsingValueString(keymess));
		}
	}
	public TitleLabel(String key) {
		this();

		if (key != null && ! key.isEmpty()) {
			this.setText(ViewSettings.getUsingValueString(key));
		}
	}
	// openswing s.inoue 2011/02/17
	public TitleLabel(String key,ExtendedImageIcon icon) {
		this();

		if (key != null && ! key.isEmpty()) {
			this.setText(ViewSettings.getUsingValueString(key));
			this.setIcon(icon);
		}
	}
	private void initializeTitleLabel() {
		Font font = ViewSettings.getCommonTitleLabelFont();
		this.setFont(font);

		this.setOpaque(true);

		this.setBackground(ViewSettings.getUsingValueColor("common.frame.title-label.bgcolor"));
		this.setForeground(ViewSettings.getUsingValueColor("common.frame.title-label.fgcolor"));
	}
}
