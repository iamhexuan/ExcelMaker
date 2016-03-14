package jp.co.worksap.rfm.excel.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import jp.co.worksap.rfm.excel.bean.CellData;
import jp.co.worksap.rfm.excel.bean.CommandOps;
import jp.co.worksap.rfm.excel.bean.RfmResult;
import jp.co.worksap.rfm.excel.bean.RfmSummary;
import jp.co.worksap.rfm.excel.bean.RfmSummary.Summary;

public class DataMaker {
	public static List<List<CellData>> makeRfmTable(Vector<Vector<Vector<RfmResult>>> grid, String feildOfInterest, StyleMaker.StyleType style) throws ReflectiveOperationException{
		List<CellData> row;
		CellData cell;
		List<List<CellData>> rfmTable = new ArrayList<List<CellData>>();
		for(int i = 0; i < grid.size()+1; i++){
			row = new ArrayList<CellData>();
			rfmTable.add(row);
			for(int j = 0; j < grid.get(0).size()+1; j++){
				if(i == 0){
					//table top header
					if(j == 0){
						cell = new CellData("Rank");
					}else{
						cell = new CellData("R"+j);
					}
					cell.setStyle(StyleMaker.StyleType.TABLE_H);
				}else if(j == 0){
					//table side header
					cell = new CellData("F"+i);
					cell.setStyle(StyleMaker.StyleType.TABLE_H);
				}else{
					List<RfmResult> mList = grid.get(i-1).get(j-1);
					RfmResult combinedM = mList.get(0).clone();
					for(int k = 1; k < mList.size(); k++){
						combinedM.setTtlBuy(combinedM.getTtlBuy() + mList.get(k).getTtlBuy());
						combinedM.setTtlPpl(combinedM.getTtlPpl() + mList.get(k).getTtlPpl());
						combinedM.setTtlTime(combinedM.getTtlTime() + mList.get(k).getTtlTime());
						combinedM.setCmpRatioBuy(combinedM.getCmpRatioBuy() + mList.get(k).getCmpRatioBuy());
						combinedM.setCmpRatioPpl(combinedM.getCmpRatioPpl() + mList.get(k).getCmpRatioPpl());
						combinedM.setCmpRatioTime(combinedM.getCmpRatioTime() + mList.get(k).getCmpRatioTime());
					}
					Method method = combinedM.getClass().getMethod("get"+(feildOfInterest.charAt(0)+"").toUpperCase()+feildOfInterest.substring(1));
					cell = new CellData(method.invoke(combinedM));
					cell.setStyle(style);
				}
				row.add(cell);
			}
		}
		return rfmTable;
	}

	public static Vector<Vector<Vector<RfmResult>>> makeGridFRM(List<RfmResult> resultList){
		Vector<Vector<Vector<RfmResult>>> grid = new Vector<Vector<Vector<RfmResult>>>();
		//grid of f(row) x r(col) x m
		int f = 0;
		int r = 0;
		int m = 0;
		for(RfmResult result : resultList){
			f = result.getfRank();
			r = result.getrRank();
			m = result.getmRank();
			if(grid.size() < f){
				grid.setSize(f);
			}
			if(grid.get(f-1) == null){
				grid.set(f-1, new Vector<Vector<RfmResult>>());
			}
			if(grid.get(f-1).size() < r){
				grid.get(f-1).setSize(r);
			}
			if(grid.get(f-1).get(r-1) == null){
				grid.get(f-1).set(r-1, new Vector<RfmResult>());
			}
			if(grid.get(f-1).get(r-1).size() < m){
				grid.get(f-1).get(r-1).setSize(m);
			}
			
			grid.get(f-1).get(r-1).set(m-1, result);
		}
		return grid;
	}
	
