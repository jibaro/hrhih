package com.hrhih.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

//import net.sf.jmimemagic.Magic;
//import net.sf.jmimemagic.MagicException;
//import net.sf.jmimemagic.MagicMatch;
//import net.sf.jmimemagic.MagicMatchNotFoundException;
//import net.sf.jmimemagic.MagicParseException;

import org.slf4j.Logger;

/**
 * 操作文件或目录 
 * @author Yesq
 */
public class FileManager {
//	private static Logger logger = Logger.getLogger(FileManager.class);
	
	private static Logger logger = MyLog.get();
	
    /**  
     * 删除文件，可以是单个文件或文件夹  
     * @param   fileName    待删除的文件名  
     * @return 文件删除成功返回true,否则返回false  
     */  
    public static boolean delete(String fileName){  
    	//System.out.println("文件名："+fileName);
        File file = new File(fileName);   
        if(!file.exists()){   
            ////System.out.println("删除文件失败："+fileName+"文件不存在"); 
            logger.info("删除文件失败："+fileName+"文件不存在");
            return false;   
        }else{   
            if(file.isFile()){
                return deleteFile(fileName);   
            }else{   
                return deleteDirectory(fileName);   
            }   
        }   
    }
       
    /**  
     * 删除单个文件  
     * @param   fileName    被删除文件的文件名  
     * @return 单个文件删除成功返回true,否则返回false  
     */  
    public static boolean deleteFile(String fileName){   
        File file = new File(fileName);   
        if(file.isFile() && file.exists()){   
            file.delete();   
            ////System.out.println("删除单个文件"+fileName+"成功！");   
            logger.info("删除单个文件"+fileName+"成功！");
            return true;   
        }else{   
            ////System.out.println("删除单个文件"+fileName+"失败！"); 
            logger.info("删除单个文件"+fileName+"失败！");
            return false;   
        }   
    }   
       
    /**  
     * 删除目录（文件夹）以及目录下的文件  
     * @param   dir 被删除目录的文件路径  
     * @return  目录删除成功返回true,否则返回false  
     */
    public static boolean deleteDirectory(String dir){
        //如果dir不以文件分隔符结尾，自动添加文件分隔符   
        if(!dir.endsWith(File.separator)){   
            dir = dir+File.separator;   
        }   
        File dirFile = new File(dir);   
        //如果dir对应的文件不存在，或者不是一个目录，则退出   
        if(!dirFile.exists() || !dirFile.isDirectory()){   
            ////System.out.println("删除目录失败"+dir+"目录不存在！");
            logger.info("删除目录失败"+dir+"目录不存在！");
            return false;   
        }   
        boolean flag = true;   
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();   
        for(int i=0;i<files.length;i++){   
            //删除子文件   
            if(files[i].isFile()){   
                flag = deleteFile(files[i].getAbsolutePath());   
                if(!flag){   
                    break;   
                }   
            }   
            //删除子目录   
            else{   
                flag = deleteDirectory(files[i].getAbsolutePath());   
                if(!flag){   
                    break;   
                }   
            }   
        }   
           
        if(!flag){   
            ////System.out.println("删除目录失败");   
            logger.info("删除目录失败");
            return false;   
        }   
           
        //删除当前目录   
        if(dirFile.delete()){
            ////System.out.println("删除目录"+dir+"成功！");  
            logger.info("删除目录"+dir+"成功！");
            return true;   
        }else{   
            ////System.out.println("删除目录"+dir+"失败！");   
            logger.info("删除目录"+dir+"失败！");
            return false;   
        }   
    }
    

