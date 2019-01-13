package genetic.models;

import lombok.Data;

import javax.persistence.Id;
import java.util.List;

//@Entity
@Data
public class RocketPosition {
    @Id
    public String uuid;
    public List<List<List<RequiredInfo>>> allRocketPositions;
}
