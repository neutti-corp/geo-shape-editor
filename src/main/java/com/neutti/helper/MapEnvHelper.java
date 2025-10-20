package com.neutti.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.file.Paths;

@Component
public class MapEnvHelper {

    @Value("${application.map.ngii.cache.path}")
    private String cachePath;
	@Resource
	Environment env;
	/**
	 * 외부접속 - Neutti
	 * @return
	 */
	public boolean isOutside() {
		String dburl = env.getProperty("spring.datasource.url");
		return dburl == null ? false : dburl.contains("14.36.12.218");
	}
	/**
	 * 운영
	 * @return
	 */
	public boolean isProd() {
		String active = env.getProperty("spring.profiles.active");
		return active == null ? false : active.equals("prod");
	}

    public boolean isDocker() {
        String active = env.getProperty("spring.profiles.active");
        return active == null ? false : active.equals("docker");
    }

	/**
	 * 개발
	 * @return
	 */
	public boolean isDev() {
		String active = env.getProperty("spring.profiles.active");
		return active == null ? false : active.equals("dev");
	}
	/**
	 * Get Application Property
	 * @param key
	 * @return
	 */
	public String getApplicationProperty(String key) {
		String value = env.getProperty("application." + key);
		return value;
	}

    public String getGeojsonCachePath() {
        String absolutePath = Paths.get(cachePath.replaceFirst("^~", System.getProperty("user.home").replaceAll("\\\\","/"))).toAbsolutePath().toString();
        if(!absolutePath.endsWith("/")){
            absolutePath = absolutePath + "/";
        }
        absolutePath += "geojson/";
        return absolutePath;
    }
}
