package elements;

import primitives.Color;
import primitives.Double3;

/**
 * @author Efrat Kugman
 * id: 314766650
 */
public class AmbientLight {

    final private Color intensity;

    public AmbientLight(Color Ia, Double3 Ka){
        this.intensity = new Color( Ia.getColor().getRed() * Ka.getD1(),
                                    Ia.getColor().getGreen() * Ka.getD2(),
                                    Ia.getColor().getBlue() * Ka.getD3());

    }

    public AmbientLight(){
        this.intensity = Color.BLACK;
    }

    public Color getIntensity() {
        return intensity;
    }
}
