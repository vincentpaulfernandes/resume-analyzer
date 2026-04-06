package com.vincent.resumeanalyzer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ResumeAnalysisResponse {

    private List<String> skills;

    @JsonProperty("experience_level")
    private String experienceLevel;

    private List<String> strengths;
    private List<String> suggestions;

    @JsonProperty("job_fit_score")
    private int jobFitScore;

    // Getters & Setters
    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }

    public String getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(String experienceLevel) { this.experienceLevel = experienceLevel; }

    public List<String> getStrengths() { return strengths; }
    public void setStrengths(List<String> strengths) { this.strengths = strengths; }

    public List<String> getSuggestions() { return suggestions; }
    public void setSuggestions(List<String> suggestions) { this.suggestions = suggestions; }

    public int getJobFitScore() { return jobFitScore; }
    public void setJobFitScore(int jobFitScore) { this.jobFitScore = jobFitScore; }
}