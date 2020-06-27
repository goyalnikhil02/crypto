package com.hmac.example;

import java.net.URI;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HMACClient {

	private final static String SECRET = "secretkey1";

	private static final Logger LOG = LoggerFactory.getLogger(HMACClient.class);

	public static void main(String[] args) throws Exception {
		new HMACClient().makeHTTPCallUsingHMAC("nikhil");
	}

	public void makeHTTPCallUsingHMAC(String username) throws Exception {

		CryptoUtil util = new CryptoUtil();
		String nonce = "1234";

		String contentToEncode = "{\"username\":\"Nikhil\",\"email\":\"nikhil@gmail.com\"}";

		StringEntity requestEntity = new StringEntity(contentToEncode, ContentType.APPLICATION_JSON);

		CloseableHttpClient client = HttpClients.createDefault();

		HttpPost httpPost = new HttpPost("http://localhost:8080/updateProfile");
		httpPost.setEntity(requestEntity);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");

		String toSign = nonce + contentToEncode;

		String hmac = util.calculateHMAC(SECRET, toSign);

		httpPost.addHeader("hmac", username + ":" + nonce +":"+hmac);

		CloseableHttpResponse response = client.execute(httpPost);

		HttpEntity entity = response.getEntity();
		String responseString = EntityUtils.toString(entity, "UTF-8");
		System.out.println(responseString);

		client.close();

	}

}
