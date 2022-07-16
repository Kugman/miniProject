package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

public class Threaded {

    private class Pixel {
        private long maxRows = 0;
        private long maxCols = 0;
        private long pixels = 0;
        public volatile int row = 0;
        public volatile int col = -1;
        private long counter = 0;
        private int percents = 0;
        private long nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         *
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            this.maxRows = maxRows;
            this.maxCols = maxCols;
            this.pixels = (long) maxRows * maxCols;
            this.nextCounter = this.pixels / 100;
            if (Threaded.this.print)
                System.out.printf("\r %02d%%", this.percents);
        }

        /**
         * Default constructor for secondary Pixel objects
         */
        public Pixel() {
        }

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object
         * - this function is critical section for all the threads, and main Pixel
         * object data is the shared data of this critical section.<br/>
         * The function provides next pixel number each call.
         *
         * @param target target secondary Pixel object to copy the row/column of the
         *               next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print,
         *         if it is -1 - the task is finished, any other value - the progress
         *         percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++this.counter;
            if (col < this.maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (Threaded.this.print && this.counter == this.nextCounter) {
                    ++this.percents;
                    this.nextCounter = this.pixels * (this.percents + 1) / 100;
                    return this.percents;
                }
                return 0;
            }
            ++row;
            if (row < this.maxRows) {
                col = 0;
                target.row = this.row;
                target.col = this.col;
                if (Threaded.this.print && this.counter == this.nextCounter) {
                    ++this.percents;
                    this.nextCounter = this.pixels * (this.percents + 1) / 100;
                    return this.percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         *
         * @param target target secondary Pixel object to copy the row/column of the
         *               next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percent = nextP(target);
            if (Threaded.this.print && percent > 0)
                synchronized (this) {
                    notifyAll();
                }
            if (percent >= 0)
                return true;
            if (Threaded.this.print)
                synchronized (this) {
                    notifyAll();
                }
            return false;
        }

        /**
         * Debug print of progress percentage - must be run from the main thread
         */
        public void print() {
            if (Threaded.this.print)
                while (this.percents < 100)
                    try {
                        synchronized (this) {
                            wait();
                        }
                        System.out.printf("\r %02d%%", this.percents);
                        System.out.flush();
                    } catch (Exception e) {
                    }
        }
    }

    private Camera camera;
    private ImageWriter imageWriter;
    private RayTracerBase tracer;
    private static final String RESOURCE_ERROR = "Renderer resource not set";
    private static final String RENDER_CLASS = "Render";
    private static final String IMAGE_WRITER_COMPONENT = "Image writer";
    private static final String CAMERA_COMPONENT = "Camera";
    private static final String RAY_TRACER_COMPONENT = "Ray tracer";

    private int threadsCount = 0;
    private static final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
    private boolean print = false; // printing progress percentage


