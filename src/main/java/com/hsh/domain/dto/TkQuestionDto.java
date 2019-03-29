package com.hsh.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author hushihai
 * @version V1.0, 2019/3/13
 */
@Getter
@Setter
public class TkQuestionDto extends TkQuestion implements Serializable {
    private static final long serialVersionUID = -5954073366308676935L;

    private List<TkQuestionOption> options;

    private List<TkQuestionArea> areas;

    private List<TkQuestionJob> jobs;

    private List<TkQuestionDto> materialQuestions;

}
