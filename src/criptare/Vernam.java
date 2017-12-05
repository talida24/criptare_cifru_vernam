/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package criptare;

import java.math.BigInteger;
import java.util.Random;
import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.lang.*;
import java .io.*;


public class Vernam extends TransformareBS {
    
    private StringBuilder key = new StringBuilder();
	private StringBuilder keyb = new StringBuilder();
	
	public Vernam(){}
    
    public class SR
    {	
		String aux;
        public SR(){
            try{
                SecureRandom secureRandomGenerator = SecureRandom.getInstance("SHA1PRNG");
				int key1 = secureRandomGenerator.nextInt();
				aux = Integer.toString(key1);
            }
            catch(NoSuchAlgorithmException e){
            }
        }
    }
    
    public class bbs {
        private double p, q, M, seed, actual;
        public String keyaux;
	
        
        public bbs(double p, double q, double seed) {
            this.p = p;
            this.q = q;
            this.M = p*q;
            this.seed = seed;
            this.actual = seed;
        }
        
        public double getrandom() {
            double r = actual*actual%M;
            actual = r;
            return r/M;
        }
 
        private double gcd(double a, double b) {
 
            if(b == 0) return a;
            return gcd(b, a%b);
 
        }
 
        public double getirandom(int i) {
            double g = gcd(p, q);
            double l = (p-1)*(q-1)/g;
 
            double exp = 1;
            for(int j = 1; j <= i; ++j) exp = exp*2%l;
 
            double x0 = seed*seed;
            double r = x0;
            for(double j = 2; j<=exp; ++j ) {
                r = r*x0%M;
            }
            return r/M;
        }
    }

    public class WichmannHill{

        int i1_seed;
        int[] i_seed;
        static final int c0 = 30269;
        static final int c1 = 30307;
        static final int c2 = 30323;
		double aux;

        public WichmannHill() {

            i1_seed = 123;
            i_seed = new int[2];
            fixupSeeds();
        }

        public void fixupSeeds() {

            // exclude 0 as seed
            if (i1_seed == 0) i1_seed++;
            for (int j = 0; j < i_seed.length; j++) {
                if (i_seed[j] == 0) i_seed[j]++;
            }
            if (i1_seed >= c0 || i_seed[0] >= c1 || i_seed[1] >= c2) {
                random();
            }
        }

        public void random() {

            i1_seed = i1_seed * 171 % c0;
            i_seed[0] = i_seed[0] * 172 % c1;
            i_seed[1] = i_seed[1] * 170 % c2;
            double value = (double) i1_seed / c0 + (double) i_seed[0] / c1 + (double) i_seed[1] / c2;
            //return value - (int) value; // ensure in range [0,1)
            //aux = value - (int) value;
			aux=value;
        }

    }
    
     public void decript(StringBuilder cript, StringBuilder Out){
		
		int n = Out.length()*8;
		StringBuilder decript = new StringBuilder();
        for(int i=0; i<n; i++)
		{
            decript.append((keyb.charAt(i) + cript.charAt(i))%2); //folosire cifru vernam
		}
        decript = BS(decript);
		File file1 = new File("fisout2.txt");
		Scriere(decript, file1);
	}
    
    
    public void criptt(){
        int n=mesaj.length()*8; //mesaj este stringul citit in text clar
		StringBuilder cript = new StringBuilder();
        for(int i=0; i<n; i++)
		{
            cript.append((keyb.charAt(i) + mesajb.charAt(i))%2); //folosire cifru vernam
		}
        StringBuilder Out = cript;
		Out = BS(Out);
		File file1 = new File("fisout.txt");
		File file2 = new File("cheie.txt");
		Scriere(key, file2);
		Scriere(Out, file1);
		decript(cript,Out);
		
	}
    
    public Vernam(int opt, int size)
    {
		//prelucrare argument primit din interfata, opt=generator folosit, size=lungimea fisierului
		int l=0;
		
        switch(opt)
        {
            case 0: 
				while(size-l>0)
				{
					SR obj1 = new SR();
					key.append(obj1.aux);
					System.out.println(key+"\n");
					l = key.length();
				}
				break;
            case 1:   
				double p = 11;
				double q = 19;
				double seed = 3;
                while(size-l>0)
				{
					bbs b = new bbs(p,q,seed);
					key.append(Double.toString(b.getrandom()));
					l = key.length();
				}
				break;
            case 2: 
				while(size-l>0){
					WichmannHill obj3 = new WichmannHill();
					key.append(Double.toString(obj3.aux));
					l = key.length();
				}
				break;
        }
		keyb = SB(key);
		criptt();
    }
    
}

