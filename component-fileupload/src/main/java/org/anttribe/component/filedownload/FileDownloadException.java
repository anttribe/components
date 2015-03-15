/*
 * 文  件   名: FileDownloadException.java
 * 版         本 : component-fileupload(Anttribe). All rights reserved.
 * 描         述 : <描述>
 * 修   改  人: zhaoyong
 * 修改时间: 2015年3月15日
 */
package org.anttribe.component.filedownload;

/**
 * 文件下载异常
 * 
 * @author zhaoyong
 * @version 2015年3月15日
 */
public class FileDownloadException extends RuntimeException
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4392935955481088890L;
    
    /**
     * <默认构造函数>
     */
    public FileDownloadException()
    {
        super();
    }
    
    /**
     * <构造函数>
     */
    public FileDownloadException(String message)
    {
        super(message);
    }
    
    /**
     * <构造函数>
     */
    public FileDownloadException(Throwable e)
    {
        super(e);
    }
    
    /**
     * <构造函数>
     */
    public FileDownloadException(String message, Throwable e)
    {
        super(message, e);
    }
}