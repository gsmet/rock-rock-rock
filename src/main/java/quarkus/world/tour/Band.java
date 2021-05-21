package quarkus.world.tour;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import java.util.List;

@Entity
@Indexed
public class Band extends PanacheEntity {

    @NotBlank
    @Column(unique = true)
    @FullTextField
    @KeywordField(name = "name_sort", sortable = Sortable.YES)
    public String name;
    public boolean alive;

    @Min(1945)
    public Integer creationYear;
    public Integer terminationYear = 0;

    public static List<Band> listAlive() {
        return Band.list("alive", true);
    }
}
