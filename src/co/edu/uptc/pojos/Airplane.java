package co.edu.uptc.pojos;

import java.util.List;

public class Airplane implements Cloneable{
    private final int id;
    private int posX;
    private int posY;
    private List<Integer> yPositions;
    private List<Integer> xPositions;
    private double speed;
    private int colorNumber;
    private double angle;
    private boolean isEditedRoute;
    public Airplane(int id, List<Integer> yPositions, List<Integer> xPositions, double speed, int colorNumber) {
        this.id = id;
        this.yPositions = yPositions;
        this.xPositions = xPositions;
        this.speed = speed;
        this.colorNumber = colorNumber;
        this.isEditedRoute = false;
    }

    public int getColorNumber() {
        return colorNumber;
    }

    public void setColorNumber(int colorNumber) {
        this.colorNumber = colorNumber;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public boolean isEditedRoute() {
        return isEditedRoute;
    }

    public void setEditedRoute(boolean editedRoute) {
        isEditedRoute = editedRoute;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public List<Integer> getyPositions() {
        return yPositions;
    }

    public void setyPositions(List<Integer> yPositions) {
        this.yPositions = yPositions;
    }

    public List<Integer> getxPositions() {
        return xPositions;
    }

    public void setxPositions(List<Integer> xPositions) {
        this.xPositions = xPositions;
    }



    public int getId() {
        return id;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public Airplane clone() {
        try {
            return (Airplane) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
