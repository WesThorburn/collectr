package canvas.dohr.canvasapp;

import android.graphics.Canvas;
import android.graphics.Point;

/**
 * Created by wes on 9/01/18.
 */

public interface GameObject {
    public void draw(Canvas canvas);
    public void update(Point point);
}
