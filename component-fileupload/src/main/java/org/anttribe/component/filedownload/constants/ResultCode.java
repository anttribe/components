/*
 * 文  件   名: ResultCode.java
 * 版         本: component-fileupload(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2014年12月29日
 */
package org.anttribe.component.filedownload.constants;

/**
 * 处理结果
 * 
 * @author zhaoyong
 * @version 2014年12月29日
 */
public enum ResultCode
{
    SUCCESS("FD00001", "成功"), INVALID_REQUEST_PARAM("FD00002", "请求参数错误"), FILE_NOT_FOUND("FD00003", "文件不存在错误"), COMPRESS_TOZIP_ERROR(
        "FD00004", "zip压缩文件失败"), FILE_IO_EXCEPTION("FD00005", "文件IO异常");
    
    /**
     * 结果码
     */
    private String code;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * @param code
     */
    private ResultCode(String code, String description)
    {
        this.code = code;
        this.description = description;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public String getDescription()
    {
        return description;
    }
}