package jongsul.cache.redis;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RequestCacheRepository extends CrudRepository<RequestCache,String> {
    // @Indexed 사용한 필드만 가능
    Optional<RequestCache> findByRequest(String username);
}