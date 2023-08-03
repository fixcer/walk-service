package dev.toannv.interview.walk.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;


@UtilityClass
public final class Constants {

    public static final String TRACKING_HEADER = "X-Correlation-Id";
    public static final String SYSTEM_ACCOUNT = "system";
    public static final String CLEAN_PREVIOUS_MONTH_DATA = "CLEAN_PREVIOUS_MONTH_DATA";

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class SortField {
        public static final String UPDATED_AT = "updatedAt";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class CacheName {
        public static final String WEEKLY_STEP = "weekly-step";
        public static final String MONTHLY_STEP = "monthly-step";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Character {
        public static final String DASH = "-";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class AsyncTask {
        public static final String RECORD_STEP_TASK_EXECUTOR = "recordStepTaskExecutor";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class RedisLock {
        public static final String DAILY_RANKING = "daily-ranking";
        public static final String WEEKLY_RANKING = "weekly-ranking";
        public static final String MONTHLY_RANKING = "monthly-ranking";
        public static final String CLEAN_PREVIOUS_MONTH_DATA = "clean-previous-month-data";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class QUEUE {
        public static final String RECORD_STEP_QUEUE = "record-step-queue";
    }

}