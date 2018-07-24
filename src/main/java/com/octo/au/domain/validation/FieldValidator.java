package com.octo.au.domain.validation;

import java.util.List;

import com.octo.au.constants.Constants;
import com.octo.au.domain.model.DataColumn;
import com.octo.au.domain.model.DataRow;
import com.octo.au.domain.model.format.Structure;
import com.octo.au.exception.CustomException;

public class FieldValidator implements DataValidator {
    @Override
    public void validate(List<DataRow> dataRows,Structure metadataStructure) throws CustomException {
        StringBuilder errorFields = new StringBuilder();
        int rowNumber=1;
        for(DataRow dataRow: dataRows){
        	for(DataColumn dataColumn : dataRow.getColumns()){
        		int columnNumber=1;
        		if(dataColumn.getType().equalsIgnoreCase(Constants.STR)){
        		    errorFields.append(ValidatorUtil.notNullString
        			.and(ValidatorUtil.notEmptyString)
                    .and(ValidatorUtil.stringBetween(1, metadataStructure.getCt().get(dataColumn.getColumnIndex()).getLength()))
                    .test(dataColumn.getValue().toString())
                    .getFieldNameIfInvalid(" Please specify valid data for "+metadataStructure.getCt().get(dataColumn.getColumnIndex()).getName()
                        						+ " with value "+ dataColumn.getValue().toString()
                        						+ " on row number "+rowNumber
                        						+ " and column number "+columnNumber).orElse(""));
        		}
        		else if(dataColumn.getType().equalsIgnoreCase(Constants.STR_NUMERIC)){
        			 errorFields.append(ValidatorUtil.notNullString
        	        .and(ValidatorUtil.notEmptyString)
        	        .and(ValidatorUtil.stringBetween(1, metadataStructure.getCt().get(dataColumn.getColumnIndex()).getLength()))
                    .and(ValidatorUtil.isValidinteger())
                    .test(dataColumn.getValue().toString())
                    .getFieldNameIfInvalid(" Please specify valid data for "+metadataStructure.getCt().get(dataColumn.getColumnIndex()).getName()
                        						+ " with value "+ dataColumn.getValue().toString()
                        						+ " on row number "+rowNumber
                        						+ " and column number "+columnNumber).orElse(""));
        		}
        		/*else if(dataColumn.getType().equalsIgnoreCase(Constants.STR_DATE)){
        			errorFields.append(ValidatorUtil.notNullString
            				.and(ValidatorUtil.notEmptyString)
                            .and(ValidatorUtil.stringBetween(1, metadataStructure.getCt().get(dataColumn.getColumnIndex()).getLength()))
                            .test(dataColumn.getValue().toString())
                            .getFieldNameIfInvalid(" Please specify valid data for "+metadataStructure.getCt().get(dataColumn.getColumnIndex()).getName()
                            						+ " with value "+ dataColumn.getValue().toString()
                            						+ " on row number "+rowNumber
                            						+ " and column number "+columnNumber).orElse(""));
        		}*/
        		columnNumber++;
        	}
        	rowNumber++;
        }
        String errors = errorFields.toString();
        if (!errors.isEmpty()) {
            throw new CustomException(errors);
        }
    }
}
