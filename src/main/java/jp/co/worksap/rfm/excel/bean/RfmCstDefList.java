package jp.co.worksap.rfm.excel.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RfmCstDefList {
	public List<RfmCstDef> defs;
	
	public RfmCstDefList(List<String> defStrList){
		this.defs = new ArrayList<RfmCstDef>();
		for(String defStr : defStrList){
			RfmCstDef def = new RfmCstDef();
			defs.add(def);
			
			String[] defStrSplits = defStr.split("-");
			Pattern p;
			Matcher m;
			for(int i = 0; i < defStrSplits.length; i++){
				if (i == 0){
					String from = defStrSplits[0];	
					
					p = Pattern.compile("r(\\d+)");
					m = p.matcher(from);
					if(m.find()) def.rFrom = Integer.parseInt(m.group(1));

					p = Pattern.compile("f(\\d+)");
					m = p.matcher(from);
					if(m.find()) def.fFrom = Integer.parseInt(m.group(1));

					p = Pattern.compile("m(\\d+)");
					m = p.matcher(from);
					if(m.find()) def.mFrom = Integer.parseInt(m.group(1));
				}
				if (i == 1){
					String to = defStrSplits[1];
					
					p = Pattern.compile("r(\\d+)");
					m = p.matcher(to);
					if(m.find()) def.rFrom = Integer.parseInt(m.group(1));

					p = Pattern.compile("f(\\d+)");
					m = p.matcher(to);
					if(m.find()) def.fFrom = Integer.parseInt(m.group(1));

					p = Pattern.compile("m(\\d+)");
					m = p.matcher(to);
					if(m.find()) def.mFrom = Integer.parseInt(m.group(1));
				}
			}
		}
	}
	
	public boolean isInRange(RfmResult result){
		for(RfmCstDef def : defs){
			if(def.isInRange(result)){
				return true;
			}
		}
		return false;
	}
}