	/**
	 * 对TXT文本文件写数据	一data一行
	 * @param data	文件内容数据
	 * @param file	文件路径
	 * @param isAdd	true:在文件末尾增加文件内容，false:替换文件内容
	 * @return
	 */
	public static boolean writerFile(String data,File file,boolean isAdd){
		boolean isSuc=true;
		BufferedWriter out = null;  
		 try {  
		     out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, isAdd),"UTF-8"));
		     out.write(data);  
		     out.write("\n"); 
		     out.flush();
		 } catch (Exception e) {
			 isSuc= false;
			 e.printStackTrace();
		 } finally {  
		     if(out!=null){
		    	 try {  
			         out.close();  
			     } catch (IOException e) {  
			         e.printStackTrace();  
			     }  
		     }
		 }
		 return isSuc;
	}
	
	/**
	 * 对TXT文本文件写数据，一datas多行
	 * @param datas	多条文件内容数据
	 * @param file	文件路径
	 * @param isAdd	true:在文件末尾增加文件内容，false:替换文件内容
	 * @return
	 */
	public static boolean writerFile(Collection<String> datas,File file,boolean isAdd){
		boolean isSuc=true;
		BufferedWriter out = null;  
		 try {  
		     out = new BufferedWriter(new OutputStreamWriter(
		             new FileOutputStream(file, isAdd),"UTF-8"));
		     
		     for(String data:datas){
	    		 out.write(data);
			     out.write("\n"); 
		     }
//		     for(String data:datas){
//		    	 if(data.matches("^[\u4e00-\u9fa5]+$")){
//		    		 out.write(data);
//		    		 out.write("\n"); 
//		    	 }
//		     }
		     out.flush();
		 } catch (Exception e) {
			 isSuc= false;
			 e.printStackTrace();
		 } finally {  
		     if(out!=null){
		    	 try {  
			         out.close();  
			     } catch (IOException e) {  
			         e.printStackTrace();  
			     }  
		     }
		 }
		 return isSuc;
	}
	
	/**
	 * 对TXT文本文件读,读取多行。
	 * @param file
	 * @return
	 */
	public static List<String> readersFile(File file,String encode){
		FileInputStream fileStream =null;
		BufferedReader reader = null;
		List<String> linelist=null;
		try {
			 fileStream = new FileInputStream(file);   
			 InputStreamReader br = new InputStreamReader(fileStream,encode); 
			 reader = new BufferedReader(br);
			 String line="";
			 linelist=new ArrayList<String>();
			 while((line=reader.readLine())!=null){
				 linelist.add(line);
			 }
//			 while((line=reader.readLine())!=null){
////				 linelist.add(line);
//				 if(!line.replaceAll("[\\s*|\t|\r|\n|　| ]+", "").trim().equals("")){
////					 System.out.println("true");
//					 line=line.replaceAll("[\\s*|\t|\r|\n|　| |\\w]", "");
//					 if(line.length()<9&&line.length()>1){
//						 linelist.add(line);
//					 }
//				 }
//			 }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(reader!=null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return linelist;
	}
	
	/**
	 * 对TXT文本文件读,读取多行。
	 * @param file
	 * @return
	 */
	public static List<String> readersFile(File file){
        FileReader fr=null;
		BufferedReader reader = null;
		List<String> linelist=null;
		try {
			 fr = new FileReader(file);
			 reader = new BufferedReader(fr);
			 String line="";
			 linelist=new ArrayList<String>();
			 while((line=reader.readLine())!=null){
				 linelist.add(line);
			 }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(reader!=null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return linelist;
	}
	
	/**
	 * 对TXT文本文件读， 只读取一行
	 * @param file 文件名
	 * @param encode 编码
	 * @return
	 */
	public static String readerFile(File file,String encode){
		String line=null;
		FileInputStream fileStream =null;
		BufferedReader in =null;
		try {
			fileStream = new FileInputStream(file);   
			InputStreamReader br = new InputStreamReader(fileStream,encode); 
			in = new BufferedReader(br);
			line =in.readLine();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		
		return line;
	}
	
	/**
	 * 系列化写文件
	 * @param obj
	 * @param file
	 * @return
	 */
//	public static boolean writeObject(List<String> list,File file){
	public static boolean writeObject(Object obj,File file){
		boolean isSuc= true;
		ObjectOutputStream out=null;
		try {
			FileOutputStream fileStream = new FileOutputStream(file,true); 
			out = new ObjectOutputStream(fileStream); 
			out.writeObject(obj);
			out.flush();
		} catch (FileNotFoundException e) {
			isSuc= false;
			e.printStackTrace();
		} catch (IOException e) {
			isSuc= false;
			e.printStackTrace();
		} finally{
			if(out!=null){
		    	 try {  
			         out.close();  
			     } catch (IOException e) {  
			         e.printStackTrace();  
			     }  
		     }
		}
		
		return isSuc;
	}
	
	/**
	 * 系列化读文件
	 * @param file 文件名
	 * @return
	 */
	public static List<String> readerObject(File file){
		List<String> blacklist=null;
		try {
			FileInputStream fileStream = new FileInputStream(file);   
			BufferedInputStream br = new BufferedInputStream(fileStream); 
			ObjectInputStream in = new ObjectInputStream(br);
			blacklist = (List<String>)in.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}   
		return blacklist;
	}
	
	//清空TXT文件内容
	public static void clearFile(File file){
		FileOutputStream out=null;
		try {
			out = new FileOutputStream(file);
			out.write(new String("").getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//复制文件
	public static long copyFile(File from,File to) throws Exception{
	    long time=new Date().getTime();
	    int length=2*1024*1024;		//缓存2M
	    FileInputStream in=new FileInputStream(from);
	    FileOutputStream out=new FileOutputStream(to);
	    FileChannel inC=in.getChannel();
	    FileChannel outC=out.getChannel();
	    int i=0;
	    while(true){
	        if(inC.position()==inC.size()){
	            inC.close();
	            outC.close();
	            return new Date().getTime()-time;
	        }
	        if((inC.size()-inC.position())<2*1024*1024)
	            length=(int)(inC.size()-inC.position());
	        else
	            length=2*1024*1024;
	        inC.transferTo(inC.position(),length,outC);
	        inC.position(inC.position()+length);
	        i++;
	    }
	}
	
	//拷贝目录
	public static void copyDirectory(File srcDir,File dstDir) throws Exception{
		if(srcDir.isDirectory()){
			if(!dstDir.exists()){
				dstDir.mkdir();
			}
			String[] children=srcDir.list();
			for(int i=0;i<children.length;i++){
				copyDirectory(new File(srcDir,children[i]),new File(dstDir,children[i]));
			}
		}else{
			copyFile(srcDir,dstDir);
		}
	}
	
//	public static String fileType(byte[] b){
//		Magic parser = new Magic() ;  
//		// getMagicMatch accepts Files or byte[],  
//		// which is nice if you want to test streams  
//		MagicMatch match = null;
//		try {
//			match = parser.getMagicMatch(b);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
////		System.out.println(match.getMimeType()) ;
//		return match.getMimeType();
//	}
       
    public static void sample(String path)
    {
    	//PropertyConfigurator.configure(path+"/com/myTest.properties");
        logger.debug("Here is DEBUG messgae");
        logger.info("Here is INFO message");
        logger.warn("Here is WARN message");
        logger.error("Here is ERROR message");
//        logger.fatal("Here is FATAL message");
    }
    public static void main(String[] args) throws Exception{
    	
//    	File from=new File("D:\\testdir\\copyfrom");
//    	File to=new File("D:\\testdir\\copyto");
//    	FileManager.copyDirectory(from, to);
    	
    	File from=new File("D:\\testdir\\testA.txt");
    	File to=new File("D:\\testdir\\testA_bak.txt");
    	FileManager.copyFile(from, to);
    	//System.out.println("copy success!");
    	//URL ftfile=SigeltonAnalyzer.class.getResource("/");
    	
    	//IndexQuartzJob.sample(ftfile.getPath());
    	
		//File indexdir=new File(ftfile.getPath()+"/file");
        
        //ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
		//String fileName = "g:/temp/xwz.txt";   
        //DeleteFileUtil.deleteFile(fileName);   
        //String fileDir = "G:/temp/temp0/temp1";   
        //DeleteFileUtil.deleteDirectory(fileDir);   
        //IndexQuartzJob.delete(fileDir);   
		
		
    }   
}
