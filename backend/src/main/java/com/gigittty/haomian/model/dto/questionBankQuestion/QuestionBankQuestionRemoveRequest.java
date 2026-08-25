package com.gigittty.haomian.model.dto.questionBankQuestion;

import com.gigittty.haomian.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 移除题库题目关联请求
 *
 * @author <a href="https://github.com/gigittty">gigittty</a>
 */
@Data
public class QuestionBankQuestionRemoveRequest implements Serializable {
    /**
     * 题库 id
     */
    private Long questionBankId;

    /**
     * 题目 id
     */
    private Long questionId;

    private static final long serialVersionUID = 1L;
}