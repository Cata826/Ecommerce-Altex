package Store.Altex.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
    @JsonIgnore
    private Set<Product> products = new HashSet<>();

}
