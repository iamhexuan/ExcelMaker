package jp.co.worksap.rfm.excel.bean;

import jp.co.worksap.rfm.excel.util.StyleMaker;


public class CellData {
	private Object dataValue;
	private StyleMaker.StyleType style;
	
	public CellData(Object dataValue){
		this.dataValue = dataValue;
	}
	public Object getDataValue() {
		return dataValue;
	}
	public void setDataValue(Object dataValue) {
		this.dataValue = dataValue;
	}
	public StyleMaker.StyleType getStyle() {
		return style;
	}
	public void setStyle(StyleMaker.StyleType style) {
		this.style = style;
	}
}
