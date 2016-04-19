/*
    This is the first assigment for systems and security module. 
    Made by Joonas Känsälä in 23.3.2016
*/

package securityassignment;

import java.math.BigInteger;

public class RSA {
	
	// Returns: [0] = gcd - [1] = factor for y
	public BigInteger[] calcGcd(BigInteger _x, BigInteger _y){
		BigInteger x = _x;
		BigInteger y = _y;
		BigInteger factor = BigInteger.ONE; 
		BigInteger leftOver = BigInteger.ONE;
		BigInteger z = BigInteger.ONE;
		BigInteger xx = BigInteger.ONE;
		BigInteger yy = BigInteger.ZERO;
		BigInteger v = BigInteger.ONE;
		BigInteger u = BigInteger.ZERO;
		
		while(!y.equals(BigInteger.ZERO)){
			factor = x.divide(y); 
			leftOver = x.subtract(factor.multiply(y));			
			x = y;
			y = leftOver;			
			z = u;
			u = xx.subtract(factor.multiply(u));
			xx = z;
			z = v;
			v = yy.subtract(factor.multiply(v));
			yy = z;
			
		}

		BigInteger returnNumber[] = new BigInteger[2];
		
		returnNumber[0] = x;
		// Avoid using negative private keys
		if(yy.compareTo(BigInteger.ZERO)==-1)
			returnNumber[1] = _x.add(yy);
		else
			returnNumber[1] = yy;	
		return returnNumber;
	}
	
	public BigInteger[] calcKeys(PrimeNumber _p, PrimeNumber _q){
		BigInteger returnKeys[] = new BigInteger[3];
		
		// Calculate n = pq
		BigInteger n = _q.getValue().multiply(_p.getValue());
			
		// Pick another number e such that e and (p-1)(q-1) are relatively prime.
		// F = (p-1)(q-1)
		BigInteger f = _q.getValue().subtract(BigInteger.ONE).multiply
							  (_p.getValue().subtract(BigInteger.ONE));		
		BigInteger e = new BigInteger(f.toString(2).length()-1, PrimeNumber.random);
		BigInteger i = BigInteger.ZERO;
		do{
				e = e.add(BigInteger.ONE);
				i = calcGcd(f, e)[0];		
		}while(!i.equals(BigInteger.ONE));
			
		// Calculate d such that ed = 1 mod (p-1)(q-1)
		BigInteger d = calcGcd(f, e)[1];
		
		returnKeys[0] = n; // Public key
		returnKeys[1] = e; // Public exponent
		returnKeys[2] = d; // Private key
		
		return returnKeys;
	}
	
	public BigInteger[] encrypt(String _message, BigInteger publicKey, BigInteger publicExponent){
		
		BigInteger encrypted[] = new BigInteger[_message.length()];
		int message[] = new int[_message.length()];
		
		for(int i=0;i<_message.length();i++)
			message[i] = ((int) _message.charAt(i));
		
		for(int i=0;i<message.length;i++){
			encrypted[i] = new BigInteger(""+message[i]).modPow(publicExponent, publicKey);
		}	
		return encrypted;
	}
	
	public BigInteger[] decrypt(BigInteger[] _encryptedMessage, BigInteger publicKey, BigInteger privateKey){
		
		BigInteger decrypted[] = new BigInteger[_encryptedMessage.length];

		for(int i=0;i<_encryptedMessage.length;i++){
			decrypted[i] = _encryptedMessage[i].modPow(privateKey, publicKey);
		}
		return decrypted;	
	}	

}
