package com.rest_library.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "readers")
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "starting_date", nullable = false)
    @CreatedDate
    private LocalDate startingDate;

    @OneToOne(mappedBy = "reader",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    private BooksBorrowingStats booksBorrowingStats;

}
