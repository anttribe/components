/*
 * 文  件   名: ImageHelperServlet.java
 * 版         本: component-image(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年2月21日
 */
package org.anttribe.component.image;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.anttribe.component.dataparser.DataParser;
import org.anttribe.component.dataparser.Parser;
import org.anttribe.component.image.constants.Keys;
import org.anttribe.component.image.constants.ResultCode;
import org.anttribe.component.image.entity.ImageEntity;
import org.anttribe.component.image.entity.Result;
import org.anttribe.component.image.service.ImageServiceType;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhaoyong
 * @version 2015年2月21日
 */
public class ImageHelperServlet extends HttpServlet
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2982389488530614607L;
    
    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageHelperServlet.class);
    
    /**
     * jsonDataParser
     */
    private DataParser jsonDataParser = DataParser.getDataParser(Parser.Json);
    
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
        ResultCode resultCode = ResultCode.SUCCESS;
        // 从请求中获取需要裁剪处理的图片
        String srcImage = request.getParameter(Keys.PARAM_SRCIMAGE);
        String actionType = request.getParameter(Keys.PARAM_ACTIONTYPE);
        if (StringUtils.isEmpty(actionType) || StringUtils.isEmpty(srcImage))
        {
            LOGGER.warn("Can get actionType or srcImage from request, actionType: {}, srcImage: {}",
                actionType,
                srcImage);
            resultCode = ResultCode.INVALID_REQUEST_PARAM;
        }
        else
        {
            ImageServiceType service = ImageServiceType.valueOf(actionType);
            if (null != service)
            {
                ImageEntity imageEntity = service.doService(request);
                if (null != imageEntity && null != imageEntity.getBufferedImage())
                {
                    // ImageIO.write(imageEntity.getBufferedImage(), format, out);
                }
            }
        }
        
        Result<String> resResult = new Result<String>();
        resResult.setMessage(resultCode.getDescription());
        PrintWriter out = response.getWriter();
        String resultString = jsonDataParser.parseToString(resResult, Result.class);
        if (!StringUtils.isEmpty(resultString))
        {
            out.write(resultString);
        }
    }
}