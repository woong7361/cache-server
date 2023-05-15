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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SaveController {

    private final RequestRepository requestRepository;
    private final RequestCacheRepository requestCacheRepository;

    @Transactional
    @PostMapping("/api/save")
    public void saveInquire(@RequestBody InquireForm inquireForm) {
        RequestForm form = RequestForm.createForm(inquireForm.getRequest(), inquireForm.getResponse());
        requestRepository.save(form);

        RequestCache requestCache =
                new RequestCache(inquireForm.getRequest(), inquireForm.getResponse(), 50000L);
        requestCacheRepository.save(requestCache);
    }

    @GetMapping("/api/findall")
    public responseForm findAll() {
        List<String> all = requestRepository.findAll().stream().map(a -> a.getRequest()).toList();
        responseForm responseForm = new responseForm(all);
        return responseForm;
    }

    @GetMapping("/api/find")
    public String find(@RequestBody findForm findForm) {
        Optional<RequestCache> cache = requestCacheRepository.findById(findForm.getRequest());
        if (cache.isPresent()) {
            return cache.get().getResponse();
        }
        Optional<RequestForm> result = requestRepository.findByRequest(findForm.getRequest());
        if (result.isPresent()) {
            return result.get().getRequest();
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

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    private static class responseForm {
        private List<String> responses;
    }
}
