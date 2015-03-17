/*
 * 文  件   名: FileUploadServlet.java
 * 版         本: component-fileupload(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2014年12月29日
 */
package org.anttribe.component.fileupload;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.anttribe.component.common.entity.Result;
import org.anttribe.component.dataparser.DataParser;
import org.anttribe.component.dataparser.Parser;
import org.anttribe.component.fileupload.config.UploaderConfig;
import org.anttribe.component.fileupload.constants.Keys;
import org.anttribe.component.fileupload.constants.ResultCode;
import org.anttribe.component.fileupload.entity.FileItemInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件上传
 * 
 * @author zhaoyong
 * @version 2014年12月29日
 */
public class FileUploadServlet extends HttpServlet
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6316255669528919158L;
    
    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadServlet.class);
    
    /**
     * xmlDataParser解析类
     */
    private DataParser xmlDataParser = DataParser.getDataParser(Parser.XML);
    
    /**
     * jsonDataParser
     */
    private DataParser jsonDataParser = DataParser.getDataParser(Parser.Json);
    
    /**
     * 上传文件配置
     */
    private Map<String, UploaderConfig> uploaderConfigs;
    
    @SuppressWarnings("unchecked")
    @Override
    public void init()
        throws ServletException
    {
        super.init();
        
        // 从servlet中读取上传配置文件路径
        String uploaderConfigFile = this.getServletConfig().getInitParameter(Keys.PARAM_UPLOADERCONFIG);
        InputStream in = FileUploadServlet.class.getClassLoader().getResourceAsStream(uploaderConfigFile);
        if (null == in)
        {
            LOGGER.warn("Can not read conig file from {}", uploaderConfigFile);
            return;
        }
        // 加载配置
        List<UploaderConfig> uploaderConfigList =
            (List<UploaderConfig>)xmlDataParser.parseToObject(in, UploaderConfig.class);
        if (!CollectionUtils.isEmpty(uploaderConfigList))
        {
            uploaderConfigs = new HashMap<String, UploaderConfig>();
            for (UploaderConfig uploaderConfig : uploaderConfigList)
            {
                if (!StringUtils.isEmpty(uploaderConfig.getConfigKey()))
                {
                    uploaderConfigs.put(uploaderConfig.getConfigKey(), uploaderConfig);
                }
            }
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        this.doPost(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        UploaderConfig uploaderConfig = null;
        String uploaderConfigKey = request.getParameter(Keys.PARAM_UPLOADERCONFIGKEY);
        if (!StringUtils.isEmpty(uploaderConfigKey) && !MapUtils.isEmpty(uploaderConfigs))
        {
            uploaderConfig = uploaderConfigs.get(uploaderConfigKey);
        }
        
        // 创建FileUploader对象，上传文件
        FileUploader filerUploader = new FileUploader(uploaderConfig);
        ResultCode result = filerUploader.upload(request, response);
        
        LOGGER.debug("File upload result: {}, fileItenInfos: {}. if error, cause: {}",
            result,
            filerUploader.getFileItemInfoList(),
            filerUploader.getCause());
        
        // 响应结果
        Result<List<FileItemInfo>> resResult = new Result<List<FileItemInfo>>();
        resResult.setResultCode(result.getCode());
        resResult.setMessage(result.getDescription());
        resResult.setData(filerUploader.getFileItemInfoList());
        
        // 写出响应
        PrintWriter out = response.getWriter();
        String resultString = jsonDataParser.parseToString(resResult, Result.class);
        if (!StringUtils.isEmpty(resultString))
        {
            out.write(resultString);
        }
    }
}