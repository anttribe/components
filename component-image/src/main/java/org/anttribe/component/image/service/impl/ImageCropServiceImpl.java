/*
 * 文  件   名: ImageCropServiceImpl.java
 * 版         本: component-image(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年2月17日
 */
package org.anttribe.component.image.service.impl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import org.anttribe.component.image.entity.ImageEntity;
import org.anttribe.component.image.service.ImageCropService;

/**
 * 图片剪裁处理
 * 
 * @author zhaoyong
 * @version 2015年2月17日
 */
public class ImageCropServiceImpl implements ImageCropService
{
    @Override
    public ImageEntity crop(Image srcImage, double x, double y, double x2, double y2)
    {
        // 计算图片的宽度和高度
        int width = (int)(x2 - x);
        int height = (int)(y2 - y);
        
        // 根据高度和宽度构造可访问图像数据缓冲区的Image对象
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        // 获得画笔
        Graphics2D g = bufferedImage.createGraphics();
        // 设置颜色，填充图片矩形
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        // 绘制图片, 根据初始x、y轴坐标进行剪裁
        g.drawImage(srcImage, (int)x, (int)y, width, height, null);
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