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
    if (box.contains(new Point(x, y))) {
      float ratio = (float)(x - box.x) / box.width;
      float difference = max - min;
      return (float)Math.floor((ratio * difference + min)/jump) * jump;
    }
    return Float.NaN;
  }

  void render(Graphics g) {
    g.setColor(new Color(0.8f, 0.8f, 0.8f));
    g.drawRect(box.x, box.y, box.width, box.height);
  }
}
