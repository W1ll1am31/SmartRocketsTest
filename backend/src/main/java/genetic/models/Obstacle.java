package genetic.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Obstacle {
    private int x;
    private int y;
    private int height;
    private int width;
}
