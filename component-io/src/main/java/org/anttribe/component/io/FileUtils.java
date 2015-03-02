/*
 * 文  件   名: FileUtils.java
 * 版         本: component-io(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2014年12月29日
 */
package org.anttribe.component.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.apache.commons.lang.StringUtils;

/**
 * 文件操作辅助类
 * 
 * @author zhaoyong
 * @version 2014年12月29日
 */
public class FileUtils
{
    /**
     * 校验文件是否存在
     * 
     * @param fileName 文件名
     * @return 校验结果
     */
    public static boolean isFileExist(String fileName)
    {
        boolean validateResult = false;
        if (!StringUtils.isEmpty(fileName))
        {
            URL url = FileUtils.class.getClassLoader().getResource(fileName);
            if (null != url)
            {
                File file = new File(url.getFile());
                validateResult = file.exists() && file.isFile() && file.canRead();
            }
        }
        return validateResult;
    }
    
    /**
     * 获取文件后缀
     * 
     * @param fileName 文件名
     * @return 文件后缀
     */
    public static String getFileSuffix(String fileName)
    {
        String suffix = "";
        if (!StringUtils.isEmpty(fileName))
        {
            int suffixPos = fileName.lastIndexOf('.');
            if (suffixPos != -1)
            {
                suffix = fileName.substring(suffixPos).toLowerCase();
            }
        }
        return suffix;
    }
    
    /**
     * 关闭文件流
     * 
     * @param in 输入流
     * @param out 输出流
     */
    public static void closeStream(InputStream in, OutputStream out)
    {
        // 关闭输入流
        if (null != in)
        {
            try
            {
                in.close();
            }
            catch (IOException ex)
            {
            }
        }
        // 关闭输出流
        if (null != out)
        {
            try
            {
                out.close();
            }
            catch (IOException ex)
            {
            }
        }
    }
}