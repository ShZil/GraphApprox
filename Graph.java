import java.awt.*;

public class Graph {
  float[][] points;
  int thickness = 15;
  float[] params;

  public static float convertionFactor = 100f;
  private static final float range = 5f;
  private static final float jump = 0.01f;
  private static final int pointsInGraph = 2 * (int)(range / jump);
  private static final boolean drawFinalLine = false;
  private static final float lerpOver = 50;

  protected Color color;

  public Graph(float[][] data, Main m, Color c) {
    this.points = transform(data, m);
    this.color = c;
  }

  public Graph(float[] parameters, Main m, Color c) {
    params = parameters;
    float maxX = m.size.width / 200f;
    this.points = new float[pointsInGraph][2];
    for (int i = 0; i < pointsInGraph; i++) {
      float x = i * jump - range;
      this.points[i][0] = x;
      this.points[i][1] = m.f(parameters, x);
    }
    this.points = transform(this.points, m);
    this.color = c;
  }

  private float[][] transform(float[][] p, Main m) {
    float[][] out = new float[p.length][2];
    for (int i = 0; i < p.length; i++) {
      out[i] = new float[]{(p[i][0] * convertionFactor) + ((float)m.size.width * 0.5f), (p[i][1] * -convertionFactor) + m.size.height/2 - thickness - 39};
    }
    return out;
  }

  public void paint(Graphics g) {
    g.setColor(this.color);
    for (int i = 0; i < points.length; i++) {
      g.fillRect((int)points[i][0], (int)points[i][1], thickness, thickness);
    }
  }

  public void paint(Graphics g, boolean connect) {
    if (!connect) {
      paint(g);
    } else {
      g.setColor(this.color);
      for (int i = 0; i < points.length; i++) {
        g.fillRect((int)points[i][0], (int)points[i][1], thickness, thickness);
        if (i > 0) {
          for (float j = 1; j < lerpOver; j++) {
            float x1 = points[i][0];
            float y1 = points[i][1];
            float x2 = points[i-1][0];
            float y2 = points[i-1][1];
            float ratio = lerpOver - j;
            float[] loc = lerp(x1, x2, y1, y2, ratio, j);
            g.fillRect((int)(loc[0]), (int)(loc[1]), thickness/2, thickness/2);
          }
        }
      }

      if (drawFinalLine) {
        g.drawLine((int)points[0][0],
        (int)points[0][1],
        (int)points[points.length - 1][0],
        (int)points[points.length - 1][1]
        );
      }
    }
  }

  private float[] lerp(float x1, float x2, float y1,
  float y2, float m, float n)
  {
    // Applying section formula
    float x = ((n * x1) + (m * x2)) / (m + n);
    float y = ((n * y1) + (m * y2)) / (m + n);

    return new float[]{x, y};
  }
}
