package be.tutak.jcr.api;

import java.util.List;
import java.util.Optional;

/**
 * Created by maarten on 28.09.16.
 */
public interface CrudRepository<T> {
    void create(T item);
    Optional<T> read(String id);
    List<T> read();
    void update(T item);
    void delete(String id);
}