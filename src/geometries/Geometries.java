package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {

    // we choose linkList because we need to go through all the objects iteratively
    private List<Intersectable> _intersectables = new LinkedList<>();


    public Geometries() {
        // nothing to add
    }

    public Geometries(Intersectable... intersectable) {
        add(intersectable);
    }

    public void add(Intersectable... intersectable) {
        _intersectables.addAll(Arrays.asList(intersectable));
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> result = new ArrayList<>();

        for (Intersectable item : _intersectables){
            if (item.findIntersections(ray) != null) {
                int i = item.findIntersections(ray).size();
                for (int j = 0; j < i; j++) {
                    result.add(item.findIntersections(ray).get(j));
                }
            }
        }

        return result;
    }
}
