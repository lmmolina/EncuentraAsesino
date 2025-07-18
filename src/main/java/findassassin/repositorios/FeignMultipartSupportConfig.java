package findassassin.repositorios;

import feign.form.spring.SpringFormEncoder;
import feign.codec.Encoder.Default;
import feign.codec.Encoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FeignMultipartSupportConfig {
    @Bean
    public Encoder feignFormEncoder() {
        return new SpringFormEncoder();
    }
}
