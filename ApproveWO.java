package com.acme.wo;

import java.util.*;

import com.ibm.json.java.*;
import com.ibm.tivoli.maximo.oslc.provider.RestClient;

public class ApproveWO 
{
	public static void main(String[] args) throws Exception
	{
		//Create the REST client
		Map<String,String> queryParams = new HashMap<String,String>();
		queryParams.put("oslc.where", "location=\"BOILER\" and status=\"WAPPR\" and worktype=\"EV\"");
		RestClient rc = new RestClient("http://localhost:7001/maximo/api/os/mxapiwodetail")
							.withMethod(RestClient.HTTPMETHOD_GET)
							.withApiKey("7j0d5ib3uugbl3jjiefn4597i8sjppcrj1ucbd0r")
							.withQueryParams(queryParams);
		//Invoke Maximo api
		JSONObject jo = (JSONObject)rc.invokeJson();
		
		//process response
		JSONArray members = (JSONArray)jo.get("member");
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("x-method-override", "PATCH");
		headers.put("patchtype", "MERGE");
		queryParams = new HashMap<String,String>();
		queryParams.put("action", "wsmethod:changeStatus");
		
		//prepare the change status request
		JSONObject changeStatusReq = new JSONObject();
		changeStatusReq.put("status", "APPR");
		changeStatusReq.put("memo", "Approving WOs");

		for(int i=0;i<members.size();i++)
		{
			JSONObject joWO = (JSONObject)members.get(i);
			String href = (String)joWO.get("href");
			rc = new RestClient(href)
					.withApiKey("7j0d5ib3uugbl3jjiefn4597i8sjppcrj1ucbd0r")
					.withMethod(RestClient.HTTPMETHOD_POST)
					.withHeaders(headers)
					.withQueryParams(queryParams);
			
			JSONArtifact ja = rc.invoke(changeStatusReq);
			if(rc.isError(ja))
			{
				throw new Exception(rc.getErrorMessage(ja));
			}
		}
	}
}
