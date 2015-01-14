package com.zz91.util.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipCompress {

    /**
     * @param args
     */
    public static void main(String[] args) throws IOException {
        compress("f:\\test","f:\\testzip.zip");
    }
    /**
     * 递归压缩文件
     * @param source 源路径,可以是文件,也可以目录
     * @param destinct  目标路径,压缩文件名
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
	private static void compress(String source,String destinct) throws IOException {
        List fileList=loadFilename(new File(source));
        ZipOutputStream zos=new ZipOutputStream(new FileOutputStream(new File(destinct)));

        byte[] buffere=new byte[8192];
        int length;
        BufferedInputStream bis;

        for(int i=0;i<fileList.size();i++) {
            File file=(File) fileList.get(i);
            zos.putNextEntry(new ZipEntry(getEntryName(source,file)));
            bis=new BufferedInputStream(new FileInputStream(file));

            while(true) {
                length=bis.read(buffere);
                if(length==-1) break;
                zos.write(buffere,0,length);
            }
            bis.close();
            zos.closeEntry();
        }
        zos.close();
    }
    /**
     * 递归获得该文件下所有文件名(不包括目录名)
     * @param file
     * @return
     */
    @SuppressWarnings("unchecked")
	private static List loadFilename(File file) {
        List filenameList=new ArrayList();
        if(file.isFile()) {
            filenameList.add(file);
        }
        if(file.isDirectory()) {
            for(File f:file.listFiles()) {
                filenameList.addAll(loadFilename(f));
            }
        }
        return filenameList;
    }
    /**
     * 获得zip entry 字符串
     * @param base
     * @param file
     * @return
     */
    private static String getEntryName(String base,File file) {
        File baseFile=new File(base);
        String filename=file.getPath();
        //int index=filename.lastIndexOf(baseFile.getName());
        if(baseFile.getParentFile().getParentFile()==null)
            return filename.substring(baseFile.getParent().length());
        return filename.substring(baseFile.getParent().length()+1);
    }
}