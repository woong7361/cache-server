package jongsul.cache.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash("hash")
@AllArgsConstructor
@Builder
public class RequestCache {
//    @Id
//    private String id;

    @Indexed // 필드 값으로 데이터 찾을 수 있게 하는 어노테이션(findByAccessToken)
    @Id
    private String request;

    private String response;

    @TimeToLive
    private Long expiration; // seconds


    public static RequestCache createRequestCache(String request, String response, Long seconds){
        return RequestCache.builder()
                .request(request)
                .response(response)
                .expiration(seconds)
                .build();
    }
}
