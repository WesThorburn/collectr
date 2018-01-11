package canvas.dohr.canvasapp;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

import java.util.Random;

/**
 * Created by wes on 11/01/18.
 */

public class Pickup {
    private Point location;
    private boolean active;
    private int radius = Constants.PICKUP_RADIUS;

    public Pickup(){
        location = new Point(0, 0);
        spawn();
    }

    public void draw(Canvas canvas){
        if(!active){
            return;
        }
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        canvas.drawCircle(location.x, location.y, radius, paint);
    }

    public void spawn(){
        Random r = new Random();
        location.set(r.nextInt(Constants.SCREEN_WIDTH), r.nextInt(Constants.SCREEN_HEIGHT));
        active = true;
    }

    public Point getLocation(){
        return location;
    }

    public int getRadius(){
        return radius;
    }

    public boolean isActive(){
        return active;
    }

    public void collect(){
        active = false;
        spawn();
    }
}
