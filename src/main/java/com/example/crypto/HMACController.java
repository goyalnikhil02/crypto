package com.example.crypto;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmac.example.CryptoUtil;
import com.example.crypto.UserDetails;


@RestController
public class HMACController {

	@PostMapping(value = "/updateProfile")
	public String updateProfile(@RequestHeader(name = "HMAC", required = true) String hmacheader,
			@RequestBody UserDetails userdata, HttpServletResponse response) throws JsonProcessingException {

		Map map = new HashMap<String, String>();

		map.put("Nikhil", "secretkey1");
		map.put("Goyal", "secretkey2");

		// first validate the the integrity of the data
		// we should use the interceptor ,but just for simplicity i am doing validation
		// here.

		String[] hmacData = hmacheader.split(":");

		String username = hmacData[0].trim();
		String nonce = hmacData[1].trim();		
		String clientHMAC = hmacData[2].trim();
		
		String key = (String) map.get(username);

		if (!validate(userdata, clientHMAC, key,nonce)) {
			return "fail";
		}
		return "Updated";
	}

	private boolean validate(UserDetails userdata, String incomingHMAC, String secretkey,String nonce)
			throws JsonProcessingException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		String jsonString = mapper.writeValueAsString(userdata);
		
		
		String datatoSign = nonce+ jsonString;
		
		String serverhmac = new CryptoUtil().calculateHMAC(secretkey, datatoSign);
        
		System.out.println(serverhmac);
        System.out.println(incomingHMAC);
        
		
		return serverhmac.equals(incomingHMAC);
	}
}
