userInfo = request.getUserInfo()
location = request.getQueryParam("location")
workType = request.getQueryParam("worktype")
woSet = service.getMboSet("WORKORDER", userInfo)
woSet.setWhere("status='WAPPR' and location='"+location+"' and worktype='"+workType+"'")
woMbo = woSet.moveFirst()
while woMbo is not None:
    woMbo.changeStatus("APPR",None,"testing")
    woMbo = woSet.moveNext()

woSet.save()