	public static List<List<CellData>> makeRfmSummary(
			Vector<Vector<Vector<RfmResult>>> gridAllAfter,
			Vector<Vector<Vector<RfmResult>>> gridMemAfter, CommandOps ops) throws IllegalArgumentException, IllegalAccessException {
		
		RfmSummary summary = new RfmSummary();
		for(int i = 0; i < gridAllAfter.size(); i++){
			for(int j = 0; j < gridAllAfter.get(i).size(); j++){
				for(int k = 0; k < gridAllAfter.get(i).get(j).size(); k++){
					RfmResult memData = gridMemAfter.get(i).get(j).get(k);
					RfmResult allData = gridAllAfter.get(i).get(j).get(k);
					
					summary.memPpl.value += memData.getTtlPpl();
					summary.memBuy.value += memData.getTtlBuy();
					summary.allPpl.value += allData.getTtlPpl();
					summary.allBuy.value += allData.getTtlBuy();
					
					if(ops.getActiveCstDef().isInRange(allData)) summary.allActivePpl.value += allData.getTtlPpl();
					if(ops.getNewCstDef().isInRange(allData)) summary.allNewPpl.value += allData.getTtlPpl();
					if(ops.getActiveCstDef().isInRange(memData)) summary.memActivePpl.value += memData.getTtlPpl();
					if(ops.getNewCstDef().isInRange(memData)) summary.memNewPpl.value += memData.getTtlPpl();
					
				}
			}
		}
		summary.memBuyRatio.value = (double)summary.memBuy.value/summary.allBuy.value;
		summary.memSaleAverage.value = (double)summary.memBuy.value/summary.memPpl.value;
		
		List<List<CellData>> grid = new ArrayList<List<CellData>>();
		List<CellData> row;
		CellData cell;
		
		for(Field f : summary.getClass().getFields()){
			row = new ArrayList<CellData>();
			grid.add(row);
			@SuppressWarnings("rawtypes")
			Summary sum = (Summary) f.get(summary);
			cell = new CellData(sum.label);
			cell.setStyle(StyleMaker.StyleType.TABLE_H);

			row.add(cell);
			cell = new CellData(sum.value);
			cell.setStyle(sum.style);
			row.add(cell);
			
		}
		return grid;
	}

	public static List<List<CellData>> concatH(List<List<CellData>> left, List<List<CellData>> right, int space) {
		if(left == null) return right;
		if(right == null) return left;
		
		unifyRowLength(left);
		unifyRowLength(right);
		int numRow = left.size() > right.size() ? left.size() : right.size();
		List<List<CellData>> combined = new ArrayList<List<CellData>>();
		for(int i = 0; i < numRow; i++){
			List<CellData> row = new ArrayList<CellData>();
			combined.add(row);
			
			if(left.get(i) == null){
				row.addAll(genSpaceCells(left.size()));
			}else{
				row.addAll(left.get(i));
			}
			row.addAll(genSpaceCells(space));
			if(right.get(i) == null){
				row.addAll(genSpaceCells(right.size()));
			}else{
				row.addAll(right.get(i));
			}
		}
		return combined;
	}
	
	private static void unifyRowLength(List<List<CellData>> data) {
		int maxRowLength = 0;
		for(List<CellData> row : data){
			if(row != null && row.size() > maxRowLength){
				maxRowLength = row.size();
			}
		}
		for(List<CellData> row : data){
			if(row == null) row = new ArrayList<CellData>();
			row.addAll(genSpaceCells(maxRowLength - row.size()));
		}
		
	}

	public static List<List<CellData>> concatV(List<List<CellData>> top, List<List<CellData>> bottom, int space) {
		if(top == null) return bottom;
		if(bottom == null) return top;
		
		List<List<CellData>> combined = new ArrayList<List<CellData>>();
		combined.addAll(top);
		for(int i = 0; i < space; i++){
			combined.add(null);
		}
		combined.addAll(bottom);
		
		return combined;
	}
	
	private static List<CellData> genSpaceCells(int space){
		List<CellData> spaces = new ArrayList<CellData>();
		for(int i = 0; i < space; i++){
			spaces.add(null);
		}
		return spaces;
	}

	private static SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	
	public static List<List<CellData>> makeSubTitle(String groupby, Date sdate, Date edate) {
		List<List<CellData>> grid = new ArrayList<List<CellData>>();
		List<CellData> row = new ArrayList<CellData>();
		grid.add(row);
		CellData cell = new CellData(groupby);
		cell.setStyle(StyleMaker.StyleType.H2);
		row.add(cell);
		
		row = new ArrayList<CellData>();
		grid.add(row);
		cell = new CellData(format.format(sdate) + "~" + format.format(edate));
		cell.setStyle(StyleMaker.StyleType.H3);
		row.add(cell);
		
		
		return grid;
	}
}
