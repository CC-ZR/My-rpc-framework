package github.rpc.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * 本类用于从配置文件中读取配置属性
 */
@Slf4j
public class PropertiesFileUtil {
    private PropertiesFileUtil() {
    }

    public static Properties readPropertiesFile(String fileName) {
        URL url = Thread.currentThread().getContextClassLoader().getResource("");
        String rpcConfigPath = "";
        // 说明存在资源文件夹
        if (url != null) {
            rpcConfigPath = url.getPath() + fileName;
        }
        // 将文件中的属性读出并返回
        Properties properties = null;
        try (InputStreamReader inputStreamReader = new InputStreamReader(
                new FileInputStream(rpcConfigPath), StandardCharsets.UTF_8)){
            properties = new Properties();
            properties.load(inputStreamReader);
        }catch (IOException e){
            log.error("occur exception when read properties file [{}]", fileName);
        }
        return properties;
    }
}
