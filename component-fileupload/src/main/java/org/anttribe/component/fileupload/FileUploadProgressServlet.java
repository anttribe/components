/*
 * 文  件   名: FileUploadProgressServlet.java
 * 版         本: component-fileupload(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2014年12月31日
 */
package org.anttribe.component.fileupload;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.anttribe.component.common.entity.Result;
import org.anttribe.component.dataparser.DataParser;
import org.anttribe.component.dataparser.Parser;
import org.anttribe.component.fileupload.constants.Keys;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhaoyong
 * @version 2014年12月31日
 */
public class FileUploadProgressServlet extends HttpServlet
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -954855119346256987L;
    
    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadProgressServlet.class);
    
    /**
     * jsonDataParser
     */
    private DataParser jsonDataParser = DataParser.getDataParser(Parser.Json);
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        super.doPost(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        ProgressListener progressListener = (ProgressListener)request.getSession().getAttribute(Keys.PARAM_PROGRESS);
        if (null == progressListener)
        {
            LOGGER.warn("Can not find progressListener from session.");
            return;
        }
        
        // 响应结果
        Result<ProgressListener> resResult = new Result<ProgressListener>();
        resResult.setData(progressListener);
        
        // 写出响应
        PrintWriter out = response.getWriter();
        String resultString = jsonDataParser.parseToString(resResult, Result.class);
        if (!StringUtils.isEmpty(resultString))
        {
            out.write(resultString);
        }
    }
}