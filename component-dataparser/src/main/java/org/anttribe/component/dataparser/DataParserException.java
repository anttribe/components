/*
 * 文  件   名: DataParserException.java
 * 版         本: component-dataparser(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2014年12月29日
 */
package org.anttribe.component.dataparser;

/**
 * 数据解析自定义异常
 * 
 * @author zhaoyong
 * @version 2014年12月29日
 */
public class DataParserException extends RuntimeException
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1289959067531300426L;
    
    /**
     * @param message
     */
    public DataParserException(String message)
    {
        super(message);
    }
    
    /**
     * @param cause
     */
    public DataParserException(Throwable cause)
    {
        super(cause);
    }
    
    /**
     * @param message
     * @param cause
     */
    public DataParserException(String message, Throwable cause)
    {
        super(message, cause);
    }
}