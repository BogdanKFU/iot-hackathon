package ru.kpfu.group11501.amirbogdan.iothackathon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amir Kadyrov
 * Date: 30.03.2019
 */
public class Util {
    private static final Coordinate t1 = new Coordinate(0,0);
    private static final Coordinate t2 = new Coordinate(4.4, 0);
    private static final Coordinate t3 = new Coordinate(4.4, 7);

    private static Coordinate getCoordinate(double r1, double r2, double r3){
        double d = t1.distance(t2);
        double a = (r1 * r1 - r2 * r2 + d * d) / (2 * d);
        double h = Math.sqrt(r1 * r1 - a * a);
        double x0 = t1.getX() + (a * (t2.getX() - t1.getX())/d);
        double y0 = t1.getY() + (a * (t2.getY() - t1.getY())/d);
        Coordinate coord1 = new Coordinate(x0 + (t2.getY() - t1.getY())/d * h, y0 - (t2.getX() - t1.getX())/d * h);
        Coordinate coord2 = new Coordinate(x0 - (t2.getY() - t1.getY())/d * h, y0 + (t2.getX() - t1.getX())/d * h);
        if (Math.abs(r3 - coord1.distance(t3)) > Math.abs(r3 - coord2.distance(t3))){
            return coord2;
        }
        else {
            return coord1;
        }
    };


}
