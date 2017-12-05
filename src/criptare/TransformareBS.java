/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package criptare;

import java .io.*;
import java.lang.*;

public class TransformareBS extends MeniuCriptare {			//mosteneste interfata

	public static StringBuilder mesaj;
	public StringBuilder mesajb=SB(mesaj);
	
	TransformareBS(){}

    
	public static int Citire(String filepath)//primeste ca argument pathul fisierului
	{
		StringBuilder str= new StringBuilder(); //fisier intrare de unde citim testul
		try {
			BufferedInputStream in = new BufferedInputStream (new FileInputStream (filepath));//path
			System.setIn (in);
			BufferedReader br = new BufferedReader (new InputStreamReader ( System .in));

			String s;
			while ((s = br. readLine ()) != null ) str.append(s);  

		} catch ( IOException e) {
			System . err. println (" Eroare intrare / iesire !");
		}

		
		System .out. println (str);

		int x = str.length();
		mesaj = str;
                return x;
	}
	
	public void Scriere(StringBuilder str, File file)
	{
		BufferedWriter output = null;
		try {
            output = new BufferedWriter(new FileWriter(file));
            output.append(str);
        } catch ( IOException e ){
            e.printStackTrace();
        } 
		try{
			output.close();
		} catch ( IOException e ){
            e.printStackTrace();
        }
		System.out.println("Date salvate");
	}

	public StringBuilder  BS(StringBuilder s)
	{
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < s.length()/8; i++) 
		{
			int a = Integer.parseInt(s.substring(8*i,(i+1)*8),2);
			str.append((char)(a));
		}
		return str;
    }

	public StringBuilder SB(StringBuilder s)
	{
		String s2 = s.toString();
		byte[] bytes = s2.getBytes();
      
		StringBuilder binary = new StringBuilder();
		for (byte b : bytes)
		{
			int val = b;
			for (int i = 0; i < 8; i++)
			{
				binary.append((val & 128) == 0 ? 0 : 1);
				val <<= 1;
			}
			//binary.append(' ');
		}
      
		return binary;
	}
}