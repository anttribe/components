/*
 * 文  件   名: FileUploader.java
 * 版         本: component-fileupload(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2014年12月29日
 */
package org.anttribe.component.fileupload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.anttribe.component.fileupload.config.UploaderConfig;
import org.anttribe.component.fileupload.constants.Keys;
import org.anttribe.component.fileupload.constants.ResultCode;
import org.anttribe.component.fileupload.entity.FileItemInfo;
import org.anttribe.component.fileupload.generator.DefaultFileNameGenerator;
import org.anttribe.component.fileupload.generator.FileNameGenerator;
import org.anttribe.component.fileupload.listener.UploadProgressListener;
import org.anttribe.component.io.FileUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadBase.IOFileUploadException;
import org.apache.commons.fileupload.FileUploadBase.InvalidContentTypeException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件上传组件
 * 
 * @author zhaoyong
 * @version 2014年12月29日
 */
public class FileUploader
{
    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploader.class);
    
    /**
     * 默认编码
     */
    private static final String DEFAULT_ENCODING = "UTF-8";
    
    /**
     * 不限制文件上传总大小
     */
    private static final long NO_LIMIT_MAX_SIZE = -1;
    
    /**
     * 不限制文件上传单个文件大小
     */
    private static final long NO_LIMIT_FILE_MAX_SIZE = -1;
    
    /**
     * 默认文件上传路径 用户当前工作路径
     */
    private static final String DEFAULT_UPLOADPATH = System.getProperty("user.dir") + "/upload";
    
    /**
     * 默认的文件名生成器
     */
    private static final FileNameGenerator DEFAULT_FILENAMEGENERATOR = new DefaultFileNameGenerator();
    
    /**
     * 上传文件的保存路径（不包含文件名）
     * 
     * 文件路径，可能是绝对路径或相对路径<br>
     * 1) 绝对路径：以根目录符开始（如：'/'、'D:\'），是服务器文件系统的路径<br>
     * 2) 相对路径：不以根目录符开始，是相对于 WEB 应用程序 Context 的路径，（如：upload 是指'${WEB-APP-DIR}/upload'）<br>
     * 3) 规则：上传文件前会检查该路径是否存在，如果不存在则会尝试生成该路径，如果生成失败则 上传失败并返回{@link ResultCode.Result#INVALID_UPLOAD_PATH}
     */
    private String uploadPath = DEFAULT_UPLOADPATH;
    
    /**
     * 接受的文件类型
     */
    private List<String> acceptTypes = new ArrayList<String>();
    
    /**
     * 文件总大小限制
     */
    private long maxSize = NO_LIMIT_MAX_SIZE;
    
    /**
     * 单个文件大小限制
     */
    private long fileMaxSize = NO_LIMIT_FILE_MAX_SIZE;
    
    /**
     * 上传失败异常
     */
    private Throwable cause;
    
    /**
     * 文件列表
     */
    private List<FileItemInfo> fileItemInfoList;
    
    /**
     * 文件名生成器
     */
    private FileNameGenerator fileNameGenerator = DEFAULT_FILENAMEGENERATOR;
    
    /**
     * <默认构造器>
     */
    public FileUploader()
    {
        this(null);
    }
    
    /**
     * <构造器>
     * 
     * @param uploadPath 上传文件的保存路径
     * @param acceptTypes 接受的文件类型
     * @param maxSize 文件总大小限制
     * @param fileMaxSize 单个文件大小限制
     */
    public FileUploader(UploaderConfig uploaderConfig)
    {
        if (null != uploaderConfig)
        {
            if (!StringUtils.isEmpty(uploaderConfig.getUploadPath()))
            {
                this.uploadPath = uploaderConfig.getUploadPath();
            }
            this.acceptTypes = uploaderConfig.getAcceptTypes();
            
            // 设置文件总大小时，如果设置成0，表示不限制
            if (0 != uploaderConfig.getMaxSize())
            {
                this.maxSize = uploaderConfig.getMaxSize();
            }
            
            if (0 != uploaderConfig.getFileMaxSize())
            {
                this.fileMaxSize = uploaderConfig.getFileMaxSize();
            }
        }
        
        LOGGER.debug("Create new FileUploader {}", this.toString());
    }
    
    /**
     * 处理上传文件
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return 处理结果
     */
    public ResultCode upload(HttpServletRequest request, HttpServletResponse response)
    {
        if (!ServletFileUpload.isMultipartContent(request))
        {
            cause = new FileUploadException("Request is not a multipart request.");
            return ResultCode.INVALID_CONTENT_TYPE;
        }
        
        ResultCode result = ResultCode.SUCCESS;
        String encoding =
            !StringUtils.isEmpty(request.getCharacterEncoding()) ? request.getCharacterEncoding() : DEFAULT_ENCODING;
        // 保存文件到服务器磁盘
        ServletFileUpload servletfileUpload = new ServletFileUpload(new DiskFileItemFactory());
        servletfileUpload.setHeaderEncoding(encoding);
        servletfileUpload.setSizeMax(this.maxSize);
        servletfileUpload.setFileSizeMax(this.fileMaxSize);
        // 添加进度监控
        ProgressListener progressListener = new UploadProgressListener();
        request.getSession().setAttribute(Keys.PARAM_PROGRESS, progressListener);
        servletfileUpload.setProgressListener(progressListener);
        try
        {
            List<FileItem> fileItemList = servletfileUpload.parseRequest(request);
            if (!CollectionUtils.isEmpty(fileItemList))
            {
                result = this.parseFileItems(request, fileItemList);
                if (ResultCode.SUCCESS == result)
                {
                    // 写入文件
                    result = this.writeFiles();
                }
            }
        }
        catch (org.apache.commons.fileupload.FileUploadException e)
        {
            cause = new FileUploadException(e);
            if (e instanceof FileSizeLimitExceededException)
            {
                result = ResultCode.FILE_SIZE_EXCEEDED;
            }
            else if (e instanceof SizeLimitExceededException)
            {
                result = ResultCode.SIZE_EXCEEDED;
            }
            else if (e instanceof InvalidContentTypeException)
            {
                result = ResultCode.INVALID_CONTENT_TYPE;
            }
            else if (e instanceof IOFileUploadException)
            {
                result = ResultCode.FILE_UPLOAD_IO_EXCEPTION;
            }
            else
            {
                result = ResultCode.OTHER_PARSE_REQUEST_EXCEPTION;
            }
        }
        
        return result;
    }
    
    /**
     * 将上传的文件写入本地磁盘
     * 
     * @return 处理结果
     */
    private ResultCode writeFiles()
    {
        if (!CollectionUtils.isEmpty(this.fileItemInfoList))
        {
            for (FileItemInfo fileItemInfo : this.fileItemInfoList)
            {
                try
                {
                    fileItemInfo.getFileItem().write(fileItemInfo.getFile());
                }
                catch (Exception e)
                {
                    cause = new FileUploadException(e);
                    return ResultCode.WRITE_FILE_FAIL;
                }
            }
        }
        return ResultCode.SUCCESS;
    }
    
    /**
     * 解析上传文件
     * 
     * @param request
     * @param fileItemList
     * @return
     */
    private ResultCode parseFileItems(HttpServletRequest request, List<FileItem> fileItemList)
    {
        // 生成文件的绝对路径
        String absolutePath = this.getAbsolutePath(request);
        if (StringUtils.isEmpty(absolutePath))
        {
            cause =
                new FileNotFoundException(String.format("Path '%s' not found or is not directory", this.uploadPath));
            return ResultCode.INVALID_UPLOAD_PATH;
        }
        
        ResultCode result = ResultCode.SUCCESS;
        this.fileItemInfoList = new ArrayList<FileItemInfo>();
        // 遍历文件项
        for (FileItem fileItem : fileItemList)
        {
            if (!fileItem.isFormField() && !StringUtils.isEmpty(fileItem.getName()))
            {
                result = this.parseFileItem(fileItem, absolutePath);
                if (ResultCode.SUCCESS == result)
                {
                    break;
                }
            }
        }
        
        return result;
    }
    
    /**
     * 解析文件项
     * 
     * @param fileItem FileItem
     * @param absolutePath 绝对路径
     * @return Result
     */
    private ResultCode parseFileItem(FileItem fileItem, String absolutePath)
    {
        String uploadFileName = fileItem.getName();
        String suffix = FileUtils.getFileSuffix(uploadFileName);
        if (!CollectionUtils.isEmpty(this.acceptTypes))
        {
            // 获取文件后缀, 验证文件是否被允许的文件
            if (!this.acceptTypes.contains(suffix))
            {
                cause = new FileUploadException(String.format("File type %s is not accepted.", suffix));
                return ResultCode.INVALID_FILE_TYPE;
            }
        }
        
        // 生成文件名
        String saveFileName = uploadFileName;
        if (null != this.fileNameGenerator)
        {
            saveFileName = this.fileNameGenerator.generate(uploadFileName, "", "");
        }
        if (!saveFileName.endsWith(suffix))
        {
            saveFileName = saveFileName + suffix;
        }
        
        String absoluteSaveFileName = absolutePath + File.separator + saveFileName;
        FileItemInfo fileItemInfo =
            new FileItemInfo(uploadFileName, this.uploadPath + "/" + saveFileName, fileItem, new File(
                absoluteSaveFileName));
        fileItemInfo.setType(suffix);
        this.fileItemInfoList.add(fileItemInfo);
        
        return ResultCode.SUCCESS;
    }
    
    /**
     * 获取绝对路径
     * 
     * @param request HttpServletRequest
     * @return String
     * @throws IOException
     */
    private String getAbsolutePath(HttpServletRequest request)
    {
        String absolutePath = this.uploadPath;
        ServletContext context = request.getSession().getServletContext();
        if (null != context)
        {
            File absoluteDirectory = new File(this.uploadPath);
            // 非绝对路径
            if (!absoluteDirectory.isAbsolute())
            {
                // 环境上下文路径
                String contextPath = context.getRealPath("/");
                absolutePath =
                    new StringBuffer().append(contextPath).append(File.separator).append(this.uploadPath).toString();
                absoluteDirectory = new File(absolutePath);
            }
            
            // 判断文件是否存在，如果不存在或者不是一个目录，创建文件目录
            if (!absoluteDirectory.exists() || absoluteDirectory.isDirectory())
            {
                try
                {
                    absoluteDirectory.mkdirs();
                }
                catch (Exception e)
                {
                    LOGGER.warn("Mkdir {} error, cause: {}", absolutePath, e);
                    absolutePath = "";
                }
            }
        }
        return absolutePath;
    }
    
    public Throwable getCause()
    {
        return cause;
    }
    
    public List<FileItemInfo> getFileItemInfoList()
    {
        return fileItemInfoList;
    }
    
    public void setFileNameGenerator(FileNameGenerator fileNameGenerator)
    {
        this.fileNameGenerator = fileNameGenerator;
    }
    
    @Override
    public String toString()
    {
        return new StringBuffer().append('{')
            .append("uploadPath")
            .append(':')
            .append(this.uploadPath)
            .append(',')
            .append("acceptTypes")
            .append(':')
            .append(this.acceptTypes)
            .append(',')
            .append("maxSize")
            .append(':')
            .append(this.maxSize)
            .append(',')
            .append("fileMaxSize")
            .append(':')
            .append(this.fileMaxSize)
            .append('}')
            .toString();
    }
}