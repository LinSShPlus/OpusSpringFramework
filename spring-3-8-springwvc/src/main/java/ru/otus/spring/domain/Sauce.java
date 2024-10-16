package ru.otus.spring.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * Sauce
 **/
@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sauce")
public class Sauce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brief", nullable = false, unique = true)
    private String brief;

    @Column(name = "name", nullable = false)
    private String name;

}
