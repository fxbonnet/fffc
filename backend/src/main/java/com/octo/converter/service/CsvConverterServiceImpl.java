package com.octo.converter.service;

import com.octo.converter.config.Configs;
import com.octo.converter.service.util.CommonUtil;
import com.octo.converter.service.util.Constants;
import com.octo.converter.service.util.CsvUtil;
import com.octo.converter.service.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

/*
to convert input file to Csv format
 */
@Service
public class CsvConverterServiceImpl implements ConverterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvConverterServiceImpl.class);

   @Autowired
   private Configs configs;

   @Autowired
   private Validator validator;


    @Override
    public File convert(File fixedFormatfile, File metadataCsv) throws ConverterException, IOException {
        List<Metadata> columns=getMetaData(metadataCsv);
    String inputDatePattern=configs.FFF_DATE_FORMAT;
    String outputDatePattern=configs.CSV_DATE_FORMAT;
        File resultcsv=File.createTempFile( configs.TMP_FOLDER+ "/"+configs.RESULT_FILE_NAME,null);
        FileOutputStream fos = new FileOutputStream(resultcsv);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        StringBuilder heading=new StringBuilder();
        int i=1;//to ignore adding comma to last
        for(Metadata columnDetails:columns){
            heading.append(columnDetails.getColumnName());
           if(i<columns.size()) heading.append(configs.CSV_COLUMN_SEPARATOR);
            i++;
        }
        heading.append(configs.CSV_ROW_SEPARATOR);
        //System.out.println(heading.toString());
        bw.write(heading.toString());
        FileReader fr=new FileReader(fixedFormatfile);
        BufferedReader br=new BufferedReader(fr);
        int cn = 0;
        int columnIndex=0;
        int rowIndex=0;
        Boolean isInsideDoubleQuote=false;
        StringBuilder row=new StringBuilder();
        StringBuilder cell=new StringBuilder();
        while((cn = br.read()) != -1)         //Read char by Char
        {
            char c = (char) cn;
            if(isInsideDoubleQuote){
                //inside double quotes all special characters but CR and LF not allowed
                if(c=='\r' || c=='\n') throw new ConverterException(Constants.ExceptionConstants.CRLF_NOT_ALLOWED+rowIndex);
                if(cell.length()<=columns.get(columnIndex).getColumnLength()){
                    if(c==configs.STRING_IDENTIFIER) {
                        isInsideDoubleQuote=false;
                        //marks end of cell
                        //validate cell for column type
                        validator.validateColumnContentForType(cell.toString(),columns.get(columnIndex).getColumnType(),true);
                        if(columns.get(columnIndex).getColumnType().equals(Constants.DATE_TYPE)){
                            row.append(CommonUtil.changeDateFormatTo(cell.toString(),inputDatePattern,outputDatePattern));
                        }else {
                            row.append(cell);
                        }
                        //System.out.println("appending cell inside double quote "+cell);
                        LOGGER.debug("appending cell inside double quote "+cell);
                        cell=new StringBuilder();//reset cell
                        columnIndex++;
                        if(columnIndex==columns.size()){//must be last column of row
                            row.append(configs.CSV_ROW_SEPARATOR);
                            bw.write(row.toString());
                            //System.out.println("row is "+row.toString());
                            row=new StringBuilder();//reset row
                            if(columnIndex<columns.size()-1){
                                throw new ConverterException(Constants.ExceptionConstants.MISSING_COLUMN+rowIndex);
                            }
                            rowIndex++;//moving to next row
                            columnIndex=0;
                        }else{
                            row.append(configs.CSV_COLUMN_SEPARATOR);
                        }
                    }
                    else if(c==Constants.space){
                        if(Boolean.valueOf(configs.IS_SPACE_ALLOWED_INSIDE_QUOTES)) {
                            cell.append(c); //add space if configured to be allowed
                        }
                    }else{
                       cell.append(c);//any other special characters are allowed
                    }

                }else{
                    throw new ConverterException(Constants.ExceptionConstants.VALUE_EXCEEDED_LENGTH+rowIndex);
                }
            }else{
                if(c==configs.STRING_IDENTIFIER){
                    isInsideDoubleQuote=true;
                    if(cell.length()>1) {
                        //first double quote mark end of cell
                        //validate cell for column type
                        validator.validateColumnContentForType(cell.toString(),columns.get(columnIndex).getColumnType(),false);
                        if(columns.get(columnIndex).getColumnType().equals(Constants.DATE_TYPE)){
                            row.append(CommonUtil.changeDateFormatTo(cell.toString(),inputDatePattern,outputDatePattern));
                        }else {
                            row.append(cell);
                        }
                        row.append(configs.CSV_COLUMN_SEPARATOR);
                        //System.out.println("appending cell before start double quote " + cell.toString());
                       LOGGER.debug("appending cell before start double quote " + cell.toString());
                        cell = new StringBuilder();//reset cell
                        columnIndex++;

                    }
                    continue;
                }
                if(c==Constants.space){
                    //first space marks end of cell
                    if(cell.length()>1) {
                        //validate cell for column type
                        validator.validateColumnContentForType(cell.toString(),columns.get(columnIndex).getColumnType(),false);
                        if(columns.get(columnIndex).getColumnType().equals(Constants.DATE_TYPE)){
                            row.append(CommonUtil.changeDateFormatTo(cell.toString(),inputDatePattern,outputDatePattern));
                        }else {
                            row.append(cell);
                        }
                        //System.out.println("appending cell after space " + cell.toString());
                        LOGGER.debug("appending cell after space " + cell.toString());
                        cell = new StringBuilder();//reset cell
                        columnIndex++;
                        if(columnIndex==columns.size()){//must be last column of row
                            row.append(configs.CSV_ROW_SEPARATOR);
                            bw.write(row.toString());
                            //System.out.println("row is "+row.toString());
                            row=new StringBuilder();//reset row
                            if(columnIndex<columns.size()-1){
                                throw new ConverterException(Constants.ExceptionConstants.MISSING_COLUMN+rowIndex);
                            }
                            rowIndex++;//moving to next row
                            columnIndex=0;
                        }else{
                            row.append(configs.CSV_COLUMN_SEPARATOR);
                        }
                    }else
                    {
                        continue;
                    }
                }
                if(c=='\r' || c=='\n' && cell.length()>1){ //either explicit CR or LF
                    //mark end of the row
                    //write row to result csv
                    //validate cell for column type
                    validator.validateColumnContentForType(cell.toString(),columns.get(columnIndex).getColumnType(),false);
                    if(columns.get(columnIndex).getColumnType().equals(Constants.DATE_TYPE)){
                        row.append(CommonUtil.changeDateFormatTo(cell.toString(),inputDatePattern,outputDatePattern));
                    }else {
                        row.append(cell);
                    }
                    row.append(configs.CSV_ROW_SEPARATOR);
                    bw.write(row.toString());
                    //System.out.println("row is "+row.toString());
                    LOGGER.debug("row is "+row.toString());
                    row=new StringBuilder();//reset row
                    if(columnIndex<columns.size()-1){
                        throw new ConverterException(Constants.ExceptionConstants.MISSING_COLUMN+rowIndex);
                    }
                    rowIndex++;//moving to next row
                    cell=new StringBuilder();//reset cell
                    columnIndex=0;//rest column index
                }else{
                    //any other alphebetical or numerical character allowed. Special characters not allowed
                    if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c=='.' || c=='-' || c==Constants.space) {
                        if (columns.get(columnIndex).getColumnLength() == cell.length()) {
                            //marks end of cell thus moving to next column
                            //validate cell for column type
                            validator.validateColumnContentForType(cell.toString(),columns.get(columnIndex).getColumnType(),false);
                            if(columns.get(columnIndex).getColumnType().equals(Constants.DATE_TYPE)){
                                row.append(CommonUtil.changeDateFormatTo(cell.toString(),inputDatePattern,outputDatePattern));
                            }else {
                                row.append(cell);
                            }
                            //System.out.println("appending cell after max lengh " + cell.toString());
                           LOGGER.debug("appending cell after max lengh " + cell.toString());
                            cell = new StringBuilder();//reset cell
                            cell.append(c);
                            columnIndex++;
                            if(columnIndex==columns.size()){//must be last column of row
                                row.append(configs.CSV_ROW_SEPARATOR);
                                bw.write(row.toString());
                                //System.out.println("row is "+row.toString());
                                row=new StringBuilder();//reset row
                                if(columnIndex<columns.size()-1){
                                    throw new ConverterException(Constants.ExceptionConstants.MISSING_COLUMN+rowIndex);
                                }
                                rowIndex++;//moving to next row
                                columnIndex=0;
                            }else{
                                row.append(configs.CSV_COLUMN_SEPARATOR);
                            }
                        } else {
                            if (c!=Constants.space) { //ignore space outside double quotes
                                cell.append(c);
                            }
                        }
                    }else{
                        throw new ConverterException(Constants.ExceptionConstants.SPECIAL_CHAR_NOT_ALLOWED+c+" at row:"+rowIndex);
                    }
                }
            }
        }
        //end of file - append last row found
        if(cell.length()>1) {
            //validate cell for column type
            validator.validateColumnContentForType(cell.toString(), columns.get(columnIndex).getColumnType(), false);
            if (columns.get(columnIndex).getColumnType().equals(Constants.DATE_TYPE)) {
                row.append(CommonUtil.changeDateFormatTo(cell.toString(), inputDatePattern, outputDatePattern));
            } else {
                row.append(cell);
            }
            row.append(configs.CSV_ROW_SEPARATOR);
            bw.write(row.toString());
            //System.out.println("last row is " + row.toString());
            LOGGER.debug("last row is " + row.toString());
            if (columnIndex < columns.size() - 1) {
                throw new ConverterException(Constants.ExceptionConstants.MISSING_COLUMN + rowIndex);
            }
        }
        bw.close();
        br.close();
        return resultcsv;
    }

    private List<Metadata> getMetaData(File metadatacsv) throws IOException {
        return CsvUtil.getDataFromCsv(org.apache.commons.io.FileUtils.readFileToString(metadatacsv, "UTF-8"));
    }

}
