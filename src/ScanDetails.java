
public class ScanDetails {
	private String language;
	private Metrics metrics;
	private String time;
	private String appID;
	private String instanceId;
	public String getAppID() {
		return appID;
	}
	public void setAppID(String appID) {
		this.appID = appID;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Metrics getMetrics() {
		return metrics;
	}
	public void setMetrics(Metrics metrics) {
		this.metrics = metrics;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "ScanDetails [language=" + language + ", metrics=" + metrics + ", time=" + time + ", appID=" + appID
				+ ", instanceId=" + instanceId + "]";
	}
	
}
