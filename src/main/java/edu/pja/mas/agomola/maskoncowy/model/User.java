package edu.pja.mas.agomola.maskoncowy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Table(name = "app_user")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(length=11, unique = true)
    private String pesel;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @PastOrPresent
    private LocalDate dateOfBirth;
    @NotBlank
    private String phoneNumber;
    private String emailAddress = null;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Gym gym;

    public int getAge(){
        if(dateOfBirth == null){
            return 0;
        }
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Client client;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Trainer trainer;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Manager manager;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Receptionist receptionist;

}




//    public void addClient(LocalDate registrationDate, Status status){
//        Client client = new Client(registrationDate, status);
//        this.addLink(roleNameClient, roleNameGeneralization, client);
//    }
//    public void addManager(LocalDate hireDate, BigDecimal salary, List<Employee> employees){
//        Manager manager = new Manager(hireDate, salary, employees);
//        this.addLink(roleNameManager, roleNameGeneralization, manager);
//    }
//    public void addTrainer(LocalDate hireDate, BigDecimal salary, String specialization, List<String> certificates){
//        Trainer trainer = new Trainer(hireDate, salary, specialization, certificates);
//        this.addLink(roleNameTrainer, roleNameGeneralization, trainer);
//    }
//    public void addReceptionist(LocalDate hireDate, BigDecimal salary, Shift shift){
//        Receptionist receptionist = new Receptionist(hireDate, salary, shift);
//        this.addLink(roleNameReceptionist, roleNameGeneralization, receptionist);
//    }
//    private final static String roleNameClient = "roleClient";
//    private final static String roleNameManager = "roleManager";
//    private final static String roleNameTrainer = "roleTrainer";
//    private final static String roleNameReceptionist = "roleReceptionist";
//    private final static String roleNameGeneralization = "generalizationUser";
