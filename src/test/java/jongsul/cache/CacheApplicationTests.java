package jongsul.cache;

import jakarta.persistence.EntityManager;
import jongsul.cache.entity.RequestForm;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class CacheApplicationTests {

	@Autowired
	private EntityManager em;


	@Test
	@Transactional
	void contextLoads() {
		RequestForm form = RequestForm.createForm("req", "res");
		em.persist(form);
		em.flush();
		em.clear();

		RequestForm requestForm = em.find(RequestForm.class, 1L);
		Assertions.assertThat(requestForm.getRequest()).isEqualTo("req");
	}

}
