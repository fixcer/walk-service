package dev.toannv.interview.walk.web.rest;

import dev.toannv.interview.walk.web.api.RankingV1ApiDelegate;
import dev.toannv.interview.walk.web.api.model.GetRankingByDateResponse;
import dev.toannv.interview.walk.web.api.model.GetRankingCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class RankingV1Controller implements RankingV1ApiDelegate {

    @Override
    public ResponseEntity<GetRankingByDateResponse> getRankingByDate(GetRankingCriteria criteria) {
        if (Objects.isNull(criteria.getDate())) {
            criteria.setDate(LocalDate.now());
        }

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
