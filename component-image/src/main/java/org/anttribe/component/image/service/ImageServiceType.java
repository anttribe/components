/*
 * 文  件   名: ImageServiceType.java
 * 版         本: component-image(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年2月21日
 */
package org.anttribe.component.image.service;

import javax.servlet.http.HttpServletRequest;

import org.anttribe.component.image.entity.ImageEntity;

/**
 * 图片处理服务类型
 * 
 * @author zhaoyong
 * @version 2015年2月21日
 */
public enum ImageServiceType
{
    /**
     * 图片裁剪
     */
    CROP
    {
        @Override
        public String type()
        {
            return "CROP";
        }
        
        @Override
        public ImageEntity doService(HttpServletRequest request)
        {
            return null;
        }
    };
    
    /**
     * 处理类型
     * 
     * @return String
     */
    public abstract String type();
    
    /**
     * 对请求进行处理
     * 
     * @param request
     * @return
     */
    public abstract ImageEntity doService(HttpServletRequest request);
    
    /**
     * 根据type字段值获取枚举
     * 
     * @param type String
     * @return ImageServiceType
     */
    public static ImageServiceType valueOfType(String type)
    {
        ImageServiceType[] serviceTypes = ImageServiceType.values();
        for (ImageServiceType serviceType : serviceTypes)
        {
            if (serviceType.type().equals(type))
            {
                return serviceType;
            }
        }
        return null;
    }
}