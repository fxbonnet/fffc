package com.octo.fffc.parser;

import com.octo.fffc.exception.InvalidStructureException;
import com.octo.fffc.model.ColumnStructure;
import com.octo.fffc.model.ColumnType;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ColumnStructureParser {

    private static final Logger logger = Logger.getLogger(ColumnStructureParser.class.getName());
    private static final int DEFINITION_FIELDS_SIZE = 3;

    public static ColumnStructure parse(String columnInfo) throws InvalidStructureException {

        String[] columns = splitColumns(columnInfo);

        String colName = parseColumnName(columns[0]);

        Integer colLength = parseColumnLength(columns[1]);

        ColumnType colType = parseColumnType(columns[2]);

        return new ColumnStructure(colName, colLength, colType);

    }

    private static String[] splitColumns(String columnInfo) throws InvalidStructureException {

        if (columnInfo == null || columnInfo.trim().isEmpty()) {
            logger.log(Level.SEVERE, "Column definition could not be found.");
            throw new InvalidStructureException("[ERROR] Column definition could not be found.");
        }

        String[] columns = columnInfo.split(",");

        if (columns.length != DEFINITION_FIELDS_SIZE) {
            logger.log(Level.SEVERE, "Invalid column definition format.");
            throw new InvalidStructureException("[ERROR] Invalid column definition format.");
        }
        return columns;
    }

    private static String parseColumnName(String column) throws InvalidStructureException {

        if (!column.trim().isEmpty()) {
            return column.trim();
        } else {
            logger.log(Level.SEVERE, "Column name of metadata file is not valid.");
            throw new InvalidStructureException("[ERROR] Column name of metadata file is not valid.");
        }
    }

    private static Integer parseColumnLength(String column) throws InvalidStructureException {
        Integer colLength;

        if (!column.trim().isEmpty()) {
            try {
                colLength = Integer.valueOf(column.trim());
                return colLength;
            } catch (NumberFormatException e) {
                logger.log(Level.SEVERE, "Column length of metadata file is invalid.");
                throw new InvalidStructureException("[ERROR] Column length of metadata file is invalid.");
            }

        } else {
            logger.log(Level.SEVERE, "Column length of metadata file is invalid.");
            throw new InvalidStructureException("[ERROR] Column length of metadata file is not valid.");
        }
    }

    private static ColumnType parseColumnType(String column) throws InvalidStructureException {

        if (!column.trim().isEmpty()) {
            ColumnType colType = ColumnType.valueOf(column.trim().toUpperCase());

            if (colType != null) {
                return colType;
            }
        }

        logger.log(Level.SEVERE, "Column type of metadata file is not valid.");
        throw new InvalidStructureException("[ERROR] Column type of metadata file is not valid.");
    }

}
