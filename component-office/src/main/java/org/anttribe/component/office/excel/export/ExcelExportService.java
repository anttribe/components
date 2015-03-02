/*
 * 文  件   名: ExcelExportService.java
 * 版         本: component-office(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2014年12月29日
 */
package org.anttribe.component.office.excel.export;

import java.util.Collection;

import org.anttribe.component.office.common.OfficeConstants;
import org.anttribe.component.office.excel.entity.ExcelExportConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * excel导出服务
 * 
 * @author zhaoyong
 * @version 2014年12月29日
 */
public class ExcelExportService
{
    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelExportService.class);
    
    /**
     * excel导出服务
     * 
     * @param config Excel导出参数配置
     * @param clazz 导出实体类
     * @param dataSet 数据
     * @return Workbook
     */
    public Workbook excelExport(ExcelExportConfig config, Class<?> clazz, Collection<?> dataSet)
    {
        LOGGER.debug("While exporting excel, pojo class {}, excel version is {}",
            clazz,
            config.getExcelType().equals(OfficeConstants.HSSF) ? "03" : "07");
        
        // 创建workbook
        Workbook workbook = this.createWorkbook(config.getExcelType(), config.getMaxMemoryDataSzie(), dataSet);
        if (null != workbook)
        {
            // 创建sheet
        }
        return workbook;
    }
    
    /**
     * 创建Workbook
     * 
     * @param type 导出excel文件类型
     * @param dataSet 数据
     * @return Workbook
     */
    private Workbook createWorkbook(String type, int maxDataSize, Collection<?> dataSet)
    {
        Workbook workbook = null;
        if (type.equals(OfficeConstants.HSSF))
        {
            workbook = new HSSFWorkbook();
        }
        else if (!CollectionUtils.isEmpty(dataSet) && dataSet.size() < maxDataSize)
        {
            // 对于海量数据，处理中会占用大量内存
            workbook = new XSSFWorkbook();
        }
        else
        {
            workbook = new SXSSFWorkbook();
        }
        return workbook;
    }
    
    /**
     * 创建Sheet
     * 
     * @param workbook 工作簿
     * @param config Excel导出参数配置
     * @param clazz 导出实体类
     * @param dataSet 数据
     */
    private void createSheet(Workbook workbook, ExcelExportConfig config, Class<?> clazz, Collection<?> dataSet)
    {
        String sheetName = config.getSheetName();
        if (!StringUtils.isEmpty(sheetName))
        {
            sheetName = WorkbookUtil.createSafeSheetName(sheetName);
        }
        
        Sheet sheet = null;
        // 创建sheet
        if (!StringUtils.isEmpty(sheetName))
        {
            sheet = workbook.createSheet(sheetName);
        }
        else
        {
            sheet = workbook.createSheet();
        }
        
    }
}