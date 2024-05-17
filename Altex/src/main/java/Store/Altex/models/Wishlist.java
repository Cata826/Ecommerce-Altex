package Store.Altex.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
public class Wishlist {

    @Id
    @GeneratedValue
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

}
