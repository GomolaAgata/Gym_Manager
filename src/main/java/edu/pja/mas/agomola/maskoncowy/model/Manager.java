package edu.pja.mas.agomola.maskoncowy.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manager extends Employee{

    @OneToMany
    @JoinColumn(name = "manager_id")
    private List<Employee> employeesSupervised = new ArrayList<>();

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;

}
