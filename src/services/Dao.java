package services;

public interface Dao<T> {

    T get(String[] params);

    void create(T t);

    void update(T t, String[] params);

    void delete(T t);
}
