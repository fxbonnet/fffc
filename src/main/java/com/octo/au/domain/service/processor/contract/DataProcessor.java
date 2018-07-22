/**
 * 
 */
package com.octo.au.domain.service.processor.contract;
import java.io.File;
import java.io.IOException;
import java.util.List;

import com.octo.au.domain.model.DataRow;
import com.octo.au.domain.model.format.Structure;
import com.octo.au.exception.CustomException;

/**
 * @author Amol Kshirsagar
 *
 */
public interface DataProcessor {
  public List<DataRow> getColumnsFromDataFile(File file,Structure metadataStructure) throws IOException, CustomException;
}
