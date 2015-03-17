package com.anttribe.component.io;

import java.io.IOException;

import org.anttribe.component.io.ZipFileUtils;
import org.junit.Test;

public class TestZipFileUtils
{
    
    @Test
    public void testZipCompressStringStringString()
    {
        try
        {
            ZipFileUtils.zipCompress("F:\\交行写作系统客户需求.txt", "F:\\", "test.zip");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testZipCompressListStringString()
    {
        try
        {
            ZipFileUtils.zipCompress(new String[] {"F:\\test\\test\\新建文本文档.txt", "F:\\交行写作系统客户需求.txt"}, "F:\\", "test.zip");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}