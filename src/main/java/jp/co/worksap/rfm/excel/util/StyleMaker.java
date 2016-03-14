package jp.co.worksap.rfm.excel.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

public class StyleMaker {

	public static Map<StyleType, CellStyle> makeStyle(Workbook wb) {
		Font font1 = wb.createFont();
        font1.setFontHeightInPoints((short)14);
        font1.setColor(IndexedColors.WHITE.getIndex());
        font1.setBoldweight(Font.BOLDWEIGHT_BOLD);
        
        Font font2 = wb.createFont();
        font2.setFontHeightInPoints((short)12);
        font2.setBoldweight(Font.BOLDWEIGHT_BOLD);

		Map<StyleType, CellStyle> styles = new HashMap<StyleType, CellStyle>();
		
		CellStyle style = wb.createCellStyle();
		styles.put(StyleType.H1,style);
		
		style = wb.createCellStyle();
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(font1);
		styles.put(StyleType.H2,style);
		
		style = wb.createCellStyle();
		style.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
		style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		style.setFillPattern(CellStyle.NO_FILL);
		style.setFont(font2);
		styles.put(StyleType.H3,style);
		
		style = createBorderedStyle(wb);
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		styles.put(StyleType.TABLE_H,style);
		
		DataFormat df = wb.createDataFormat();
		style = createBorderedStyle(wb);
		style.setDataFormat(df.getFormat("#,##0"));
		styles.put(StyleType.TABLE_C_NUM,style);

		style = createBorderedStyle(wb);
		style.setDataFormat(df.getFormat("#,##0.00%"));
		styles.put(StyleType.TABLE_C_PRC,style);

		style = createBorderedStyle(wb);
		style.setDataFormat(df.getFormat("0.00"));
		styles.put(StyleType.TABLE_C_2D,style);
		
		df = wb.createDataFormat();
		style = createBorderedStyle(wb);
		style.setDataFormat(df.getFormat("¥#,##0"));
		styles.put(StyleType.TABLE_C_CURRENCY,style);
		
		df = wb.createDataFormat();
		style = createBorderedStyle(wb);
		style.setDataFormat(df.getFormat("¥#,##0.00"));
		styles.put(StyleType.TABLE_C_CURRENCY_2D,style);
		
		return styles;
	}

	private static CellStyle createBorderedStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		return style;
	}

	public static enum StyleType {
		H1,H2,H3,TABLE_H,TABLE_C_NUM,TABLE_C_PRC,TABLE_C_2D,TABLE_C_CURRENCY,TABLE_C_CURRENCY_2D;
	}

}
