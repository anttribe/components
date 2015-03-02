/*
 * 文  件   名: ImageEntity.java
 * 版         本: component-image(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年2月9日
 */
package org.anttribe.component.image.entity;

import java.awt.image.BufferedImage;

/**
 * 抽象图片实体对象
 * 
 * @author zhaoyong
 * @version 2015年2月9日
 */
public class ImageEntity
{
    /**
     * 可访问图像数据缓冲区的Image对象
     */
    private BufferedImage bufferedImage;
    
    /**
     * 图片宽度
     */
    private int width;
    
    /**
     * 图片高度
     */
    private int height;
    
    /**
     * <默认构造器>
     */
    public ImageEntity()
    {
    }
    
    public BufferedImage getBufferedImage()
    {
        return bufferedImage;
    }
    
    public void setBufferedImage(BufferedImage bufferedImage)
    {
        this.bufferedImage = bufferedImage;
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public void setWidth(int width)
    {
        this.width = width;
    }
    
    public int getHeight()
    {
        return height;
    }
    
    public void setHeight(int height)
    {
        this.height = height;
    }
}