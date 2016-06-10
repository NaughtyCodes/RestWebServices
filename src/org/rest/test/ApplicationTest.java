package org.rest.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ApplicationTest {
	
	public static void main(String args[]) throws CustomException {
		try{
			File f = new File("TestCase.txt");
			//FileOutputStream fout = new FileOutputStream(f);
			FileInputStream fin = new FileInputStream(f);
			int i = 0;
			String s = "";
			while((i = fin.read()) != -1){
				s = s+(char)i+"";
				//System.out.println((char)i+"__"+i);
			}
			System.out.println(s);
			s = "Testinggggggggggggg";
			byte b[] = s.getBytes();
			//fout.write(b);
			//fout.close();
			ApplicationTest.sum();
		} catch(FileNotFoundException e){
			//e.printStackTrace();
			System.out.println("calling --> FileNotFoundException");
		} catch(IOException e){
			System.out.println("calling --> IOException");
		}
	}
	
	public static void sum(){
		int a = 10;
		int b = 5;
		int c = 10/2;
		System.out.println(c);
		try {
			throw new CustomException("custom exception");
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//throw new ArithmeticException("user calling exception");		
	}

}
