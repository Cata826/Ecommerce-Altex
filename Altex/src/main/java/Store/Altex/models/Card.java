package Store.Altex.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;



    @Getter
    @Setter
    @Entity
    public class Card {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String fullname;

        @Column(nullable = false, length = 12)
        @Pattern(regexp = "\\d{12}", message = "Numărul de card trebuie să aibă 12 cifre")
        private String number;


        @Column(nullable = false)
        private int cvv;

        @Column(nullable = false)
        @Pattern(regexp = "^(0[1-9]|1[0-2])/(\\d{2})$", message = "Formatul expirării trebuie să fie MM/YY")
        private String expiration;



//        @ManyToOne(fetch = FetchType.LAZY)
//        @JoinColumn(name = "user_id")
//        @JsonIgnore
//        private User user;


        private String address;
        private String city;
        private String country;
        private String zipcode;
    }


