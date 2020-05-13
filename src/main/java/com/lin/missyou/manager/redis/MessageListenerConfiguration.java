package com.lin.missyou.manager.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/5/10
 */
@Configuration
public class MessageListenerConfiguration {

    @Value("${spring.redis.listen-pattern}")
    public String pattern;

    @Bean
    public RedisMessageListenerContainer listenerContainer(RedisConnectionFactory factory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        Topic keyEventTopic = new PatternTopic(this.pattern);
        container.addMessageListener(new KeyEventMessageListener(), keyEventTopic);
        return container;
    }
}
