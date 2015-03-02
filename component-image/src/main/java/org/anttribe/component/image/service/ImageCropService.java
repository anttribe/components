/*
 * 文  件   名: ImageCropService.java
 * 版         本: component-image(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年2月17日
 */
package org.anttribe.component.image.service;

import java.awt.Image;

import org.anttribe.component.image.entity.ImageEntity;

/**
 * 图片剪裁处理
 * 
 * @author zhaoyong
 * @version 2015年2月17日
 */
public interface ImageCropService
{
    /**
     * 根据给定的给定的x,y轴坐标对图片进行剪裁
     * 
     * @param srcImage 原图片的Image对象
     * @param x 剪裁的起始点x轴坐标
     * @param y 剪裁的起始点y轴坐标
     * @param x2 剪裁的终点x轴坐标
     * @param y2 剪裁的终点y轴坐标
     * @return ImageEntity
     */
    ImageEntity crop(Image srcImage, double x, double y, double x2, double y2);
}