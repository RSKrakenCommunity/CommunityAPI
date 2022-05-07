package kraken.plugin.api;

public interface Filter<T> {

    boolean accept(T t);

}
