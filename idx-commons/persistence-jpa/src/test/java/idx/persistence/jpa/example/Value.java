package idx.persistence.jpa.example;

/**
 * Created by pwalser on 30.01.2017.
 */
public class Value<T> {
    private volatile T value;

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}
