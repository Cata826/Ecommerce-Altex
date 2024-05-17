package Store.Altex.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String short_description;
    private String long_description;
    private double pret;

    @Column(name = "image_url")
    private String imageUrl;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Wishlist> wishlist;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Cart> order;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Review> review;
    private int stars;
    private String image;
    private int quantity;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "product_category",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private Set<Category> categories = new HashSet<>();


}
