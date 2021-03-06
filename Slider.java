import java.awt.*;
import java.awt.geom.Point2D;

public class Slider {
  private static int valueXOffset = 15;

  String name;
  Rectangle box;
  Color c;
  float min;
  float max;
  float jump;
  float value;
  float lastPressedX = 0;

  Slider(String n, int x, int y, int w, int h, Color clr, float min, float max, float jump, float defaultValue) {
    name = n;
    box = new Rectangle(x, y, w, h);
    c = clr;
    this.min = min;
    this.max = max;
    this.jump = jump;
    this.value = defaultValue;
  }

  Slider(String n, float x, float y, float w, float h, Color clr, float min, float max, float jump, float defaultValue) {
    name = n;
    box = new Rectangle((int)x, (int)y, (int)w, (int)h);
    c = clr;
    this.min = min;
    this.max = max;
    this.jump = jump;
    this.value = defaultValue;
  }

  float click(int x, int y) {
    if (box.contains(new Point(x, y))) {
      float ratio = (float)(x - box.x) / box.width;
      float difference = max - min;
      float val = (float)Math.floor((ratio * difference + min)/jump) * jump;
      value = val;
      lastPressedX = x;
      return val;
    }
    return Float.NaN;
  }

  void render(Graphics g) {
    // TODO: Make the selected part appear different!

    g.setColor(c);

    g.drawRect(box.x, box.y, box.width, box.height);

    float off = box.width / ((max - min) / jump);

    for (float i = 0; i <= box.width; i += off) {
      g.drawLine((int)(box.width/2 + i),
                box.y,
                (int)(box.width/2 + i),
                box.y + box.height
                );
      float ratio = i / box.width;
      float difference = max - min;
      float val = (float)Math.floor((ratio * difference + min) / jump) * jump;
      if (value == val) {
        g.fillRect((int)(box.width/2 + i),
                  box.y,
                  (int)(off+2),
                  box.height
                  );
      }
    }

    g.setFont(new Font("Arial", Font.PLAIN, box.height));
    /*g.drawString(name,
                box.x - (name.length() * box.height / 2),
                (int)(box.y + box.height*0.8)
                );*/
    g.drawString(Main.numberFormat(value),
                box.x + box.width + Slider.valueXOffset,
                (int)(box.y + box.height*0.8)
                );
  }
}
