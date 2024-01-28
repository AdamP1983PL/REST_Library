package com.rest_library.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "book_borrowing")
public class BooksBorrowingStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @ManyToMany
    @JoinTable(name = "borrowings_books",
            joinColumns = {@JoinColumn(name = "fk_borrowing")},
            inverseJoinColumns = {@JoinColumn(name = "fk_individualBook")})
    private List<IndividualBook> individualBooks = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "fk_reader")
    private Reader reader;

    @CreatedDate
    @Column(name = "borrowing_date", nullable = false)
    private LocalDate borrowingDate;

    @Column(name = "return_date", nullable = false)
    private LocalDate returnDate;

    //    todo CascadeType
}
