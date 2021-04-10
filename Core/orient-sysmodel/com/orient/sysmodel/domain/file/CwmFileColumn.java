package com.orient.sysmodel.domain.file;

public class CwmFileColumn extends AbstractCwmFileColumn implements java.io.Serializable{

	
	public CwmFileColumn()
	{
		
	}
	
	public CwmFileColumn(String id,String fileid, String colname, String filecolname, String order)
	{
		super(id,fileid,colname,filecolname,order);
	}
}
