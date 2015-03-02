/*
 * 文  件   名: ImageHelper.java
 * 版         本: component-image(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年2月9日
 */
package org.anttribe.component.image;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.anttribe.component.image.entity.ImageEntity;
import org.anttribe.component.image.service.ImageCropService;
import org.anttribe.component.image.service.ImageZoomService;

/**
 * 图片处理的辅助类
 * 
 * @author zhaoyong
 * @version 2015年2月9日
 */
public class ImageHelper
{
    /**
     * 图片可缩放处理service
     */
    private ImageZoomService imageZoomService;
    
    /**
     * 图片剪裁处理service
     */
    private ImageCropService imageCropService;
    
    /**
     * <p>
     * 缩放图片到指定输出流位置
     * </p>
     * 根据给定的待缩放图片的文件路径, 然后指定缩放后的文件格式和输出流对象, 缩放图片到指定输出流位置
     * 
     * @param imageFilePath 待缩放图片的文件路径
     * @param width 缩放后图片宽度
     * @param height 缩放后图片高度
     * @param format 缩放后图片文件格式
     * @param proportion 是否是等比例缩放图片
     * @param out 输出流
     * @throws IOException IO异常信息
     */
    public void zoomImage(String imageFilePath, int width, int height, String format, boolean proportion,
        OutputStream out)
        throws IOException
    {
        ImageEntity imageEntity = null;
        // 根据图片文件名构造文件对象
        File file = new File(imageFilePath);
        if (!file.exists())
        {
            throw new FileNotFoundException("File is not found in this system.");
        }
        
        Image bufferedImage = ImageIO.read(file);
        // service层处理图片缩放操作
        imageEntity = imageZoomService.zoom(bufferedImage, width, height, proportion);
        if (null != imageEntity && null != imageEntity.getBufferedImage())
        {
            ImageIO.write(imageEntity.getBufferedImage(), format, out);
        }
    }
    
    /**
     * <p>
     * 缩放图片到指定输出流位置
     * </p>
     * 根据给定的待缩放图片的URL,然后指定缩放后的文件格式和输出流对象,缩放图片到指定输出流位置
     * 
     * @param imageURL 图片的URL
     * @param width 缩放后图片宽度
     * @param height 缩放后图片高度
     * @param format 缩放后图片文件格式
     * @param proportion 是否是等比例缩放图片
     * @param out 输出流
     * @throws IOException IO异常信息
     */
    public void zoomImage(URL imageURL, int width, int height, String format, boolean proportion, OutputStream out)
        throws IOException
    {
        ImageEntity imageEntity = null;
        Image bufferedImage = ImageIO.read(imageURL);
        // service层处理图片缩放操作
        imageEntity = imageZoomService.zoom(bufferedImage, width, height, proportion);
        if (null != imageEntity && null != imageEntity.getBufferedImage())
        {
            ImageIO.write(imageEntity.getBufferedImage(), format, out);
        }
    }
    
    /**
     * <p>
     * 缩放图片到指定输出流位置
     * </p>
     * 根据给定的待缩放图片的输入流,然后指定缩放后的文件格式和输出流对象,缩放图片到指定输出流位置
     * 
     * @param input 图片的输入流
     * @param width 缩放后图片宽度
     * @param height 缩放后图片高度
     * @param format 缩放后图片文件格式
     * @param proportion 是否是等比例缩放图片
     * @param out 输出流
     * @throws IOException IO异常信息
     */
    public void zoomImage(InputStream input, int width, int height, String format, boolean proportion, OutputStream out)
        throws IOException
    {
        ImageEntity imageEntity = null;
        Image bufferedImage = ImageIO.read(input);
        // service层处理图片缩放操作
        imageEntity = imageZoomService.zoom(bufferedImage, width, height, proportion);
        if (null != imageEntity && null != imageEntity.getBufferedImage())
        {
            ImageIO.write(imageEntity.getBufferedImage(), format, out);
        }
    }
    
    /**
     * <p>
     * 剪裁图片到指定输出流位置
     * </p>
     * 根据给定的给定的x,y轴坐标对图片进行剪裁，然后保存到指定输出流中
     * 
     * @param input 图片的输入流
     * @param x 剪裁的起始点x轴坐标
     * @param y 剪裁的起始点y轴坐标
     * @param x2 剪裁的终点x轴坐标
     * @param y2 剪裁的终点y轴坐标
     * @param format 缩放后图片文件格式
     * @param out 输出流
     * @throws IOException IO异常信息
     */
    public void cropImage(InputStream input, double x, double y, double x2, double y2, String format, OutputStream out)
        throws IOException
    {
        ImageEntity imageEntity = null;
        Image bufferedImage = ImageIO.read(input);
        // service层处理图片剪裁操作
        imageEntity = imageCropService.crop(bufferedImage, x, y, x2, y2);
        if (null != imageEntity && null != imageEntity.getBufferedImage())
        {
            ImageIO.write(imageEntity.getBufferedImage(), format, out);
        }
    }
    
    public void setImageZoomService(ImageZoomService imageZoomService)
    {
        this.imageZoomService = imageZoomService;
    }
    
    public void setImageCropService(ImageCropService imageCropService)
    {
        this.imageCropService = imageCropService;
    }
}