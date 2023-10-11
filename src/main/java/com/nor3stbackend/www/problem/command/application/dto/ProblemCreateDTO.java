package com.nor3stbackend.www.problem.command.application.dto;

public class ProblemCreateDTO {

    private Long problemId;
    private String problemString;
    private String problemAudioLocation;

    public ProblemCreateDTO() {
    }

    public ProblemCreateDTO(Long problemId, String problemString, String problemAudioLocation) {
        this.problemId = problemId;
        this.problemString = problemString;
        this.problemAudioLocation = problemAudioLocation;
    }

    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public String getProblemString() {
        return problemString;
    }

    public void setProblemString(String problemString) {
        this.problemString = problemString;
    }

    public String getProblemAudioLocation() {
        return problemAudioLocation;
    }

    public void setProblemAudioLocation(String problemAudioLocation) {
        this.problemAudioLocation = problemAudioLocation;
    }
}
