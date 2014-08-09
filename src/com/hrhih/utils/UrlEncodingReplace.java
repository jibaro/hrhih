package com.hrhih.utils;

public class UrlEncodingReplace {
	/**
	 * @param args
	 */
	
	public static String getValiGBK(String str){
		String value=null;
		try{
			
			if(!str.equals(null)){
			value= new String(str.getBytes("ISO-8859-1"),"GBK");
			}else{
				value="";
			}
	    
		}catch(Exception e){
			e.printStackTrace();
		}
		return value;
	  }
	
    public static String[] getValiGBK(String[] str){
        
        String[] value=null;
        try{
            for(int i=0;i<str.length;i++){
                if(!str.equals(null)){
                    value[i]= new String(str[i].getBytes("ISO-8859-1"),"GBK");
                    }else{
                        value[i]="";
                    }
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return value;
    }
	
	
	public static String getValiGB2312(String str){
		String value=null;
		try{
			
			if(!str.equals(null)){
			value= new String(str.getBytes("ISO-8859-1"),"gb2312");
			}else{
				value="";
			}
	    
		}catch(Exception e){
			e.printStackTrace();
		}
		return value;
	  }
    public static String[] getValiGB2312(String[] str){
        
        String[] value=null;
        try{
            for(int i=0;i<str.length;i++){
                if(!str.equals(null)){
                    value[i]= new String(str[i].getBytes("ISO-8859-1"),"gb2312");
                    }else{
                        value[i]="";
                    }
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return value;
    }
    public static String getValiUTF8(String str){
		String value=null;
		try{
			
			if(!str.equals(null)){
				value= new String(str.getBytes("ISO-8859-1"),"UTF-8");
			}else{
				value="";
			}
	    
		}catch(Exception e){
			e.printStackTrace();
		}
		return value;
	  }
    public static String[] getValiUTF8(String[] str){
        
        String[] value=null;
        try{
            for(int i=0;i<str.length;i++){
                if(!str.equals(null)){
                    value[i]= new String(str[i].getBytes("ISO-8859-1"),"UTF-8");
                    }else{
                        value[i]="";
                    }
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return value;
    }
    
    public static void main(String args[]){
    	//System.out.println(getValiUTF8("   "));
    }

}

