/*
 * 文  件   名: DefaultFileNameGenerator.java
 * 版         本: (Anttribe)webim-base-infra. All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2014年12月29日
 */
package org.anttribe.component.fileupload.generator;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhaoyong
 * @version 2014年12月29日
 */
public class DefaultFileNameGenerator implements FileNameGenerator
{
    
    /**
     * 最大的序列值
     */
    private static final int MAX_SERIAL = 999999;
    
    /**
     * atomicInteger
     */
    private final AtomicInteger atomicInteger = new AtomicInteger();
    
    @Override
    public String generate(String srcFileName, String prefix, String suffix)
    {
        int serial = this.getNextSerial();
        long millseconds = System.currentTimeMillis();
        
        // 生成规则 millseconds_serial
        return String.format("%013d_%06d", millseconds, serial);
    }
    
    /**
     * 获取下一个序列值
     * 
     * @return int
     */
    private int getNextSerial()
    {
        int value = atomicInteger.incrementAndGet();
        if (value >= MAX_SERIAL)
        {
            atomicInteger.set(0);
        }
        return value;
    }
    
}