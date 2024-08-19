package ru.otr.learn.configuration;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "database")
public class DbProperties {
	String dialect;
	String url;
	String user;
	String password;
	String driver;
	Map<String, Object> properties;
	List<PoolProperties> pools;

	public DbProperties() {
	}

	@Data
	public static class PoolProperties {
		int poolSize;
		int maxPoolSize;
		int minPoolSize;
		long idleTimeout;
		int maxIdleTime;
	}
}
