package Store.Altex.models;

import java.util.Date;

import Store.Altex.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Getter
@Setter
public class ChatMessage {

    private String nickname;
    private String content;
    private Date timestamp;

    // Constructors, getters, and setters
}