package com.token;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

public class ImportKeys {

	public KeyPair getKey() throws Exception {
		FileInputStream is = new FileInputStream("/home/rnd-nb-scl1344/Desktop/Imp/key");

		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		keystore.load(is, "India@123".toCharArray());

		String alias = "in";

		Key key = keystore.getKey(alias, "India@123".toCharArray());
		// Get certificate of public key
		Certificate cert = keystore.getCertificate(alias);

		// Get public key
		PublicKey publicKey = cert.getPublicKey();

		// Return a key pair
		// return new KeyPair(publicKey, (PrivateKey) key);
		// }
		// return null;
		return new KeyPair(publicKey, (PrivateKey) key);
	}

	
	
	public Key getKeys() throws Exception {
		FileInputStream is = new FileInputStream("C:\\Users\\G521784\\Downloads\\certificates\\key");

		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		keystore.load(is, "India@123".toCharArray());

		String alias = "in";

		Key key = keystore.getKey(alias, "India@123".toCharArray());
		return key;
	}
}