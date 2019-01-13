package genetic.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequiredInfo {
    private float x;
    private float y;
    private float heading;
    private String colour;
}
