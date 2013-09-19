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
	
	private int start;
	private int end;
	private String current_line;
	private BufferedReader input;

	public Scanner(BufferedReader input){
		start = end = 0;
		current_line = null;
		this.input = input;
	}
	
	public Token nextToken(){
		int comment_counter = 0;
		
		/*
		 * read a line if no current line, if at the end of the line get a new line
		 */
		if(current_line == null){
			try {
				current_line = input.readLine();
			} catch (IOException e) {
				System.out.println("no line to read");
			}
			start = end = 0;
		}
			
		

		
		//Ignore control characters, TODO fix this to not ignore whitespace
		if( (int)current_line.charAt(end) < 32){
			end++;
		}
		
	
	//Branch based on character
		
		//String Catch (Upper || Lower case)
		if((65 <= (int)current_line.charAt(end) && (int)current_line.charAt(end) <= 90) || (97 <= (int)current_line.charAt(end) && (int)current_line.charAt(end) <= 122 )){
			System.out.println("Found Word");
		}
			
			//start String procedure
				//check for keyword
				
		//Num Catch
		if(48 <= (int)current_line.charAt(end) && (int)current_line.charAt(end) <= 57){
			System.out.println("Found Numeral");
		}
			
			//start NUM procedure
			
		//TODO change to switch statement	
		//Comment catch
		if(current_line.length() > end + 1 && (int)current_line.charAt(end) == 47 && (int)current_line.charAt(end+1) == 42){
				comment_counter++;
				end += 2;	
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
				}			
			}

		//Plus Catch
		if((int)current_line.charAt(end) == 43){		
			System.out.println("Plus");
			Token T = new Token(TokenType.PLUS,null);
			start = ++end;
			return T;
		}
		//Minus Catch
		if((int)current_line.charAt(end) == 45){		
			System.out.println("Minus");
			Token T = new Token(TokenType.MINUS,null);
			start = ++end;
			return T;
		}
		//Mult Catch
		if((int)current_line.charAt(end) == 42){		
			System.out.println("Mult");
			Token T = new Token(TokenType.MULT,null);
			start = ++end;
			return T;
		}
		//Div Catch
		if((int)current_line.charAt(end) == 47){		
			System.out.println("Div");
			Token T = new Token(TokenType.DIV,null);
			start = ++end;
			return T;
		}
		return null;
	}
	
	/** 
	 * Determined it is a word token, find word and return token. 
	 * It must start with a letter.
	 * Valid character set: a .. z | A .. Z | 0 .. 9 | _ | $
	 * @return ID token, or special keyword token
	 */
	private Token wordToken(){
		//TODO map of keywords
		
		return null;
	}
	
	private Token numeralToken(){
		//TODO
		
		return null;
	}
	
	private Token symbolToken(){
		//TODO
		
		return null;
	}
}
