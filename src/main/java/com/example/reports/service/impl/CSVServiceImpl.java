package com.example.reports.service.impl;

import com.example.reports.exception.CSVGenerationException;
import com.example.reports.service.CSVService;
import com.example.reports.service.model.DB2QueryResult;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CSVServiceImpl implements CSVService {

    @Override
    public byte[] generateCSV(DB2QueryResult result) {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream();
             OutputStreamWriter writer = new OutputStreamWriter(output, StandardCharsets.UTF_8);
             CSVWriter csvWriter = new CSVWriter(writer)) {

            List<String> columns = result.getColumnNames();
            csvWriter.writeNext(columns.toArray(new String[0]));

            for (List<Object> row : result.getRows()) {
                String[] values = row.stream()
                        .map(v -> v == null ? "" : String.valueOf(v))
                        .toArray(String[]::new);
                csvWriter.writeNext(values);
            }

            csvWriter.flush();
            return output.toByteArray();
        } catch (IOException ex) {
            throw new CSVGenerationException("Failed to generate CSV", ex);
        }
    }
}
