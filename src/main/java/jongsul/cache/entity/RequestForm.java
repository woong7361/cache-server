package jongsul.cache.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RequestForm {

//    @Id @GeneratedValue
//    @Column(name = "requst_form_id")
//    private Long id;

    @Id
    private String request;

    private String response;


    public RequestForm(String request, String response) {
        this.request = request;
        this.response = response;
    }

    public static RequestForm createForm(String request, String response) {
        return new RequestForm(request, response);
    }
}
