package com.hsh.controller;

import com.google.common.collect.Lists;
import com.hsh.domain.dto.TkPaperQuestion;
import com.hsh.domain.dto.TkQuestionDto;
import com.hsh.domain.dto.TkQuestionOption;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.List;

/**
 * @author hushihai
 * @version V1.0, 2019/3/13
 */
@RestController
@RequestMapping(value = "/tkQuestion")
@SuppressWarnings("unchecked")
public class TkQuestionController {

    @PostMapping(value = "/splitToOptions")
    public List<TkPaperQuestion> splitToOptions(@RequestBody String split){
        split = StringUtils.trim(split);
        List<TkPaperQuestion> paperQuestions = Lists.newArrayList();
        //从当前系统中获取换行符"
        String[] questions = split.split("(" + "\r\n" + ")" + "{2,}");
        for (String question : questions) {
            TkPaperQuestion paperQuestion = new TkPaperQuestion();
            TkQuestionDto questionDto = new TkQuestionDto();
            String[] questionStrArr = question.split("\r\n");
            List<TkQuestionOption> options = Lists.newArrayList();
            for (int i = 0; i < questionStrArr.length; i++) {
                if(i == 0){
                    String title = questionStrArr[i];
                    questionDto.setTitle(title.trim());
                }else{
                    TkQuestionOption option = new TkQuestionOption();
                    option.setOptionContent(questionStrArr[i].trim());
                    option.setSeq(i);
                    option.setOptionImg(StringUtils.EMPTY);
                    options.add(option);
                }
            }
            //设置试题基础默认属性
            questionDto.setOptions(options);
            questionDto.setCategory(1);
            questionDto.setTitleImg(StringUtils.EMPTY);
            questionDto.setAnalysisImg(StringUtils.EMPTY);
            questionDto.setAnalysis(StringUtils.EMPTY);
            questionDto.setIsMaterial(0);
            questionDto.setDisabled(0);
            questionDto.setYear(Calendar.getInstance().get(Calendar.YEAR));
            //设置试题试卷关联关系基础默认属性
            paperQuestion.setQuestion(questionDto);
            paperQuestion.setSeq(0);
            paperQuestion.setScore(10);
            paperQuestion.setDisabled(0);
            paperQuestion.setType(3);
            paperQuestions.add(paperQuestion);
        }
        return paperQuestions;
    }
}
