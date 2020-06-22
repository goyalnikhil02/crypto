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

import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;

public class JWSExample {

	public static JwtClaims setClaim() {
		// Create the Claims, which will be the content of the JWT
		JwtClaims claims = new JwtClaims();
		claims.setIssuer("NIKHILCompany"); // who creates the token and signs it
		claims.setAudience("Goyal"); // to whom the token is intended to be sent
		claims.setExpirationTimeMinutesInTheFuture(10); // time when the token will expire (10 minutes from now)
		claims.setGeneratedJwtId(); // a unique identifier for the token
		claims.setIssuedAtToNow(); // when the token was issued/created (now)
		claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid (2 minutes ago)
		claims.setSubject("subject"); // the subject/principal is whom the token is about
		claims.setClaim("email", "goyal.nikhil@gamil.com"); // additional claims/attributes about the subject can be
															// added
		List<String> groups = Arrays.asList("group-one", "other-group", "group-three");
		claims.setStringListClaim("groups", groups); // multi-valued claims work too and will end up as a JSON array

		return claims;

	}

	public static RSAPrivateKey getKeyPairUsingKeyStore() throws Exception {
		ImportKeys key = new ImportKeys();
		KeyPair keyPair = key.getKey();

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		RSAPublicKeySpec publicKeySpec = keyFactory.getKeySpec(keyPair.getPublic(), RSAPublicKeySpec.class);
		RSAPrivateKeySpec privateKeySpec = keyFactory.getKeySpec(keyPair.getPrivate(), RSAPrivateKeySpec.class);

		RSAPrivateKey privateRsaKey = (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);
		RSAPublicKey publicRsaKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
		String encodedKey = Base64.getEncoder().encodeToString(publicRsaKey.getEncoded());
		System.out.println("Public key ::" + encodedKey);
		return privateRsaKey;

	}

	public static void main(String[] args) throws JoseException {
		JWSExample obj = new JWSExample();
		try {
			obj.createSignToken(setClaim());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void createSignToken(JwtClaims claims) throws Exception {
		JsonWebSignature jws = new JsonWebSignature();
		jws.setPayload(claims.toJson());

		RSAPrivateKey privateKey = getKeyPairUsingKeyStore();

		jws.setKey(privateKey);
		jws.setKeyIdHeaderValue("k1");

		jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

		String jwt = jws.getCompactSerialization();
		System.out.println("JWT: " + jwt);

	}

}
