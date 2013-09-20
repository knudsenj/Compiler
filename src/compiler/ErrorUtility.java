package compiler;

public class ErrorUtility {
	
	/**
	 * Decodes an integer to a user readable error message.
	 * @param i the error code
	 * @return a message to the user
	 */
	public static String decodeError(int i){
		if(i>0){
			return "Unknown character: "+(char)i;
		}else{
			switch(i){
			case -1:
				return "Integer too large";
			}
		}
		return "Unknown Error";
	}
}
