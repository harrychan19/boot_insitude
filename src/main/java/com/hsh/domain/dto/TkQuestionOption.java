package com.hsh.domain.dto;

import java.io.Serializable;

/**
 * @author ibm
 */
public class TkQuestionOption implements Serializable{
    private static final long serialVersionUID = -2801074609759285349L;
    private Integer id;
    private Integer questionId;
    private Integer seq;
    private String optionMark;
    private String optionContent;
    private String optionImg;
    private boolean isCorrect;

    private Integer disabled;

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

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getOptionMark() {
        return optionMark;
    }

    public void setOptionMark(String optionMark) {
        this.optionMark = optionMark == null ? null : optionMark.trim();
    }

    public String getOptionContent() {
        return optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent == null ? null : optionContent.trim();
    }

    public String getOptionImg() {
        return optionImg;
    }

    public void setOptionImg(String optionImg) {
        this.optionImg = optionImg == null ? null : optionImg.trim();
    }

    public boolean getIsCorrect() {
        return this.isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }
}