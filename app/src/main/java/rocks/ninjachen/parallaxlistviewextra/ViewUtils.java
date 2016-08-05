package rocks.ninjachen.parallaxlistviewextra;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by ninja on 8/3/16.
 */
public class ViewUtils {
    /**
     * draw a double circle, first draw a tintcolor out-circle then a white in-circle
     *
     * @param tintColor
     * @param isHollow
     * @return
     */
    public static Bitmap drawDoubleCircle(int tintColor, boolean isHollow) {
        final int r = BootstrapApplication.getInstance().getResources().getDimensionPixelSize(R.dimen.dot_radius_in_map_list);
        return drawDoubleCircle(tintColor, r, true, Color.WHITE, r / 2);
    }

    /**
     *
     * @param outerColor
     * @param outerRadius
     * @param isHollow is empty inside
     * @param innerColor
     * @param innerRadius
     * @return
     */
    public static Bitmap drawDoubleCircle(int outerColor, int outerRadius, boolean isHollow, int innerColor, int innerRadius) {
        //define bitmap is 48x48 pixel
        float radius = outerRadius;
        final int x = (int) (2 * radius / 3 * 4);
        final int y = x;
        Bitmap outBitmap = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(outBitmap);
        float centX = x / 2;
        float centY = y / 2;
        Paint paint = new Paint();
        paint.setColor(outerColor);
        canvas.drawCircle(centX, centY, radius, paint);
        if (isHollow) {
            paint.setColor(innerColor);
            radius = innerRadius;
            canvas.drawCircle(centX, centY, radius, paint);
        }
        return outBitmap;
    }
}
