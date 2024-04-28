//package ru.murza.foodmodel.models;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotEmpty;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
//@Entity(name = "State")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "state", schema = "restaurant_schema", catalog = "postgres")
//public class State {
//    @Id
//    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(
//            name = "title",
//            nullable = false
//    )
//    @NotEmpty(message = "Not empty status name!")
//    private String title;
//
//    @OneToMany(mappedBy = "state")
//    @JsonIgnore
//    private List<Invoice> invoices;
//}
