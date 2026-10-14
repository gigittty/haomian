package com.gigittty.haomian.job.once;

import cn.hutool.core.collection.CollUtil;
import com.gigittty.haomian.esdao.QuestionEsDao;
import com.gigittty.haomian.mapper.QuestionMapper;
import com.gigittty.haomian.model.dto.question.QuestionEsDTO;
import com.gigittty.haomian.model.entity.Question;
import com.gigittty.haomian.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 全量同步问题到 es
 *
 * @author <a href="https://github.com/gigittty">gigittty</a>
 */
// todo 取消注释开启任务
//@Component
@Slf4j
public class FullSyncQuestionToEs implements CommandLineRunner {

//    @Resource
//    private QuestionService QuestionService;

    @Resource
    private QuestionMapper QuestionMapper;

    @Resource
    private QuestionEsDao QuestionEsDao;

    @Override
    public void run(String... args) {
        // List<Question> questionList = QuestionService.list();
        List<Question> questionList = QuestionMapper.listQuestionWithDelete();
        if (CollUtil.isEmpty(questionList)) {
            return;
        }
        // 转化为 Es 实体类
        List<QuestionEsDTO> questionEsDTOList = questionList.stream()
                .map(QuestionEsDTO::objToDto)
                .collect(Collectors.toList());
        // 分页插入到 Es
        final int pageSize = 500;
        int total = questionEsDTOList.size();
        log.info("FullSyncQuestionToEs start, total {}", total);
        for (int i = 0; i < total; i += pageSize) {
            int end = Math.min(i + pageSize, total);
            log.info("sync from {} to {}", i, end);
            QuestionEsDao.saveAll(questionEsDTOList.subList(i, end));
        }
        log.info("FullSyncQuestionToEs end, total {}", total);
    }
}
