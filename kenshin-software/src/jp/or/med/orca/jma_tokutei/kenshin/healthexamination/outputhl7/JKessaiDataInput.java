package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7;

/**
 * 決済データの入力データ
 */
public class JKessaiDataInput {
	private long KihonTanka = -1;
	private int KihonMadoFutanSyubetu = -1;
	private long KihonMadoFutan = -1;

	private long SyousaiTanka = -1;
	private int SyousaiMadoFutanSyubetu = -1;
	private long SyousaiMadoFutan = -1;

	private long TsuikaTanka = -1;
	private int TsuikaMadoFutanSyubetu = -1;
	private long TsuikaMadoFutan = -1;

	private long DockTanka = -1;
	private int DockMadoFutanSyubetu = -1;
	private long DocMadoFutan = -1;

	private long MadoFutanSonota = -1;
	private long kihonHokenjyaJyougen = -1;
	private long syousaiHokenjyaJyougen = -1;
	private long tsuikaHokenjyaJyougen = -1;
	private long dockHokenjyaJyougen = -1;

	private boolean existsSyousai = false;
	private boolean existsTsuika = false;
	private boolean existsDoc = false;

	/**
	 * @return trueならすべての要素にデータが入っている。
	 */
	public boolean Check()
	{
		if(
			( KihonTanka == -1 ||
			KihonMadoFutanSyubetu == -1 ||
			KihonMadoFutan == -1 ||
			SyousaiTanka == -1 ||
			SyousaiMadoFutanSyubetu == -1 ||
			SyousaiMadoFutan == -1 ||
			TsuikaTanka == -1 ||
			TsuikaMadoFutanSyubetu == -1 ||
			TsuikaMadoFutan == -1 ||
			MadoFutanSonota == -1 ) &&
			// add ver2 s.inoue 2009/06/17
			( DockTanka == -1 ||
			DockMadoFutanSyubetu == -1 ||
			DocMadoFutan == -1)
		)
		{
			return false;
		}

		return true;
	}

	/**
	 * @return the kihonTanka
	 */
	public long getKihonTanka() {
		return KihonTanka;
	}

	/**
	 * @param kihonTanka the kihonTanka to set
	 */
	public void setKihonTanka(long kihonTanka) {
		KihonTanka = kihonTanka;
	}

	/**
	 * @return the kihonMadoFutanSyubetu
	 */
	public int getKihonMadoFutanSyubetu() {
		return KihonMadoFutanSyubetu;
	}

	/**
	 * @param kihonMadoFutanSyubetu the kihonMadoFutanSyubetu to set
	 */
	public void setKihonMadoFutanSyubetu(int kihonMadoFutanSyubetu) {
		KihonMadoFutanSyubetu = kihonMadoFutanSyubetu;
	}

	/**
	 * @return the kihonMadoFutan
	 */
	public long getKihonMadoFutan() {
		return KihonMadoFutan;
	}

	/**
	 * @param kihonMadoFutan the kihonMadoFutan to set
	 */
	public void setKihonMadoFutan(long kihonMadoFutan) {
		KihonMadoFutan = kihonMadoFutan;
	}

	/**
	 * @return the syousaiTanka
	 */
	public long getSyousaiTanka() {
		return SyousaiTanka;
	}

	/**
	 * @param syousaiTanka the syousaiTanka to set
	 */
	public void setSyousaiTanka(long syousaiTanka) {
		SyousaiTanka = syousaiTanka;
	}

	/**
	 * @return the syousaiMadoFutanSyubetu
	 */
	public int getSyousaiMadoFutanSyubetu() {
		return SyousaiMadoFutanSyubetu;
	}

	/**
	 * @param syousaiMadoFutanSyubetu the syousaiMadoFutanSyubetu to set
	 */
	public void setSyousaiMadoFutanSyubetu(int syousaiMadoFutanSyubetu) {
		SyousaiMadoFutanSyubetu = syousaiMadoFutanSyubetu;
	}

	/**
	 * @return the syousaiMadoFutan
	 */
	public long getSyousaiMadoFutan() {
		return SyousaiMadoFutan;
	}

	/**
	 * @param syousaiMadoFutan the syousaiMadoFutan to set
	 */
	public void setSyousaiMadoFutan(long syousaiMadoFutan) {
		SyousaiMadoFutan = syousaiMadoFutan;
	}

