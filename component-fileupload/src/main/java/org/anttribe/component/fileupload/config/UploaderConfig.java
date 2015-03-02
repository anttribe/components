/*
 * 文  件   名: UploaderConfig.java
 * 版         本: component-fileupload(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2014年12月29日
 */
package org.anttribe.component.fileupload.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 上传文件配置
 * 
 * @author zhaoyong
 * @version 2014年12月29日
 */
public class UploaderConfig
{
    /**
     * 键, 唯一确定一条配置信息
     */
    private String configKey;
    
    /**
     * 上传文件的保存路径（不包含文件名）
     * 
     * 文件路径，可能是绝对路径或相对路径<br>
     * 1) 绝对路径：以根目录符开始（如：'/'、'D:\'），是服务器文件系统的路径<br>
     * 2) 相对路径：不以根目录符开始，是相对于 WEB 应用程序 Context 的路径，（如：upload 是指'${WEB-APP-DIR}/upload'）<br>
     * 3) 规则：上传文件前会检查该路径是否存在，如果不存在则会尝试生成该路径，如果生成失败则 上传失败并返回{@link Constants.ResultCode#INVALID_UPLOAD_PATH}
     */
    private String uploadPath;
    
    /**
     * 接受的文件类型
     */
    private List<String> acceptTypes = new ArrayList<String>();
    
    /**
     * 文件总大小限制
     */
    private long maxSize;
    
    /**
     * 单个文件大小限制
     */
    private long fileMaxSize;
    
    public String getConfigKey()
    {
        return configKey;
    }
    
    public void setConfigKey(String configKey)
    {
        this.configKey = configKey;
    }
    
    public String getUploadPath()
    {
        return uploadPath;
    }
    
    public void setUploadPath(String uploadPath)
    {
        this.uploadPath = uploadPath;
    }
    
    public List<String> getAcceptTypes()
    {
        return acceptTypes;
    }
    
    public void setAcceptTypes(List<String> acceptTypes)
    {
        this.acceptTypes = acceptTypes;
    }
    
    public long getMaxSize()
    {
        return maxSize;
    }
    
    public void setMaxSize(long maxSize)
    {
        this.maxSize = maxSize;
    }
    
    public long getFileMaxSize()
    {
        return fileMaxSize;
    }
    
    public void setFileMaxSize(long fileMaxSize)
    {
        this.fileMaxSize = fileMaxSize;
    }
}