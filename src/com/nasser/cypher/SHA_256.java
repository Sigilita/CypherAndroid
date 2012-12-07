package com.nasser.cypher;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

	public class SHA_256{
		String name;
		int id;
		public SHA_256() {
			name="SHA-256";
			id=1;
		}
		public SHA_256(String name,int id){
			this.name=name;
			this.id=id;
		}
		    public byte[] encode(String input) throws Exception {
		        String keyString = "untextomuylargo!@$@#$#@$#*&(*&}{23432432432dsfsdf";
		        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

		       //Inicializacion del IV (Initialization vector)
		        byte[] iv = new byte[cipher.getBlockSize()];
		        new SecureRandom().nextBytes(iv);
		        IvParameterSpec ivSpec = new IvParameterSpec(iv);

		        // hash keyString con SHA-256 y recorte a 128
		        MessageDigest digest = MessageDigest.getInstance("SHA-256");
		        digest.update(keyString.getBytes());
		        byte[] key = new byte[16];
		        System.arraycopy(digest.digest(), 0, key, 0, key.length);
		        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");

		        // Encriptacion
		        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
		        byte[] encrypted = cipher.doFinal(input.getBytes("UTF-8"));
		        
		        int lengt= encrypted.length + iv.length;
		        byte[] sms =new byte[lengt];
		        for(int i=0;i<iv.length;i++){
		        	sms[i]=iv[i];
		        }
		        for(int i=0; i<encrypted.length ;i++){
		        	sms[i+iv.length]=encrypted[i];
		        }
		        return sms;

		}
		    public String desencriptar(byte[]encrypted) throws Exception{
		    	String keyString = "untextomuylargo!@$@#$#@$#*&(*&}{23432432432dsfsdf";
		        byte[] iv = new byte[16];
		        int length=encrypted.length-iv.length;
		        byte[]decode= new byte[length];

		        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		        
		        for(int i=0;i<16;i++){
		        	iv[i]=encrypted[i];
		        }
		        for(int i=0;i<16;i++){
		        	decode[i]=encrypted[iv.length +i];
		        }
		        //Preparacion del vector de inicializacion y del mensaje
		        IvParameterSpec ivSpec = new IvParameterSpec(iv);

		     // hash keyString con SHA-256 y recorte a 128
		        MessageDigest digest = MessageDigest.getInstance("SHA-256");
		        digest.update(keyString.getBytes());
		        byte[] key = new byte[16];
		        System.arraycopy(digest.digest(), 0, key, 0, key.length);
		        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
		        
		        // desencriptacion
		        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
		        byte[] decrypted = cipher.doFinal(decode);
		       return new String(decrypted, "UTF-8");
		    }
	}