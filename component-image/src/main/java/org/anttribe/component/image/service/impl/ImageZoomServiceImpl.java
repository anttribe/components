/*
 * 文  件   名: ImageZoomServiceImpl.java
 * 版         本: component-image(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年2月9日
 */
package org.anttribe.component.image.service.impl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import org.anttribe.component.image.entity.ImageEntity;
import org.anttribe.component.image.service.ImageZoomService;

/**
 * 图片缩放处理
 * 
 * @author zhaoyong
 * @version 2015年2月9日
 */
public class ImageZoomServiceImpl implements ImageZoomService
{
    @Override
    public ImageEntity zoom(Image srcImage, int width, int height, boolean proportion)
    {
        // 如果是等比例缩放,计算等比例缩放的图片高度和宽度
        if (proportion)
        {
            // 这里使用double类型是为了不损失精度
            double srcWidth = srcImage.getWidth(null);
            double srcHeight = srcImage.getHeight(null);
            
            if (srcWidth >= srcHeight)
            {
                height = (int)Math.round(srcHeight / (srcWidth / width));
            }
            else
            {
                width = (int)Math.round(srcWidth / (srcHeight / height));
            }
        }
        
        // 根据高度和宽度构造可访问图像数据缓冲区的Image对象
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        // 获得画笔
        Graphics2D g = bufferedImage.createGraphics();
        // 设置颜色，填充图片矩形
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        // 绘制图片
        g.drawImage(srcImage, 0, 0, width, height, null);
        // 释放图片上下文使用的系统资源
        g.dispose();
        
        // 构造domain对象返回
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setBufferedImage(bufferedImage);
        imageEntity.setWidth(bufferedImage.getWidth());
        imageEntity.setHeight(bufferedImage.getHeight());
        
        return imageEntity;
    }
}