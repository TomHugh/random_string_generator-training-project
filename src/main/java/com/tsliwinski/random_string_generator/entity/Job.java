package com.tsliwinski.random_string_generator.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="JOB")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private long id;
    @Column(name = "CHARSET", nullable = false)
    @NonNull
    private String charset;
    @Column(name = "MIN", nullable = false)
    @NonNull
    private int min;
    @Column(name = "MAX", nullable = false)
    @NonNull
    private int max;
    @Column(name = "QUANTITY", nullable = false)
    @NonNull
    private int quantity;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RESULT_ID", referencedColumnName = "ID")
    private Result result;
}
