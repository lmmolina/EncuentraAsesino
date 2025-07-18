package findassassin.repositorios;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "stabilityAiClient", url = "https://api.stability.ai", configuration = FeignMultipartSupportConfig.class)
public interface StabilityAiClient {
    @PostMapping(value = "/v2beta/stable-image/generate/sd3",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = "image/*")
    byte[] generateImage(
            @RequestHeader("Authorization") String authorization,
            @RequestBody MultiValueMap<String, Object> data
    );
}
