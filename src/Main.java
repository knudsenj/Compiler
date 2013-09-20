import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import token.Token;
import token.TokenType;
import Scanner.Scanner;


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
		File file = new File(fileName);
		BufferedReader  myReader = null; 
		try{
			myReader = new BufferedReader(new FileReader(file));
		} catch(IOException e){
			System.out.println("File not found");
		}
		Scanner myScanner = new Scanner(myReader);
		myScanner.setTrace(scannerTrace);
		
		Token t = null;
		do{
			t = myScanner.nextToken();
		} while(t.getName() != TokenType.ENDFILE);
		
	}
}
