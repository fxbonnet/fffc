package com.octo.fffc.helper;

import com.octo.fffc.model.MetadataColumn;
import com.octo.fffc.model.XlRow;
import com.octo.fffc.model.XlTable;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author alanterriaga
 * @project FFFC
 */
@Component
public class FileWriterHelper {

    Logger logger = LoggerFactory.getLogger(FileWriterHelper.class);

    /**
     * Generate Workboo to CSV File
     *
     * @param xlTable
     * @return
     * @throws IOException
     */
    public HSSFWorkbook createWorkbook(XlTable xlTable) {

        if(CollectionUtils.isEmpty(xlTable.getHeaders())){
            logger.debug("CSV File with no header information");
            return null;
        }

        logger.debug("Creating CSV File Data");
        int rowNum=0;

        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet worksheet = workbook.createSheet("FFFC");
        worksheet.setDefaultColumnWidth(30);

        HSSFRow hssfRow = worksheet.createRow(rowNum++);

        // HEADER
        createHeader(hssfRow, xlTable.getHeaders());

        // VALUES
        for(XlRow xlRow : xlTable.getRows()){
            hssfRow = worksheet.createRow(rowNum++);

            createDataLine(hssfRow, xlTable.getHeaders(), xlRow);
        }

        logger.debug("Finished creation CSV File Data");

        return workbook;
    }

    private void createHeader(HSSFRow hssfRow, List<MetadataColumn> metadataColumns){

        logger.debug("Generating header to file");

        int column = 0;
        for(MetadataColumn metadataColumn : metadataColumns){

            HSSFCell cell = hssfRow.createCell(column);
            cell.setCellValue(metadataColumn.getHeader());
            column++;
        }
    }

    private void createDataLine(HSSFRow hssfRow, List<MetadataColumn> metadataColumns, XlRow xlRow){

        logger.debug("Generating data values to file");

        int column = 0;
        for(String value : xlRow.getValues()){

            MetadataColumn metadataColumn = metadataColumns.get(column);

            HSSFCell cell = hssfRow.createCell(column);

            switch (metadataColumn.getColumnFormat()){
                case DATE:
                    LocalDate localDate = LocalDate.parse(value);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy");
                    cell.setCellValue(localDate.format(formatter));
                    break;
                case NUMERIC:
                    cell.setCellValue(Double.parseDouble(value));
                    break;
                default:
                    cell.setCellValue(value);
                    break;
            }

            column++;
        }
    }

}
