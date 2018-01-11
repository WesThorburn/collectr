package canvas.dohr.canvasapp;

/**
 * Created by wes on 10/01/18.
 */

public class Stats {
    private int timeInGame = 0;
    private double maxRadius = 0;
    private double maxSpeed = 0;
    private int numOrbsCollected = 0;

    public void setTime(int time){
        timeInGame = time;
    }

    public void setRadius(double radius){
        if(radius > maxRadius){
            maxRadius = radius;
        }
    }

    public void setSpeed(double speed){
        if(speed > maxSpeed){
            maxSpeed = speed;
        }
    }

    public void collectOrb(){
        numOrbsCollected++;
    }

    public String getTimeInGame(){
        return timeInGame + " seconds";
    }

    public String getMaxRadius(){
        return maxRadius + "cm";
    }

    public String getMaxSpeed(){
        return maxSpeed + "m/s";
    }

    public int getNumOrbsCollected(){
        return numOrbsCollected;
    }
}
