package jp.co.worksap.rfm.excel.bean;

public class RfmResult {
	private String rfmCd;
	private String spCd;
	private String segVal;
	private Integer execNo;
	private Integer rRank;
	private Integer fRank;
	private Integer mRank;
	private Long ttlPpl;
	private Long ttlTime;
	private Long ttlBuy;
	private Double cmpRatioPpl;
	private Double cmpRatioTime;
	private Double cmpRatioBuy;
	
	public RfmResult clone(){
		RfmResult clone = new RfmResult();
		clone.rfmCd = this.rfmCd;
		clone.spCd = this.spCd;
		clone.segVal = this.segVal;
		clone.execNo = this.execNo;
		clone.rRank = this.rRank;
		clone.fRank = this.fRank;
		clone.ttlPpl = this.ttlPpl;
		clone.ttlTime = this.ttlTime;
		clone.ttlBuy = this.ttlBuy;
		clone.cmpRatioPpl = this.cmpRatioPpl;
		clone.cmpRatioTime = this.cmpRatioTime;
		clone.cmpRatioBuy = this.cmpRatioBuy;
		return clone;
	}
	
	public String getRfmCd() {
		return rfmCd;
	}
	public void setRfmCd(String rfmCd) {
		this.rfmCd = rfmCd;
	}
	public String getSpCd() {
		return spCd;
	}
	public void setSpCd(String spCd) {
		this.spCd = spCd;
	}
	public String getSegVal() {
		return segVal;
	}
	public void setSegVal(String segVal) {
		this.segVal = segVal;
	}
	public Integer getExecNo() {
		return execNo;
	}
	public void setExecNo(Integer execNo) {
		this.execNo = execNo;
	}
	public Integer getrRank() {
		return rRank;
	}
	public void setrRank(Integer rRank) {
		this.rRank = rRank;
	}
	public Integer getfRank() {
		return fRank;
	}
	public void setfRank(Integer fRank) {
		this.fRank = fRank;
	}
	public Integer getmRank() {
		return mRank;
	}
	public void setmRank(Integer mRank) {
		this.mRank = mRank;
	}
	public Long getTtlPpl() {
		return ttlPpl;
	}
	public void setTtlPpl(Long ttlPpl) {
		this.ttlPpl = ttlPpl;
	}
	public Long getTtlTime() {
		return ttlTime;
	}
	public void setTtlTime(Long ttlTime) {
		this.ttlTime = ttlTime;
	}
	public Long getTtlBuy() {
		return ttlBuy;
	}
	public void setTtlBuy(Long ttlBuy) {
		this.ttlBuy = ttlBuy;
	}
	public Double getCmpRatioPpl() {
		return cmpRatioPpl;
	}
	public void setCmpRatioPpl(Double cmpRatioPpl) {
		this.cmpRatioPpl = cmpRatioPpl;
	}
	public Double getCmpRatioTime() {
		return cmpRatioTime;
	}
	public void setCmpRatioTime(Double cmpRatioTime) {
		this.cmpRatioTime = cmpRatioTime;
	}
	public Double getCmpRatioBuy() {
		return cmpRatioBuy;
	}
	public void setCmpRatioBuy(Double cmpRatioBuy) {
		this.cmpRatioBuy = cmpRatioBuy;
	}
}
