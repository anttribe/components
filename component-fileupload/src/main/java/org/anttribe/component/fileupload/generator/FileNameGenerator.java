/*
 * 文  件   名: FileNameGenerator.java
 * 版         本: component-fileupload(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2014年12月29日
 */
package org.anttribe.component.fileupload.generator;

/**
 * 文件名生成器
 * 
 * @author zhaoyong
 * @version 2014年12月29日
 */
public interface FileNameGenerator
{
    /**
     * 生成文件名
     * 
     * @param srcFileName 源文件名
     * @param prefix 前缀
     * @param suffix 后缀
     * @return String
     */
    String generate(String srcFileName, String prefix, String suffix);
}