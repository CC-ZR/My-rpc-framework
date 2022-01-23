package github.rpc.extension;

/**
 * 本质上就是存储一个变量并加锁
 * 防止同时进行修改出现同步问题
 * @param <T>
 */
public class Holder<T> {
    private volatile T value;

    public T get(){
        return value;
    }

    public void set(T value){
        this.value = value;
    }
}
