/*
 * 文  件   名: FileDownloader.java
 * 版         本 : component-fileupload(Anttribe). All rights reserved.
 * 描         述 : <描述>
 * 修   改  人: zhaoyong
 * 修改时间: 2015年3月11日
 */
package org.anttribe.component.filedownload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.anttribe.component.filedownload.constants.ResultCode;
import org.anttribe.component.io.ZipFileUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件下载器
 * 
 * @author zhaoyong
 * @version 2015年3月11日
 */
public class FileDownloader
{
    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FileDownloader.class);
    
    /**
     * 缓存文件位置
     */
    private static final String TEMP_FILE_FIRECTORY = System.getProperty("user.dir") + "/temp";
    
    /**
     * 默认下载文件后缀
     */
    private static final String DEFAULT_DOWNLOAD_FILE_SUBFIX = ".zip";
    
    /**
     * 下载的文件列表
     */
    private List<File> downloadFiles = new ArrayList<File>();
    
    /**
     * 下载失败异常
     */
    private Throwable cause;
    
    /**
     * 下载文件
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param filenames String[] 下载多个文件
     * @return ResultCode
     */
    public ResultCode download(HttpServletRequest request, HttpServletResponse response, String[] filenames)
    {
        ResultCode resultCode = this.parseDownloadFiles(request, filenames);
        if (ResultCode.SUCCESS == resultCode && !CollectionUtils.isEmpty(downloadFiles))
        {
            // 进行文件下载：如果是多个文件，包装成zip文件进行下载; 单个文件，就直接下载
            if (downloadFiles.size() == 1)
            {
                this.doDownloadFile(request, response, downloadFiles.get(0));
            }
            else
            {
                try
                {
                    // 根据当前日期生成文件名
                    String downloadFilename = "";
                    // 包装成zip文件
                    ZipFileUtils.zipCompress(downloadFiles.toArray(new File[downloadFiles.size()]),
                        TEMP_FILE_FIRECTORY,
                        downloadFilename + DEFAULT_DOWNLOAD_FILE_SUBFIX);
                    this.doDownloadFile(request, response, new File(TEMP_FILE_FIRECTORY + File.separator
                        + downloadFilename + DEFAULT_DOWNLOAD_FILE_SUBFIX));
                }
                catch (IOException e)
                {
                    LOGGER.error("While compress download files to zip, get error: {}.", e);
                    resultCode = ResultCode.COMPRESS_TOZIP_ERROR;
                }
            }
        }
        return resultCode;
    }
    
    /**
     * 
     * @param request
     * @param downloadFile
     * @return
     */
    private ResultCode doDownloadFile(HttpServletRequest request, HttpServletResponse response, File downloadFile)
    {
        return ResultCode.SUCCESS;
    }
    
    /**
     * 解析出请求中的文件
     * 
     * @param request HttpServletRequest
     * @param filenames String[]
     * @return ResultCode
     */
    private ResultCode parseDownloadFiles(HttpServletRequest request, String[] filenames)
    {
        // 处理每一个下载的文件
        for (String downloadFilename : filenames)
        {
            if (!StringUtils.isEmpty(downloadFilename))
            {
                File downloadFile = this.getDownloadFile(request, downloadFilename);
                if (null == downloadFile)
                {
                    cause = new FileDownloadException(String.format("File %s is not found.", downloadFilename));
                    return ResultCode.FILE_NOT_FOUND;
                }
                downloadFiles.add(downloadFile);
            }
        }
        return ResultCode.SUCCESS;
    }
    
    /**
     * 根据文件名从本地获取文件
     * 
     * @param request HttpServletRequest
     * @param filename filename
     * @return File
     */
    private File getDownloadFile(HttpServletRequest request, String filename)
    {
        File absoluteDirectory = null;
        
        ServletContext context = request.getSession().getServletContext();
        if (null != context)
        {
            absoluteDirectory = new File(filename);
            // 非绝对路径
            if (!absoluteDirectory.isAbsolute())
            {
                // 环境上下文路径
                String contextPath = context.getRealPath("/");
                String absolutePath =
                    new StringBuffer().append(contextPath).append(File.separator).append(filename).toString();
                absoluteDirectory = new File(absolutePath);
            }
            
            // 判断文件是否存在，如果不存在或者不是一个目录，创建文件目录
            if (!absoluteDirectory.exists() || absoluteDirectory.isDirectory())
            {
                LOGGER.warn("File {} not found or is a file directory.", filename);
                absoluteDirectory = null;
            }
        }
        return absoluteDirectory;
    }
    
    public Throwable getCause()
    {
        return cause;
    }
}