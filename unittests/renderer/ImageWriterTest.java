package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    @Test
    void writeToImage() {
        ImageWriter imageWriter=new ImageWriter("test-pink",800,500);
        for(int i=0;i<800;i++){
            for(int j=0;j<500;j++){
                //if it on limit 800/16=50
                if(i%50==0){
                    imageWriter.writePixel(i,j, Color.BLACK);
                }
                //if it on limit 500/10=50
                else if(j%50==0){
                    imageWriter.writePixel(i,j, Color.BLACK);
                }
                //any other test
                else{
                    imageWriter.writePixel(i,j, new Color(250d,100d,150d));
                }
            }
        }
        imageWriter.writeToImage();
    }

}