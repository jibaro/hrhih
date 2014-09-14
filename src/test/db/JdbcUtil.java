package test.db;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.hrhih.utils.FileManager;


public class JdbcUtil {
	
	private static Properties info=new Properties();
	static{
		try {
			InputStream fis=JdbcUtil.class.getResourceAsStream("/springJDBC.properties");
			info.load(fis);	
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//用于获得数据库连接。
	private static final ThreadLocal<Connection> tl=new ThreadLocal<Connection>();
	public static Connection  getConnection() throws ClassNotFoundException, SQLException{		
		Connection conn=tl.get();
			if(conn==null){
				Class.forName(info.getProperty("mydriver"));
				String url=info.getProperty("jdbc_url");
				System.out.println(url);
    			conn=DriverManager.getConnection(url,info.getProperty("jdbc_username"),info.getProperty("jdbc_password"));
    		tl.set(conn);
    	   }
    	return conn;
    }
    
	//关闭数据库连接。
	public static void getCloseJDBC(ResultSet rs,Statement stm,Connection conn){		
		if(rs!=null)  try{ rs.close();} catch(Exception ee){}
		if(stm!=null)  try{ stm.close();} catch(Exception ee){}
		if(conn!=null) try{ conn.close();} catch(Exception ee){}
	}
	
	//用于获得系列。
	public static Integer getSeq(String seq) throws Exception{
		Connection conn=null;
		Statement stm=null;
		ResultSet rs=null;
		try{
		 	conn=getConnection();
      		stm=conn.createStatement();
     		String sql="select "+seq+".nextval from dual";
      		rs=stm.executeQuery(sql);
      		rs.next();
      		return rs.getInt(1);
		}finally{
			JdbcUtil.getCloseJDBC(rs, stm, null);
		}
	}
/*	
	public static Integer getSeqPool(String seq) throws Exception{
		Connection conn=null;
		Statement stm=null;
		ResultSet rs=null;
		try{
		 	conn=DBManager.getConnection();
      		stm=conn.createStatement();
     		String sql="select "+seq+".nextval from dual";
      		rs=stm.executeQuery(sql);
      		rs.next();
      		return rs.getInt(1);
		}finally{
			JdbcUtil.getCloseJDBC(rs, stm, null);
		}
	}
*/	
	//测试方法
	public static void main(String[] args) throws Exception{	
		Connection conn=getConnection();
		System.out.println("===>>>"+conn);
		
		
//		t_Province(conn);
//		t_City(conn);
//		t_District(conn);
		
//		updateReginPinyin(conn,"D:/MyTest/hrhih/region","District2.txt","T_District","Id");	//5000
//		updateReginPinyin(conn,"D:/MyTest/hrhih/region","City2.txt","T_city","CityID");	//8000
//		updateReginPinyin(conn,"D:/MyTest/hrhih/region","Province2.txt","T_Province","ProID");   //9999

//		exportRegion(conn,"D:/MyTest/hrhih/region","Province_sug.txt","T_Province","ProName");
//		exportRegion(conn,"D:/MyTest/hrhih/region","City_sug.txt","T_city","CityName");
		exportRegion(conn,"D:/MyTest/hrhih/region","District_sug.txt","T_District","DisName");
		
		conn.close();
		//System.out.println(getSeq("sql_seq"));
	}
	
	public static void updateReginPinyin(Connection conn,String path,String filename,String tablename,String idname) throws Exception{
		int seqnum=5000;
		
		conn.setAutoCommit(false);
		String psql="UPDATE "+tablename+" SET pingyin=?, szm=?,seqnum=? where "+idname+"=? ;";
		PreparedStatement statement = conn.prepareStatement(psql);
		List<String> list=FileManager.readersFile(new File(path,filename), "utf-8");
		for(String line:list){
			try {
				if(line==null||"".equals(line.trim())){
					continue;
				}
				String[] lineArr=line.split("@");
				if(lineArr.length<4){
					continue;
				}
				statement.setString(1, lineArr[2]);
				statement.setString(2, lineArr[3]);
				statement.setInt(3, seqnum);
				statement.setInt(4, Integer.parseInt(lineArr[0]));
				statement.addBatch();
				seqnum--;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("line=="+line);
			}
		}
		statement.executeBatch();
		conn.commit();
		statement.clearBatch();
		statement.close();
	}
	
	public static void exportRegion(Connection conn,String path,
									String filename,String tablename,String reFName) throws Exception{
		String sql="select * from "+tablename+";";
		Statement stm=conn.createStatement();
		ResultSet rs=stm.executeQuery(sql);
		List<String> list=new ArrayList<String>();
		while(rs.next()){
			StringBuffer sb=new StringBuffer();
			sb.append(rs.getString(reFName)).append("@").append(rs.getString("pingyin"))
			.append("@").append(rs.getString("szm")).append(",").append(rs.getInt("seqnum"));
			list.add(sb.toString());
		}
		FileManager.writerFile(list, new File(path,filename), true);
		
		rs.close();
		stm.close();
	}
	
	public static void t_Province(Connection conn) throws Exception{
		String sql="select * from T_Province;";
		Statement stm=conn.createStatement();
		ResultSet rs=stm.executeQuery(sql);
		List<String> list=new ArrayList<String>();
		while(rs.next()){
			StringBuffer sb=new StringBuffer();
			sb.append(rs.getInt("ProID")).append("@").append(rs.getString("ProName"));
			list.add(sb.toString());
//			System.out.println(rs.getInt("ProID")+"@"+rs.getString("ProName")+"@"+rs.getInt("ProSort"));
		}
		FileManager.writerFile(list, new File("D:/MyTest/hrhih/region","Province.txt"), true);
		
		rs.close();
		stm.close();
		
	}
	
	public static void t_City(Connection conn) throws Exception{
		String sql="select * from T_city;";
		Statement stm=conn.createStatement();
		ResultSet rs=stm.executeQuery(sql);
		List<String> list=new ArrayList<String>();
		while(rs.next()){
			StringBuffer sb=new StringBuffer();
			sb.append(rs.getInt("CityID")).append("@").append(rs.getString("CityName"));
			list.add(sb.toString());
//			System.out.println(rs.getInt("ProID")+"@"+rs.getString("ProName")+"@"+rs.getInt("ProSort"));
		}
		FileManager.writerFile(list, new File("D:/MyTest/hrhih/region","City.txt"), true);
		
		rs.close();
		stm.close();
	}
	
	public static void t_District(Connection conn) throws Exception{
		String sql="select * from T_District;";
		Statement stm=conn.createStatement();
		ResultSet rs=stm.executeQuery(sql);
		List<String> list=new ArrayList<String>();
		while(rs.next()){
			StringBuffer sb=new StringBuffer();
			sb.append(rs.getInt("Id")).append("@").append(rs.getString("DisName"));
			list.add(sb.toString());
//			System.out.println(rs.getInt("ProID")+"@"+rs.getString("ProName")+"@"+rs.getInt("ProSort"));
		}
		FileManager.writerFile(list, new File("D:/MyTest/hrhih/region","District.txt"), true);
		
		rs.close();
		stm.close();
	}
}


