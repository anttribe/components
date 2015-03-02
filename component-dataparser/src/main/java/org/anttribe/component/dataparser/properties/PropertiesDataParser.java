/*
 * 文  件   名: PropertiesDataParser.java
 * 版         本: component-dataparser(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年2月3日
 */
package org.anttribe.component.dataparser.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.anttribe.component.dataparser.DataParser;
import org.anttribe.component.dataparser.DataParserException;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Properties数据解析
 * 
 * @author zhaoyong
 * @version 2015年2月3日
 */
public class PropertiesDataParser extends DataParser
{
    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(PropertiesDataParser.class);
    
    @Override
    public String parseToString(Object obj, Class<?> clazz)
        throws DataParserException
    {
        return null;
    }
    
    @Override
    public Object parseToObject(String data, Class<?> clazz)
        throws DataParserException
    {
        return null;
    }
    
    @Override
    public Object parseToObject(InputStream in, Class<?> clazz)
        throws DataParserException
    {
        Object obj = null;
        if (null != in)
        {
            Properties prop = new Properties();
            try
            {
                prop.load(in);
            }
            catch (IOException e)
            {
                logger.error("Error to parse InputStream to Properties. cause: {}", e);
                throw new DataParserException(e);
            }
            
            if (null != clazz)
            {
                try
                {
                    obj = clazz.newInstance();
                    BeanUtils.populate(obj, prop);
                }
                catch (Exception e)
                {
                    logger.error("Error to Properties to Object. cause: {}", e);
                    throw new DataParserException(e);
                }
            }
        }
        return obj;
    }
    
}