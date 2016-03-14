package jp.co.worksap.rfm.excel.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.worksap.rfm.excel.bean.RfmResult;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

public class ReadCsv {
	private static final Map<String, String> RFM_RESULT_COLUMN_MAP = new HashMap<String, String>();
	static {
		RFM_RESULT_COLUMN_MAP.put("RFM分析コード", "rfmCd");
		RFM_RESULT_COLUMN_MAP.put("ショップコード", "spCd");
		RFM_RESULT_COLUMN_MAP.put("分割値", "segVal");
		RFM_RESULT_COLUMN_MAP.put("実行No", "execNo");
		RFM_RESULT_COLUMN_MAP.put("Rランク", "rRank");
		RFM_RESULT_COLUMN_MAP.put("Fランク", "fRank");
		RFM_RESULT_COLUMN_MAP.put("Mランク", "mRank");
		RFM_RESULT_COLUMN_MAP.put("該当人数", "ttlPpl");
		RFM_RESULT_COLUMN_MAP.put("合計購入回数", "ttlTime");
		RFM_RESULT_COLUMN_MAP.put("合計購入金額", "ttlBuy");
		RFM_RESULT_COLUMN_MAP.put("該当人数構成比", "cmpRatioPpl");
		RFM_RESULT_COLUMN_MAP.put("合計購入回数構成比", "cmpRatioTime");
		RFM_RESULT_COLUMN_MAP.put("合計購入金額構成比", "cmpRatioBuy");
	}
	
	@SuppressWarnings("deprecation")
	public static List<RfmResult> readRfmResult(String fileLoc) throws IOException{
		MyMappingStrategy<RfmResult> strat = new MyMappingStrategy<RfmResult>();
		strat.setType(RfmResult.class);
	    strat.setColumnMapping(RFM_RESULT_COLUMN_MAP);
	    CsvToBean<RfmResult> csv = new CsvToBean<RfmResult>();
	    BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileLoc), "SHIFT_JIS"));
	    CSVReader read = new CSVReader(in);
	    List<RfmResult> resultList = csv.parse(strat, read);
	    return resultList;
	}
	public static class MyMappingStrategy<T> extends HeaderColumnNameTranslateMappingStrategy<T>{
		private Map<String, String> columnMapping = new HashMap<String, String>();
		
		@Override
		public String getColumnName(int col) {
			return col < header.length ? columnMapping.get(header[col]) : null;
		}
		
		@Override
		public Map<String, String> getColumnMapping() {
			return columnMapping;
		}

		@Override
		public void setColumnMapping(Map<String, String> columnMapping) {
			this.columnMapping.clear();
			for (Map.Entry<String, String> entry : columnMapping.entrySet()) {
				this.columnMapping.put(entry.getKey(), entry.getValue());
			}
		}
	}
}
