package quarkus.world.tour;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class Band extends PanacheEntity {

    @NotBlank
    @Column(unique = true)
    public String name;
    public boolean alive;

    @Min(1945)
    public Integer creationYear;
    public Integer terminationYear = 0;

    public static List<Band> listAlive() {
        return Band.list("alive", true);
    }
}
