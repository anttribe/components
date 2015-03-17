/*
 * 文  件   名: Result.java
 * 版         本: component-fileupload(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2014年12月29日
 */
package org.anttribe.component.common.entity;

/**
 * 结果数据封装
 * 
 * @author zhaoyong
 * @version 2014年12月29日
 */
public class Result<T>
{
    /**
     * 结果码
     */
    private String resultCode;
    
    /**
     * 错误描述
     */
    private String message;
    
    /**
     * 返回结果
     */
    private T data;
    
    /**
     * <默认构造函数>
     */
    public Result()
    {
    }
    
    public String getResultCode()
    {
        return resultCode;
    }
    
    public void setResultCode(String resultCode)
    {
        this.resultCode = resultCode;
    }
    
    public T getData()
    {
        return data;
    }
    
    public void setData(T data)
    {
        this.data = data;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
}