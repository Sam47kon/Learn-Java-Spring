package ru.otr.learn.configuration;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "database")
@Value
public class DbProperties {
	String dialect;
	String url;
	String user;
	String password;
	String driver;
	Map<String, Object> properties;
	List<PoolProperties> pools;

	@Value
	public static class PoolProperties {
		int poolSize;
		int maxPoolSize;
		int minPoolSize;
		long idleTimeout;
		int maxIdleTime;
	}
}
