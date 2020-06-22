package com.token;
/*
 * package jwt;
 * 
 * import com.nimbusds.jose.JWSAlgorithm; import com.nimbusds.jose.JWSHeader;
 * import com.nimbusds.jose.JWSObject; import com.nimbusds.jose.JWSSigner;
 * import com.nimbusds.jose.JWSVerifier; import com.nimbusds.jose.Payload;
 * import com.nimbusds.jose.crypto.RSASSASigner; import
 * com.nimbusds.jose.crypto.RSASSAVerifier;
 * 
 * import java.security.PublicKey;
 * 
 * import org.jose4j.jwk.RsaJsonWebKey; import org.jose4j.jwk.RsaJwkGenerator;
 * import org.jose4j.lang.JoseException;
 * 
 * import com.nimbusds.jose.*; import com.nimbusds.jose.crypto.*; import
 * com.nimbusds.jose.jwk.*;
 * 
 * public class JWS1 { public static void main(String[] args) {
 * 
 * // RSA signatures require a public and private RSA key pair, // the public
 * key must be made known to the JWS recipient to // allow the signatures to be
 * verified RsaJsonWebKey rsaJWK = null; try { rsaJWK =
 * RsaJwkGenerator.generateJwk(2048); } catch (JoseException e) { // TODO
 * Auto-generated catch block e.printStackTrace(); }
 * 
 * // Give the JWK a Key ID (kid), which is just the polite thing to do
 * rsaJWK.setKeyId("k1");
 * 
 * 
 * //RSAKey rsaJWK = new RsaJwkGenerator(2048).keyID("123").generate();
 * PublicKey rsaPublicJWK = rsaJWK.getPublicKey();
 * 
 * // Create RSA-signer with the private key // JWSSigner signer = new
 * RSASSASigner(rsaJWK);
 * 
 * // Prepare JWS object with simple string as payload JWSObject jwsObject = new
 * JWSObject(new
 * JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaJWK.getKeyID()).build(), new
 * Payload("In RSA we trust!"));
 * 
 * // Compute the RSA signature jwsObject.sign(signer);
 * 
 * // To serialize to compact form, produces something like //
 * eyJhbGciOiJSUzI1NiJ9.SW4gUlNBIHdlIHRydXN0IQ.IRMQENi4nJyp4er2L //
 * mZq3ivwoAjqa1uUkSBKFIX7ATndFF5ivnt-m8uApHO4kfIFOrW7w2Ezmlg3Qd //
 * maXlS9DhN0nUk_hGI3amEjkKd0BWYCB8vfUbUv0XGjQip78AI4z1PrFRNidm7 //
 * -jPDm5Iq0SZnjKjCNS5Q15fokXZc8u0A String s = jwsObject.serialize();
 * 
 * // To parse the JWS and verify it, e.g. on client-side jwsObject =
 * JWSObject.parse(s); JWSVerifier verifier = new RSASSAVerifier(rsaPublicJWK);
 * 
 * // assertTrue(jwsObject.verify(verifier));
 * 
 * // assertEquals("In RSA we trust!", jwsObject.getPayload().toString()); } }
 */