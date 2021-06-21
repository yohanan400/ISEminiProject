package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

/**
 * Render class responsible to render the scene
 *
 * @author Aviel Buta and Yakir Yohanan
 */
public class Render {

    //--------------------------------------------------- FIELDS ----------------------------------------------------//
    /**
     * The object responsible to make the jpeg after the render of the scene
     */
    ImageWriter _imageWriter = null;

    /**
     * The camera of the scene
     */
    Camera _camera = null;

    /**
     * The ray tracer object to trace after the rays from the camera to the scene objects
     */
    RayTracerBase _rayTracerBase = null;

    /**
     * Count the threads
     */
    private int threadsCount = 0;

    /**
     * How many threads we want to stay release.
     * Spare threads if trying to use all the cores.
     */
    private static final int SPARE_THREADS = 2;

    /**
     * Printing progress percentage
     * True for print, false else
     */
    private boolean print = false;

    /**
     * Static variable for determine the max depth number of the recursion
     */
    private static final int MAX_DEPTH_OF_ADAPTIVE = 8;

    /**
     * Static variable for determine the starting number of the recursion depth
     */
    private static final int STARTING_DEPTH = 1;

    /**
     * Static variable for determine the starting sign of the quarter
     * 1 equals to plus sign and -1 equals to minus sign
     * In particular:
     * 1,1   - The first quarter
     * -1,1   - The second quarter
     * -1,-1  - The third quarter
     * 1,-1  - The fourth quarter
     */
    private static final int STARTING_SIGN = 1;

    /**
     * To cast multiple rays (super sampling) - true, or not - false
     */
    private boolean superSampling = false;

    /**
     * To cast rays adaptively (true) or not (false)
     */
    private boolean adaptiveGrid = false;


    //--------------------------------------------------- SETTERS ---------------------------------------------------//

    /**
     * Set multi-threading <br>
     * - if the parameter is 0 - number of cores less 2 is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
        if (threads != 0)
            this.threadsCount = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            this.threadsCount = cores <= 2 ? 1 : cores;
        }

        // Return this for chaining
        return this;
    }

    /**
     * Set debug printing on
     *
     * @return this (Render)
     */
    public Render setDebugPrint() {
        print = true;

        // Return this for chaining
        return this;
    }

    /**
     * Set the image object
     *
     * @param imageWriter The image object (ImageWriter)
     * @return this (Render)
     */
    public Render setImageWriter(ImageWriter imageWriter) {
        _imageWriter = imageWriter;

        // Return this for chaining
        return this;
    }

    /**
     * Set the camera object
     *
     * @param camera the camera object (Camera)
     * @return this (Render)
     */
    public Render setCamera(Camera camera) {
        _camera = camera;

        // return this for chaining
        return this;

    }

    /**
     * Set the rayTracerBase object
     *
     * @param rayTracerBase The rayTracerBase object (RayTracerBase)
     * @return this (Render)
     */
    public Render setRayTracerBase(RayTracerBase rayTracerBase) {
        _rayTracerBase = rayTracerBase;

        // return this for chaining
        return this;
    }

    /**
     * Set if to cast multiple rays (superSampling)
     *
     * @param superSampling boolean
     * @return this (Render)
     */
    public Render setSuperSampling(boolean superSampling) {
        this.superSampling = superSampling;

        // Return this for chaining
        return this;
    }

    /**
     * Set if to cast rays adaptively (true) or not (false)
     *
     * @param adaptiveGrid boolean
     * @return this (Render)
     */
    public Render setAdaptiveGrid(boolean adaptiveGrid) {
        this.adaptiveGrid = adaptiveGrid;

        // Return this for chaining
        return this;
    }

    //--------------------------------------- PIXEL CLASS ---------------------------------------//

