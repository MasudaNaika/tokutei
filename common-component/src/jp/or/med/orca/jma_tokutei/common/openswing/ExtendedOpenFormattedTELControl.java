package jp.or.med.orca.jma_tokutei.common.openswing;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;
import org.openswing.swing.client.FormattedTextControl;

public class ExtendedOpenFormattedTELControl extends FormattedTextControl{

	private static final String FORMAT_TEXT_STRING = "###-####";
	private static final String FORMAT_TEXT_ARROWED = "0123456789";

    public ExtendedOpenFormattedTELControl() throws ParseException{
    	super();

    	MaskFormatter mask = new MaskFormatter(FORMAT_TEXT_STRING);
        mask.setValidCharacters(FORMAT_TEXT_ARROWED);
        this.setFormatter(mask);
    }
}
