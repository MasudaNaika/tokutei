package jp.or.med.orca.jma_tokutei.kenshin.admin.frame;

/**
 * DB情報接続画面のBeanクラス
 */
public class JNetworkDBConnectionFrameData {
	
	//FDBのユーザ名
	private String fdbUserName;
	
	//FDBのパスワード
	private String fdbPass;

	//FDBフォルダのパス
	private String fdbFolderPath;

	//ホスト名 or IPアドレス
	private String fdbIPAddress;

	//FDBのポート番号
	private String fdbPortNumber;

	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("fdbUserName:[");
		sb.append(fdbUserName);
		sb.append("] fdbPass:[");
		sb.append(fdbPass);
		sb.append("] fdbFolderPath:[");
		sb.append(fdbFolderPath);
		sb.append("] fdbIPAddress:[");
		sb.append(fdbIPAddress);
		sb.append("] fdbPortNumber:[");
		sb.append(fdbPortNumber);
		sb.append("]");
		return sb.toString();
	}


	public String getFdbUserName() {
		return fdbUserName;
	}

	public void setFdbUserName(String fdbUserName) {
		this.fdbUserName = fdbUserName;
	}

	public String getFdbPass() {
		return fdbPass;
	}

	public void setFdbPass(String fdbPass) {
		this.fdbPass = fdbPass;
	}

	public String getFdbFolderPath() {
		return fdbFolderPath;
	}

	public void setFdbFolderPath(String fdbFolderPath) {
		this.fdbFolderPath = fdbFolderPath;
	}

	public String getFdbIPAddress() {
		return fdbIPAddress;
	}

	public void setFdbIPAddress(String fdbIPAddress) {
		this.fdbIPAddress = fdbIPAddress;
	}

	public String getFdbPortNumber() {
		return fdbPortNumber;
	}

	public void setFdbPortNumber(String fdbPortNumber) {
		this.fdbPortNumber = fdbPortNumber;
	}
}
