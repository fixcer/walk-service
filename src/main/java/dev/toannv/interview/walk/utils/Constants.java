package dev.toannv.interview.walk.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;


@UtilityClass
public final class Constants {

    public static final String TRACKING_HEADER = "X-Correlation-Id";
    public static final String SYSTEM_ACCOUNT = "system";

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class SortField {
        public static final String UPDATED_AT = "updatedAt";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Role {
        public static final String ADMIN = "ADMIN";
        public static final String USER = "USER";
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
        public static final String STEP_ARCHIVE_TASK_EXECUTOR = "stepArchiveTaskExecutor";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class RedisLock {
        public static final String DAILY_RANKING = "daily-ranking";
        public static final String WEEKLY_RANKING = "weekly-ranking";
        public static final String MONTHLY_RANKING = "monthly-ranking";
    }

}