package com.token;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;

public class JWEToken {

	  
	public static void main(String[] args) throws Exception {
		
		
		ImportKeys key = new ImportKeys();
		KeyPair keyPair = key.getKey();

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		RSAPublicKeySpec publicKeySpec = keyFactory.getKeySpec(keyPair.getPublic(), RSAPublicKeySpec.class);
		RSAPrivateKeySpec privateKeySpec = keyFactory.getKeySpec(keyPair.getPrivate(), RSAPrivateKeySpec.class);

		RSAPrivateKey privateRsaKey = (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);
		RSAPublicKey publicRsaKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);

		
		// Create the Claims, which will be the content of the JWT
		JwtClaims claims = new JwtClaims();
		claims.setIssuer("Issuer"); // who creates the token and signs it
		claims.setAudience("Audience"); // to whom the token is intended to be sent
		claims.setExpirationTimeMinutesInTheFuture(10); // time when the token will expire (10 minutes from now)
		claims.setGeneratedJwtId(); // a unique identifier for the token
		claims.setIssuedAtToNow(); // when the token was issued/created (now)
		claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid (2 minutes ago)
		claims.setSubject("subject"); // the subject/principal is whom the token is about
		claims.setClaim("email", "nikhil.goyal@example.com"); // additional claims/attributes about the subject can be
																// added
		List<String> groups = Arrays.asList("group-one", "other-group", "group-three");
		claims.setStringListClaim("groups", groups); // multi-valued claims work too and will end up as a JSON array

		

	
		
		System.out.println("--------------------------");
		System.out.println("Claim Set : \n" + claims);

		RsaJsonWebKey rsaJsonWebKey = null;
		try {
			rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
		} catch (JoseException e) {
			e.printStackTrace();
		}

		rsaJsonWebKey.setKeyId("k1");

		JsonWebSignature jws = new JsonWebSignature();

		jws.setPayload(claims.toJson());

	    // The JWT is signed using the sender's private key	
		jws.setKey(privateRsaKey);
		
		jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());
		jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
		// jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);

		String innerjwt = jws.getCompactSerialization();

		 // The outer JWT is a JWE
	    JsonWebEncryption jwe = new JsonWebEncryption();
	    
	 // The output of the ECDH-ES key agreement will encrypt a randomly generated content encryption key
	    jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.RSA_OAEP_256); //RSA_OAEP_256

	    // The content encryption key is used to encrypt the payload
	    // with a composite AES-CBC / HMAC SHA2 encryption algorithm
	    String encAlg = ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256;
	    jwe.setEncryptionMethodHeaderParameter(encAlg);

	    
	    // We encrypt to the receiver using their public key
	    
	    jwe.setKey(publicRsaKey);
	    System.out.println("private key :" + Base64.getEncoder().encodeToString(privateRsaKey.getEncoded()));
	    jwe.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());
       
	    // A nested JWT requires that the cty (Content Type) header be set to "JWT" in the outer JWT
	   // jwe.setContentTypeHeaderValue("CTY"); 

	    // The inner JWT is the payload of the outer JWT
	    jwe.setPayload(innerjwt);

	    // Produce the JWE compact serialization, which is the complete JWT/JWE representation,
	    // which is a string consisting of five dot ('.') separated
	    // base64url-encoded parts in the form Header.EncryptedKey.IV.Ciphertext.AuthenticationTag
	    String jwt = jwe.getCompactSerialization();


	    // Now you can do something with the JWT. Like send it to some other party
	    // over the clouds and through the interwebs.
	    System.out.println("JWE: " + jwt);
		System.out.println()	;    
	    
	    
	    

	}
}