	/**
	 * @return the tsuikaTanka
	 */
	public long getTsuikaTanka() {
		return TsuikaTanka;
	}

	/**
	 * @param tsuikaTanka the tsuikaTanka to set
	 */
	public void setTsuikaTanka(long tsuikaTanka) {
		TsuikaTanka = tsuikaTanka;
	}

	/**
	 * @return the tsuikaMadoFutanSyubetu
	 */
	public int getTsuikaMadoFutanSyubetu() {
		return TsuikaMadoFutanSyubetu;
	}

	/**
	 * @param tsuikaMadoFutanSyubetu the tsuikaMadoFutanSyubetu to set
	 */
	public void setTsuikaMadoFutanSyubetu(int tsuikaMadoFutanSyubetu) {
		TsuikaMadoFutanSyubetu = tsuikaMadoFutanSyubetu;
	}

	/**
	 * @return the tsuikaMadoFutan
	 */
	public long getTsuikaMadoFutan() {
		return TsuikaMadoFutan;
	}

	/**
	 * @param tsuikaMadoFutan the tsuikaMadoFutan to set
	 */
	public void setTsuikaMadoFutan(long tsuikaMadoFutan) {
		TsuikaMadoFutan = tsuikaMadoFutan;
	}

	/**
	 * @return the docMadoFutan
	 */
	public long getDocMadoFutan() {
		return DocMadoFutan;
	}

	/**
	 * @param tsuikaMadoFutan the tsuikaMadoFutan to set
	 */
	public void setDocMadoFutan(long docMadoFutan) {
		DocMadoFutan = docMadoFutan;
	}


	public long getMadoFutanSonota() {
		return MadoFutanSonota;
	}

	public void setMadoFutanSonota(long madoFutanSonota) {
		MadoFutanSonota = madoFutanSonota;
	}

	public long getDockHokenjyaJyougen() {
		return dockHokenjyaJyougen;
	}

	public void setDockHokenjyaJyougen(long dockHokenjyaJyougen) {
		this.dockHokenjyaJyougen = dockHokenjyaJyougen;
	}

	public long getKihonHokenjyaJyougen() {
		return kihonHokenjyaJyougen;
	}

	public void setKihonHokenjyaJyougen(long kihonHokenjyaJyougen) {
		this.kihonHokenjyaJyougen = kihonHokenjyaJyougen;
	}

	public long getSyousaiHokenjyaJyougen() {
		return syousaiHokenjyaJyougen;
	}

	public void setSyousaiHokenjyaJyougen(long syousaiHokenjyaJyougen) {
		this.syousaiHokenjyaJyougen = syousaiHokenjyaJyougen;
	}

	public long getTsuikaHokenjyaJyougen() {
		return tsuikaHokenjyaJyougen;
	}

	public void setTsuikaHokenjyaJyougen(long tsuikaHokenjyaJyougen) {
		this.tsuikaHokenjyaJyougen = tsuikaHokenjyaJyougen;
	}

	public boolean isExistsTsuika() {
		return existsTsuika;
	}

	public void setExistsTsuika(boolean existsTsuika) {
		this.existsTsuika = existsTsuika;
	}

	public boolean isExistsSyousai() {
		return existsSyousai;
	}

	public void setExistsSyousai(boolean existsSyousai) {
		this.existsSyousai = existsSyousai;
	}

	public long getDockMadoFutan() {
		return DocMadoFutan;
	}

	public void setDockMadoFutan(long dockMadoFutan) {
		DocMadoFutan = dockMadoFutan;
	}

	public int getDockMadoFutanSyubetu() {
		return DockMadoFutanSyubetu;
	}

	public void setDockMadoFutanSyubetu(int dockMadoFutanSyubetu) {
		DockMadoFutanSyubetu = dockMadoFutanSyubetu;
	}

	public long getDockTanka() {
		return DockTanka;
	}

	public void setDockTanka(long dockTanka) {
		DockTanka = dockTanka;
	}

	public boolean isExistsDoc() {
		return existsDoc;
	}

	public void setExistsDoc(boolean existsDoc) {
		this.existsDoc = existsDoc;
	}


}
