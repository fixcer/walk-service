package dev.toannv.interview.walk.repository;

import dev.toannv.interview.walk.domain.StepArchive;
import dev.toannv.interview.walk.repository.base.IWalkRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface IStepArchiveRepository extends IWalkRepository<StepArchive, Long> {

    @Modifying
    @Query(value = "insert into step_archives (step_archive_id, step_id, user_id, steps, date) values (?, ?, ?, ?, ?) on conflict (step_id) do update set steps = ?, version = step_archives.version + 1, updated_at = current_timestamp, updated_by = 'script';", nativeQuery = true)
    void upsertStepArchive(long stepArchiveId, long stepId, long userId, int steps, Date date, int stepsConflict);

}
