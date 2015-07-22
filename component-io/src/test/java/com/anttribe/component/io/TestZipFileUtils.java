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
            ZipFileUtils.zipCompress("/upload", "D:\\", "test.zip");
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
            ZipFileUtils.zipCompress(new String[] {"F:\\Anttribe项目\\TeamWritten-团队写作工具\\详细设计"}, "F:\\", "test.zip");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}