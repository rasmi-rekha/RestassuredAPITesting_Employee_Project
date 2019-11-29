package com.employeeapi.utilities;

import org.apache.commons.codec.binary.Base64;
import com.employeeapi.base.TestBase;

public class CreateAccessToken extends TestBase{

	public String getAccessToken(String username,String password){
		String accessToken = null;
		String forEncoding = username + ":" + password ;
		byte[] encoded_ClientCredentials = Base64.encodeBase64(forEncoding.getBytes());
		accessToken =  new String(encoded_ClientCredentials);
		return accessToken;
	}
}
