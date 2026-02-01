package com.example.reports.service;

import com.example.reports.service.model.DB2QueryResult;

public interface CSVService {

    byte[] generateCSV(DB2QueryResult result);
}
