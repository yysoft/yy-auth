package com.zz91.util.file;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

public class SmbUnZip {

	private String remoteHostIp;  //远程主机IP
    private String account;       //登陆账户
    private String password;      //登陆密码
    private String shareDocName;  //共享文件夹名称
	
	public SmbUnZip (){
		
	}
	
	public SmbUnZip(String remoteHostIp, String account, String password,String shareDocName){
		this.remoteHostIp = remoteHostIp;
        this.account = account;
        this.password = password;
        this.shareDocName = shareDocName;
	}
	
	public boolean unzipToRemote(String source, String dest){
    	String conStr = "smb://"+account+":"+password+"@"+remoteHostIp+"/"+shareDocName+"/";
    	if (!dest.endsWith("/")) dest += "/";
    	try {
			ZipInputStream zin = new ZipInputStream(new SmbFileInputStream(conStr+source));
			ZipEntry entry;
			while((entry=zin.getNextEntry())!=null){
				if (entry.isDirectory()) {
	                new SmbFile(dest + entry.getName()).mkdirs();
	            } else {
	                SmbFileOutputStream output =
	                        new SmbFileOutputStream(conStr+dest + entry.getName());
	                byte[] buf = new byte[1024];
	                int count;
	                while ((count = zin.read(buf, 0, 1024)) != -1) {
	                    output.write(buf, 0, count);
	                }
	                output.flush();
	                output.close();
	            }
			}
			return true;
		} catch (SmbException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
		return false;
	}
	
	public boolean unzipToLocal(String source, String dest){
		String conStr = "smb://"+account+":"+password+"@"+remoteHostIp+"/"+shareDocName+"/";
    	if (!dest.endsWith("/")) dest += "/";
    	try {
			ZipInputStream zin = new ZipInputStream(new SmbFileInputStream(conStr+source));
			ZipEntry entry;
			while((entry=zin.getNextEntry())!=null){
				if (entry.isDirectory()) {
	                new SmbFile(dest + entry.getName()).mkdirs();
	            } else {
	                SmbFileOutputStream output =
	                        new SmbFileOutputStream(conStr+dest + entry.getName());
	                byte[] buf = new byte[1024];
	                int count;
	                while ((count = zin.read(buf, 0, 1024)) != -1) {
	                    output.write(buf, 0, count);
	                }
	                output.flush();
	                output.close();
	            }
			}
			return true;
		} catch (SmbException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
		return false;
	}
	
	public static void main(String[] args) {
		RemoteFileUtil remoteFileUtil = new RemoteFileUtil("192.168.2.212", "mays", "c", "data");
		remoteFileUtil.writeFolder("resources/root");
		
		SmbUnZip smbunzip = new SmbUnZip("192.168.2.212", "mays", "c", "data");
		smbunzip.unzipToRemote("resources/root.zip", "resources/root/");
	}
}
