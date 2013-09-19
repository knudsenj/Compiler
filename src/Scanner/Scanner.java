package Scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import token.Token;
import token.TokenType;

public class Scanner {
	int current_char = 0;
	
	
	public BufferedReader input;

	public Scanner(BufferedReader input){
		this.input = input;
	}
	
	public Token nextToken(){
		int comment_counter = 0;
		String current_line ="";
		
		
		//Read Line	if no current line
		if(current_line == ""){
			try {
				current_line = input.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			current_char = 0;
		}
			
		

		
		//Ignore control characters
		if( (int)current_line.charAt(current_char) < 32){
			current_char++;
		}
		
	
	//Branch based on character
		
		//String Catch (Upper || Lower case)
		if((65 <= (int)current_line.charAt(current_char) && (int)current_line.charAt(current_char) <= 90) || (97 <= (int)current_line.charAt(current_char) && (int)current_line.charAt(current_char) <= 122 )){
			System.out.println("Found Word");
		}
			
			//start String procedure
				//check for keyword
				
		//Num Catch
		if(48 <= (int)current_line.charAt(current_char) && (int)current_line.charAt(current_char) <= 57){
			System.out.println("Found Numeral");
		}
			
			//start NUM procedure
			
			
		//Comment catch
		if(current_line.length() > current_char + 1 && (int)current_line.charAt(current_char) == 47 && (int)current_line.charAt(current_char+1) == 42){
				comment_counter++;
				current_char += 2;	
				while(comment_counter > 0){
					if((int)current_line.charAt(current_char) == 47 && (int)current_line.charAt(current_char+1) == 42){
						comment_counter++;
						current_char += 2;
					}
					if((int)current_line.charAt(current_char) == 42 && (int)current_line.charAt(current_char+1) == 47){
						comment_counter--; 
						current_char +=2;
					}
					else{
						current_char ++;
					}
				}			
			}

		//Plus Catch
		if((int)current_line.charAt(current_char) == 43){		
			System.out.println("Plus");
			Token T = new Token(TokenType.PLUS,null);
			current_char++;
			return T;
		}
		//Minus Catch
		if((int)current_line.charAt(current_char) == 45){		
			System.out.println("Minus");
			Token T = new Token(TokenType.MINUS,null);
			current_char ++;
			return T;
		}
		//Mult Catch
		if((int)current_line.charAt(current_char) == 42){		
			System.out.println("Mult");
			Token T = new Token(TokenType.MULT,null);
			current_char ++;
			return T;
		}
		//Div Catch
		if((int)current_line.charAt(current_char) == 47){		
			System.out.println("Div");
			Token T = new Token(TokenType.DIV,null);
			current_char ++;
			return T;
		}
		
		//
		
		
		
		
		
		
		
		
		
		
		return null;
	}
}
