package com.company.librarysystem.repository;

import com.company.librarysystem.entity.IssueRecord;
import com.company.librarysystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IssueRecordRepository extends JpaRepository<IssueRecord, Long> {
    // This tells Spring to write a SQL query to find all books borrowed by a specific user
    List<IssueRecord> findByUser(User user);
}