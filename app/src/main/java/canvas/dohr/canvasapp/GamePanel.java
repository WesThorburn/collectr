package canvas.dohr.canvasapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by wes on 9/01/18.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
    private MainThread thread;
    private Pickup pickup;
    private Player player;
    private Point playerPoint;

    public GamePanel(Context context){
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        pickup = new Pickup();
        player = new Player();
        playerPoint = player.getLocation();
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        while(!thread.isRunning()){
            Log.w("RETRY", "Trying thread start");
            try{
                thread.setRunning(false);
                thread.join();
            } catch(Exception e){ e.printStackTrace();}
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                player.updateDirection(new Point((int)event.getX(), (int)event.getY()));
        }
        return true;
    }

    public void update(){
        player.update(playerPoint);
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if(player.inSession()){
            canvas.drawColor(Color.WHITE);
            pickup.draw(canvas);
            player.draw(canvas);
            drawMap(canvas);
            detectPickupCollisions();
        }
        else{
            showStats(canvas);
        }
    }

    public void drawMap(Canvas canvas){
        Paint borderPaint = new Paint();
        borderPaint.setColor(Color.RED);
        Rect borderLeft = new Rect(0, 0, Constants.BORDER_WIDTH, Constants.SCREEN_HEIGHT);
        Rect borderTop = new Rect(0, 0, Constants.SCREEN_WIDTH, Constants.BORDER_WIDTH);
        Rect borderRight = new Rect(Constants.SCREEN_WIDTH - Constants.BORDER_WIDTH, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        Rect borderBottom = new Rect(0, Constants.SCREEN_HEIGHT - Constants.BORDER_WIDTH, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        canvas.drawRect(borderLeft, borderPaint);
        canvas.drawRect(borderTop, borderPaint);
        canvas.drawRect(borderRight, borderPaint);
        canvas.drawRect(borderBottom, borderPaint);
    }

    public void detectPickupCollisions(){
        if(!pickup.isActive()){
            return;
        }
        Point playerPt = player.getLocation();
        Point pickupPt = pickup.getLocation();
        double distance = Math.sqrt(Math.pow(pickupPt.x - playerPt.x, 2) + Math.pow(pickupPt.y - playerPt.y, 2));
        if(distance < player.getRadius() + pickup.getRadius()){
            player.collectPickup();
            pickup.collect();
        }
    }

    public void showStats(Canvas canvas){
        canvas.drawColor(Color.GRAY);
    }
}
