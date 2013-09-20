import java.io.File;

import compiler.Parser;


public class Main {

	/**
	 * The running method
	 * @param args
	 * 		-s		set the tracer for the scanner
	 * 		-file	the file to compile, rather than 'file' input the 
	 * 				file name and extension
	 * 		TODO write a help command
	 */
	public static void main(String[] args) {
		String fileName = null;
		boolean scannerTrace = false;
		for(String arg: args){
			switch(arg){
			case "s": 
				scannerTrace = true;
				break;
			default:
				fileName = arg;
			}
		}
		Parser p = new Parser(new File(fileName));
		p.setScannerTrace(scannerTrace);
		p.start();
	}
}
