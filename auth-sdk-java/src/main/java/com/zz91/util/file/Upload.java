package com.zz91.util.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.zz91.util.domain.UploadDto;
import com.zz91.util.domain.UploadResult;
import com.zz91.util.file.FileUtils;

public class Upload {

    public final static String DEFAULT_ROOT_FOLDER = "/usr/data/resources";
    public final static String DEFAULT_TMP_FOLDER = "/tmp/";

    private static Upload _instance = null;

    public Upload() {

    }

    synchronized public static Upload getInstance() {
        if (_instance == null) {
            _instance = new Upload();
        }
        return _instance;
    }

    public List<UploadResult> upload(HttpServletRequest request, UploadDto config) throws FileUploadException {

        List<UploadResult> uploadedFile = new ArrayList<UploadResult>();
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(config.getSizeThreshold());

        // 需要判断临时文件夹是否存在,不存在则创建一个
        File file = new File(config.getTmpFolder());
        if (!file.exists()) {
            file.mkdir();
        } else {
            factory.setRepository(new File(config.getTmpFolder()));
        }

        ServletFileUpload fu = new ServletFileUpload(factory);
        fu.setSizeMax(config.getSizeMax()); // 设置最多上传字节数 2M 变量

        try {
            @SuppressWarnings("rawtypes")
            List fileItems = fu.parseRequest(request);
            @SuppressWarnings("rawtypes")
            Iterator iter = fileItems.iterator();

            while (iter.hasNext()) {
                UploadResult result = new UploadResult();
                FileItem item = (FileItem) iter.next();
                if (!item.isFormField()) {
                    String name = item.getName();
                    long size = item.getSize();
                    if ((name == null || name.equals("")) && size == 0) {
                        result.setError("001"); // 没有文件上传
                        uploadedFile.add(result);
                        continue;
                    }

                    if (config.getAllowFileType() == null && config.getDeniedFileType() == null) {
                        result.setError("002");
                        uploadedFile.add(result);
                        continue;
                    }

                    boolean nextFile = false;
                    // 黑名单
                    if (config.getDeniedFileType() != null) {
                        for (String s : config.getDeniedFileType()) {
                            if (name.toLowerCase().endsWith(s)) {
                                nextFile = true;
                                break;
                            }
                        }
                    }
                    if (nextFile) {
                        result.setError("003"); // 文件被黑名单过滤
                        uploadedFile.add(result);
                        continue;
                    }

                    String prefix = null;
                    // 白名单
                    if (config.getAllowFileType() != null) {
                        for (String s : config.getAllowFileType()) {
                            if (name.toLowerCase().endsWith(s)) {
                                // 在白名单中存在,允许上传
                                prefix = s;
                                break;
                            }
                        }
                    }

                    if (prefix == null) {
                        result.setError("004"); // 文件不属于白名单范围
                        uploadedFile.add(result);
                        continue;
                    }

                    // 执行上传操作
                    String filename = String.valueOf(System.currentTimeMillis()) + String.valueOf((int) (Math.random() * 10000));

                    if (!config.getUploadFolder().endsWith("/")) {
                        config.setUploadFolder(config.getUploadFolder() + "/");
                    }
                    if (!config.getRootFolder().endsWith("/")) {
                        config.setRootFolder(config.getRootFolder() + "/");
                    }

                    // root folder: /usr/data/resources
                    // upload folder: /model/...
                    FileUtils.makedir(config.getRootFolder() + config.getUploadFolder());
                    FileUtils.SaveFileFromInputStream(item.getInputStream(), config.getRootFolder() + config.getUploadFolder(), filename
                            + prefix);

                    result.setSuccess(true);
                    result.setUploadedFilename(filename + prefix);
                    result.setSubmitedFIlename(name);
                    result.setPath(config.getUploadFolder());
                    result.setPrefix(prefix);
                    uploadedFile.add(result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileUploadException(e.getMessage(), e);
        }

        return uploadedFile;
    }

    public String getDateFolder() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR) + "/" + (now.get(Calendar.MONTH) + 1) + "/" + now.get(Calendar.DAY_OF_MONTH);
    }

}
