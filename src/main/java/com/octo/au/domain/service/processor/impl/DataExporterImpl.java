package com.octo.au.domain.service.processor.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.octo.au.domain.model.DataColumn;
import com.octo.au.domain.model.DataRow;
import com.octo.au.domain.model.format.ColumnTemplate;
import com.octo.au.domain.service.processor.contract.DataExporter;
import com.octo.au.util.CSVUtils;

public class DataExporterImpl implements DataExporter{

	public boolean exportData(List<DataRow> dataRows, String targetFileName, List<ColumnTemplate> columnTemplates)
			throws IOException {
		FileWriter writer = new FileWriter(targetFileName);
		List<String> headers = new ArrayList<String>();
		for (ColumnTemplate ct : columnTemplates) {
			headers.add(ct.getName());
		}
		CSVUtils.writeLine(writer, headers);

		for (DataRow d : dataRows) {
			List<String> csvData = new ArrayList<>();
			for (DataColumn dc : d.getColumns()) {
				csvData.add((String) dc.getValue());
			}
			// CSVUtils.writeLine(writer, list);
			CSVUtils.writeLine(writer, csvData, ',', '\"');
		}
		writer.flush();
		writer.close();
		return true;
	}
}