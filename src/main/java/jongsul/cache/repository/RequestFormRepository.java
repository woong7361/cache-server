package jongsul.cache.repository;

import jongsul.cache.entity.RequestForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestFormRepository extends JpaRepository<RequestForm, Long> {
}
