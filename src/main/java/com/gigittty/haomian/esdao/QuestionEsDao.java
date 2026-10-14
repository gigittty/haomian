package com.gigittty.haomian.esdao;

import com.gigittty.haomian.model.dto.question.QuestionEsDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * 题目 ES 操作
 *
 * @author <a href="https://github.com/gigittty">gigittty</a>
 */

public interface QuestionEsDao extends ElasticsearchRepository<QuestionEsDTO, Long> {
    List<QuestionEsDTO> findByQuestionId(Long questionId);
}
