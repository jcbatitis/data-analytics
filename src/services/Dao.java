package services;

import java.util.List;

public interface Dao<T> {

    List<T> getAll();

    T get(String id);

    Boolean create(T t);

    Boolean update(T t);

    Boolean delete(T t);
}
