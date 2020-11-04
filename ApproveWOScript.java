package com.acme.wo;

import java.util.*;

import com.ibm.json.java.*;
import com.ibm.tivoli.maximo.oslc.provider.RestClient;

public class ApproveWOScript 
{
	public static void main(String[] args) throws Exception
	{
		Map<String,String> queryParams = new HashMap<String,String>();
		queryParams.put("worktype", "EV");
		queryParams.put("location", "BOILER");
		RestClient rc = new RestClient("http://localhost:7001/maximo/api/script/apprwo")
							.withMethod(RestClient.HTTPMETHOD_POST)
							.withApiKey("7j0d5ib3uugbl3jjiefn4597i8sjppcrj1ucbd0r")
							.withQueryParams(queryParams);
		JSONArtifact ja = rc.invokeJson();
		if(rc.isError(ja))
		{
			throw new Exception(rc.getErrorMessage(ja));
		}

	}
}
