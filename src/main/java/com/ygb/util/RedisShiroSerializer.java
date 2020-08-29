package com.ygb.util;




import com.ygb.config.RedisConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;



import java.io.*;

//自定义序列化类
@Component
public class RedisShiroSerializer  implements RedisSerializer {
    private  static final Logger LOGGER = LoggerFactory.getLogger(RedisConfig.class);

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        byte[] result = null;
        if(o == null){
            return new byte[0];
        }

        try(
                ByteArrayOutputStream byteStrem = new ByteArrayOutputStream(128);
                ObjectOutputStream outputStream = new ObjectOutputStream(byteStrem)
        ){
            if(!(o instanceof Serializable)){
                throw new IllegalArgumentException(RedisSerializer.class.getSimpleName() +
                        "requires a Serializable payload  but received an object of type [" +
                        o.getClass().getName() + "]");
            }
            outputStream.writeObject(o);
            outputStream.flush();
            result = byteStrem.toByteArray();

        } catch (IOException e) {
            LOGGER.error("Failed to serialize",e);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        Object result = null;
        if(bytes == null || bytes.length == 0){
            return null;
        }
        try(
                ByteArrayInputStream byteArrayInputStrea = new ByteArrayInputStream(bytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStrea);
        ){
            result = objectInputStream.readObject();
        }catch (Exception e){
            LOGGER.error("Failed to deserialize",e);
            throw new RuntimeException();
        }

        return result;
    }
}

