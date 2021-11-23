package commons;

import org.jetbrains.annotations.NotNull;
import org.jooq.Record;
import org.jooq.Result;

public interface DAO<T extends Record> {

    @NotNull T get(String id);

    @NotNull Result<T> all();

    void create(@NotNull T entity);

    void update(@NotNull T entity);

    void delete(@NotNull T entity);
}
