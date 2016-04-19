/*
    This is the first assigment for systems and security module. 
    Made by Joonas Känsälä in 23.3.2016
*/

package securityassignment;

import java.math.BigInteger;
import java.security.SecureRandom;


public class PrimeNumber{
	
	private final BigInteger TWO = new BigInteger("2");
	public int bitCount;

	public static SecureRandom random = new SecureRandom();
	private BigInteger value = new BigInteger(bitCount, random);
	
	
	public PrimeNumber(int _bitCount){
		bitCount = _bitCount;
		int i = 0;
		do{
			i++;
			value = new BigInteger(bitCount, random);
		}
                while(!this.isPrime());
		
	}
	
	public boolean isPrime() {
		BigInteger square = sqrt(this.value);
		
		if(this.value.compareTo(TWO)==-1)
			return false;

		if(this.value.remainder(TWO).equals(BigInteger.ZERO))
			return false;

		for(BigInteger i = new BigInteger("3"); 
			i.compareTo(square)==-1; 
			// Skip even numbers 
                        i=i.add(TWO)){ 
				if(this.value.remainder(i).equals(BigInteger.ZERO)){
					System.out.println("False at i "+i+"\n\n");
					return false;
			}
		}
		return true; 
                // Others passed -> prime
	}
	
	public BigInteger getValue() {
		return value;
	}
	
	public static BigInteger sqrt(BigInteger x) {
	    BigInteger div = BigInteger.ZERO.setBit(x.bitLength()/2);
	    BigInteger div2 = div;
	    for(;;) {
	        BigInteger y = div.add(x.divide(div)).shiftRight(1);
	        if (y.equals(div) || y.equals(div2))
	            return y;
	        div2 = div;
	        div = y;
	    }
	}
	
	public static BigInteger factorial(BigInteger n) {
	    BigInteger result = BigInteger.ONE;

	    while (!n.equals(BigInteger.ZERO)) {
	        result = result.multiply(n);
	        n = n.subtract(BigInteger.ONE);
	    }
	    return result;
	}
	
}