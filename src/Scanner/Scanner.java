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
	private static HashMap<String, Token> wordTable;
	static{
		symbolMap = new HashMap<>();
		symbolMap.put("+", new Token(TokenType.PLUS, null));
		symbolMap.put("-", new Token(TokenType.MINUS, null));
		symbolMap.put("*", new Token(TokenType.MULT, null));
		symbolMap.put("/", new Token(TokenType.DIV, null));
		symbolMap.put("&", new Token(TokenType.ERROR, "no such symbol"));
		symbolMap.put("&&", new Token(TokenType.ANDTHEN, null));
		symbolMap.put("|", new Token(TokenType.ERROR, "no such symbol"));
		symbolMap.put("||", new Token(TokenType.ORELSE, null));
		symbolMap.put("<", new Token(TokenType.LT, null));
		symbolMap.put("<=", new Token(TokenType.LTEQ, null));
		symbolMap.put(">", new Token(TokenType.GT, null));
		symbolMap.put(">=", new Token(TokenType.GTEQ, null));
		symbolMap.put("=", new Token(TokenType.EQ, null));
		symbolMap.put("/=", new Token(TokenType.NEQ, null));
		symbolMap.put(":=", new Token(TokenType.ASSIGN, null));
		symbolMap.put(";", new Token(TokenType.SEMI, null));
		symbolMap.put(",", new Token(TokenType.COMMA, null));
		symbolMap.put("(", new Token(TokenType.LPAREN, null));
		symbolMap.put(")", new Token(TokenType.RPAREN, null));
		symbolMap.put("[", new Token(TokenType.LSQR, null));
		symbolMap.put("]", new Token(TokenType.RSQR, null));
		symbolMap.put("{", new Token(TokenType.LCRLY, null));
		symbolMap.put("}", new Token(TokenType.RCRLY, null));
		
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
	}
	int comment_counter = 0;
	private int end;
	private String current_line;
	private BufferedReader input;

	
	
	
	public Scanner(BufferedReader input){
		end = 0;
		
		current_line = null;
		this.input = input;
	}
	
	public Token nextToken(){
		
		String str = "";
		/*
		 * read a line if no current line, if at the end of the line get a new line 
		 */
		if(current_line == null || end == current_line.length()){
			try {
				current_line = input.readLine();
				//System.out.println("reading next line");
			} catch (IOException e) {
				System.out.println("no line to read");
			}
			end = 0;
		}
			
		// Ignore control characters and whitespace before comment
		while(current_line.length() > end && (int)current_line.charAt(end) <= 32){
			end++;
		}
		

		// Comment Catch
		if(current_line.length() > end + 1 && (int)current_line.charAt(end) == 47 && (int)current_line.charAt(end+1) == 42){
				comment_counter++;
				end += 2;
				//New line within Comment
				if(current_line == null || end == current_line.length()){
					try {
						current_line = input.readLine();
						//System.out.println("reading next line");
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
							//System.out.println("reading next line");
						} catch (IOException e) {
							System.out.println("no line to read");
						}
						end = 0;
					}
					
				}			
			}
				
		
		
		// Ignore control characters and whitespace after comment
		while(current_line.length() > end && (int)current_line.charAt(end) <= 32){
			end++;
		}
		
		
		//Next line if trailing whitespace after comment
		if(current_line == null || end == current_line.length()){
			try {
				current_line = input.readLine();
				//System.out.println("reading next line");
			} catch (IOException e) {
				System.out.println("no line to read");
			}
			end = 0;
		}
		
		

		
		
		
		
		// ID/Keyword Catch
		if((65 <= (int)current_line.charAt(end) && (int)current_line.charAt(end) <= 90) || (97 <= (int)current_line.charAt(end) && (int)current_line.charAt(end) <= 122 )){		
			str = str + current_line.charAt(end);
			end++;
			while(current_line.length() > end  && ((65 <= (int)current_line.charAt(end) && (int)current_line.charAt(end) <= 90) || (97 <= (int)current_line.charAt(end) && (int)current_line.charAt(end) <= 122 ) || (48 <= (int)current_line.charAt(end) && (int)current_line.charAt(end) <= 57)))
			{
				str = str + current_line.charAt(end);
				end++;
			}
			//check for Keyword in table
			Token T = wordTable.get(str);
			if(T != null){
				return T;
			}
			else{
				T = new Token(TokenType.ID,str);
				return T;
			}
		}
		
		// Number Catch
		if(48 <= (int)current_line.charAt(end) && (int)current_line.charAt(end) <= 57){
			str = str + current_line.charAt(end);
			end++;
			while(current_line.length() > end  && ( 48 <= (int)current_line.charAt(end) && (int)current_line.charAt(end) <= 57)){
				str = str + current_line.charAt(end);
				end++;
			}
			Token T = new Token(TokenType.NUM,str);
			end++;
			return T;
		}

		// Symbol Catch
		str = str + current_line.charAt(end);
		end++;
		Token T = symbolMap.get(str);
		if (T != null && current_line.length() > end)
		{
			str = str + current_line.charAt(end);
			Token U = symbolMap.get(str);
			if( U != null){
				end++;
				return U;
			}
			else{
				return T;
			}
		}
		if(T != null){
			return T;
		}
		else{
			//report error
			System.out.println("Unrecognized Symbol:"+str);
			return null;
		}
		
		
	}
}
