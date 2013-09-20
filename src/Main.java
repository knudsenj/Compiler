import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Scanner.Scanner;


public class Main {

	public static void main(String[] args) {
		File file = new File(args[0]);
		BufferedReader  myReader = null; 
		try{
			myReader = new BufferedReader(new FileReader(file));
		} catch(IOException e){
			System.out.println("File not found");
		}
		Scanner myScanner = new Scanner(myReader);
		
		
		int x= 0;
		while(x < 20){
			System.out.println(myScanner.nextToken());
			x++;
		}
		
	}
}
