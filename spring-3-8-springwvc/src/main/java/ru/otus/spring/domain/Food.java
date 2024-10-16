package ru.otus.spring.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * Food
 **/
@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "food")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brief", nullable = false, unique = true)
    private String brief;

    @Column(name = "name", nullable = false)
    private String name;

}
