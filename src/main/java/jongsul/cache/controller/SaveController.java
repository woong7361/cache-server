package jongsul.cache.controller;

import jongsul.cache.entity.RequestForm;
import jongsul.cache.repository.RequestFormRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SaveController {

    private final RequestFormRepository requestFormRepository;

    @PostMapping("/api/save-inquire")
    public void saveInquire(@RequestBody InquireForm inquireForm) {
        RequestForm form = RequestForm.createForm(inquireForm.getRequest(), inquireForm.getResponse());
        requestFormRepository.save(form);
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    private static class InquireForm {
        private String request;
        private String response;
        
    }
}
