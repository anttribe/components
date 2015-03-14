/*
 * 文  件   名: FileDownloader.java
 * 版         本 : component-fileupload(Anttribe). All rights reserved.
 * 描         述 : <描述>
 * 修   改  人: zhaoyong
 * 修改时间: 2015年3月11日
 */
package org.anttribe.component.filedownload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.anttribe.component.filedownload.constants.ResultCode;
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
     * 下载失败异常
     */
    private Throwable cause;
    
    /**
     * 下载文件
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ResultCode
     */
    public ResultCode download(HttpServletRequest request, HttpServletResponse response)
    {
        return null;
    }
}