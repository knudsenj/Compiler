package Scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;

import token.Token;
import token.TokenType;

public class Scanner {
	private static HashMap<String, Token> symbolMap;
	private HashMap<String, Token> wordTable;
	private int wordIndex;
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
		
	}
	private int comment_counter;
	private int start, end;
	private String current_line;
	private BufferedReader input;
	private boolean tracing;
	private int line_number;
	
	public Scanner(BufferedReader input){
		start = end = comment_counter = wordIndex = 0;
		tracing = false;
		line_number = 0;
		current_line = null;
		this.input = input;
		wordTable = new HashMap<>();
		wordTable.put("and", new Token(TokenType.AND, null));
		wordTable.put("bool", new Token(TokenType.BOOL, null));
		wordTable.put("branch", new Token(TokenType.BRANCH, null));
		wordTable.put("case", new Token(TokenType.CASE, null));
		wordTable.put("continue", new Token(TokenType.CONTINUE, null));
		wordTable.put("default", new Token(TokenType.DEFAULT, null));
		wordTable.put("else", new Token(TokenType.ELSE, null));
		wordTable.put("end", new Token(TokenType.END, null));
		wordTable.put("exit", new Token(TokenType.EXIT, null));
		wordTable.put("if", new Token(TokenType.IF, null));
		wordTable.put("int", new Token(TokenType.INT, null));
		wordTable.put("loop", new Token(TokenType.LOOP, null));
		wordTable.put("mod", new Token(TokenType.MOD, null));
		wordTable.put("or", new Token(TokenType.OR, null));
		wordTable.put("ref", new Token(TokenType.REF, null));
		wordTable.put("return", new Token(TokenType.RETURN, null));
		wordTable.put("void", new Token(TokenType.VOID, null));
		wordTable.put("not", new Token(TokenType.NOT, null));
		wordTable.put("true", new Token(TokenType.BLIT, 1));
		wordTable.put("false", new Token(TokenType.BLIT, 0));
	}
	
	public Token nextToken(){
		/*
		 * read a line if no current line, if at the end of the line get a new line 
		 */
		if(current_line == null || start >= current_line.length()-1){
			try {
				current_line = input.readLine();
				if(tracing){
					System.out.println(++line_number+": "+current_line);
				}
				if(current_line.equals("null")){
					return new Token(TokenType.ENDFILE, null);
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
		

		// Comment Catch
		if(current_line.length() > end + 1 && (int)current_line.charAt(end) == 47 && (int)current_line.charAt(end+1) == 42){
			comment_counter++;
			end += 2;
			//New line within Comment
			if(current_line == null || end == current_line.length()){
				try {
					current_line = input.readLine();
				} catch (IOException e) {
					System.out.println("no line to read");
				}
				end = 0;
			}
		
			while(comment_counter > 0){
				if((int)current_line.charAt(end) == 47 && (int)current_line.charAt(end+1) == 42){
					comment_counter++;
					end += 2;
				}
				if((int)current_line.charAt(end) == 42 && (int)current_line.charAt(end+1) == 47){
					comment_counter--; 
					end +=2;
				}
				else{
					end ++;
				}
				//New line within comment
				if(current_line == null || end == current_line.length()){
					try {
						current_line = input.readLine();
					} catch (IOException e) {
						System.out.println("no line to read");
					}
					end = 0;
				}
			}
			start = end;
			return nextToken();
		}
		
		Token t = null;
		// ID/Keyword Catch
		if(Character.isLetter(current_line.charAt(start))){		
			t = wordToken();
			if(tracing){
				System.out.println("\t"+t);
			}
			return t;
		}
		
		// Number Catch
		if(Character.isDigit(current_line.charAt(end))){
			t = numeralToken();
			if(tracing){
				System.out.println("\t"+t);
			}
			return t;
		}

		// Symbol Catch
		if(symbolMap.containsKey(current_line.charAt(start)+"")){
			t = symbolToken();
			if(tracing){
				System.out.println("\t"+t);
			}
			return t;
		}
		
		// Unrecognized character
		start = ++end;
		t = new Token(TokenType.ERROR, (int)current_line.charAt(start-1));
		if(tracing){
			System.out.println("\t"+t);
		}
		return t;
	}
	
	private Token symbolToken(){
		if(symbolMap.containsKey(current_line.substring(start, end+1))){
			end++;
			return symbolToken();
		}
		Token t = symbolMap.get(current_line.substring(start, end));
		start = end;
		return t;
	}
	
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
	
	private Token wordToken(){
		if(Character.isLetterOrDigit(current_line.charAt(end)) ||
				current_line.charAt(end) == '_' ||
				current_line.charAt(end) == '$'){
			end++;
			return wordToken();
		}
		String word = current_line.substring(start, end);
		start = end;
		if(wordTable.containsKey(word)){
			return wordTable.get(word);
		} else {
			wordTable.put(word, new Token(TokenType.ID, wordIndex++));
			return wordTable.get(word);
		}
	}
	
	public void setTrace(boolean tracing){
		this.tracing = tracing;
	}
	
	public boolean isTracing(){
		return this.tracing;
	}
}
