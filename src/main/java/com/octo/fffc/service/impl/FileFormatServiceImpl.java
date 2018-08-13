package com.octo.fffc.service.impl;

import com.octo.fffc.exceptions.InputFileException;
import com.octo.fffc.exceptions.MetadataFileException;
import com.octo.fffc.helper.FileWriterHelper;
import com.octo.fffc.model.InputRequestDto;
import com.octo.fffc.model.Metadata;
import com.octo.fffc.model.XlTable;
import com.octo.fffc.service.FileFormatService;
import com.octo.fffc.utils.Converters;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author alanterriaga
 * @project FFFC
 */
@Service
public class FileFormatServiceImpl implements FileFormatService {

    Logger logger = LoggerFactory.getLogger(FileFormatServiceImpl.class);

    @Autowired
    Converters converters;

    @Autowired
    FileWriterHelper fileWriterHelper;

    /**
     * Format the request files and convert to a CSV file
     *
     * @param inputRequestDto
     * @return
     * @throws Exception
     */
    public boolean formatAndCreateFile(InputRequestDto inputRequestDto) throws Exception {

        Metadata metadata = converters.convertInputToMetadata(inputRequestDto.getMetaDataFileName());

        if(metadata == null || CollectionUtils.isEmpty(metadata.getMetadataColumnList())){
            logger.debug("No metadata information found");
            throw new MetadataFileException();
        }

        XlTable xlTable = converters.convertInputToXlTable(metadata, inputRequestDto.getDataFileName());

        if(xlTable == null || CollectionUtils.isEmpty(xlTable.getRows())){
            logger.debug("No input information found");
            throw new InputFileException();
        }

        HSSFWorkbook workbook= fileWriterHelper.createWorkbook(xlTable);

        if(workbook != null){
            converters.saveFile(workbook);
            return true;
        }

        return false;
    }
}
