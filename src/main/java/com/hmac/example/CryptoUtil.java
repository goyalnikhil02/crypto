package com.hmac.example;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CryptoUtil {

	private final static String HMAC_SHA1_ALGORITHM = "HmacSHA1";

	
	private static final Logger LOG = LoggerFactory.getLogger(HMACClient.class);

	public String calculateMD5(String contentToEncode) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		digest.update(contentToEncode.getBytes());
		String result = new String(Base64.getEncoder().encodeToString(digest.digest()));
		return result;
	}

	public String calculateHMAC(String secret, String data) {
		System.out.println("sECRET"+":"+data+"::"+secret);
		try {
			SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), HMAC_SHA1_ALGORITHM);
			Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(data.getBytes());

			String base64HmacSha256 = Base64.getEncoder().encodeToString(rawHmac);
			System.out.println("Base64: " + base64HmacSha256);
			return base64HmacSha256;
		} catch (GeneralSecurityException e) {
			LOG.warn("Unexpected error while creating hash: " + e.getMessage(), e);
			throw new IllegalArgumentException();
		}
	}
}
