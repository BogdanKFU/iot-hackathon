package ru.kpfu.group11501.amirbogdan.iothackathon;

/**
 * Created by Amir Kadyrov
 * Date: 30.03.2019
 */
public class Coordinate {
    private double x;
    private double y;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Coordinate add(Coordinate coordinate){
        return new Coordinate(this.x + coordinate.getX(), this.y + coordinate.getY());
    }

    public Coordinate subtract(Coordinate coordinate){
        return new Coordinate(this.x - coordinate.getX(), this.y - coordinate.getY());
    }

    public double distance(Coordinate coordinate){
        return Math.sqrt((this.x - coordinate.getX()) * (this.x - coordinate.getX()) + (this.y - coordinate.getY()) * (this.y - coordinate.getY()));
    }

    @Override
    public String toString() {
        return "x: " + this.x + "; y: " + this.y;
    }
}
