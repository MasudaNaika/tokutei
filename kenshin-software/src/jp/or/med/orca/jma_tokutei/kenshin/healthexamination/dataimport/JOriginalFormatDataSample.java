package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport;

public class JOriginalFormatDataSample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			JOriginalFormatDataReader.Read("kekka.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
