package edu.pja.mas.agomola.maskoncowy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Employee{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @PastOrPresent
    private LocalDate hireDate;

    @Temporal(TemporalType.DATE)
    private LocalDate terminationDate = null;
    private BigDecimal salary;
    private BigDecimal bonus;


    public Employee(LocalDate hireDate, BigDecimal salary) {
        this.hireDate = hireDate;
        this.salary = salary;
    }

    public BigDecimal getCompensation() {
        if (salary != null) {
            return salary.add(bonus != null ? bonus : BigDecimal.ZERO);
        }
        return BigDecimal.ZERO;
    }

}
