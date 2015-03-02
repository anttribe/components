/*
 * 文  件   名: Parser.java
 * 版         本: component-dataparser(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2014年12月29日
 */
package org.anttribe.component.dataparser;

import org.anttribe.component.dataparser.json.JsonDataParser;
import org.anttribe.component.dataparser.properties.PropertiesDataParser;
import org.anttribe.component.dataparser.xml.XmlDataParser;

/**
 * 支持的解析类型枚举
 * 
 * @author zhaoyong
 * @version 2014年12月29日
 */
public enum Parser
{
    Json(JsonDataParser.class), XML(XmlDataParser.class), Properties(PropertiesDataParser.class);
    
    /**
     * 解析类对象
     */
    private Class<?> parseClazz;
    
    /**
     * <默认构造函数>
     */
    private Parser(Class<?> parseClazz)
    {
        this.parseClazz = parseClazz;
    }
    
    public Class<?> getParseClazz()
    {
        return parseClazz;
    }
    
}