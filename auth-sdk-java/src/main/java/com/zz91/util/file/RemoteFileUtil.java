package com.zz91.util.file;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

public class RemoteFileUtil {

    public static final String REMOTE_HOST_IP = "192.168.2.178";
    public static final String LOGIN_ACCOUNT = "root";
    public static final String LOGIN_PASSWORD = "zj88friend";
    public static final String SHARE_DOC_NAME = "data";

    private String remoteHostIp;  //远程主机IP
    private String account;       //登陆账户
    private String password;      //登陆密码
    private String shareDocName;  //共享文件夹名称

//    private static RemoteFileUtil _instance;
//
//    synchronized public static RemoteFileUtil getInstance() {
//		if (_instance == null) {
//			_instance = new RemoteFileUtil(remoteHostIp,account,password,shareDocName);
//		}
//		return _instance;
//	}

    /**
     * 默认构造函数
     */
    public RemoteFileUtil(){
        this.remoteHostIp = REMOTE_HOST_IP;
        this.account = LOGIN_ACCOUNT;
        this.password = LOGIN_PASSWORD;
        this.shareDocName = SHARE_DOC_NAME;
    }

    /**
     * 构造函数
     * @param remoteHostIp  远程主机Ip
     * @param account       登陆账户
     * @param password      登陆密码
     * @param sharePath     共享文件夹路径
     */
    public RemoteFileUtil(String remoteHostIp, String account, String password,String shareDocName) {
        this.remoteHostIp = remoteHostIp;
        this.account = account;
        this.password = password;
        this.shareDocName = shareDocName;
    }
    