    /**
     * Pixel is an internal helper class whose objects are associated with a Render
     * object that they are generated in scope of. It is used for multithreading in
     * the Renderer and for follow up its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each
     * thread.
     *
     * @author Dan
     */
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
            if (Render.this.print)
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
         * if it is -1 - the task is finished, any other value - the progress
         * percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++this.counter;
            if (col < this.maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (Render.this.print && this.counter == this.nextCounter) {
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
                if (Render.this.print && this.counter == this.nextCounter) {
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
            if (Render.this.print && percent > 0)
                synchronized (this) {
                    notifyAll();
                }
            if (percent >= 0)
                return true;
            if (Render.this.print)
                synchronized (this) {
                    notifyAll();
                }
            return false;
        }

        /**
         * Debug print of progress percentage - must be run from the main thread
         */
        public void print() {
            if (Render.this.print) {
                while (this.percents < 100) {
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
        }
    }
    //--------------------------------------- END OF Pixel CLASS ---------------------------------------//


    //----------------------------------------------- CAMERA'S METHODS -----------------------------------------------//

    /**
     * Cast ray from camera in order to paint a pixel
     *
     * @param nX  resolution on X axis (number of pixels in row)
     * @param nY  resolution on Y axis (number of pixels in column)
     * @param col pixel's column number (pixel index in row)
     * @param row pixel's row number (pixel index in column)
     */
    private void castRay(int nX, int nY, int col, int row) {
        Color color = Color.BLACK;
        // If adaptive grid ware selected so do it, and if not its depend
        // if to cast multiple rays (superSampling) or just ont
        if (adaptiveGrid) {
            color = adaptiveGrid(nX, nY, col, row);
        } else {
            Ray ray;
            if (superSampling) {
                int numOfRays = 100;
                for (int i = 1; i < numOfRays; i++) {
                    ray = _camera.constructRayThroughPixel(nX, nY, col, row);
                    color = color.add(_rayTracerBase.traceRay(ray));
                }
                color = color.reduce(numOfRays);
            } else {
                ray = _camera.constructRayThroughPixel(nX, nY, col, row);
                color = color.add(_rayTracerBase.traceRay(ray));
            }
        }
        // Paint the current pixel
        _imageWriter.writePixel(col, row, color);
    }

    /**
     * Cast rays adaptively to the object and calculate the final color of the pixel
     *
     * @param nX  number of columns (int)
     * @param nY  number of rows (int)
     * @param col column index of the point in the view plane (int)
     * @param row row index of the point in the view plane (int)
     * @return The final color of the pixel (Color)
     */
    private Color adaptiveGrid(int nX, int nY, int col, int row) {

        Color color = Color.BLACK;
        List<Color> colors = new ArrayList<>();
        // Collect all the four colors of the four pixel's corners
        // and add it to 'color' for calculating the average color of the pixel (the final color of the pixel)
        // ---
        // The order of the ray list is:
        // up-right corner   -  1  -- index 0
        // up-left corner    -  2  -- index 2
        // down-left corner  -  3  -- index 1
        // down-right corner -  4  -- index 3
        // --
        // The order of the colors of the corners are in the same order of the ray list
        List<Ray> ray = _camera.constructRayThroughPixelAdaptive(nX, nY, col, row, STARTING_DEPTH, STARTING_SIGN, STARTING_SIGN);
        for (int i = 0; i < ray.size(); i++) {
            // We save the colors for comparing them for knowing if we need to make deeper grid
            colors.add(_rayTracerBase.traceRay(ray.get(i)));
            color = color.add(colors.get(i));
        }

        // We organize the list of the rays and they colors (colRayList) refer to:
        // up-right corner   -  1  -- index 0
        // up-left corner    -  2  -- index 1
        // down-left corner  -  3  -- index 2
        // down-right corner -  4  -- index 3
        // the average color -     -- index 4
        List<Color> colRayList = new ArrayList<>();
        colRayList.add(colors.get(0));
        colRayList.add(colors.get(2));
        colRayList.add(colors.get(1));
        colRayList.add(colors.get(3));


        // If not all four corners are with the same variety, make deeper grid
        if (!colRayList.get(0).isEqual(colRayList.get(1)) ||
                !colRayList.get(1).isEqual(colRayList.get(2)) ||
                !colRayList.get(2).isEqual(colRayList.get(3))) {

            color = Color.BLACK;
            List<Color> colRay1 = adaptiveGrid2(nX, nY, col, row, STARTING_DEPTH + 1, 1, 1, List.of(colRayList.get(0))); // Right up
            color = color.add(colRay1.get(4));

            List<Color> colRay2 = adaptiveGrid2(nX, nY, col, row, STARTING_DEPTH + 1, -1, 1
                    , List.of(colRay1.get(1), colRayList.get(1), colRay1.get(2))); // Left up
            color = color.add(colRay2.get(4));


            List<Color> colRay3 = adaptiveGrid2(nX, nY, col, row, STARTING_DEPTH + 1, -1, -1,
                    List.of(colRay1.get(2), colRay2.get(2), colRayList.get(2))); // left down
            color = color.add(colRay3.get(4));


            List<Color> colRay4 = adaptiveGrid2(nX, nY, col, row, STARTING_DEPTH + 1, 1, -1,
                    List.of(colRay1.get(3), colRay1.get(2), colRay3.get(3), colRayList.get(3))); // right down
            color = color.add(colRay4.get(4));

        }
        return color.reduce(4);
    }

    /**
     * @param nX        number of columns (int)
     * @param nY        number of rows (int)
     * @param col       column index of the point in the view plane (int)
     * @param row       row index of the point in the view plane (int)
     * @param depth     The depth of the recursion
     * @param signX     The X sign of the current quarter
     * @param signY     The Y sign of the current quarter
     * @param oldColors List of colors we already calculate
     * @return List of the corners colors and the average of them
     */
    private List<Color> adaptiveGrid2(int nX, int nY, int col, int row, int depth, int signX, int signY, List<Color> oldColors) {

        Color color = Color.BLACK;
        // List to save the colors for calculating and reorganized
        List<Color> colors = new ArrayList<>();
        // List to save the final colors
        List<Color> colRayList = new ArrayList<>();

        // REORGANIZED
        // We organize the list of the rays and they colors (colRayList) refer to:
        // up-right corner   -  1  -- index 0
        // up-left corner    -  2  -- index 1
        // down-left corner  -  3  -- index 2
        // down-right corner -  4  -- index 3
        // the average color -     -- index 4
        List<Ray> rays = _camera.constructRayThroughPixelAdaptive(nX, nY, col, row, depth, signX, signY); // The new corners

        // First quarter
        if (signX > 0 && signY > 0) {
            colRayList.add(oldColors.get(0)); // up-right corner
            color = color.add(oldColors.get(0));

            for (int i = 0; i < 3; i++) {
                colRayList.add(_rayTracerBase.traceRay(rays.get(i)));
                color = color.add(colRayList.get(i + 1));
            }
        }

        // Second quarter
        if (signX < 0 && signY > 0) {
            colRayList.add(oldColors.get(0)); // up-right corner
            color = color.add(oldColors.get(0));

            colRayList.add(oldColors.get(1)); // up-left corner
            color = color.add(oldColors.get(1));

            colors.add(_rayTracerBase.traceRay(rays.get(0)));
            colRayList.add(colors.get(0)); // down-left corner
            color = color.add(colors.get(0));

            colRayList.add(oldColors.get(2)); // down-right corner
            color = color.add(oldColors.get(2));
        }

        // Third quarter
        if (signX < 0 && signY < 0) {

            colRayList.add(oldColors.get(0)); // up-right corner
            color = color.add(oldColors.get(0));


            colRayList.add(oldColors.get(1)); // up-left corner
            color = color.add(oldColors.get(1));


            colRayList.add(oldColors.get(2)); // down-left corner
            color = color.add(oldColors.get(2));


            colors.add(_rayTracerBase.traceRay(rays.get(0)));
            colRayList.add(colors.get(0)); // down-right corner
            color = color.add(colors.get(0));
        }

        // Fourth quarter
        if (signX > 0 && signY < 0) {
            //We receive all the corners colors as parameters (in 'oldRays')
            // so we just add them to 'colRayList'.
            for (int i = 0; i < 4; i++) {
                colRayList.add(oldColors.get(i)); // We receive them sorted so we just save them
                color = color.add(oldColors.get(i));
            }
        }

        if (depth >= MAX_DEPTH_OF_ADAPTIVE) {
            colRayList.add(color.reduce(4));
            return colRayList;
        }

        // Check if all corners in the same variety
        if (!colRayList.get(0).isEqual(colRayList.get(1)) ||
                !colRayList.get(1).isEqual(colRayList.get(2)) ||
                !colRayList.get(2).isEqual(colRayList.get(3))) {

            color = Color.BLACK;
            List<Color> colRay1 = adaptiveGrid2(nX, nY, col, row, depth + 1, 1, 1, List.of(colRayList.get(0))); // Right up
            color = color.add(colRay1.get(4));

            List<Color> colRay2 = adaptiveGrid2(nX, nY, col, row, depth + 1, -1, 1
                    , List.of(colRay1.get(1), colRayList.get(1), colRay1.get(2))); // Left up
            color = color.add(colRay2.get(4));

            List<Color> colRay3 = adaptiveGrid2(nX, nY, col, row, depth + 1, -1, -1,
                    List.of(colRay1.get(2), colRay2.get(2), colRayList.get(2))); // Left down
            color = color.add(colRay3.get(4));


            List<Color> colRay4 = adaptiveGrid2(nX, nY, col, row, depth + 1, 1, -1,
                    List.of(colRay1.get(3), colRay1.get(2), colRay3.get(3), colRayList.get(3))); // Right down
            color = color.add(colRay4.get(4));
        }

        // Returns a list with four current corners
        // colors, and the average of the colors
        colRayList.add(color.reduce(4));
        return colRayList;
    }


    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object - with multi-threading
     */
    private void renderImageThreaded() {
        final int nX = _imageWriter.getNx();
        final int nY = _imageWriter.getNy();
        final Pixel thePixel = new Pixel(nY, nX);
        // Generate threads
        Thread[] threads = new Thread[threadsCount];
        for (int i = threadsCount - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();
                while (thePixel.nextPixel(pixel))
                    castRay(nX, nY, pixel.col, pixel.row);
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

    /**
     * Go through all the pixels and calculate their color and paint them
     */
    public void renderImage() {
        try {

            // Check if have a image writer object
            if (_imageWriter == null) {
                throw new MissingResourceException("Missing imageWriter", "ImageWriter", "String imageName/ int nX/ int nY");
            }

            // Check if have a camera object
            if (_camera == null) {
                throw new MissingResourceException("Missing camera", "Camera", "Point3D p0/ Vector vUp/ Vector vTo");
            }

            // Check if have ray tracer (base) object
            if (_rayTracerBase == null) {
                throw new MissingResourceException("Missing rayTracerBase", "RayTracerBase", "Scene scene");
            }

            final int nX = _imageWriter.getNx();
            final int nY = _imageWriter.getNy();
            if (threadsCount == 0)
                for (int i = 0; i < nY; ++i)
                    for (int j = 0; j < nX; ++j)
                        castRay(nX, nY, j, i);
            else
                renderImageThreaded();
        } catch (MissingResourceException e) {
            // If one of the objects are null then throw UnsupportedOperationException to the user
            throw new UnsupportedOperationException("No implement yet:" + e.getClassName());
        }
    }

    /**
     * Print a grid on the image
     *
     * @param interval The interval between all grid lines (int)
     * @param color    The color of the lines (Color)
     */
    public void printGrid(int interval, Color color) {

        // Check if have a image writer object
        if (_imageWriter == null) {
            throw new MissingResourceException("Missing imageWriter", "ImageWriter", "String imageName/ int nX/ int nY");
        }

        // Create the grid
        for (int i = 0; i < _imageWriter.getNx(); i++) {
            for (int j = 0; j < _imageWriter.getNy(); j++) {
                if ((i % interval == 0) || (j % interval == 0))
                    // Paint the current pixel
                    _imageWriter.writePixel(i, j, color);
            }
        }
    }

    /**
     * Produce to the image
     */
    public void writeToImage() {

        // Check if have a image writer object
        if (_imageWriter == null) {
            throw new MissingResourceException("Missing imageWriter", "ImageWriter", "String imageName/ int nX/ int nY");
        }
        // Write to image
        _imageWriter.writeToImage();
    }
}
