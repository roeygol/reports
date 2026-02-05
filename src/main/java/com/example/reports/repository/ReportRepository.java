package com.example.reports.repository;

import com.example.reports.domain.Report;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ReportRepository extends MongoRepository<Report, String> {

    Optional<Report> findByReportName(String reportName);
}
