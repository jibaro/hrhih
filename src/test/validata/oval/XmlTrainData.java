package test.validata.oval;

import net.sf.oval.guard.PostValidateThis;

public class XmlTrainData {
    private String xm;
    private String sfzh;
    private String qjStart;
    private String qjEnd;
    private String rxrq;
    private String yhcs;
    private String kxlh;
    private int age;
    
    public XmlTrainData(){}
    
    public XmlTrainData(String xm, String kxlh,int age){
    	this.xm = xm;
    	this.kxlh = kxlh;
    	this.age=age;
    }
    
    public XmlTrainData(String xm, String sfzh, String qjStart, String qjEnd, String rxrq, String yhcs, String kxlh,int age){
        this.xm = xm;
        this.sfzh = sfzh;
        this.qjStart = qjStart;
        this.qjEnd = qjEnd;
        this.rxrq = rxrq;
        this.yhcs = yhcs;
        this.kxlh = kxlh;
        this.age=age;
    }
    
    private boolean isValid(String xm) {
        //to do 数据库根据身份证号查询出的姓名进行比较
        if("myname".equals(xm))
            return true;
        return false;
    }
    
    private boolean isValid_kxlh(String kxlh){
    	System.out.println("===isValid_kxlh");
    	if(kxlh!=null&&kxlh.indexOf("_")!=-1){  //不等于-1表示包含。
    		return false;
    	}
        return true;
    }
    
    @PostValidateThis
    public void setAge(int age){
    	 this.age=age;
    }
    
}
