package org.example.application.model;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "positions")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "position_name", nullable = false)
    private String positionName;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "positions_to_employees",
            joinColumns = @JoinColumn(name = "id_position"),
            inverseJoinColumns = @JoinColumn(name = "id_employee")
    )
    private List<Employee> employeeList;

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", positionName='" + positionName + '\'' +
                ", employeeList=" + employeeList +
                '}';
    }
}
