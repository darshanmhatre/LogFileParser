
public class Metrics {
	private int totalLOC=0;
	private int executableLOC=0;
	private int chargableLOC=0;
	public int getTotalLOC() {
		return totalLOC;
	}
	public void setTotalLOC(int totalLOC) {
		this.totalLOC = totalLOC;
	}
	public int getExecutableLOC() {
		return executableLOC;
	}
	public void setExecutableLOC(int executableLOC) {
		this.executableLOC = executableLOC;
	}
	public int getChargableLOC() {
		return chargableLOC;
	}
	public void setChargableLOC(int chargableLOC) {
		this.chargableLOC = chargableLOC;
	}
	@Override
	public String toString() {
		return "Metrics [totalLOC=" + totalLOC + ", executableLOC=" + executableLOC + ", chargableLOC=" + chargableLOC
				+ "]";
	}
	
}
