package dev.toannv.interview.walk.service.ranking;

import com.querydsl.jpa.impl.JPAQuery;
import dev.toannv.interview.walk.domain.DailyRanking;
import dev.toannv.interview.walk.domain.QDailyRanking;
import dev.toannv.interview.walk.exception.ErrorCode;
import dev.toannv.interview.walk.exception.ValidationException;
import dev.toannv.interview.walk.repository.IDailyRankingRepository;
import dev.toannv.interview.walk.utils.CommonUtils;
import dev.toannv.interview.walk.web.api.model.GetRankingByDateResponse;
import dev.toannv.interview.walk.web.api.model.GetRankingCriteria;
import dev.toannv.interview.walk.web.api.model.Paging;
import dev.toannv.interview.walk.web.api.model.RankingItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class RankingService implements IRankingService {

    private final IDailyRankingRepository dailyRankingRepository;

    @Override
    public GetRankingByDateResponse getDailyRanking(final GetRankingCriteria criteria) {
        final JPAQuery<DailyRanking> query = new JPAQuery<DailyRanking>()
                .from(QDailyRanking.dailyRanking)
                .orderBy(QDailyRanking.dailyRanking.steps.desc(), QDailyRanking.dailyRanking.stepId.asc())
                .limit(criteria.getSize() + 1L);

        if (StringUtils.isBlank(criteria.getCursor())) {
            return findAllByCriteria(criteria.getSize(), query);
        }

        var pair = CommonUtils.cursorToStepsAndStepId(criteria.getCursor());
        final var steps = pair.getLeft();
        final var stepId = pair.getRight();
        if (0 == steps || 0L == stepId) {
            log.warn("Invalid cursor: {}", criteria.getCursor());
            throw new ValidationException(String.format("Invalid cursor: %s", criteria.getCursor()), ErrorCode.INVALID_CURSOR);
        }

        query.where(QDailyRanking.dailyRanking.steps.lt(steps)
                .or(QDailyRanking.dailyRanking.steps.eq(steps)
                        .and(QDailyRanking.dailyRanking.stepId.gt(stepId))));
        return findAllByCriteria(criteria.getSize(), query);
    }

    private GetRankingByDateResponse findAllByCriteria(final int size, final JPAQuery<DailyRanking> query) {
        final var entities = dailyRankingRepository.findAll(query);

        if (CollectionUtils.isEmpty(entities)) {
            return new GetRankingByDateResponse()
                    .date(LocalDate.now())
                    .paging(new Paging()
                            .size(size)
                            .cursor(null)
                            .hasNext(false))
                    .ranking(Collections.emptyList());
        }

        if (entities.size() > size) {
            var lastEntityReality = entities.get(entities.size() - 2);
            return new GetRankingByDateResponse()
                    .date(LocalDate.now())
                    .paging(new Paging()
                            .size(size)
                            .cursor(CommonUtils.toCursor(lastEntityReality.getSteps(), lastEntityReality.getStepId()))
                            .hasNext(true))
                    .ranking(IntStream.range(0, entities.size() - 1)
                            .mapToObj(idx -> {
                                final var entity = entities.get(idx);
                                return new RankingItem()
                                        .userId(entity.getUserId())
                                        .steps(entity.getSteps());
                            })
                            .toList());
        }

        return new GetRankingByDateResponse()
                .date(LocalDate.now())
                .paging(new Paging()
                        .size(size)
                        .cursor(null)
                        .hasNext(false))
                .ranking(entities.stream().map(dailyRanking -> new RankingItem()
                                .userId(dailyRanking.getUserId())
                                .steps(dailyRanking.getSteps()))
                        .toList());
    }
}
