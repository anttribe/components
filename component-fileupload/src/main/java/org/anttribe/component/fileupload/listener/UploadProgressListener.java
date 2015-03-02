/*
 * 文  件   名: UploadProgressListener.java
 * 版         本: component-fileupload(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2014年12月30日
 */
package org.anttribe.component.fileupload.listener;

import org.apache.commons.fileupload.ProgressListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件上传进度监控
 * 
 * @author zhaoyong
 * @version 2014年12月30日
 */
public class UploadProgressListener implements ProgressListener
{
    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadProgressListener.class);
    
    /**
     * 已读取文件字节
     */
    private long readBytes;
    
    /**
     * 总文件大小
     */
    private long contentSize;
    
    /**
     * 文件个数
     */
    private int items;
    
    /**
     * <默认构造器>
     */
    public UploadProgressListener()
    {
    }
    
    @Override
    public void update(long pBytesRead, long pContentLength, int pItems)
    {
        this.readBytes = pBytesRead;
        this.contentSize = pContentLength;
        this.items = pItems;
        
        LOGGER.trace("While file uploading, readBytes: {}, contentSize: {}, items: {}",
            pBytesRead,
            pContentLength,
            pItems);
    }
    
    public long getReadBytes()
    {
        return readBytes;
    }
    
    public long getContentSize()
    {
        return contentSize;
    }
    
    public int getItems()
    {
        return items;
    }
    
}