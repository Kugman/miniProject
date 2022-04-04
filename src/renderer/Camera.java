package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

import static primitives.Util.*;

public class Camera {
    //Camera Location Point
    private Point location;

    //Camera Orientation Vectors
    private Vector directionRight;
    private Vector directionUp;
    private Vector directionTo;

    //Camera Fields
    private double width;
    private double height;
    private double distance;
    private int sizeGrid = 1;

    private ImageWriter imageWriter;
    private RayTracerBase rayTracerBase;

    //-----------constructor-----------------//
    public Camera(Point location, Vector directionTo, Vector directionUp){
        this.location = location;
        this.directionTo = directionTo.normalize();
        this.directionUp = directionUp.normalize();
        if(!isZero(this.directionTo.dotProduct(this.directionUp)))
            throw new IllegalArgumentException("The 2 Vectors are not orthogonal");
        this.directionRight = this.directionTo.crossProduct(this.directionUp).normalize();
    }

    //-----------Getters---------------------//
    public Point getLocation(){
        return this.location;
    }

    public Vector getDirectionRight(){
        return this.directionRight;
    }

    public Vector getDirectionUp(){
        return this.directionUp;
    }

    public Vector getDirectionTo(){
        return this.directionTo;
    }

    //------------Setters---------------------//
    public Camera setVPSize(double width, double height){
        this.width = width;
        this.height = height;
        return this;
    }

    public Camera setVPDistance(double distance){
        this.distance = distance;
        return this;
    }

    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    public Camera setRayTracer(RayTracerBase rayTracerBase) {
        this.rayTracerBase = rayTracerBase;
        return this;
    }

    //------Camera operations------------------//
    public Ray constructRay(int nX, int nY, int j, int i) {

        //calculate pc
        Point PC = location.add(directionTo.scale(distance));
        //Ratio (pixel width & height)
        double Ry = height / nY;
        double Rx = width / nX;
        //Pixel[i,j] center
        double Yi = -(i - (nY - 1) / 2d) * Ry;
        double Xj = (j - (nX - 1) / 2d) * Rx;
        Point Pij = PC;
        if (isZero(Xj) && isZero(Yi))
            return new Ray(location, Pij.subtract(location));

        //only 1 is zero
        if (isZero(Xj)) {
            Pij = PC.add(directionUp.scale(Yi));
            return new Ray(location, Pij.subtract(location));
        }

        if (isZero(Yi)) {
            Pij = PC.add(directionRight.scale(Xj));
            return new Ray(location, Pij.subtract(location));
        }

        //IF NO ONE IS ZERO
        Pij = PC.add(directionRight.scale(Xj).add(directionUp.scale(Yi)));
        Vector Vij = Pij.subtract(location);
        return new Ray(location, Vij);

    }

    public List<Ray> constructRays(int nX, int nY, int j, int i){
        Ray centerRay = constructRay(nX, nY, j, i);

        double Rx = this.width / nX;//the length of pixel in X axis
        double Ry = this.height / nY;//the length of pixel in Y axis

        Point Pij = getPij(nX, nY, j, i, width, height, distance);
        Point tmp;
        //-----SuperSampling-----
        List<Ray> rays = new LinkedList<>();//the return list, construct Rays Through Pixels


        double n = Math.floor(Math.sqrt(sizeGrid));
        int delta = (int) (n / 2d);//the movement from the center of the pixel
        //length and width of each sub pixel
        double gapX = Rx / n;
        double gapY = Ry / n;

        for (int row = -delta; row <= delta; row++) {
            for (int col = -delta; col <= delta; col++) {
                tmp = new Point(Pij.getX(), Pij.getY(), Pij.getZ());//
                if (!isZero(row)) {
                    tmp = tmp.add(directionRight.scale(row * (double) Math.random() * ((gapX)) - (gapX) / 2));//(double) Math.random()*((gapX))-gapX/2))
                    //tmp = tmp.add(_vRIGHT.scale(row * gapX));
                }
                if (!isZero(col)) {
                    tmp = tmp.add(directionRight.scale(col * (double) Math.random() * ((gapY)) - (gapY) / 2));//(double) Math.random()*((gapY))-gapY/2));
                    //tmp = tmp.add(_vRIGHT.scale(col * gapY));
                }
                rays.add(new Ray(location, tmp.subtract(location).normalize()));
            }
        }
        return rays;
    }

    private Point getPij(int nX, int nY, int j, int i, double width, double height, double distance) {
        //calculate pc
        Point PC = location.add(directionUp.scale(distance));
        //Ratio (pixel width & height)
        double Ry = height / nY;
        double Rx = width / nX;
        //Pixel[i,j] center
        double Yi = -(i - (nY - 1) / 2d) * Ry;
        double Xj = (j - (nX - 1) / 2d) * Rx;
        Point Pij = PC;


        //if both are zero
        if (isZero(Xj) && isZero(Yi)) return Pij;

        //only 1 is zero
        if (isZero(Xj)) {
            Pij = PC.add(directionUp.scale(Yi));
            return Pij;
        }

        if (isZero(Yi)) {
            Pij = PC.add(directionRight.scale(Xj));
            return Pij;
        }

        //IF NO ONE IS ZERO
        Pij = PC.add(directionRight.scale(Xj).add(directionUp.scale(Yi)));
        return Pij;
    }

    public void renderImage(){
        if(this.location == null) throw new MissingResourceException("missing value", "Point", "location");
        if(this.directionRight == null) throw new MissingResourceException("missing value", "Vector", "directionRight");
        if(this.directionUp == null) throw new MissingResourceException("missing value", "Vector", "directionUp");
        if(this.directionTo == null) throw new MissingResourceException("missing value", "Vector", "directionTo");
        if(this.imageWriter == null) throw new MissingResourceException("missing value", "ImageWriter", "imageWriter");
        if(this.rayTracerBase == null) throw new MissingResourceException("missing value", "RayTracerBase", "rayTracerBase");
//        throw new UnsupportedOperationException();

        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();

        for (int i = 0; i < nY; ++i)
            for (int j = 0; j < nX; ++j)
                imageWriter.writePixel(j, i, rayTracerBase.traceRay(constructRay(nX, nY, j, i)));

    }

    public void printGrid(int interval, Color color){
        if(this.imageWriter == null) throw new MissingResourceException("missing value", "ImageWriter", "imageWriter");
        for (int i = 0; i < imageWriter.getNx(); i++)
            for (int j = 0; j < imageWriter.getNy(); j++) {
                if (i % interval == 0) this.imageWriter.writePixel(i, j, color);
                else if (j % interval == 0) this.imageWriter.writePixel(i, j, color);
            }
    }

    public void writeToImage(){
        if(this.imageWriter == null) throw new MissingResourceException("missing value", "ImageWriter", "imageWriter");
        this.imageWriter.writeToImage();
    }

}