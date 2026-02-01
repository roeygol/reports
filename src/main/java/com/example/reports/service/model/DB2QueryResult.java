package com.example.reports.service.model;

import java.util.List;

public class DB2QueryResult {

    private final List<String> columnNames;
    private final List<List<Object>> rows;

    public DB2QueryResult(List<String> columnNames, List<List<Object>> rows) {
        this.columnNames = columnNames;
        this.rows = rows;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public List<List<Object>> getRows() {
        return rows;
    }

    public boolean isEmpty() {
        return rows == null || rows.isEmpty();
    }
}
