/*
 * 文  件   名: JsonDataParser.java
 * 版         本: component-dataparser(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2014年12月29日
 */
package org.anttribe.component.dataparser.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.anttribe.component.dataparser.DataParser;
import org.anttribe.component.dataparser.DataParserException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JSON类型数据解析类
 * 
 * @author zhaoyong
 * @version 2014年12月29日
 */
public class JsonDataParser extends DataParser
{
    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(JsonDataParser.class);
    
    @Override
    public String parseToString(Object obj, Class<?> clazz)
        throws DataParserException
    {
        String resultString = null;
        
        JsonConfig config = new JsonConfig();
        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        config.setIgnoreTransientFields(true);
        
        Package resultPackage = obj.getClass().getPackage();
        if (resultPackage.getName().equals("java.lang"))
        {
            resultString = obj.toString();
        }
        else if (obj.getClass().isArray())
        {
            JSONArray jsonArray = JSONArray.fromObject(obj, config);
            resultString = jsonArray.toString();
        }
        else if (obj instanceof List)
        {
            JSONArray jsonArray = JSONArray.fromObject(((List<?>)obj).toArray(), config);
            resultString = jsonArray.toString();
        }
        else if (obj instanceof String)
        {
            resultString = "\"" + obj + "\"";
        }
        else
        {
            JSONObject jsonObject = JSONObject.fromObject(obj, config);
            resultString = jsonObject.toString();
        }
        
        return resultString;
    }
    
    @Override
    public Object parseToObject(String data, Class<?> clazz)
        throws DataParserException
    {
        Object obj = null;
        if (!StringUtils.isEmpty(data))
        {
            try
            {
                if (data.startsWith("[") && data.endsWith("]"))
                {
                    JSONArray jsonArray = JSONArray.fromObject(data);
                    obj = JSONArray.toArray(jsonArray, clazz);
                }
                else if (data.startsWith("{") && data.endsWith("}"))
                {
                    JSONObject jsonObject = JSONObject.fromObject(data);
                    obj = JSONObject.toBean(jsonObject, clazz);
                }
            }
            catch (Exception e)
            {
                logger.error("Error to parse String to Json. cause: {}", e);
                throw new DataParserException(e);
            }
        }
        
        return obj;
    }
    
    @Override
    public Object parseToObject(InputStream in, Class<?> clazz)
        throws DataParserException
    {
        Object obj = null;
        if (null != in)
        {
            // 读取输入流内容
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuffer strB = new StringBuffer("");
            try
            {
                String str = "";
                while (!StringUtils.isEmpty(str = reader.readLine()))
                {
                    strB.append(str);
                }
                
                obj = this.parseToObject(strB.toString(), clazz);
            }
            catch (IOException e)
            {
                logger.error("Error to parse InputStream to Json. cause: {}", e);
                throw new DataParserException(e);
            }
        }
        return obj;
    }
}