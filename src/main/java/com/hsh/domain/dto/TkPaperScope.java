package com.hsh.domain.dto;

import java.io.Serializable;

/**
 * @author ibm
 */
public class TkPaperScope  implements Serializable{
    private static final long serialVersionUID = -3336196982476939569L;
    private Integer id;
    private Integer paperId;
    private Integer scope;
    private String scopeName;

    private Integer disabled;

    public String getScopeName() {
        return scopeName;
    }

    public void setScopeName(String scopeName) {
        this.scopeName = scopeName;
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

    public Integer getScope() {
        return scope;
    }

    public void setScope(Integer scope) {
        this.scope = scope;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }
}