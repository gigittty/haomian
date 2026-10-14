package com.gigittty.haomian.job.cycle;

import cn.hutool.core.collection.CollUtil;
import com.gigittty.haomian.esdao.QuestionEsDao;
import com.gigittty.haomian.mapper.QuestionMapper;
import com.gigittty.haomian.model.dto.question.QuestionEsDTO;
import com.gigittty.haomian.model.entity.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 增量同步问题到 es
 *
 * @author <a href="https://github.com/gigittty">gigittty</a>
 */
// todo 取消注释开启任务
@Component
@Slf4j
public class IncSyncQuestionToEs {
    @Resource
    private QuestionMapper QuestionMapper;

    @Resource
    private QuestionEsDao QuestionEsDao;

    /**
     * 每分钟执行一次
     */
    @Scheduled(fixedRate = 60 * 1000)
    public void run() {
        // 查询近 5 分钟内的数据
        Date fiveMinutesAgoDate = new Date(System.currentTimeMillis() - 5 * 60 * 1000L);
        List<Question> questionList = QuestionMapper.listQuestionWithDelete(fiveMinutesAgoDate);
        if (CollUtil.isEmpty(questionList)) {
            log.info("no inc Question");
            return;
        }
        List<QuestionEsDTO> questionEsDTOList = questionList.stream()
                .map(QuestionEsDTO::objToDto)
                .collect(Collectors.toList());
        final int pageSize = 500;
        int total = questionEsDTOList.size();
        log.info("IncSyncQuestionToEs start, total {}", total);
        for (int i = 0; i < total; i += pageSize) {
            int end = Math.min(i + pageSize, total);
            log.info("sync from {} to {}", i, end);
            QuestionEsDao.saveAll(questionEsDTOList.subList(i, end));
        }
        log.info("IncSyncQuestionToEs end, total {}", total);
    }
}
