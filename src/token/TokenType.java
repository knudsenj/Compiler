package token;

public enum TokenType {
	ID,			//identifier
	NUM,		//number
	BLIT,		//boolean literal
	ENDFILE, 	//end of file
	ERROR, 		//error
	AND,		//and
	BOOL,		//bool
	BRANCH, 	//branch
	CASE,		//case
	CONTINUE,	//continue
	DEFAULT,	//default
	ELSE,		//else
	END,		//end
	EXIT,		//exit
	IF,			//if
	INT,		//int
	LOOP,		//loop
	MOD,		//mod
	OR,			//or
	REF,		//ref
	RETURN,		//return
	VOID,		//void
	NOT, 		//not
	PLUS,		// +
	MINUS,		// -
	MULT,		// *
	DIV,		// /
	ANDTHEN,	// &&
	ORELSE,		// ||
	LT,  		// <
	LTEQ,		// <=
	GT,			// >
	GTEQ,		// >=
	EQ,			// =
	NEQ,		// /=
	ASSIGN,		// :=
	SEMI,		// ;
	COMMA,		// ,
	LPAREN,		// (
	RPAREN,		// )
	LSQR,		// [
	RSQR,		// ]
	LCRLY,		// {
	RCRLY,		// }
}
