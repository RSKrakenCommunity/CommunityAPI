package kraken.plugin.api;

/**
 * An action which takes 1 parameter.
 */
public interface Action1<T> {

    void call(T t);

}
