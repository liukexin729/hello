package com.example.hello.util;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

public class FileUtil {

    /**
     * 返回源文件后缀,带点.
     * @fileName 源文件名称
     * @return
     * @throws Exception
     */
    public static String fileSuffix(String fileName) throws Exception{
        if(StringUtils.isEmpty(fileName)){throw new Exception("文件名不能为空");}
        return fileName.substring(fileName.lastIndexOf("."), fileName.length());
    }
    public static void existsDelete(String fileName,String filePath) {
        File pathFile = new File(filePath);
        if(!pathFile.exists() || pathFile.isFile()) {
            return;
        }
        for(File file:pathFile.listFiles()) {
            String a = file.getName();

            if(file.isFile() && fileName.equals(file.getName())) {
                file.delete();
                break;
            }
        }
    }
}
