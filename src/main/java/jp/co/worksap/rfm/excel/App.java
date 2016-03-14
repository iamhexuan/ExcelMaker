package jp.co.worksap.rfm.excel;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

import com.github.jankroken.commandline.CommandLineParser;
import com.github.jankroken.commandline.OptionStyle;
import com.github.jankroken.commandline.domain.InvalidCommandLineException;

import jp.co.worksap.rfm.excel.bean.CommandOps;
import jp.co.worksap.rfm.excel.service.AppService;

public class App 
{
    public static void main( String[] args )
    {
    	try {
	    	CommandOps commandOps = parseArgs(args);
			AppService service = new AppService(commandOps);
			service.start();
			System.out.println("Result file has been saved to: " + commandOps.getOutputFolder());
			System.exit(0);
		} catch(InvalidCommandLineException commandLineException){
			System.out.println("System requires the following required and optional input:");
			System.out.println("-b=FILE -a=FILE -B=FILE -A=FILE -n=STR -t=STR [-g=STR] [-o=FILE]");
			System.out.println("-b, --membef=CSV_FILE");
			System.out.println("	RFM analysis CSV File Location for member before/as-baseline.");
			System.out.println("-a, --memaft=CSV_FILE");
			System.out.println("	RFM analysis CSV File Location for member after/as-comparison.");
			System.out.println("-B, --allbef=CSV_FILE");
			System.out.println("	RFM analysis CSV File Location for all customer after/as-baseline.");
			System.out.println("-A, --allaft=CSV_FILE");
			System.out.println("	RFM analysis CSV File Location for all customer after/as-comparison.");
			System.out.println("-n, --newdef=STR");
			System.out.println("	New Customer Definition. Example: r1f1-r1f5 r2f1-r3f4.");
			System.out.println("-t, --actdef=STR");
			System.out.println("	Active Customer Definition. Example: r1f1-r1f5 r2f1-r3f4.");
			System.out.println("-g, --groupby=STR");
			System.out.println("	Group Analysis result by a specific column, written in camelCase. Default:segVal");
			System.out.println("-o, --output=XLSX_FILE");
			System.out.println("	Full path for the output file. Make sure the file extension is .xlsx");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
    }
    
    private static CommandOps parseArgs(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException, ParseException{
    	CommandOps commandOps = CommandLineParser.parse(CommandOps.class, args, OptionStyle.SIMPLE);
    	commandOps.parseXlsName();
    	return commandOps;
    }
    
}
