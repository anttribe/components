/*
 * 文  件   名: DataParser.java
 * 版         本: component-dataparser(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2014年12月29日
 */
package org.anttribe.component.dataparser;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据解析处理服务类
 * 
 * @author zhaoyong
 * @version 2014年12月29日
 */
public abstract class DataParser
{
    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(DataParser.class);
    
    /**
     * 根据名称获取解析类
     * 
     * @param parserName 解析名
     * @return DataParser
     */
    public static DataParser getDataParser(Parser parser)
    {
        // 默认解析类
        Class<?> parseClazz = Parser.Json.getParseClazz();
        if (null != parser)
        {
            parseClazz = parser.getParseClazz();
        }
        
        logger.info("while getting DataParser, parseClazz is {}.", parseClazz);
        
        try
        {
            return (DataParser)parseClazz.newInstance();
        }
        catch (Exception e)
        {
            logger.info("while getting DataParser, got error: {}.", e);
            throw new DataParserException(e);
        }
    }
    
    /**
     * 将指定对象解析成字符串
     * 
     * @param obj 待解析的对象
     * @param clazz 待解析对象的Class对象
     * @return String
     */
    public abstract String parseToString(Object obj, Class<?> clazz)
        throws DataParserException;
    
    /**
     * 解析字符串数据成对象
     * 
     * @param data 数据
     * @param clazz Class<?>
     * @return Object
     */
    public abstract Object parseToObject(String data, Class<?> clazz)
        throws DataParserException;
    
    /**
     * 从输入流中解析出对象
     * 
     * @param in InputStream
     * @param clazz Class<?>
     * @return Object
     */
    public abstract Object parseToObject(InputStream in, Class<?> clazz)
        throws DataParserException;
}