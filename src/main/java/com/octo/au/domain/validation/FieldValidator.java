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
        for(DataRow dataRow: dataRows){
        	for(DataColumn dataColumn : dataRow.getColumns()){
        		if(dataColumn.getType().equalsIgnoreCase(Constants.STR)){
        		errorFields.append(ValidatorUtil.notNullString.and(ValidatorUtil.notEmptyString)
                        .and(ValidatorUtil.stringBetween(1, metadataStructure.getCt().get(dataColumn.getColumnIndex()).getLength())).test((String)dataColumn.getValue())
                        .getFieldNameIfInvalid(" Please specify valid data for  "+metadataStructure.getCt().get(dataColumn.getColumnIndex()).getName()).orElse(""));
        		}
        	}
        }
        /*errorFields.append(ValidatorUtil.notNullInteger.and(ValidatorUtil.greaterThanZero)
            .and(ValidatorUtil.integerBetween(18, 60)).test(employee.getAge())
            .getFieldNameIfInvalid(" Please specify valid age ").orElse(""));*/
        String errors = errorFields.toString();
        if (!errors.isEmpty()) {
            throw new CustomException(errors);
        }
    }
}