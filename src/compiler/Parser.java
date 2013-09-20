package compiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import token.Token;
import token.TokenType;

public class Parser {
	Scanner scanner;
	
	/**
	 * Constructs a parser to parse the file given
	 * @param file the file to parse
	 */
	public Parser(File file){
		BufferedReader  myReader = null; 
		try{
			myReader = new BufferedReader(new FileReader(file));
		} catch(IOException e){
			System.out.println(new File("").getAbsolutePath());
			System.out.println("File not found");
		}
		scanner = new Scanner(myReader);
	}
	
	/**
	 * Parses the file, currently just loops nextToken on the scanner
	 * until it gets to the the ENDFILE token.
	 */
	public void start(){
		Token t = null;
		do{
			t = scanner.nextToken();
		} while(t.getName() != TokenType.ENDFILE);
	}
	
	/**
	 * sets the scanners tracer
	 * @param s true will turn the tracer on
	 */
	public void setScannerTrace(boolean s){
		scanner.setTrace(s);
	}
}