    public InputStream readFileStream(String remoteFileName){
    	SmbFile smbFile = null;
    	String conStr = null;
        conStr = "smb://"+account+":"+password+"@"+remoteHostIp+"/"+shareDocName+"/"+remoteFileName;
        InputStream is =null;
        try {
            smbFile = new SmbFile(conStr);
            is = smbFile.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
			e.printStackTrace();
		}
//    	try {
//			is = new SmbFileInputStream(smbFile);
//			;
//		} catch (SmbException e) {
//			e.printStackTrace();
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		}
		return is;
    }
    
    
    public SmbFile smbFile(String remoteFileName){
    	String conStr = null;
        conStr = "smb://"+account+":"+password+"@"+remoteHostIp+"/"+shareDocName+"/"+remoteFileName;
        try {
			return new SmbFile(conStr);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
    }
    
    /**
     * 对远程共享文件进行读取所有行
     * @param remoteFileName  文件名  说明：参数为共享目录下的相对路径
     *                                若远程文件的路径为：shareDoc\test.txt,则参数为test.txt(其中shareDoc为共享目录名称);
     *                                若远程文件的路径为：shareDoc\doc\text.txt,则参数为doc\text.txt;
     * @return  文件的所有行
     */
    public List<String> readFile(String remoteFileName){
        SmbFile smbFile = null;
        BufferedReader reader = null;
        List<String> resultLines = null;
        //构建连接字符串,并取得文件连接
        String conStr = null;
        conStr = "smb://"+account+":"+password+"@"+remoteHostIp+"/"+shareDocName+"/"+remoteFileName;
        try {
            smbFile = new SmbFile(conStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        
        //创建reader
        try {
        	InputStreamReader isr = new InputStreamReader(new SmbFileInputStream(smbFile));
            reader = new BufferedReader(isr);
        } catch (SmbException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        //循环对文件进行读取
        String line;
        try {
            line = reader.readLine();
            if(line != null && line.length()>0){
                resultLines = new ArrayList<String>();
            }
            while (line != null) {
                resultLines.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回
        return resultLines;
    }
    
    public SmbFile[] listFiles(String remoteFolder){
    	String conStr = "smb://"+account+":"+password+"@"+remoteHostIp+"/"+shareDocName+"/"+remoteFolder;
    	try {
			SmbFile smbFile = new SmbFile(conStr);
			return smbFile.listFiles();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SmbException e) {
			e.printStackTrace();
		}
		return null;
    }
    

    /**
     * 对远程共享文件进行写入
     * @param is                本地文件的输入流
     * @param remoteFileName    远程文件名    说明：参数为共享目录下的相对路径
     *                                             若远程文件的路径为：shareDoc\test.txt,则参数为test.txt(其中shareDoc为共享目录名称);
     *                                             若远程文件的路径为：shareDoc\doc\text.txt,则参数为doc\text.txt;
     * @return
     */
    public boolean writeFile(InputStream is,String remoteFileName){
        SmbFile smbFile = null;
        OutputStream os = null;
        byte[] buffer = new byte[1024*8];
        //构建连接字符串,并取得文件连接
        String conStr = null;
        conStr = "smb://"+account+":"+password+"@"+remoteHostIp+"/"+shareDocName+"/"+remoteFileName;
        try {
            smbFile = new SmbFile(conStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }

        //获取远程文件输出流并写文件到远程共享文件夹
        try {
            os = new BufferedOutputStream(new SmbFileOutputStream(smbFile));
            while((is.read(buffer))!=-1){
                os.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean writeFolder(String remoteFolder){
    	SmbFile smbFile = null;
    	String conStr = "smb://"+account+":"+password+"@"+remoteHostIp+"/"+shareDocName+"/"+remoteFolder;
    	try {
			smbFile = new SmbFile(conStr);
			if(!smbFile.exists()){
				smbFile.mkdirs();
			}
			return true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SmbException e) {
			e.printStackTrace();
		}
		return false;
    }
    

    /**
     * 对远程共享文件进行写入重载
     * @param localFileName   要写入的本地文件全名
     * @param remoteFileName  远程文件名    说明：参数为共享目录下的相对路径
     *                                           若远程文件的路径为：shareDoc\test.txt,则参数为test.txt(其中shareDoc为共享目录名称);
     *                                           若远程文件的路径为：shareDoc\doc\text.txt,则参数为doc\text.txt;
     * @return
     */
    public boolean writeFile(String localFileFullName ,String remoteFileName){
        try {
            return writeFile(new FileInputStream(new File(localFileFullName)),remoteFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 对远程共享文件进行写入重载
     * @param localFileName   要写入的本地文件
     * @param remoteFileName  远程文件名    说明：参数为共享目录下的相对路径
     *                                           若远程文件的路径为：shareDoc\test.txt,则参数为test.txt(其中shareDoc为共享目录名称);
     *                                           若远程文件的路径为：shareDoc\doc\text.txt,则参数为doc\text.txt;
     * @return
     */
    public boolean writeFile(File localFile ,String remoteFileName){
        try {
            return writeFile(new FileInputStream(localFile),remoteFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteFile(String remoteFileName){
    	SmbFile smbFile = null;
    	String conStr = "smb://"+account+":"+password+"@"+remoteHostIp+"/"+shareDocName+"/"+remoteFileName;
    	try {
			smbFile = new SmbFile(conStr);
			smbFile.delete();
			return true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SmbException e) {
			e.printStackTrace();
		}
    	return false;
    }
    
    public boolean deleteFolder(String remoteFolder){
    	return deleteFile(remoteFolder);
    }

    public static void main(String[] args){
//        RemoteFileUtil util = new RemoteFileUtil("192.168.2.178","mays","zj88friend","data");
//		List<String> fileList = util.readFile("html/root/index2c5716d512490894.html");
//        for(String s:fileList){
//        	System.out.println(s);
//        }
//        System.out.println(fileList.size());
//      List<String> lines = util.readFile("test.txt");
//      for (String string : lines) {
//          System.out.println(string);
//      }
//        util.writeFolder("resources/newfolder/");
//        util.writeFile(new File("/root/root.zip"), "resources/periodical/2010/9/27/root.zip");
//        SmbFile file = util.smbFile("resources/1.jpg");
//        try {
//			System.out.println("exists:"+file.exists());
//		} catch (SmbException e) {
//			e.printStackTrace();
//		}
//        System.out.println("expiration:"+file.getExpiration());
//        System.out.println("LastModified:"+file.getLastModified());
//        System.out.println("IfModifiedSince:"+file.getIfModifiedSince());
//        System.out.println("Date:"+file.getDate());
//        System.out.println("headerField:"+file.getHeaderField(0));
//        
//        try {
//			System.out.println(DateUtil.toString(DateUtil.getDate(new Date(file.getDate()), "yyyy-MM-dd"), "yyyy-MM-dd"));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
        
        
    }



}