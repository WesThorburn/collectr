package canvas.dohr.canvasapp;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

import java.util.Random;

/**
 * Created by wes on 9/01/18.
 */

public class Player implements GameObject {

    private int color;
    private Point location;
    private float absSpeed = 4;
    private int timeAlive = 0;
    private double spdX;
    private double spdY;
    private float radius;
    private double directionRadians;
    private boolean inSession;

    public Player(){
        this.color = Constants.STARTING_COLOR;
        this.location = new Point(Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT/2);
        this.radius = Constants.STARTING_RADIUS;
        updateSpeed();
        inSession = true;
    }

    @Override
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        //Outline
        paint.setColor(Color.GRAY);
        canvas.drawCircle(location.x, location.y, radius + Constants.PLAYER_OUTLINE_WIDTH, paint);

        //Player body
        paint.setColor(color);
        canvas.drawCircle(location.x, location.y, radius, paint);
    }

    @Override
    public void update(Point point){
        if(!inSession){
            return;
        }
        timeAlive++;
        updateSpeed();
        updateLocation(point);
        checkStillInSession();
    }

    public Point getLocation(){
        return new Point(location.x, location.y);
    }

    public float getRadius(){
        return radius;
    }

    public boolean inSession(){
        return inSession;
    }

    private void updateSpeed(){
        this.spdX = Math.cos(directionRadians) * absSpeed;
        this.spdY = Math.sin(directionRadians) * absSpeed;
    }

    public void updateLocation(Point point){
        double testXPosition = point.x += this.spdX;
        double testYPosition = point.y += this.spdY;

        if(testXPosition < radius ||
                testYPosition < radius ||
                testXPosition > Constants.SCREEN_WIDTH - radius ||
                testYPosition > Constants.SCREEN_HEIGHT - radius){
            radius -= 1;
        }
        else{
            location.set(point.x += this.spdX, point.y += this.spdY);
        }
    }

    public void updateDirection(Point point){
        directionRadians = Math.atan2(location.y - point.y, location.x - point.x);
        directionRadians += Math.PI; //Corrects for 180 degree offset
    }

    public void collectPickup(){
        absSpeed *= 1.01;
    }

    public void checkStillInSession(){
        if(radius > 1){
            inSession = true;
        }
        else{
            inSession = false;
        }
    }
}
