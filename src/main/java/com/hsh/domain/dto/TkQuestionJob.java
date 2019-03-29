package com.hsh.domain.dto;

import java.io.Serializable;

/**
 * @author ibm
 */
public class TkQuestionJob implements Serializable{
    private static final long serialVersionUID = -5514146181859431374L;
    private Integer id;
    private Integer questionId;
    private Integer jobId;
    private String jobName;

    private Integer disabled;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }
}