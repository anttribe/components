/*
 * 文  件   名: FileDownloader.java
 * 版         本 : component-fileupload(Anttribe). All rights reserved.
 * 描         述 : <描述>
 * 修   改  人: zhaoyong
 * 修改时间: 2015年3月11日
 */
package org.anttribe.component.filedownload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.anttribe.component.filedownload.constants.ResultCode;
import org.anttribe.component.filedownload.entity.Range;
import org.anttribe.component.io.FileUtils;
import org.anttribe.component.io.ZipFileUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
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
     * 默认下载文件MIME Type
     */
    private static final String DEFAULT_CONTENT_TYPE = "application/force-download";
    
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
                    String downloadFilename = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
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
     * 下载文件
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param downloadFile File
     * @return ResultCode
     */
    private ResultCode doDownloadFile(HttpServletRequest request, HttpServletResponse response, File downloadFile)
    {
        int fileLength = (int)downloadFile.length();
        // 设置响应头
        response.setContentType(DEFAULT_CONTENT_TYPE);
        response.setContentLength(fileLength);
        response.setHeader("Content-Disposition", "attachment;filename=" + downloadFile.getName());
        
        int begin = 0;
        int end = fileLength - 1;
        // 从请求中获取上次下载文件的位置
        Range range = this.parseDownloadRange(request);
        if (null != range)
        {
            if (null != range.getBegin())
            {
                begin = range.getBegin().intValue();
            }
            
            response.setHeader("Accept-Ranges", "bytes");
            // 206
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            String contentRangeValue = String.format("bytes %d-%d/%d", begin, end, fileLength);
            response.setHeader("Content-Range", contentRangeValue);
        }
        
        try
        {
            this.doDownloadFile(response, downloadFile, begin, end);
            
            return ResultCode.SUCCESS;
        }
        catch (IOException e)
        {
            cause = e;
            if (e instanceof FileNotFoundException)
            {
                return ResultCode.FILE_NOT_FOUND;
            }
            return ResultCode.FILE_IO_EXCEPTION;
        }
    }
    
    /**
     * 实际的文件下载操作，将文件写出响应，即输入流
     * 
     * @param response HttpServletResponse
     * @param downloadFile File
     * @param begin int
     * @param end int
     * @throws IOException
     */
    private void doDownloadFile(HttpServletResponse response, File downloadFile, int begin, int end)
        throws IOException
    {
        InputStream in = null;
        OutputStream out = null;
        try
        {
            in = new BufferedInputStream(new FileInputStream(downloadFile));
            out = new BufferedOutputStream(response.getOutputStream());
            
            // 跳过begin个字节
            in.skip(begin);
            
            int len = 0;
            int left = end - begin + 1;
            byte[] buff = new byte[1024];
            // 将文件内容写入输出流
            while ((len = in.read(buff)) != -1 && left > 0)
            {
                out.write(buff, 0, len);
                left = left - len;
            }
            out.flush();
        }
        finally
        {
            FileUtils.closeStream(in, out);
        }
    }
    
    /**
     * 从请求中获取上次下载文件的位置
     * 
     * @param request HttpServletRequest
     * @return Range
     */
    public Range parseDownloadRange(HttpServletRequest request)
    {
        // 获取RANGE请求头
        String rangeHeaderValue = request.getHeader("RANGE");
        if (StringUtils.isEmpty(rangeHeaderValue))
        {
            return null;
        }
        Range range = new Range();
        if (rangeHeaderValue.indexOf("-") != -1)
        {
            rangeHeaderValue = rangeHeaderValue.substring(0, rangeHeaderValue.indexOf("-"));
        }
        range.setBegin(Integer.getInteger(rangeHeaderValue));
        return range;
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