import geometries.Plane;
import geometries.Sphere;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import renderer.Threaded;
import renderer.Camera;
import scene.Scene;

import static java.awt.Color.WHITE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class part1Tests {

    @Test
    public void test()
    {
        boolean flag=false;
        System.out.print("third statement");

        Scene scene = new Scene("test");
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVPSize(200, 200).setVPDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0, 0, 0)));
        Plane plane = new Plane(new Point(0, 0, -300), new Vector(0, 0, 1));

        double i=15, j=3;
        for (int x = -90, y = 140; x < 0; x += j, y -= i, i += 1.5, j += 0.2)
            scene.lights.add(new SpotLight(new Color(400, 300, 300), new Point(x, y, 150),
                    camera.getDirectionTo()).setKC(1).setKL(0.005).setKQ(0.0005));

        i = 15; j = -3;
        for (int x = 90, y = 140; x > 0; x += j, y -= i, i += 1.5, j -= 0.2)
            scene.lights.add(new SpotLight(new Color(400, 300, 300), new Point(x, y, 150),
                    camera.getDirectionTo()).setKC(1).setKL(0.005).setKQ(0.0005));

        //teddy bear
        scene.geometries.add(
                //body
                new Sphere(new Point(4, 8, 18), 25).setEmission(new Color(77, 38, 0)).setMaterial(new Material().setKD(0.3).setKS(0.7).setNShininess(100).setKT(0.5).setKR(0)),
                //right leg
                new Sphere(new Point(26, -11, 18), 8).setEmission(new Color(77, 38, 0)).setMaterial(new Material().setKD(0.3).setKS(0.7).setNShininess(100).setKT(0.5).setKR(0)),
                //left leg
                new Sphere(new Point(-14, -12, 18), 8).setEmission(new Color(77, 38, 0)).setMaterial(new Material().setKD(0.3).setKS(0.7).setNShininess(100).setKT(0.5).setKR(0)),
                //left hand
                new Sphere(new Point(-21, 16, 18), 8).setEmission(new Color(77, 38, 0)).setMaterial(new Material().setKD(0.3).setKS(0.7).setNShininess(100).setKT(0.5).setKR(0)),
                //right hand
                new Sphere(new Point(27, 16, 18), 8).setEmission(new Color(77, 38, 0)).setMaterial(new Material().setKD(0.3).setKS(0.7).setNShininess(100).setKT(0.5).setKR(0)),
                //head
                new Sphere(new Point(3, 39, 18), 18).setEmission(new Color(77, 38, 0)).setMaterial(new Material().setKD(0.3).setKS(0.7).setNShininess(100).setKT(0.5).setKR(0)),
                //right eye
                new Sphere(new Point(11, 43, 35.71), 2.2).setEmission(new Color(java.awt.Color.BLACK)).setMaterial(new Material().setKD(0.3).setKS(0.7).setNShininess(100).setKT(0).setKR(0)),
                //left eye
                new Sphere(new Point(-4, 43, 35.94), 2.2).setEmission(new Color(java.awt.Color.BLACK)).setMaterial(new Material().setKD(0.3).setKS(0.7).setNShininess(100).setKT(0).setKR(0)),
                //nose
                new Sphere(new Point(2.699, 37.57, 37.94), 2.5).setEmission(new Color(java.awt.Color.gray)).setMaterial(new Material().setKD(0.3).setKS(0.7).setNShininess(100).setKT(0).setKR(0)),
                //left ear
                new Sphere(new Point(-8, 58, 18), 7).setEmission(new Color(77, 38, 0)).setMaterial(new Material().setKD(0.3).setKS(0.7).setNShininess(100).setKT(0.5).setKR(0)),
                //right ear
                new Sphere(new Point(14, 58, 18), 7).setEmission(new Color(77, 38, 0)).setMaterial(new Material().setKD(0.3).setKS(0.7).setNShininess(100).setKT(0.5).setKR(0))

        );

        scene.geometries.add(plane.setEmission(new Color(255, 60, 60)).setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(1200).setKT(0).setKR(0)));
        /////////////mirror
        scene.geometries.add(new Plane(new Point(0, -20, 0), new Vector(0, -40, 0)).setEmission(new Color(0, 40, 60)).setMaterial(new Material().setKR(1)));

        DirectionalLight direction_light = new DirectionalLight(new Color(0, 0, 1), new Vector(1, -1, 1));
        SpotLight spot_light = new SpotLight(new Color(0, 1, 0), new Point(4, 8, 18), new Vector(0, 0, 1));//1, 4E-4, 2E-5,;
        PointLight point_light = new PointLight(new Color(400, 300, 300), new Point(0, 145, 50));
        //on mirror

        scene.lights.add(point_light.setKC(1).setKL(0.05).setKQ(0.00005));
        scene.lights.add(direction_light);
        scene.lights.add(spot_light.setKC(1).setKL(4E-4).setKQ(2E-5));


        if(!flag) {

            ImageWriter imageWriter = new ImageWriter("Part1_test", 600, 500);


            camera.setImageWriter(imageWriter).setRayTracer(new RayTracerBasic(scene));
            camera.setImprovement(9);
            camera.renderImage();
            camera.writeToImage();
        }
        else {

            ImageWriter imageWriter = new ImageWriter("Part2-threads_test", 600, 500);

            Threaded render = new Threaded();
            render.setCamera(camera).setImageWriter(imageWriter).setRayTracer(new RayTracerBasic(scene));
            render.setImprovement(9);
            render.setMultithreading(3).setDebugPrint().renderImage();
            render.writeToImage();
        }

    }


}
