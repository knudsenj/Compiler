import java.io.BufferedReader;
import java.io.InputStreamReader;

import Scanner.Scanner;


public class Main {

	public static void main(String[] args) {
		System.out.println("Type a token in to test:");

		BufferedReader  myReader = new BufferedReader(new InputStreamReader(System.in));
		Scanner myScanner = new Scanner(myReader);
		
		while(true){
			myScanner.nextToken();
		}
		
	}
}
