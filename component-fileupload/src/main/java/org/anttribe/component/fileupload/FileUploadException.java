/*
 * 文  件   名: FileUploadException.java
 * 版         本: component-fileupload(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2014年12月29日
 */
package org.anttribe.component.fileupload;

/**
 * 文件上传自定义异常
 * 
 * @author zhaoyong
 * @version 2014年12月29日
 */
public class FileUploadException extends RuntimeException
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1256089618004030250L;
    
    /**
     * <默认构造函数>
     */
    public FileUploadException()
    {
        super();
    }
    
    /**
     * <构造函数>
     */
    public FileUploadException(String message)
    {
        super(message);
    }
    
    /**
     * <构造函数>
     */
    public FileUploadException(Throwable e)
    {
        super(e);
    }
    
    /**
     * <构造函数>
     */
    public FileUploadException(String message, Throwable e)
    {
        super(message, e);
    }
}