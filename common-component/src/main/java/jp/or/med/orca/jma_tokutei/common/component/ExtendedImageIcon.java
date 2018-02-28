package jp.or.med.orca.jma_tokutei.common.component;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * ImageIcon拡張クラス
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
		imageWidth=image.getWidth(frame);		//original imageのwidthを求める
		imageHeight= image.getHeight(frame);	//original imageのhightを求める

		//scaleを乗ずることにより縮小・拡大のsizeを設定
		image=icon.getImage().getScaledInstance(
				(int)(imageWidth*scale),
				(int)(imageHeight*scale),
				Image.SCALE_FAST);

		return new ImageIcon(image);				//拡大・縮小したイメージのImageIconに設定
	}

	public ImageIcon setStrechIcon(JButton btn,double scale){

		int imageWidth=0;
		int imageHeight=0;

		Image image;

		image=icon.getImage();
		imageWidth=image.getWidth(btn);		//original imageのwidthを求める
		imageHeight= image.getHeight(btn);	//original imageのhightを求める

		//scaleを乗ずることにより縮小・拡大のsizeを設定
		image=icon.getImage().getScaledInstance(
				(int)(imageWidth*scale),
				(int)(imageHeight*scale),
				Image.SCALE_FAST);

		return new ImageIcon(image);				//拡大・縮小したイメージのImageIconに設定
	}

	public ImageIcon setStrechIcon(JDialog frame,double scale){

		int imageWidth=0;
		int imageHeight=0;

		Image image;

		image=icon.getImage();
		imageWidth=image.getWidth(frame);		//original imageのwidthを求める
		imageHeight= image.getHeight(frame);	//original imageのhightを求める

		//scaleを乗ずることにより縮小・拡大のsizeを設定
		image=icon.getImage().getScaledInstance((int)(imageWidth*scale), (int)(imageHeight*scale),Image.SCALE_FAST);
		return new ImageIcon(image);				//拡大・縮小したイメージのImageIconに設定
	}
}
