package demo.authentication.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @JsonBackReference
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
