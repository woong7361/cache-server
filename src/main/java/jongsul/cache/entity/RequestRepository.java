package jongsul.cache.entity;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<RequestForm, String> {
    Optional<RequestForm> findByRequest(String request);
}