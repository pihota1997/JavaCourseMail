package commons;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface DAO<T> {

    @NotNull T get(String id);

    @NotNull List<T> all();

    void create(@NotNull T entity);

    void update(@NotNull T entity);

    void delete(@NotNull T entity);
}
