package jp.co.worksap.rfm.excel.bean;

public class RfmCstDef {
	public Integer rFrom;
	public Integer fFrom;
	public Integer mFrom;
	public Integer rTo;
	public Integer fTo;
	public Integer mTo;
	
	public boolean isInRange(RfmResult result){
		int r = result.getrRank();
		int f = result.getfRank();
		int m = result.getmRank();
		if(rFrom != null && rFrom < r){
			return false;
		}
		if(fFrom != null && fFrom < f){
			return false;
		}
		if(mFrom != null && mFrom < m){
			return false;
		}
		if(rTo != null && rTo > r){
			return false;
		}
		if(fTo != null && fTo > f){
			return false;
		}
		if(mTo != null && mTo > m){
			return false;
		}
		return true;
	}
}
