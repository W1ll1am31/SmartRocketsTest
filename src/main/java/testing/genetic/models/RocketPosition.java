package testing.genetic.models;

import lombok.Data;
import processing.core.PVector;

import javax.persistence.Id;
import java.util.List;

//@Entity
@Data
public class RocketPosition {
    @Id
    public String uuid;
    public List<List<List<PVector>>> allRocketPositions;
}
