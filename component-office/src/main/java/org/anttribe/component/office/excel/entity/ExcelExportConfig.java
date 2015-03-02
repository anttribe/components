/*
 * 文  件   名: ExcelExportConfig.java
 * 版         本: component-office(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年1月4日
 */
package org.anttribe.component.office.excel.entity;

import org.anttribe.component.office.common.OfficeConstants;

/**
 * Excel导出参数配置
 * 
 * @author zhaoyong
 * @version 2015年1月4日
 */
public class ExcelExportConfig
{
    /**
     * excel文件类型(默认03版本)
     */
    private String excelType = OfficeConstants.HSSF;
    
    /**
     * 最大内存数据条数(限定数据条数, 操作海量数据时, 减少内存占用)
     */
    private int maxMemoryDataSzie = OfficeConstants.DEFAULT_MAX_MEMORY_DATASIZE;
    
    /**
     * sheet的名称
     */
    private String sheetName;
    
    public String getExcelType()
    {
        return excelType;
    }
    
    public void setExcelType(String excelType)
    {
        this.excelType = excelType;
    }
    
    public int getMaxMemoryDataSzie()
    {
        return maxMemoryDataSzie;
    }
    
    public void setMaxMemoryDataSzie(int maxMemoryDataSzie)
    {
        this.maxMemoryDataSzie = maxMemoryDataSzie;
    }

    public String getSheetName()
    {
        return sheetName;
    }

    public void setSheetName(String sheetName)
    {
        this.sheetName = sheetName;
    }
}