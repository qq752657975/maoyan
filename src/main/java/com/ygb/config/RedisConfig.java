package com.ygb.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ygb.dbBean.RedisParams;
import com.ygb.util.RedisMessageListener;
import org.omg.CORBA.Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class RedisConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfig.class);
    @Autowired
    private MessageListener redisMessageListener;

    private Set<RedisNode> getRedisNodes(RedisParams redisParams){
        Set<RedisNode> set = new HashSet<>();
        String[] host = redisParams.getHostName().split(",");
        String[] port = redisParams.getPort().split(",");
        for(int i = 0;i<host.length;i++){
            RedisNode node = new RedisNode(host[i],Integer.parseInt(port[i]));
            set.add(node);
        }
        return set;
    }

    /**
     * 获取哨兵模式的连接设置
     */

    private RedisSentinelConfiguration getSentinelConfig(RedisParams redisParams){
        RedisSentinelConfiguration config = new RedisSentinelConfiguration();
        config.setMaster(redisParams.getMaster()); //设置master名称
        config.setSentinels(getRedisNodes(redisParams));  //设置哨兵模式节点地址
        return config;
    }


    /**
     * 获取集群模式的连接配置
     */
    private RedisClusterConfiguration getClisterConfig(RedisParams redisParams){
        RedisClusterConfiguration config = new RedisClusterConfiguration();
        config.setClusterNodes(getRedisNodes(redisParams)); //设置集群节点
        return config;
    }


    /**
     * 单机模式
     */
    private RedisStandaloneConfiguration getStandaloneConfig(RedisParams redisParams){
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisParams.getHostName());
        redisStandaloneConfiguration.setPort(Integer.parseInt(redisParams.getPort()));
        return redisStandaloneConfiguration;
    }

    /*
        获取jedis的连接工厂(获取连接池)
     */
    private JedisConnectionFactory jedisConnectionFactory(RedisParams redisParams){
        if(redisParams.getConnectType() == 1){
            return new JedisConnectionFactory(getStandaloneConfig(redisParams));
        }else if(redisParams.getConnectType() ==2) {
            return new JedisConnectionFactory(getStandaloneConfig(redisParams));
        }else if(redisParams.getConnectType() ==3) {
            return new JedisConnectionFactory(getStandaloneConfig(redisParams));
        }else {
            throw new RuntimeException("unknow connection factory");
        }
    }

    /**
     * 配置jedis连接工厂
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedisParams redisParams){
        LOGGER.info(redisParams.toString());
        //redis.properties 单机 哨兵 集群
        JedisConnectionFactory fac = jedisConnectionFactory(redisParams);
        fac.getPoolConfig().setMaxIdle(redisParams.getMaxIdle());
        fac.getPoolConfig().setMaxTotal(redisParams.getMaxTotal());
        fac.getPoolConfig().setMaxWaitMillis(redisParams.getMaxWaitMillis());
        fac.getPoolConfig().setMinEvictableIdleTimeMillis(redisParams.getMinEvictableIdleTimeMillis());
        fac.getPoolConfig().setNumTestsPerEvictionRun(redisParams.getNumTestsPerEvictionRun());
        fac.getPoolConfig().setTimeBetweenEvictionRunsMillis(redisParams.getTimeBetweenEvictionRunsMillis());
        fac.getPoolConfig().setTestOnBorrow(redisParams.isTestOnBorrow());
        fac.getPoolConfig().setTestWhileIdle(redisParams.isTestWhileIdle());
        return fac;
    }


    /**
     *生成redis模板，以spring的方式操作redis
     */
    @Bean
    public RedisTemplate secondKillerRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate redis = new StringRedisTemplate();
        redis.setConnectionFactory(redisConnectionFactory);
        redis.setKeySerializer(new StringRedisSerializer());
        redis.setHashKeySerializer(new StringRedisSerializer());
        redis.setValueSerializer(new StringRedisSerializer());
        redis.setHashValueSerializer(new StringRedisSerializer());
        //等待参数，防止异常
        redis.afterPropertiesSet();
        return redis;
    }
    /**
     * 生成redis模板以json的格式存入
     */
    @Bean
    @Primary //告诉spring 在犹豫的时候优先选择哪一个具体的实现。
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Object,Object> redis = new RedisTemplate<>();
        redis.setConnectionFactory(redisConnectionFactory);
        redis.setKeySerializer(new StringRedisSerializer());
        redis.setHashKeySerializer(new StringRedisSerializer());
//        redis.setValueSerializer(new RedisShiroSerializer());
//        redis.setHashValueSerializer(new RedisShiroSerializer());
        //以json的格式存入
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(objectMapper);
        redis.setHashValueSerializer(serializer);
        redis.setValueSerializer(serializer);
        redis.afterPropertiesSet();
        return redis;
    }
    @Bean
    public RedisTemplate<String,Object>  stringRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> redis = new RedisTemplate<>();
        redis.setConnectionFactory(redisConnectionFactory);
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        redis.setKeySerializer(stringSerializer );
        redis.setValueSerializer(stringSerializer );
        redis.setHashKeySerializer(stringSerializer );
        redis.setHashValueSerializer(stringSerializer );
        redis.afterPropertiesSet();
        return redis;
    }

    @Bean
    public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer(){
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<Object>(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(objectMapper);

        return serializer;
    }
    /**
     * 消息监听器
     */
    @Bean
    MessageListenerAdapter messageListenerAdapter(RedisMessageListener redisMessageListener, Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer){
        //消息接收者以及对应的默认处理方法
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(redisMessageListener, "receiveMessage");
        //消息的反序列化方式
        messageListenerAdapter.setSerializer(jackson2JsonRedisSerializer);

        return messageListenerAdapter;
    }
    /**
     * message listener container
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory
            , MessageListenerAdapter messageListenerAdapter){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //添加消息监听器
        container.addMessageListener(messageListenerAdapter, new PatternTopic("__keyevent@0__:expired"));

        return container;
    }
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }

}
