package com.tsliwinski.random_string_generator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="RESULT")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private long id;
    @Column(name = "FILENAME", nullable = false)
    private String filename;
//    @OneToOne(mappedBy = "result", fetch = FetchType.LAZY)
//    private Job job;
}
