package ksch.visit.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

// TODO Move class into commons
@Configuration
public class JacksonConfiguration {

    @Bean
    @Primary
    public ObjectMapper objectMapper2() {
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(LocalDateTimeSerializer.INSTANCE);
        var result = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .registerModule(module)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        ;
        return result;
    }

//    @Bean
//    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
//        return new Jackson2ObjectMapperBuilder()
//                .serializers(LocalDateTimeSerializer.INSTANCE)
//                .serializationInclusion(JsonInclude.Include.NON_NULL);
//    }
}
