package com.orient.sysmodel.domain.role;

import java.util.Iterator;

import com.orient.sysmodel.operationinterface.IFunction;



/**
 * Function entity. @author MyEclipse Persistence Tools
 */
public class Function extends AbstractFunction implements java.io.Serializable, IFunction {

    // Constructors

    /** default constructor */
    public Function() {
    }

	/** minimal constructor */
    public Function(Long functionid, String url) {
        super(functionid, url);        
    }
    
    /** full constructor */
    public Function(String code, String name, Long functionid, String url, String notes, String addFlg, String delFlg, String editFlg, Long position, String logtype, String logshow, Long isShow, String tbomFlg) {
        super(code, name, functionid, url, notes, addFlg, delFlg, editFlg, position, logtype, logshow, isShow, tbomFlg);        
    }
    
    @SuppressWarnings("unchecked")
	public RoleFunctionTbom findRoleFunTbom(RoleFunctionTbomId roleFunTbomId)
	{
		
		for(Iterator<RoleFunctionTbom> it = this.getRoleFunctionTboms().iterator(); it.hasNext();)
		{
			RoleFunctionTbom roleFunTbom = it.next();
			if(roleFunTbom.getId().toString().equals(roleFunTbomId.toString()))
				return roleFunTbom;
		}
		
		return null;
	}
	
	public RoleFunctionTbom deleteRoleFunTbom(RoleFunctionTbomId roleFunTbomId)
	{
		
		RoleFunctionTbom roleFunTbom = findRoleFunTbom(roleFunTbomId);
		if(roleFunTbom!=null)
			this.getRoleFunctionTboms().remove(roleFunTbom);
		
		return roleFunTbom;
	}
   
}
