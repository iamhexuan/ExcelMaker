package jp.co.worksap.rfm.excel.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import jp.co.worksap.rfm.excel.bean.CellData;
import jp.co.worksap.rfm.excel.util.StyleMaker;
import jp.co.worksap.rfm.excel.util.StyleMaker.StyleType;

public class XlsxMaker {
	
	private Workbook wb;
	private Sheet sheet;
	private Map<StyleType, CellStyle> styles;
	
	public XlsxMaker(){
		wb = new XSSFWorkbook();
		styles = StyleMaker.makeStyle(wb);	
	}
	
	public void writeData(List<List<CellData>> data) {
		sheet = wb.createSheet("Sample1");
		sheet.setAutobreaks(true);
		
		PrintSetup printSetup = sheet.getPrintSetup();
		printSetup.setFitHeight((short)1);
		printSetup.setFitWidth((short)1);
		
		int rowNum = 0;
		int colNum = 0;
		Row row = null;
		Cell cell = null;
		for(List<CellData> dataRows : data){
			row = sheet.createRow(rowNum);
			rowNum++;
			colNum = 0;
			if(dataRows == null) continue;
			
			for(CellData dataCol : dataRows){
				cell = row.createCell(colNum);
				colNum ++;
				if(dataCol == null) continue;
				
				cell.setCellStyle(styles.get(dataCol.getStyle()));
				
				if(dataCol.getDataValue() instanceof String){
					cell.setCellValue((String)dataCol.getDataValue());
				}else if(dataCol.getDataValue() instanceof Long){
					cell.setCellValue((Long)dataCol.getDataValue());
				}else if(dataCol.getDataValue() instanceof Integer){
					cell.setCellValue((Integer)dataCol.getDataValue());
				}else if(dataCol.getDataValue() instanceof Double){
					cell.setCellValue((Double)dataCol.getDataValue());
				}
			}
		}
		
		autoSizeAllColumn(data.get(0).size());
	}
	
	public void autoSizeAllColumn(int size) {
		for(int i = 0; i < size; i++){
			sheet.autoSizeColumn(i);
		}
	}

	public void output(String folderPath) throws IOException{
		String file = folderPath;
		FileOutputStream out = new FileOutputStream(file);
		wb.write(out);
		out.close();
	}

}
