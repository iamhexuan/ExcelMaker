package jp.co.worksap.rfm.excel.service;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import jp.co.worksap.rfm.excel.bean.CellData;
import jp.co.worksap.rfm.excel.bean.CommandOps;
import jp.co.worksap.rfm.excel.bean.RfmResult;
import jp.co.worksap.rfm.excel.util.DataMaker;
import jp.co.worksap.rfm.excel.util.ReadCsv;
import jp.co.worksap.rfm.excel.util.StyleMaker;

public class AppService {
	
	private Map<String, List<RfmResult>> resultGroupsAllAfter;
	private Map<String, List<RfmResult>> resultGroupsMemAfter;
	private Map<String, List<RfmResult>> resultGroupsAllBefore;
	private Map<String, List<RfmResult>> resultGroupsMemBefore;
	
	private CommandOps commandOps;
	private XlsxMaker xlsxMaker; 
	
	public AppService(CommandOps commandOps) throws IOException, ReflectiveOperationException {
		this.commandOps = commandOps;
		this.xlsxMaker = new XlsxMaker();
		
		List<RfmResult> rfmAllAfter = ReadCsv.readRfmResult(commandOps.getSourceAllAfter());
		List<RfmResult> rfmMemAfter = ReadCsv.readRfmResult(commandOps.getSourceMemAfter());
		resultGroupsAllAfter = groupResults(rfmAllAfter, commandOps.getGroupby());
		resultGroupsMemAfter = groupResults(rfmMemAfter, commandOps.getGroupby());
		
		List<RfmResult> rfmAllBefore = ReadCsv.readRfmResult(commandOps.getSourceAllBefore());
		List<RfmResult> rfmMemBefore = ReadCsv.readRfmResult(commandOps.getSourceMemBefore());
		resultGroupsAllBefore = groupResults(rfmAllBefore, commandOps.getGroupby());
		resultGroupsMemBefore = groupResults(rfmMemBefore, commandOps.getGroupby());
		
		String errMsg = validateResult();
		if(!"".equals(errMsg)){
			throw new RuntimeException(errMsg);
		}
	}
	
	private String validateResult(){
		String errMsg = "";
		if(resultGroupsAllAfter.size() != resultGroupsMemAfter.size() || 
			resultGroupsMemAfter.size() != resultGroupsAllBefore.size() || 
			resultGroupsAllBefore.size() != resultGroupsMemBefore.size()){
			errMsg += "All csv need to have the same number of segment value";
		}
		
		String key = (String) resultGroupsAllAfter.keySet().toArray()[0];
		if(resultGroupsAllAfter.get(key).size() != resultGroupsMemAfter.get(key).size() || 
				resultGroupsMemAfter.get(key).size() != resultGroupsAllBefore.get(key).size() || 
				resultGroupsAllBefore.get(key).size() != resultGroupsMemBefore.get(key).size()){
			errMsg += "All csv need to have the same number of R and F and M setting";
		}
		return errMsg;
	}
	
    private Map<String, List<RfmResult>> groupResults(List<RfmResult> rfmResults, String groupBy) 
    		throws ReflectiveOperationException{
    	Map<String, List<RfmResult>> groupResult = new HashMap<String, List<RfmResult>>();
    	List<RfmResult> resultList = null;
    	for(RfmResult rfmResult : rfmResults){
    		Method method = RfmResult.class.getMethod("get" + (groupBy.charAt(0)+"").toUpperCase() + groupBy.substring(1));
    		String groupByValue = method.invoke(rfmResult).toString();
    		resultList = groupResult.get(groupByValue);
    		if(resultList == null){
    			resultList = new ArrayList<RfmResult>();
    			groupResult.put(groupByValue, resultList);
    		}else{
    			resultList.add(rfmResult);
    		}
    	}
    	return groupResult;
    }

	public void start() throws ReflectiveOperationException, IOException{
		
		List<List<CellData>> data = null; 
		
		for(Entry<String, List<RfmResult>> entry : resultGroupsAllAfter.entrySet()){
			String groupby = entry.getKey();

			Vector<Vector<Vector<RfmResult>>> gridAllAfter = DataMaker.makeGridFRM(resultGroupsAllAfter.get(groupby));
			Vector<Vector<Vector<RfmResult>>> gridMemAfter = DataMaker.makeGridFRM(resultGroupsMemAfter.get(groupby));
			List<List<CellData>> afterNumberTable = DataMaker.makeRfmTable(gridAllAfter,"ttlPpl", StyleMaker.StyleType.TABLE_C_NUM);
			List<List<CellData>> afterPercentTable = DataMaker.makeRfmTable(gridAllAfter, "cmpRatioPpl", StyleMaker.StyleType.TABLE_C_PRC);
			List<List<CellData>> afterSummaryTable = DataMaker.makeRfmSummary(gridAllAfter, gridMemAfter, commandOps);
			
			Vector<Vector<Vector<RfmResult>>> gridAllBefore = DataMaker.makeGridFRM(resultGroupsAllBefore.get(groupby));
			Vector<Vector<Vector<RfmResult>>> gridMemBefore = DataMaker.makeGridFRM(resultGroupsMemBefore.get(groupby));
			List<List<CellData>> beforeNumberTable = DataMaker.makeRfmTable(gridAllBefore,"ttlPpl", StyleMaker.StyleType.TABLE_C_NUM);
			List<List<CellData>> beforePercentTable = DataMaker.makeRfmTable(gridAllBefore,"cmpRatioPpl", StyleMaker.StyleType.TABLE_C_PRC);
			List<List<CellData>> beforeSummaryTable = DataMaker.makeRfmSummary(gridAllBefore, gridMemBefore, commandOps);
			
			List<List<CellData>> subTitleAfter = DataMaker.makeSubTitle(groupby, commandOps.getSdateAfter(), commandOps.getEdateAfter());
			List<List<CellData>> subTitleBefore = DataMaker.makeSubTitle(groupby, commandOps.getSdateBefore(), commandOps.getEdateBefore());

			List<List<CellData>> afterTables = combineTables(afterNumberTable, afterPercentTable, afterSummaryTable, subTitleAfter);
			List<List<CellData>> beforeTables = combineTables(beforeNumberTable, beforePercentTable, beforeSummaryTable, subTitleBefore);
			List<List<CellData>> dataGroup = combineBeforAfter(afterTables, beforeTables);
			
			data = DataMaker.concatV(data, dataGroup, 3);
		}
		
		xlsxMaker.writeData(data);
		xlsxMaker.output(commandOps.getOutputFolder());
	}
	

	private List<List<CellData>> combineBeforAfter(List<List<CellData>> afterTables,
			List<List<CellData>> beforeTables) {
		List<List<CellData>> combined = DataMaker.concatH(afterTables, beforeTables, 5);
		return combined;
	}

	private List<List<CellData>> combineTables(List<List<CellData>> numberTable, List<List<CellData>> percentTable, 
			List<List<CellData>> summaryTable, List<List<CellData>> subTitle){
		List<List<CellData>> combined = DataMaker.concatH(numberTable, percentTable,1);
		combined = DataMaker.concatV(summaryTable, combined, 1);
		combined = DataMaker.concatV(subTitle, combined, 1);
		return combined;
	}
}
