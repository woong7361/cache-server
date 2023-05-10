package jongsul.cache.controller;

import jongsul.cache.entity.RequestForm;
import jongsul.cache.entity.RequestRepository;
import jongsul.cache.redis.RequestCache;
import jongsul.cache.redis.RequestCacheRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SaveController {

    private final RequestRepository requestRepository;
    private final RequestCacheRepository requestCacheRepository;

    @PostMapping("/api/save-inquire")
    public void saveInquire(@RequestBody InquireForm inquireForm) {
        RequestForm form = RequestForm.createForm(inquireForm.getRequest(), inquireForm.getResponse());
        requestRepository.save(form);
    }

    @Transactional
    @GetMapping("/api/test/{id}")
    public void test(@PathVariable("id") String id) {
        RequestForm form = RequestForm.createForm("request"+id, "response"+id);
        requestRepository.save(form);

        RequestCache requestCache = new RequestCache(form.getRequest(), form.getResponse(), 3000L);
        requestCacheRepository.save(requestCache);

    }


    @Transactional
    @GetMapping("/api/test2")
    public void test2() {
        log.info(requestRepository.findByRequest("request").get().getResponse());
        log.info(requestCacheRepository.findByRequest("request").get().getResponse());
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    private static class InquireForm {
        private String request;
        private String response;
        
    }
}
