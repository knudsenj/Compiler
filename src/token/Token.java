package token;

public class Token {
	private TokenType name;
	private Integer lexeme;
	
	public Token(TokenType name, Integer lexeme){
		this.name = name;
		this.lexeme = lexeme;
	}

	public Integer getLexeme() {
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
				.append(", ")
				.append(lexeme)
				.append(")")
				.toString();
	}
}