    public Threaded setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
        if (threads != 0)
            this.threadsCount = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            this.threadsCount = cores <= 2 ? 1 : cores;
        }
        return this;
    }

    public Threaded setDebugPrint() {
        print = true;
        return this;
    }

    public Threaded setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    public Threaded setImageWriter(ImageWriter imgWriter) {
        this.imageWriter = imgWriter;
        return this;
    }

    public Threaded setRayTracer(RayTracerBase tracer) {
        this.tracer = tracer;
        return this;
    }

    public void writeToImage() {
        if (imageWriter == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);

        imageWriter.writeToImage();
    }

    private void castRay(int nX, int nY, int col, int row) {
        Ray ray = camera.constructRay(nX, nY, col, row);
        Color color = tracer.traceRay(ray);
        imageWriter.writePixel(col, row, color);
    }

    private void castRayRecursive(int nX, int nY, int col, int row) {
        //call function for get list of 4 colors of 4 corners
        List<Color> corners = colors4Corners(nX, nY, col, row);

        Point center = camera.getCenterPij(nX, nY, col, row);
        Color color = castRayRecursive(camera.getRx(nX),camera.getRy(nY),recursionDepth,center,corners);

        imageWriter.writePixel(col, row, color);
    }

    private Color castRayRecursive(double Rx, double Ry, int level, Point pc, List<Color> corners) {

        Color sumColors = Color.BLACK;
        if(level <= 0 || ( corners.get(0).equals(corners.get(1)) &&
                corners.get(0).equals(corners.get(2)) && corners.get(0).equals(corners.get(3)))) {
            for(var color : corners) sumColors = sumColors.add(color);
            return sumColors.reduce(4);
        }

        //call function for get list of 5 colors of 5 centers
        List<Color> centers = colors5Centers(pc,Rx,Ry);

        //recursive calls:
        sumColors = sumColors.add(castRayRecursive(Rx/2,Ry/2,level-1,
                camera.getCenterPij(pc,Rx/2,Ry/2,0,0,2,2),
                List.of(corners.get(0),centers.get(0),centers.get(1),centers.get(2))));

        sumColors = sumColors.add(castRayRecursive(Rx/2,Ry/2,level-1,
                camera.getCenterPij(pc,Rx/2,Ry/2,0,1,2,2),
                List.of(centers.get(0),corners.get(1),centers.get(2),centers.get(3))));

        sumColors = sumColors.add(castRayRecursive(Rx/2,Ry/2,level-1,
                camera.getCenterPij(pc,Rx/2,Ry/2,1,0,2,2),
                List.of(centers.get(1),centers.get(2),corners.get(2),centers.get(4))));

        sumColors = sumColors.add(castRayRecursive(Rx/2,Ry/2,level-1,
                camera.getCenterPij(pc,Rx/2,Ry/2,1,1,2,2),
                List.of(centers.get(2),centers.get(3),centers.get(4),corners.get(3))));

        return sumColors.reduce(4);
    }

    private List<Color> colors4Corners(int nX, int nY, int col, int row) {
        List<Ray> rays = camera.cornersColors(nX,nY,col,row);
        List<Color> colors = new LinkedList<Color>();

        for(var ray : rays)
            colors.add(tracer.traceRay(ray));

        return colors;
    }

    private List<Color> colors5Centers(Point pc, double Rx, double Ry) {
        List<Ray> rays = camera.centersColors(pc, Rx, Ry);
        List<Color> colors = new LinkedList<Color>();

        for(var ray : rays)
            colors.add(tracer.traceRay(ray));

        return colors;
    }

    private void renderImageThreaded() {
        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();
        final Pixel thePixel = new Pixel(nY, nX);
        // Generate threads
        Thread[] threads = new Thread[threadsCount];
        for (int i = threadsCount - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();
                while (thePixel.nextPixel(pixel)) {
                    //castRayRecursive(nX, nY, pixel.col, pixel.row);
                    //castRay(nX, nY, pixel.col, pixel.row);

                    if(gridNumber <= 1) {

                        castRayRecursive(nX, nY, pixel.col, pixel.row);
                        //castRay(nX, nY, pixel.col, pixel.row);
                    }
                    else
                        gridPixel(nX,nY,pixel.col,pixel.row);

                }
            });
        }
        // Start threads
        for (Thread thread : threads)
            thread.start();

        // Print percents on the console
        thePixel.print();

        // Ensure all threads have finished
        for (Thread thread : threads)
            try {
                thread.join();
            } catch (Exception e) {
            }

        if (print)
            System.out.print("\r100%");
    }

    private void gridPixel(int nX,int nY,int x,int y) {
        Color sumColors = Color.BLACK;
        List<Ray> rays = new LinkedList<Ray>();

        for(int i = 0; i< gridNumber; i++) //all rows
        {
            for(int j = 0; j < gridNumber; j++) //all columns
            {
                Ray ray = camera.constructRayGrid(nX, nY, x, y, j, i, gridNumber);

                rays.add(ray);
            }
        }
        //sumColors = sumColors.add(rayTracer.traceRay(ray));
        for(var ray : rays)
            sumColors = sumColors.add(tracer.traceRay(ray));

        imageWriter.writePixel(x, y, sumColors.reduce(rays.size()));
    }

    public void renderImage() {
        if (imageWriter == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);
        if (camera == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, CAMERA_COMPONENT);
        if (tracer == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, RAY_TRACER_COMPONENT);

        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();
        if (threadsCount == 0)
            for (int i = 0; i < nY; ++i) {
                for (int j = 0; j < nX; ++j) {
                    //castRay(nX, nY, j, i);

                    if(gridNumber <= 1) {
                        castRay(nX, nY, j, i);
                    }
                    else
                        //paint pixel
                        gridPixel(nX,nY,j,i);
                }
                System.out.printf("\r %02d%%", i);
            }
        else
            renderImageThreaded();
    }

    public void printGrid(int step, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        for (int i = 0; i < nY; ++i)
            for (int j = 0; j < nX; ++j)
                if (j % step == 0 || i % step == 0)
                    imageWriter.writePixel(j, i, color);
    }

    int gridNumber = 0;
    public void setImprovement(int n) {
        gridNumber = n;
    }

    //depth of recursion
    int recursionDepth = 3;

}
