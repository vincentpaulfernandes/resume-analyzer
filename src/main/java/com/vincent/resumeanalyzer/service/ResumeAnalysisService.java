package com.vincent.resumeanalyzer.service;

import com.vincent.resumeanalyzer.dto.ResumeAnalysisResponse;
import com.vincent.resumeanalyzer.entity.ResumeAnalysis;
import com.vincent.resumeanalyzer.repository.ResumeAnalysisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResumeAnalysisService {

    @Autowired
    private ResumeAnalysisRepository repository;

    public ResumeAnalysis saveAnalysis(ResumeAnalysisResponse dto) {

        ResumeAnalysis entity = new ResumeAnalysis();

        entity.setSkills(dto.getSkills());
        entity.setStrengths(dto.getStrengths());
        entity.setSuggestions(dto.getSuggestions());
        entity.setExperienceLevel(dto.getExperienceLevel());
        entity.setJobFitScore(dto.getJobFitScore());

        return repository.save(entity);
    }
}