package canvas.dohr.canvasapp;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by wes on 9/01/18.
 */

public class MainThread extends Thread{
    public static final int MAX_FPS = 60;
    private double averageFPS;
    private final SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    void setRunning(boolean running){
        this.running = running;
    }

    public boolean isRunning(){
        return this.running;
    }

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run(){
        long startTime;
        double timeMillis = 1000/MAX_FPS;
        double waitTime;
        int frameCount = 0;
        double totalTime = 0;
        double targetTime = 1000/MAX_FPS;

        while(running){
            startTime = System.nanoTime();
            canvas = null;

            try{
                canvas = this.surfaceHolder.lockCanvas();
                synchronized(surfaceHolder){
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            } catch(Exception e){
                e.printStackTrace();
            } finally {
                if(canvas != null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch(Exception e){e.printStackTrace();}
                }
            }
            timeMillis = (System.nanoTime() - startTime)/1000000;
            waitTime = targetTime - timeMillis;
            try{
                if(waitTime > 0){
                    sleep((long)waitTime);
                }
            }catch(Exception e){e.printStackTrace();}

            totalTime += System.nanoTime() - startTime;
            frameCount++;

            if(frameCount == MAX_FPS){
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
                Log.v("FPS","FPS" + averageFPS);
            }
        }
    }
}
