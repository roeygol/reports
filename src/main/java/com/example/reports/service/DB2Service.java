package com.example.reports.service;

import com.example.reports.service.model.DB2QueryResult;

public interface DB2Service {

    DB2QueryResult executeQuery(String query);
}

