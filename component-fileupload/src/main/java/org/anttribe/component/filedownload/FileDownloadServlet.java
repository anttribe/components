/*
 * 文  件   名: FileDownloadServlet.java
 * 版         本 : component-fileupload(Anttribe). All rights reserved.
 * 描         述 : <描述>
 * 修   改  人: zhaoyong
 * 修改时间: 2015年3月15日
 */
package org.anttribe.component.filedownload;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.anttribe.component.common.entity.Result;
import org.anttribe.component.dataparser.DataParser;
import org.anttribe.component.dataparser.Parser;
import org.anttribe.component.filedownload.constants.ResultCode;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件下载Servlet
 * 
 * @author zhaoyong
 * @version 2015年3月15日
 */
public class FileDownloadServlet extends HttpServlet
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2434052476614172020L;
    
    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FileDownloadServlet.class);
    
    /**
     * jsonDataParser
     */
    private DataParser jsonDataParser = DataParser.getDataParser(Parser.Json);
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        this.doPost(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        ResultCode result = ResultCode.SUCCESS;
        Throwable cause = null;
        
        // 请求参数中获取文件名
        String[] filenames = request.getParameterValues("filename");
        if (ArrayUtils.isEmpty(filenames))
        {
            result = ResultCode.INVALID_REQUEST_PARAM;
        }
        else
        {
            FileDownloader fileDownloader = new FileDownloader();
            result = fileDownloader.download(request, response, filenames);
            cause = fileDownloader.getCause();
        }
        LOGGER.debug("File download result: {}. if error, cause: {}", result, cause);
        
        if (result != ResultCode.SUCCESS)
        {
            // 响应结果
            Result<Throwable> resResult = new Result<Throwable>();
            resResult.setResultCode(result.getCode());
            resResult.setMessage(result.getDescription());
            resResult.setData(cause);
            
            // 写出响应
            PrintWriter out = response.getWriter();
            String resultString = jsonDataParser.parseToString(resResult, Result.class);
            if (!StringUtils.isEmpty(resultString))
            {
                out.write(resultString);
            }
        }
    }
}