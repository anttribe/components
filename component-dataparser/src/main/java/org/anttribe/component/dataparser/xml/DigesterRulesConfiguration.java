/*
 * 文  件   名: DigesterRulesConfiguration.java
 * 版         本: component-dataparser(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2014年12月29日
 */
package org.anttribe.component.dataparser.xml;

import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Digester的规则文件配置
 * 
 * @author zhaoyong
 * @version 2014年12月29日
 */
public class DigesterRulesConfiguration
{
    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(DigesterRulesConfiguration.class);
    
    /**
     * 默认的配置文件
     */
    public static final String DEFAULT_CONFIG_FILE = "digester-rules/config.properties";
    
    /**
     * Digester规则文件后缀
     */
    private static final String DIGESTERRULE_FILESUFFIX = ".xml";
    
    /**
     * 配置文件
     */
    private String configFile = DEFAULT_CONFIG_FILE;
    
    /**
     * digesterRulesProp
     */
    private Properties digesterRulesProp;
    
    /**
     * digesterRules
     */
    private Map<String, URL> digesterRules = new HashMap<String, URL>();
    
    /**
     * <默认构造器> 不指定配置文件时, 加载默认配置文件
     */
    public DigesterRulesConfiguration()
    {
    }
    
    /**
     * <默认构造器>
     */
    public DigesterRulesConfiguration(String configFile)
    {
        this.configFile = configFile;
    }
    
    /**
     * 获取DigesterRule
     * 
     * @param clazz Class<?>
     * @return URL
     */
    public URL getDigesterRule(Class<?> clazz)
    {
        if (null != clazz)
        {
            return getDigesterRule(clazz.getName());
        }
        return null;
    }
    
    /**
     * 获取DigesterRule
     * 
     * @param className 类全限定名
     * @return URL
     */
    public URL getDigesterRule(String className)
    {
        logger.info("While getting digester rule file, params className: {}", className);
        
        URL digestRuleUrl = null;
        if (!StringUtils.isEmpty(className))
        {
            if (!MapUtils.isEmpty(digesterRules))
            {
                digestRuleUrl = digesterRules.get(className);
            }
            
            if (null == digestRuleUrl)
            {
                logger.info("Can't get digester rule from cache.");
                if (MapUtils.isEmpty(digesterRulesProp))
                {
                    logger.debug("Start initialing digester rules configuration.");
                    
                    digesterRulesProp = new Properties();
                    try
                    {
                        Enumeration<URL> resources = this.getClass().getClassLoader().getResources(this.configFile);
                        while (resources.hasMoreElements())
                        {
                            digesterRulesProp.load(resources.nextElement().openStream());
                        }
                        logger.debug("End initialing digester rules configuration.");
                    }
                    catch (Exception e)
                    {
                        logger.error("Initialing digester rules error, cause: {}.", e);
                    }
                }
                
                String digesterRuleFile = digesterRulesProp.getProperty(className);
                if (!StringUtils.isEmpty(digesterRuleFile))
                {
                    digestRuleUrl = this.getClass().getClassLoader().getResource(digesterRuleFile);
                }
            }
            
            if (null == digestRuleUrl)
            {
                try
                {
                    // 取当前类路径下的文件
                    digestRuleUrl =
                        Class.forName(className).getResource(className + DIGESTERRULE_FILESUFFIX);
                }
                catch (ClassNotFoundException e)
                {
                    logger.error("When getting digester rule from the current class path: {}, cause: {}.", className, e);
                }
            }
            
            if (null != digestRuleUrl)
            {
                digesterRules.put(className, digestRuleUrl);
            }
        }
        
        logger.info("While getting digester rule file, return: {}", digestRuleUrl);
        return digestRuleUrl;
    }
    
    public void setConfigFile(String configFile)
    {
        this.configFile = configFile;
    }
}