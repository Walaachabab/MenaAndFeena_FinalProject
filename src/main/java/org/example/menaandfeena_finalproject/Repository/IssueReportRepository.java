package org.example.menaandfeena_finalproject.Repository;

import org.example.menaandfeena_finalproject.Model.IssueReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueReportRepository extends JpaRepository<IssueReport, Integer>
{ IssueReport findIssueReportById(Integer id);

    int countByReporterIdAndStatus(Integer id, String completed);

    List<IssueReport> findByReporterId(Integer id);
}
