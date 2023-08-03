package dev.toannv.interview.walk.service.ranking;

import dev.toannv.interview.walk.web.api.model.GetRankingByDateResponse;
import dev.toannv.interview.walk.web.api.model.GetRankingCriteria;

public interface IRankingService {

    /**
     * Get daily ranking by given criteria
     *
     * @param criteria {@link GetRankingCriteria} criteria
     * @return {@link GetRankingByDateResponse} response
     */
    GetRankingByDateResponse getDailyRanking(GetRankingCriteria criteria);

}
