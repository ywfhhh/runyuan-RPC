package com.ywf.rpc.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import cn.hutool.setting.yaml.YamlUtil;

import java.util.Map;

public class ConfigUtils {

    /**
     * 加载配置对象，支持不同的文件类型和环境
     *
     * @param tClass      配置类
     * @param prefix      配置前缀
     * @param environment 环境
     * @param fileType    文件类型（properties 或 yaml）
     * @param <T>         配置类类型
     * @return 配置对象
     * @throws IllegalArgumentException 如果文件类型不支持
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix, String environment, String fileType) {
        // 动态构建配置文件路径
        String configFile = buildConfigFilePath(environment, fileType);

        if (StrUtil.isBlank(fileType) || "properties".equalsIgnoreCase(fileType)) {
            return loadConfigProp(tClass, prefix, configFile);
        } else if ("yaml".equalsIgnoreCase(fileType)) {
            return loadConfigYaml(tClass, prefix, configFile);
        } else {
            throw new IllegalArgumentException("不支持的文件类型: " + fileType);
        }
    }

    /**
     * 构建配置文件路径
     *
     * @param environment 环境
     * @param fileType    文件类型（properties 或 yaml）
     * @return 配置文件路径
     */
    private static String buildConfigFilePath(String environment, String fileType) {
        StringBuilder configFileBuilder = new StringBuilder("application");
        if (StrUtil.isNotBlank(environment)) {
            configFileBuilder.append("-").append(environment);
        }
        if (StrUtil.isNotBlank(fileType)) {
            configFileBuilder.append(".").append(fileType); // 动态追加文件类型（properties/yaml）
        }else{
            configFileBuilder.append(".properties");
        }
        return configFileBuilder.toString();
    }

    /**
     * 加载 properties 文件配置
     *
     * @param tClass     配置类
     * @param prefix     配置前缀
     * @param configFile 配置文件路径
     * @param <T>        配置类类型
     * @return 配置对象
     */
    private static <T> T loadConfigProp(Class<T> tClass, String prefix, String configFile) {
        Props props = new Props(configFile);
        return props.toBean(tClass, prefix);
    }

    /**
     * 加载 yaml 文件配置
     *
     * @param tClass     配置类
     * @param prefix     配置前缀
     * @param configFile 配置文件路径
     * @param <T>        配置类类型
     * @return 配置对象
     */
    private static <T> T loadConfigYaml(Class<T> tClass, String prefix, String configFile) {
        // 加载 YAML 文件内容
        ClassPathResource resource = new ClassPathResource(configFile);

        // 使用 Hutool YamlUtil 加载 YAML 数据
        Map<String, Object> yamlData = YamlUtil.load(resource.getStream(), Map.class);

        // 如果 YAML 中的根节点有 prefix 配置，则提取并转换成目标对象
        if (yamlData.containsKey(prefix)) {
            Map<String, Object> configData = (Map<String, Object>) yamlData.get(prefix);
            return BeanUtil.mapToBean(configData, tClass, true);
        } else {
            return null;
        }
    }
}
