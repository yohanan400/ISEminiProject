package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;

import java.util.MissingResourceException;

/**
 * Render class responsible to render the scene
 *
 * @author Aviel Buta and Yakir Yohanan
 */
public class Render {

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
            System.out.println(cores);
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

    /**
     * Cast ray from camera in order to color a pixel
     * @param nX resolution on X axis (number of pixels in row)
     * @param nY resolution on Y axis (number of pixels in column)
     * @param col pixel's column number (pixel index in row)
     * @param row pixel's row number (pixel index in column)
     */
    private void castRay(int nX, int nY, int col, int row) {
        Color color = Color.BLACK;
        for (int k = 1; k <= 10; k++) {
            Ray ray = _camera.constructRayThroughPixel(nX, nY, col, row);
            color = color.add(_rayTracerBase.traceRay(ray));
        }
        _imageWriter.writePixel(col, row, color.reduce(10));
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
