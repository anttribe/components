/*
 * 文  件   名: ZipFileUtils.java
 * 版         本: component-io(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年3月17日
 */
package org.anttribe.component.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
/* 使用ant.jar解决压缩文件名的乱码问题 */
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

/**
 * 文件zip压缩和解压缩辅助类
 * 
 * @author zhaoyong
 * @version 2015年3月17日
 */
public final class ZipFileUtils
{
    /**
     * 压缩多个文件
     * 
     * @param srcFiles 源文件列表
     * @param destZipFilepath 目标zip文件路径
     * @param destZipFilename 目标zip文件名
     * @throws IOException
     */
    public static void zipCompress(String[] srcFiles, String destZipFilepath, String destZipFilename)
        throws IOException
    {
        if (ArrayUtils.isEmpty(srcFiles))
        {
            return;
        }
        
        // 获取zip文件路径，判断是否存在
        File destZipFileDirectory = new File(destZipFilepath);
        if (!destZipFileDirectory.exists() || destZipFileDirectory.isDirectory())
        {
            destZipFileDirectory.mkdirs();
        }
        
        // 如果目标文件存在，删除后重新生成
        File destZipFile = new File(destZipFilepath + File.separator + destZipFilename);
        if (destZipFile.exists())
        {
            destZipFile.delete();
        }
        
        ZipOutputStream zos =
            new ZipOutputStream(new CheckedOutputStream(new FileOutputStream(destZipFile), new CRC32()));
        // 遍历所有文件或文件夹，压缩文件
        for (String srcFilepath : srcFiles)
        {
            if (StringUtils.isEmpty(srcFilepath))
            {
                continue;
            }
            
            File srcFile = new File(srcFilepath);
            if (srcFile.exists())
            {
                // 判断保存的zip的文件路径是否是源文件的子文件夹，避免无限递归压缩
                if (srcFile.isDirectory() && destZipFilepath.indexOf(srcFilepath) != -1)
                {
                    continue;
                }
                
                zipCompress(srcFile.getParentFile().getAbsolutePath(), srcFile, zos);
            }
        }
        zos.close();
    }
    
    /**
     * 压缩多个文件
     * 
     * @param srcFiles 源文件列表
     * @param destZipFilepath 目标zip文件路径
     * @param destZipFilename 目标zip文件名
     * @throws IOException
     */
    public static void zipCompress(File[] srcFiles, String destZipFilepath, String destZipFilename)
        throws IOException
    {
        if (ArrayUtils.isEmpty(srcFiles))
        {
            return;
        }
        
        // 获取zip文件路径，判断是否存在
        File destZipFileDirectory = new File(destZipFilepath);
        if (!destZipFileDirectory.exists() || destZipFileDirectory.isDirectory())
        {
            destZipFileDirectory.mkdirs();
        }
        
        // 如果目标文件存在，删除后重新生成
        File destZipFile = new File(destZipFilepath + File.separator + destZipFilename);
        if (destZipFile.exists())
        {
            destZipFile.delete();
        }
        
        ZipOutputStream zos =
            new ZipOutputStream(new CheckedOutputStream(new FileOutputStream(destZipFile), new CRC32()));
        // 遍历所有文件或文件夹，压缩文件
        for (File srcFile : srcFiles)
        {
            if (srcFile.exists())
            {
                // 判断保存的zip的文件路径是否是源文件的子文件夹，避免无限递归压缩
                if (srcFile.isDirectory() && destZipFilepath.indexOf(srcFile.getAbsolutePath()) != -1)
                {
                    continue;
                }
                
                zipCompress(srcFile.getParentFile().getAbsolutePath(), srcFile, zos);
            }
        }
        zos.close();
    }
    
    /**
     * 压缩文件
     * 
     * @param srcFilepath 源文件或文件目录
     * @param destZipFilepath 目标zip文件路径
     * @param destZipFilename 目标文件名
     * @throws IOException
     */
    public static void zipCompress(String srcFilepath, String destZipFilepath, String destZipFilename)
        throws IOException
    {
        if (!StringUtils.isEmpty(srcFilepath) && !StringUtils.isEmpty(destZipFilepath)
            && !StringUtils.isEmpty(destZipFilename))
        {
            File srcFile = new File(srcFilepath);
            if (!srcFile.exists())
            {
                throw new FileNotFoundException("File [" + srcFilepath + "] not found.");
            }
            // 判断保存的zip的文件路径是否是源文件的子文件夹，避免无限递归压缩
            if (srcFile.isDirectory() && destZipFilepath.indexOf(srcFilepath) != -1)
            {
                return;
            }
            
            // 获取zip文件路径，判断是否存在
            File destZipFileDirectory = new File(destZipFilepath);
            if (!destZipFileDirectory.exists() || destZipFileDirectory.isDirectory())
            {
                destZipFileDirectory.mkdirs();
            }
            
            // 如果目标文件存在，删除后重新生成
            File destZipFile = new File(destZipFilepath + File.separator + destZipFilename);
            if (destZipFile.exists())
            {
                // SecurityManager securityManager = new SecurityManager();
                // securityManager.checkDelete(destZipFile.getAbsolutePath());
                destZipFile.delete();
            }
            
            ZipOutputStream zos =
                new ZipOutputStream(new CheckedOutputStream(new FileOutputStream(destZipFile), new CRC32()));
            zipCompress(srcFile.getParentFile().getAbsolutePath(), srcFile, zos);
            zos.close();
        }
    }
    
    /**
     * zip压缩文件
     * 
     * @param srcRootDirectory 需要压缩的文件的根路径
     * @param srcFile 被压缩的文件
     * @param zos ZipOutputStream
     * @throws IOException
     */
    private static void zipCompress(String srcRootDirectory, File srcFile, ZipOutputStream zos)
        throws IOException
    {
        if (null == srcFile || !srcFile.exists())
        {
            return;
        }
        
        // 压缩单个文件
        if (srcFile.isFile())
        {
            // 获取文件相对于要压缩文件夹的文件路径
            String absolutePath = srcFile.getAbsolutePath();
            if (!StringUtils.isEmpty(srcRootDirectory) && absolutePath.indexOf(srcRootDirectory) != -1)
            {
                absolutePath = absolutePath.substring(srcRootDirectory.length());
            }
            if (absolutePath.indexOf(File.separator) == 0)
            {
                absolutePath = absolutePath.substring(1);
            }
            
            ZipEntry entry = new ZipEntry(absolutePath);
            zos.putNextEntry(entry);
            // 将被压缩文件的内容输出到压缩文件中
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFile));
            int len = 0;
            byte[] buff = new byte[1024];
            while ((len = bis.read(buff, 0, buff.length)) != -1)
            {
                zos.write(buff, 0, len);
            }
            bis.close();
            zos.closeEntry();
        }
        else
        {
            // 压缩整个目录
            File[] subFiles = srcFile.listFiles();
            if (!ArrayUtils.isEmpty(subFiles))
            {
                for (File subFile : subFiles)
                {
                    zipCompress(srcRootDirectory, subFile, zos);
                }
            }
        }
    }
}