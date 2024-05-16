package Store.Altex.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class LoginRequest {

    private String email;
    private String password;

    @JsonCreator
    public LoginRequest(@JsonProperty("email") String email,
                               @JsonProperty("password") String password)
 {

        this.email=email;
        this.password=password;
    }
}
