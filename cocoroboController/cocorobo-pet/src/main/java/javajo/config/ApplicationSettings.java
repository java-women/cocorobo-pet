package javajo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Eriko on 2016/09/25.
 */
@Component
@ConfigurationProperties(prefix = "cocorobo")
@Data
public class ApplicationSettings {
	private String auth;
	private String cognitive;
}
