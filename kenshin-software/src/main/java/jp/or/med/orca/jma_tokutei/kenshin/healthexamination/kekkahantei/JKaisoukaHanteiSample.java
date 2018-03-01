package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.kekkahantei;

public class JKaisoukaHanteiSample {
	public static void main(String[] argv)
	{
		JKaisoukaHanteiData Data = new JKaisoukaHanteiData();
		Data.setAge(56);
		Data.setGender("1");
		
		Data.setHukui_Jissoku(170);
		Data.setBMI(29);
		
		Data.setKuhukujiKetto(90);
		Data.setHbA1c(4.0);
		Data.setKettoHukuyaku(0);
		
		Data.setChuseiShibou(200);
		Data.setHDLCholesterol(200);
		Data.setShishitsuHukuyaku(0);
		
		Data.setShushukuKetsuatsuNo1(30);
		Data.setKakutyoKetsuatsuNo1(70);
		Data.setKetsuatsuHukuyaku(0);
		
		Data.setKitsuen(0);
		
		int Result = JKaisoukaHantei.Hantei(Data);
		
		System.out.println(Result);
	}
}
