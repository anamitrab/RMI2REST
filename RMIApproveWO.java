package psdi.iface.util;

import java.rmi.RemoteException;

import psdi.app.workorder.WORemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.util.MXSession;

public class RMIApproveWO {
	
	public static void main(String[] args) throws RemoteException, MXException
	{
		MXSession mxSession = createMXSession("rmi://localhost/MXServer","wilson","wilson");
		MboSetRemote msr = mxSession.getMboSet("WORKORDER");
		msr.setWhere("location='BOILER' and worktype='EV' and status='WAPPR'");
		WORemote mbo = (WORemote)msr.moveFirst();
		while(mbo != null)
		{
			mbo.changeStatus("APPR", null, "test");
			mbo = (WORemote)msr.moveNext();
		}
		msr.save();
	}
	
	static MXSession createMXSession(String rmiurl, String username, String password)
	{
		MXSession session = null;
		try
		{
			if(rmiurl.startsWith("rmi:"))
			{
				rmiurl = rmiurl.substring(6);
			}
			session = MXSession.getSession();
			session.setHost(rmiurl);
			session.setUserName(username);
			session.setPassword(password);
			session.connect();
			return session;	
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}


}
