package jp.or.med.orca.jma_tokutei.common.component;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * ImageIconŠg’£ƒNƒ‰ƒX
 * @author s.inoue
 * @version 2.0
 */
public class ExtendedImageIcon extends ImageIcon {
	private ImageIcon icon = null;

	public ExtendedImageIcon(String path){
		icon = new ImageIcon(path);
	}

	public ImageIcon setStrechIcon(JFrame frame,double scale){

		int imageWidth=0;
		int imageHeight=0;

		Image image;

		image=icon.getImage();
		imageWidth=image.getWidth(frame);		//original image‚Ìwidth‚ğ‹‚ß‚é
		imageHeight= image.getHeight(frame);	//original image‚Ìhight‚ğ‹‚ß‚é

		//scale‚ğæ‚¸‚é‚±‚Æ‚É‚æ‚èk¬EŠg‘å‚Ìsize‚ğİ’è
		image=icon.getImage().getScaledInstance(
				(int)(imageWidth*scale),
				(int)(imageHeight*scale),
				Image.SCALE_FAST);

		return new ImageIcon(image);				//Šg‘åEk¬‚µ‚½ƒCƒ[ƒW‚ÌImageIcon‚Éİ’è
	}

	public ImageIcon setStrechIcon(JButton btn,double scale){

		int imageWidth=0;
		int imageHeight=0;

		Image image;

		image=icon.getImage();
		imageWidth=image.getWidth(btn);		//original image‚Ìwidth‚ğ‹‚ß‚é
		imageHeight= image.getHeight(btn);	//original image‚Ìhight‚ğ‹‚ß‚é

		//scale‚ğæ‚¸‚é‚±‚Æ‚É‚æ‚èk¬EŠg‘å‚Ìsize‚ğİ’è
		image=icon.getImage().getScaledInstance(
				(int)(imageWidth*scale),
				(int)(imageHeight*scale),
				Image.SCALE_FAST);

		return new ImageIcon(image);				//Šg‘åEk¬‚µ‚½ƒCƒ[ƒW‚ÌImageIcon‚Éİ’è
	}

	public ImageIcon setStrechIcon(JDialog frame,double scale){

		int imageWidth=0;
		int imageHeight=0;

		Image image;

		image=icon.getImage();
		imageWidth=image.getWidth(frame);		//original image‚Ìwidth‚ğ‹‚ß‚é
		imageHeight= image.getHeight(frame);	//original image‚Ìhight‚ğ‹‚ß‚é

		//scale‚ğæ‚¸‚é‚±‚Æ‚É‚æ‚èk¬EŠg‘å‚Ìsize‚ğİ’è
		image=icon.getImage().getScaledInstance((int)(imageWidth*scale), (int)(imageHeight*scale),Image.SCALE_FAST);
		return new ImageIcon(image);				//Šg‘åEk¬‚µ‚½ƒCƒ[ƒW‚ÌImageIcon‚Éİ’è
	}
}
