/*
 * 文  件   名: ExcelHelper.java
 * 版         本: component-office(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2014年12月29日
 */
package org.anttribe.component.office.excel;

import java.util.Collection;

import org.anttribe.component.office.excel.entity.ExcelExportConfig;
import org.anttribe.component.office.excel.export.ExcelExportService;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * excel操作辅助类
 * 
 * @author zhaoyong
 * @version 2014年12月29日
 */
public class ExcelHelper
{
    /**
     * excel导出服务
     * 
     * @param config Excel导出参数配置
     * @param clazz 导出实体类
     * @param dataSet 数据
     * @return Workbook
     */
    public static Workbook excelExport(ExcelExportConfig config, Class<?> clazz, Collection<?> dataSet)
    {
        return new ExcelExportService().excelExport(config, clazz, dataSet);
    }
}