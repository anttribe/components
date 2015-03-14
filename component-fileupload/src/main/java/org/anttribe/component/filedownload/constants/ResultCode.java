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
    SUCCESS("FU00001", "成功"), SIZE_EXCEEDED("FU00002", "文件总大小超过限制"), FILE_SIZE_EXCEEDED("FU00003", "单个文件大小超过限制"), INVALID_FILE_TYPE(
        "FU00004", "文件类型不正确"), INVALID_CONTENT_TYPE("FU00005", "请求表单类型不正确"), FILE_UPLOAD_IO_EXCEPTION("FU00006",
        "文件上传 IO 错误"), OTHER_PARSE_REQUEST_EXCEPTION("FU00007", "解析上传请求其他异常"), WRITE_FILE_FAIL("FU00008", "文件写入失败"), INVALID_UPLOAD_PATH(
        "FU00009", "文件的保存路径不正确");
    
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