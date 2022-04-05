package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * @author Efrat Kugman
 * id: 314766650
 */
public class AmbientLight extends Light {

    public AmbientLight(Color Ia, Double3 Ka){
        super(new Color(Ia.getColor().getRed() * Ka.getD1(),
                Ia.getColor().getGreen() * Ka.getD2(),
                Ia.getColor().getBlue() * Ka.getD3()));

    }

    public AmbientLight(){
        super(Color.BLACK);
    }


}
