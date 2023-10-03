package services;

import java.util.List;

public interface Dao<T> {

    List<T> getAll();

    T get(String id);

    void create(T t);

    void update(T t);

    void delete(T t);
}
