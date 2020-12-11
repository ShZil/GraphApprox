import java.awt.*;
import java.awt.geom.Point2D;

public class Slider {
  Rectangle box;
  Color c;
  float min;
  float max;
  float jump;

  Slider(int x, int y, int w, int h, Color clr, float min, float max, float jump) {
    box = new Rectangle(x, y, w, h);
    c = clr;
    this.min = min;
    this.max = max;
    this.jump = jump;
  }

  float click(int x, int y) {
    if (box.contains(new Point2D(x, y))) {
      return this.box.width / jump;
    }
    return Float.NaN;
  }
}
