package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7;

/**
 * 決済情報の出力データ
 */
public class JKessaiDataOutput {
	private long TankaGoukei;
	private long MadoFutanKihon;
	private long MadoFutanSyousai;
	private long MadoFutanTsuika;
	private long MadoFutanDoc;
	private long MadoFutanGoukei;
	private long SeikyuKingaku;
	private long tsuikaTankaGoukei;
	private long docTankaGoukei;

	private long MadoFutanSonota;

	/**
	 * @return the tankaGoukei
	 */
	public long getTankaGoukei() {
		return TankaGoukei;
	}
	/**
	 * @param tankaGoukei the tankaGoukei to set
	 */
	public void setTankaGoukei(long tankaGoukei) {
		TankaGoukei = tankaGoukei;
	}
	/**
	 * @return the madoFutanKihon
	 */
	public long getMadoFutanKihon() {
		return MadoFutanKihon;
	}
	/**
	 * @param madoFutanKihon the madoFutanKihon to set
	 */
	public void setMadoFutanKihon(long madoFutanKihon) {
		MadoFutanKihon = madoFutanKihon;
	}
	/**
	 * @return the madoFutanSyousai
	 */
	public long getMadoFutanSyousai() {
		return MadoFutanSyousai;
	}
	/**
	 * @param madoFutanSyousai the madoFutanSyousai to set
	 */
	public void setMadoFutanSyousai(long madoFutanSyousai) {
		MadoFutanSyousai = madoFutanSyousai;
	}
	/**
	 * @return the madoFutanTsuika
	 */
	public long getMadoFutanTsuika() {
		return MadoFutanTsuika;
	}
	/**
	 * @param madoFutanTsuika the madoFutanTsuika to set
	 */
	public void setMadoFutanTsuika(long madoFutanTsuika) {
		MadoFutanTsuika = madoFutanTsuika;
	}
	/**
	 * @return the madoFutanDoc
	 */
	public long getMadoFutanDoc() {
		return MadoFutanDoc;
	}
	/**
	 * @param madoFutanDoc to set
	 */
	public void setMadoFutanDoc(long madoFutanDoc) {
		MadoFutanDoc = madoFutanDoc;
	}

	/**
	 * @return the madoFutanGoukei
	 */
	public long getMadoFutanGoukei() {
		return MadoFutanGoukei;
	}
	/**
	 * @param madoFutanGoukei the madoFutanGoukei to set
	 */
	public void setMadoFutanGoukei(long madoFutanGoukei) {
		MadoFutanGoukei = madoFutanGoukei;
	}
	/**
	 * @return the seikyuKingaku
	 */
	public long getSeikyuKingaku() {
		return SeikyuKingaku;
	}
	/**
	 * @param seikyuKingaku the seikyuKingaku to set
	 */
	public void setSeikyuKingaku(long seikyuKingaku) {
		SeikyuKingaku = seikyuKingaku;
	}
	public long getTsuikaTankaGoukei() {
		return tsuikaTankaGoukei;
	}
	public void setTsuikaTankaGoukei(long tsuikaTankaGoukei) {
		this.tsuikaTankaGoukei = tsuikaTankaGoukei;
	}
	// add ver2 s.inoue 2009/07/09
	public long getDocTankaGoukei() {
		return docTankaGoukei;
	}
	public void setDocTankaGoukei(long docTankaGoukei) {
		this.docTankaGoukei = docTankaGoukei;
	}

	public long getMadoFutanSonota() {
		return MadoFutanSonota;
	}
	public void setMadoFutanSonota(long madoFutanSonota) {
		MadoFutanSonota = madoFutanSonota;
	}
}
