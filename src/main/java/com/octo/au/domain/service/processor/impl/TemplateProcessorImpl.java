/**
 * 
 */
package com.octo.au.domain.service.processor.impl;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.octo.au.constants.Constants;
import com.octo.au.domain.model.format.ColumnTemplate;
import com.octo.au.domain.model.format.Structure;
import com.octo.au.domain.service.processor.contract.TemplateProcessor;

/**
 * @author Amol Kshirsagar
 *
 */
public class TemplateProcessorImpl implements TemplateProcessor{
	
  public Structure createStructureTemplates(File file) throws IOException {
	  Structure structure = new Structure();
		try (Scanner scanner = new Scanner(file, Constants.ENCODING.name())) {
			int lineNumber = 0;
			while (scanner.hasNextLine()) {
				structure.getCt().add(processLine(scanner.nextLine(), lineNumber));
				lineNumber++;
			}
		} catch (IOException e) {
			throw e;
		}
		return structure;
	}

	private ColumnTemplate processLine(String aLine, int lineNumber) {
		ColumnTemplate columnTemplate = new ColumnTemplate();
		try (Scanner scanner = new Scanner(aLine)) {
			scanner.useDelimiter(Constants.STR_DELIMITER_METADATA_FILE);
			if (scanner.hasNext()) {
				String name = scanner.next();
				String length = scanner.next();
				String type = scanner.next();
				columnTemplate.setIndex(lineNumber);
				columnTemplate.setLength(Integer.valueOf(length));
				columnTemplate.setName(name);
				columnTemplate.setType(type);
			} else {
				throw new NoSuchElementException(Constants.STR_CUSTOM_COMMENT_IDENTIFIER+Constants.STR_USER_MESSAGE_NO_NEXTLINE_METADATAFILE);
			}
		}
		return columnTemplate;
	}
}
