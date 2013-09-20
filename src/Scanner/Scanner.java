package Scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import token.Token;
import token.TokenType;

public class Scanner {
	/**
	 * A symbol map to hold all the possible symbols found in a standard C*13
	 * program.
	 */
	private static HashMap<String, Token> symbolMap;
	/**
	 * A map to determine keywords and used words in the program
	 */
	private HashMap<String, Token> wordMap;
	/**
	 * a table to determine the word of an index
	 */
	private ArrayList<String> wordTable;
	/**
	 * Load the symbolMap for all scanners
	 */
	static{
		symbolMap = new HashMap<>();
		symbolMap.put("+", new Token(TokenType.PLUS, null));
		symbolMap.put("-", new Token(TokenType.MINUS, null));
		symbolMap.put("*", new Token(TokenType.MULT, null));
		symbolMap.put("/", new Token(TokenType.DIV, null));
		symbolMap.put("&", new Token(TokenType.ERROR, (int)'&'));
		symbolMap.put("&&", new Token(TokenType.ANDTHEN, null));
		symbolMap.put("|", new Token(TokenType.ERROR, (int)'|'));
		symbolMap.put("||", new Token(TokenType.ORELSE, null));
		symbolMap.put("<", new Token(TokenType.LT, null));
		symbolMap.put("<=", new Token(TokenType.LTEQ, null));
		symbolMap.put(">", new Token(TokenType.GT, null));
		symbolMap.put(">=", new Token(TokenType.GTEQ, null));
		symbolMap.put("=", new Token(TokenType.EQ, null));
		symbolMap.put("/=", new Token(TokenType.NEQ, null));
		symbolMap.put(":=", new Token(TokenType.ASSIGN, null));
		symbolMap.put(":", new Token(TokenType.ERROR, (int)':'));
		symbolMap.put(";", new Token(TokenType.SEMI, null));
		symbolMap.put(",", new Token(TokenType.COMMA, null));
		symbolMap.put("(", new Token(TokenType.LPAREN, null));
		symbolMap.put(")", new Token(TokenType.RPAREN, null));
		symbolMap.put("[", new Token(TokenType.LSQR, null));
		symbolMap.put("]", new Token(TokenType.RSQR, null));
		symbolMap.put("{", new Token(TokenType.LCRLY, null));
		symbolMap.put("}", new Token(TokenType.RCRLY, null));
		symbolMap.put("/*", new Token(TokenType.NSTCOM, null));
		symbolMap.put("--", new Token(TokenType.COM, null));
		
	}
	private int start, end;
	private String current_line;
	private BufferedReader input;
	private boolean tracing;
	private int line_number;
	
	/**
	 * Constructs a Scanner with tracer turned off, to read given input.
	 * @param input The input stream for the scanner to read
	 */
	public Scanner(BufferedReader input){
		start = end = 0;
		tracing = false;
		line_number = 0;
		current_line = null;
		this.input = input;
		wordTable = new ArrayList<>();
		wordMap = new HashMap<>();
		wordMap.put("and", new Token(TokenType.AND, null));
		wordMap.put("bool", new Token(TokenType.BOOL, null));
		wordMap.put("branch", new Token(TokenType.BRANCH, null));
		wordMap.put("case", new Token(TokenType.CASE, null));
		wordMap.put("continue", new Token(TokenType.CONTINUE, null));
		wordMap.put("default", new Token(TokenType.DEFAULT, null));
		wordMap.put("else", new Token(TokenType.ELSE, null));
		wordMap.put("end", new Token(TokenType.END, null));
		wordMap.put("exit", new Token(TokenType.EXIT, null));
		wordMap.put("if", new Token(TokenType.IF, null));
		wordMap.put("int", new Token(TokenType.INT, null));
		wordMap.put("loop", new Token(TokenType.LOOP, null));
		wordMap.put("mod", new Token(TokenType.MOD, null));
		wordMap.put("or", new Token(TokenType.OR, null));
		wordMap.put("ref", new Token(TokenType.REF, null));
		wordMap.put("return", new Token(TokenType.RETURN, null));
		wordMap.put("void", new Token(TokenType.VOID, null));
		wordMap.put("not", new Token(TokenType.NOT, null));
		wordMap.put("true", new Token(TokenType.BLIT, 1));
		wordMap.put("false", new Token(TokenType.BLIT, 0));
	}
	
