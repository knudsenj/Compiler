package token;

public class Token {
	private TokenType name;
	private String lexeme;
	
	public Token(TokenType name, String lexeme){
		this.name = name;
		this.lexeme = lexeme;
	}

	public String getLexeme() {
		return lexeme;
	}

	public TokenType getName() {
		return name;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		return sb.append("Token(")
				.append(name)
				.append(",")
				.append(lexeme)
				.append(")")
				.toString();
	}
}
