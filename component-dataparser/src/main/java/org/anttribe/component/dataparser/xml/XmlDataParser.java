/*
 * 文  件   名: XmlDataParser.java
 * 版         本: component-dataparser(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2014年12月29日
 */
package org.anttribe.component.dataparser.xml;

import java.io.InputStream;
import java.net.URL;

import org.anttribe.component.dataparser.DataParser;
import org.anttribe.component.dataparser.DataParserException;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * XML 数据解析类
 * 
 * @author zhaoyong
 * @version 2014年12月29日
 */
public class XmlDataParser extends DataParser
{
    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(XmlDataParser.class);
    
    /**
     * Digester Rule规则文件配置
     */
    private DigesterRulesConfiguration digesterRuleConfiguration;
    
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
        Digester digester = null;
        if (null == digesterRuleConfiguration)
        {
            logger.warn("Not setting digesterRuleConfiguration, will use default digesterRuleConfiguration.");
            digesterRuleConfiguration = new DigesterRulesConfiguration();
        }
        
        URL digesterRuleUrl = digesterRuleConfiguration.getDigesterRule(clazz);
        if (null == digesterRuleUrl)
        {
            digester = new Digester();
            logger.warn("Can not find digester rule for class {}", clazz.getName());
        }
        else
        {
            digester = DigesterLoader.createDigester(digesterRuleUrl);
        }
        
        try
        {
            return digester.parse(in);
        }
        catch (Exception e)
        {
            logger.error("Error to parse InputStream. cause: {}", e);
            throw new DataParserException("Error to parser InputStream.");
        }
    }
    
    /**
     * 设置配置
     * 
     * @param digesterRuleConfiguration
     */
    public void setDigesterRuleConfiguration(DigesterRulesConfiguration digesterRuleConfiguration)
    {
        this.digesterRuleConfiguration = digesterRuleConfiguration;
    }
}