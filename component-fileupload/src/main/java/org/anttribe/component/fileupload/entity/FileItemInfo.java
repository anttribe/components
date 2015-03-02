/*
 * 文  件   名: FileItemInfo.java
 * 版         本: component-fileupload(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2014年12月29日
 */
package org.anttribe.component.fileupload.entity;

import java.io.File;

import org.apache.commons.fileupload.FileItem;

/**
 * 文件项信息
 * 
 * @author zhaoyong
 * @version 2014年12月29日
 */
public class FileItemInfo
{
    /**
     * 文件名称
     */
    private String fileName;
    
    /**
     * 文件路径
     */
    private String filePath;
    
    /**
     * 文件大小
     */
    private long size = 0L;
    
    /**
     * 文件类型
     */
    private String type;
    
    /**
     * 文件绝对路径
     */
    private String absolutePath;
    
    /**
     * 文件项
     */
    private transient FileItem fileItem;
    
    /**
     * 文件
     */
    private transient File file;
    
    /**
     * <构造器>
     * 
     * @param fileName 文件名
     * @param file 文件
     */
    public FileItemInfo(String fileName, String filePath, FileItem fileItem, File file)
    {
        this.fileName = fileName;
        this.filePath = filePath;
        
        this.fileItem = fileItem;
        this.file = file;
        
        this.size = fileItem.getSize();
        this.absolutePath = file.getAbsolutePath();
    }
    
    public String getFileName()
    {
        return fileName;
    }
    
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
    
    public String getFilePath()
    {
        return filePath;
    }
    
    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }
    
    public long getSize()
    {
        return size;
    }
    
    public void setSize(long size)
    {
        this.size = size;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getAbsolutePath()
    {
        return absolutePath;
    }
    
    public void setAbsolutePath(String absolutePath)
    {
        this.absolutePath = absolutePath;
    }
    
    public FileItem getFileItem()
    {
        return fileItem;
    }
    
    public void setFileItem(FileItem fileItem)
    {
        this.fileItem = fileItem;
    }
    
    public File getFile()
    {
        return file;
    }
    
    public void setFile(File file)
    {
        this.file = file;
    }
}