package ru.kpfu.group11501.amirbogdan.iothackathon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amir Kadyrov
 * Date: 30.03.2019
 */
public class Util {
    private static ArrayList getDistances(int a, int b, int c){
        double cos = (a * a + b * b - c * c) / (2 * a * b);
        double sin = Math.sqrt(1 - cos * cos);
        ArrayList data = new ArrayList();
        data.add(c * cos);
        data.add(c * sin);
        return data;
    };
}
