package com.hsh.domain.dto;

import java.io.Serializable;

/**
 * @author ibm
 */
public class TkQuestionArea  implements Serializable{
    private static final long serialVersionUID = -4025543747969714492L;
    private Integer id;
    private Integer questionId;
    private Integer areaId;
    private String areaName;

    private Integer disabled;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
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

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }
}