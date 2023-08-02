package dev.toannv.interview.walk.config;

import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import org.hibernate.dialect.PostgreSQL10Dialect;

import java.sql.Types;

public class PostgreSQL10JsonDialect extends PostgreSQL10Dialect {

    public PostgreSQL10JsonDialect() {
        super();
        this.registerHibernateType(
            Types.OTHER, JsonNodeBinaryType.class.getName()
        );
    }
}
