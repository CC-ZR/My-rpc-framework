package github.rpc.utils;

public class RuntimeUtil {
    /**
     * 获取cpu核心数
     */
    public static int cpus(){
        return Runtime.getRuntime().availableProcessors();
    }
}
