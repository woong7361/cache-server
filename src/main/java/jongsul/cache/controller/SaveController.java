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

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SaveController {

    private final RequestRepository requestRepository;
    private final RequestCacheRepository requestCacheRepository;

    @PostMapping("/api/save")
    public void saveInquire(@RequestBody InquireForm inquireForm) {
        RequestForm form = RequestForm.createForm(inquireForm.getRequest(), inquireForm.getResponse());
        requestRepository.save(form);

        RequestCache requestCache =
                new RequestCache(inquireForm.getRequest(), inquireForm.getResponse(), 50000L);
        requestCacheRepository.save(requestCache);
    }

    @Transactional
    @GetMapping("/api/find")
    public String find(@RequestBody findForm findForm) {
        Optional<RequestCache> cacheData = requestCacheRepository.findById(findForm.getRequest());
        if (cacheData.isPresent()) {
            return cacheData.get().getResponse();
        }

        Optional<RequestForm> data = requestRepository.findByRequest(findForm.getRequest());
        if (data.isPresent()) {

            RequestCache requestCache =
                    new RequestCache(data.get().getRequest(), data.get().getResponse(), 50000L);
            requestCacheRepository.save(requestCache);
            return data.get().getResponse();
        }

        return "none";
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    private static class InquireForm {
        private String request;
        private String response;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    private static class findForm {
        private String request;
    }
}
