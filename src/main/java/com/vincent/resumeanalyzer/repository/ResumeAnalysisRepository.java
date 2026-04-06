package com.vincent.resumeanalyzer.repository;

import com.vincent.resumeanalyzer.entity.ResumeAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeAnalysisRepository extends JpaRepository<ResumeAnalysis, Long> {

    ResumeAnalysis findTopByOrderByIdDesc();
}
