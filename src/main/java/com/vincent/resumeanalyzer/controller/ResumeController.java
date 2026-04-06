package com.vincent.resumeanalyzer.controller;

import com.vincent.resumeanalyzer.ai.AIService;
import com.vincent.resumeanalyzer.dto.ResumeAnalysisResponse;
import com.vincent.resumeanalyzer.entity.ResumeAnalysis;
import com.vincent.resumeanalyzer.repository.ResumeAnalysisRepository;
import com.vincent.resumeanalyzer.service.ResumeAnalysisService;
import com.vincent.resumeanalyzer.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private AIService aiService;

    @Autowired
    private ResumeAnalysisRepository resumeAnalysisRepository;

    @Autowired
    private ResumeAnalysisService resumeAnalysisService;

    // ✅ Upload resume and analyze
    @PostMapping("/upload")
    public ResponseEntity<ResumeAnalysisResponse> uploadResume(@RequestParam("file") MultipartFile file) {
        try {
            // Validate file type
            if (!file.getContentType().equals("application/pdf")) {
                return ResponseEntity.badRequest().body(
                        new ResumeAnalysisResponse()
                );            }

// Optional: limit file size (5 MB example)
            if (file.getSize() > 5 * 1024 * 1024) {
                return ResponseEntity.badRequest().body(
                        new ResumeAnalysisResponse()
                );            }

            if (file.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            // 1️⃣ Extract text from resume
            String text = resumeService.extractText(file);

            // 2️⃣ AI analysis
            ResumeAnalysisResponse response = aiService.analyzeResume(text);

            // 3️⃣ Save analysis to DB
            ResumeAnalysis entity = new ResumeAnalysis();
            entity.setSkills(response.getSkills());
            entity.setStrengths(response.getStrengths());
            entity.setSuggestions(response.getSuggestions());
            entity.setExperienceLevel(response.getExperienceLevel());
            entity.setJobFitScore(response.getJobFitScore());

            resumeAnalysisService.saveAnalysis(response);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // ✅ Get the latest resume analysis
    @GetMapping("/latest")
    public ResponseEntity<ResumeAnalysisResponse> getLatestAnalysis() {
        try {
            ResumeAnalysis latest = resumeAnalysisRepository.findTopByOrderByIdDesc();
            if (latest == null) {
                return ResponseEntity.notFound().build();
            }

            ResumeAnalysisResponse response = new ResumeAnalysisResponse();
            response.setSkills(latest.getSkills());
            response.setStrengths(latest.getStrengths());
            response.setSuggestions(latest.getSuggestions());
            response.setExperienceLevel(latest.getExperienceLevel());
            response.setJobFitScore(latest.getJobFitScore());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResumeAnalysisResponse>> getAllAnalyses() {
        List<ResumeAnalysis> all = resumeAnalysisRepository.findAll();

        List<ResumeAnalysisResponse> responseList = all.stream().map(entity -> {
            ResumeAnalysisResponse response = new ResumeAnalysisResponse();
            response.setSkills(entity.getSkills());
            response.setStrengths(entity.getStrengths());
            response.setSuggestions(entity.getSuggestions());
            response.setExperienceLevel(entity.getExperienceLevel());
            response.setJobFitScore(entity.getJobFitScore());
            return response;
        }).toList();

        return ResponseEntity.ok(responseList);
    }
}