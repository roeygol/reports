package com.example.reports.service.impl;

import com.example.reports.exception.ReportQueryExecutionException;
import com.example.reports.service.DB2Service;
import com.example.reports.service.model.DB2QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class DB2ServiceImpl implements DB2Service {

    private static final Logger log = LoggerFactory.getLogger(DB2ServiceImpl.class);

    /**
     * Pattern to detect existing WITH UR clause at end of query.
     */
    private static final Pattern ISOLATION_CLAUSE_PATTERN =
            Pattern.compile("\\s+WITH\\s+UR\\s*;?\\s*$", Pattern.CASE_INSENSITIVE);

    private static final String ISOLATION_CLAUSE = " WITH UR";

    private final JdbcTemplate jdbcTemplate;

    public DB2ServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public DB2QueryResult executeQuery(String query) {
        String executableQuery = ensureIsolationClause(query);
        try {
            return jdbcTemplate.query(con -> con.prepareStatement(executableQuery),
                    this::extractResult);
        } catch (Exception ex) {
            log.error("Failed to execute DB2 query", ex);
            throw new ReportQueryExecutionException("Failed to execute DB2 query", ex);
        }
    }

    /**
     * Appends WITH UR (Uncommitted Read) to the query if not already present.
     * Report queries run with UR to avoid locking and allow concurrent updates.
     */
    private String ensureIsolationClause(String query) {
        if (query == null || query.isBlank()) {
            return query;
        }
        String trimmed = query.trim();
        if (ISOLATION_CLAUSE_PATTERN.matcher(trimmed).find()) {
            return trimmed;
        }
        String withoutSemicolon = trimmed.replaceAll(";\\s*$", "");
        return withoutSemicolon + ISOLATION_CLAUSE;
    }

    private DB2QueryResult extractResult(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        List<String> columnNames = new ArrayList<>(columnCount);
        for (int i = 1; i <= columnCount; i++) {
            columnNames.add(metaData.getColumnLabel(i));
        }

        List<List<Object>> rows = new ArrayList<>();
        while (rs.next()) {
            List<Object> row = new ArrayList<>(columnCount);
            for (int i = 1; i <= columnCount; i++) {
                row.add(rs.getObject(i));
            }
            rows.add(row);
        }

        return new DB2QueryResult(columnNames, rows);
    }
}

