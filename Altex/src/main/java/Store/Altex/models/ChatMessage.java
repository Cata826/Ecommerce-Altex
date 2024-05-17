package Store.Altex.models;
import java.util.Date;
import lombok.*;

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

}