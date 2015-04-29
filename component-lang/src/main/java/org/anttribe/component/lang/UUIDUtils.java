/*
 * 文  件   名: ZipFileUtils.java
 * 版         本: component-io(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年3月22日
 */
package org.anttribe.component.lang;

import java.util.UUID;

/**
 * UUID处理辅助类
 * 
 * @author zhaoyong
 * @version 2015年3月22日
 */
public final class UUIDUtils
{
    /**
     * 生成唯一主键(UUID)
     * 
     * @return String
     */
    public static String getRandomUUID()
    {
        String s = UUID.randomUUID().toString();
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
    }
}