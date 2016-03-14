package jp.co.worksap.rfm.excel.bean;

import jp.co.worksap.rfm.excel.util.StyleMaker;

public class RfmSummary{

	public Summary<Long> memPpl = new Summary<Long>("Member Count", 0L, StyleMaker.StyleType.TABLE_C_NUM);
	public Summary<Long> memBuy = new Summary<Long>("Member Sales Amount", 0L, StyleMaker.StyleType.TABLE_C_CURRENCY);
	public Summary<Long> allPpl = new Summary<Long>("Customer Count", 0L, StyleMaker.StyleType.TABLE_C_NUM);
	public Summary<Long> allBuy = new Summary<Long>("Customer Sales Amount", 0L, StyleMaker.StyleType.TABLE_C_CURRENCY);
	public Summary<Long> memActivePpl = new Summary<Long>("Active Member Count", 0L, StyleMaker.StyleType.TABLE_C_NUM);
	public Summary<Long> memNewPpl = new Summary<Long>("New Member Count", 0L, StyleMaker.StyleType.TABLE_C_NUM);
	public Summary<Long> allActivePpl = new Summary<Long>("Active Customer Count", 0L, StyleMaker.StyleType.TABLE_C_NUM);
	public Summary<Long> allNewPpl = new Summary<Long>("New Customer Count", 0L, StyleMaker.StyleType.TABLE_C_NUM);
	public Summary<Double> memBuyRatio = new Summary<Double>("Member Sales Ratio", 0.0, StyleMaker.StyleType.TABLE_C_2D);
	public Summary<Double> memSaleAverage = new Summary<Double>("Member Sales Average", 0.0, StyleMaker.StyleType.TABLE_C_CURRENCY_2D);
	
	public class Summary<T> {
		
		public String label;
		public T value;
		public StyleMaker.StyleType style;
		
		Summary(String label, T initValu, StyleMaker.StyleType style){
			this.label = label;
			this.value = initValu;
			this.style = style;
		}
	}
}
