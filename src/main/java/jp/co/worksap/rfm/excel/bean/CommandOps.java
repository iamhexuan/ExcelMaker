package jp.co.worksap.rfm.excel.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.github.jankroken.commandline.annotations.*;

public class CommandOps {
    private String sourceMemBefore;
    private String sourceMemAfter;
    private String sourceAllBefore;
    private String sourceAllAfter;
    
    private RfmCstDefList newCstDef;
    private RfmCstDefList activeCstDef;
    
    private String groupby = "segVal";
    private String outputFolder;
    
    @Option
    @LongSwitch("membef")
    @ShortSwitch("b")
    @SingleArgument
    @Required
	public void setSourceMemBefore(String sourceMemBefore) {
		this.sourceMemBefore = sourceMemBefore;
	}
    
	@Option
    @LongSwitch("memaft")
    @ShortSwitch("a")
    @SingleArgument
    @Required
	public void setSourceMemAfter(String sourceMemAfter) {
		this.sourceMemAfter = sourceMemAfter;
	}
	
    @Option
    @LongSwitch("allbef")
    @ShortSwitch("B")
    @SingleArgument
    @Required
    public void setSourceAllBefore(String sourceAllBefore) {
		this.sourceAllBefore = sourceAllBefore;
	}
    
    @Option
    @LongSwitch("allaft")
    @ShortSwitch("A")
    @SingleArgument
    @Required
    public void setSourceAllAfter(String sourceAllAfter) {
		this.sourceAllAfter = sourceAllAfter;
	}
    
    @Option
    @LongSwitch("newdef")
    @ShortSwitch("n")
    @AllAvailableArguments
    @Required
    //example: r1f1-r1f5 r2f1-r3f4
    public void setNewCstDef(List<String> newCstDef) {
		this.newCstDef = new RfmCstDefList(newCstDef);
	}

    @Option
    @LongSwitch("actdef")
    @ShortSwitch("t")
    @AllAvailableArguments
    @Required
    public void setActiveCstDef(List<String> activeCstDef) {
		this.activeCstDef = new RfmCstDefList(activeCstDef);
	}
	
    @Option
    @LongSwitch("groupby")
    @ShortSwitch("g")
    @SingleArgument
    public void setGroupby(String groupby) {
		this.groupby = groupby;
	}

    @Option
    @LongSwitch("output")
    @ShortSwitch("o")
    @SingleArgument
    public void setOutputFolder(String outputFolder) {
		this.outputFolder = outputFolder;
	}
    
    
    public String getSourceMemBefore() {
		return sourceMemBefore;
	}
    public String getSourceMemAfter() {
    	return sourceMemAfter;
    }
    public String getSourceAllBefore() {
    	return sourceAllBefore;
    }
    public String getSourceAllAfter() {
    	return sourceAllAfter;
    }
    public RfmCstDefList getNewCstDef() {
  		return newCstDef;
  	}
    public RfmCstDefList getActiveCstDef() {
  		return activeCstDef;
  	}
    public String getGroupby() {
    	return groupby;
    }
    public String getOutputFolder() {
    	if(outputFolder == null || "".equals(outputFolder)){
    		return System.getProperty("user.dir") + "/result.xlsx";
    	}
    	return outputFolder;
    }
    
    private Date edateAfter;
    private Date sdateAfter;
    
    private Date edateBefore;
    private Date sdateBefore;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    public void parseXlsName() throws ParseException{
    	//name of the file: rfmcd_execno(_segval)_timestampe_startdate_enddate.xls
    	String[] namePartsAfter = getSourceMemAfter().split("_");
    	edateAfter = dateFormat.parse(namePartsAfter[namePartsAfter.length-1]);
    	sdateAfter = dateFormat.parse(namePartsAfter[namePartsAfter.length-2]);
	
		String[] namePartsBefore = getSourceMemBefore().split("_");
		edateBefore = dateFormat.parse(namePartsBefore[namePartsBefore.length-1]);
		sdateBefore = dateFormat.parse(namePartsBefore[namePartsBefore.length-2]);
    }
    
	public Date getEdateAfter() {
		return edateAfter;
	}

	public Date getSdateAfter() {
		return sdateAfter;
	}

	public Date getEdateBefore() {
		return edateBefore;
	}

	public Date getSdateBefore() {
		return sdateBefore;
	}
	


}
