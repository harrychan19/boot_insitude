package com.hsh.domain.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author ibm
 */
public class TkPaperQuestion implements Serializable{
    private static final long serialVersionUID = 5520131904405220846L;
    private Integer id;
    private Integer paperId;
    private String partName;
    private Integer questionId;
    private Integer seq;
    private Integer type;
    private Integer questionSeq;
    private Integer parentId;
    private Integer score;
    private TkQuestionDto question;
    private List<TkPaperQuestion> materialQuestions;

    private Integer disabled;

    public List<TkPaperQuestion> getMaterialQuestions() {
        return materialQuestions;
    }

    public void setMaterialQuestions(List<TkPaperQuestion> materialQuestions) {
        this.materialQuestions = materialQuestions;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public TkQuestionDto getQuestion() {
        return question;
    }

    public void setQuestion(TkQuestionDto question) {
        this.question = question;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public Integer getQuestionSeq() {
        return questionSeq;
    }

    public void setQuestionSeq(Integer questionSeq) {
        this.questionSeq = questionSeq;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

}