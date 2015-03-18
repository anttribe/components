/*
 * 文  件   名: Range.java
 * 版         本: component-fileupload(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年3月18日
 */
package org.anttribe.component.filedownload.entity;

/**
 * 范围
 * 
 * @author zhaoyong
 * @version 2015年3月18日
 */
public class Range
{
    /**
     * 开始位置
     */
    private Integer begin;
    
    /**
     * 结束位置
     */
    private Integer end;
    
    /**
     * <默认构造器>
     */
    public Range()
    {
    }
    
    /**
     * <默认构造器>
     * 
     * @param begin
     * @param end
     */
    public Range(Integer begin, Integer end)
    {
        this.begin = begin;
        this.end = end;
    }
    
    public Integer getBegin()
    {
        return begin;
    }
    
    public void setBegin(Integer begin)
    {
        this.begin = begin;
    }
    
    public Integer getEnd()
    {
        return end;
    }
    
    public void setEnd(Integer end)
    {
        this.end = end;
    }
}