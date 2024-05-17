package Store.Altex.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "`order`")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;




}