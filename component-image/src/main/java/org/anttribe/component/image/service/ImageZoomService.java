/*
 * 文  件   名: ImageZoomService.java
 * 版         本: component-image(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年2月9日
 */
package org.anttribe.component.image.service;

import java.awt.Image;

import org.anttribe.component.image.entity.ImageEntity;

/**
 * 图片缩放处理
 * 
 * @author zhaoyong
 * @version 2015年2月9日
 */
public interface ImageZoomService
{
    /**
     * 图片缩放实际处理
     * 
     * @param srcImage 原图片的Image对象
     * @param width 缩放图片的宽度
     * @param height 缩放图片的高度
     * @param proportion 是否等比例缩放
     * @return 自定义的ImageBean对象
     */
    ImageEntity zoom(Image srcImage, int width, int height, boolean proportion);
}