	/**
	 * Gets the next token found in the C*13 program given when constructed.
	 * @return the next token. At the end of the file it will give an ENDFILE token, 
	 * if called again it will return another ENDFILE.
	 */
	public Token nextToken(){
		/*
		 * read a line if no current line, if at the end of the line get a new line 
		 */
		if(current_line == null || start >= current_line.length()-1){
			try {
				current_line = input.readLine();
				if(current_line == null){
					return trace(new Token(TokenType.ENDFILE, null));
				}
				if(tracing){
					System.out.println(++line_number+": "+current_line);
				}
				current_line += " ";
			} catch (IOException e) {
				System.out.println("an IOException occured when reading line in scanner");
			}
			start = end = 0;
		}
			
		// Ignore control characters and whitespace
		try{
			while(Character.isWhitespace(current_line.charAt(end))){
				start = ++end;
			}
		}catch(StringIndexOutOfBoundsException e){
			return nextToken();
		}
		
		// ID/Keyword Catch
		if(Character.isLetter(current_line.charAt(start))){	
			return trace(wordToken());
		}
		
		// Number Catch
		if(Character.isDigit(current_line.charAt(end))){
			return trace(numeralToken());
		}

		// Symbol Catch
		if(symbolMap.containsKey(current_line.charAt(start)+"")){
			Token t = symbolToken();
			if(t.getName() == TokenType.COM){
				current_line = null;
				return nextToken();
			} else if(t.getName() == TokenType.NSTCOM){
				skipComment();
				return nextToken();
			}
			return trace(t);
		}
		
		// Unrecognized character
		start = ++end;
		return trace(new Token(TokenType.ERROR, (int)current_line.charAt(start-1)));
	}
	
	/*
	 *	the token is determined to be a symbolToken, find the largest symbol.
	 *	ie. find <= versus < and = 
	 */
	private Token symbolToken(){
		if(symbolMap.containsKey(current_line.substring(start, end+1))){
			end++;
			return symbolToken();
		}
		Token t = symbolMap.get(current_line.substring(start, end));
		start = end;
		return t;
	}
	
	/*
	 * the token is determined to be a numeral, the longest numeral possible.
	 * if the numeral is larger than a possible integer, return an ERROR token code -1.
	 */
	private Token numeralToken(){
		if(Character.isDigit(current_line.charAt(end))){
			end++;
			return numeralToken();
		}
		Token t;
		try{
			int i = Integer.parseInt(current_line.substring(start, end));
			t = new Token(TokenType.NUM, i);
		} catch(NumberFormatException e){
			t = new Token(TokenType.ERROR, -1);
		}
		start = end;
		return t;
	}
	
	/*
	 *	token is found to be a word, find the largest word. use the wordMap to 
	 *	determine if it is a keyword or a ID. 
	 */
	private Token wordToken(){
		if(Character.isLetterOrDigit(current_line.charAt(end)) ||
				current_line.charAt(end) == '_' ||
				current_line.charAt(end) == '$'){
			end++;
			return wordToken();
		}
		String word = current_line.substring(start, end);
		start = end;
		if(wordMap.containsKey(word)){
			return wordMap.get(word);
		} else {
			wordMap.put(word, new Token(TokenType.ID, wordTable.size()));
			wordTable.add(word);
			return wordMap.get(word);
		}
	}
	
	/*
	 * comment is found read until end of comment is found. Recursively call
	 * itself if start of comment is found. Skip line if -- is found.
	 * TODO clean this up
	 */
	private void skipComment(){
		while(!current_line.substring(start, end+1).equals("*/")){
			switch(current_line.substring(start, end+1)){
			case "*":
			case "/":
				end++;
				break;
			case "/*":
				start = ++end;
				skipComment();
				break;
			default:
				start = ++end;
			}
			if(end >= current_line.length()-1){
				try {
					current_line = input.readLine();
					if(current_line == null){
						return;
					}
					if(tracing){
						System.out.println(++line_number+": "+current_line);
					}
					current_line += " ";
				} catch (IOException e) {
					System.out.println("an IOException occured when reading line in scanner");
				}
				start = end = 0;
			}
		}
		start = ++end;
	}
	
	/*
	 * print the token if the trace is on.
	 */
	private Token trace(Token t){
		if(tracing){
			System.out.print("\t"+t);
			if(t.getName() == TokenType.ID){
				System.out.println(": "+wordTable.get(t.getLexeme()));
			} else {
				System.out.println();
			}
		}
		return t;
	}
	
	/**
	 * Set whether the trace is on or off.
	 * @param tracing true if trace is to be printed
	 */
	public void setTrace(boolean tracing){
		this.tracing = tracing;
	}
	
	/**
	 * determine if the trace is turned on or not.
	 * @return true if trace is on
	 */
	public boolean isTracing(){
		return this.tracing;
	}
}
