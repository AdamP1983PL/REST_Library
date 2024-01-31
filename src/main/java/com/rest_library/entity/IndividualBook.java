package com.rest_library.entity;

import com.rest_library.enums.Status;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "individual_books")
public class IndividualBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "fk_title")
    private Title title;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @ManyToMany(mappedBy = "individualBooks",
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private final List<BooksBorrowingStats> booksBorrowingStats = new ArrayList<>();

//    @ManyToOne
//    @JoinColumn(name = "fk_books_borrowing_stats")
//    private BooksBorrowingStats booksBorrowingStats;


}
