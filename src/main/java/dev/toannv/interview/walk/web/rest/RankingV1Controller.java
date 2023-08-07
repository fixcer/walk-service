package dev.toannv.interview.walk.web.rest;

import dev.toannv.interview.walk.service.ranking.IRankingService;
import dev.toannv.interview.walk.service.step.IStepService;
import dev.toannv.interview.walk.web.api.RankingV1ApiDelegate;
import dev.toannv.interview.walk.web.api.model.GetRankingByDateResponse;
import dev.toannv.interview.walk.web.api.model.GetRankingCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class RankingV1Controller implements RankingV1ApiDelegate {

    private final IRankingService rankingService;
    private final IStepService stepService;

    @Override
    public ResponseEntity<GetRankingByDateResponse> getRankingByCurrentDate(final GetRankingCriteria criteria) {
        return ResponseEntity.ok(rankingService.getDailyRanking(criteria));
    }

    @Override
    public ResponseEntity<Void> clearDailyRankingCache() {
        stepService.refreshDailyRanking();
        return ResponseEntity.ok().build();

    }

}
