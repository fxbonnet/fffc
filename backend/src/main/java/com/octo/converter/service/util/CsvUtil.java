package com.octo.converter.service.util;


import com.octo.converter.service.Metadata;
import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringReader;
import java.util.List;


public class CsvUtil {
        private static final Logger LOGGER = LoggerFactory.getLogger(CsvUtil.class);
        public static void writeDataToCsv(PrintWriter writer,List<Metadata> data ) {

            try {

                ColumnPositionMappingStrategy mapStrategy
                        = new ColumnPositionMappingStrategy();

                mapStrategy.setType(Metadata.class);

                String[] columns = new String[]{"columnName","columnLength","columnType"};
                mapStrategy.setColumnMapping(columns);

                StatefulBeanToCsv btcsv = new StatefulBeanToCsvBuilder(writer)
                        .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                        .withMappingStrategy(mapStrategy)
                        .withSeparator(',')
                        .build();
                btcsv.write(data);

            } catch (CsvException ex) {
                LOGGER.error("Error mapping Bean to CSV", ex);
            }
        }

        public static List<Metadata> getDataFromCsv(String csv){
            ColumnPositionMappingStrategy strategy
                    = new ColumnPositionMappingStrategy();
            strategy.setType(Metadata.class);
            strategy.setColumnMapping("columnName","columnLength","columnType");

            CsvToBean csvToBean = new CsvToBeanBuilder(new StringReader(csv))
                    .withType(Metadata.class)
                    .withMappingStrategy(strategy)
                    .withSeparator(',')
                    .build();

            List<Metadata> data = csvToBean.parse();
            return data;
        }
    }



