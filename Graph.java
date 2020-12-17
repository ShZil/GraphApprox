import java.awt.*;

public class Graph {
  float[][] points;
  int thickness = 15;
  float[] params;

  public static float convertionFactor = 100f;
  private static final float range = 5f;
  private static final float jump = 0.1f;
  private static final int pointsInGraph = 2 * (int)(range / jump);

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
    // System.out.println("Color: "+this.color.getRed()+","+this.color.getGreen()+","+this.color.getBlue());
    for (int i = 0; i < points.length; i++) {
      g.fillRect((int)points[i][0], (int)points[i][1], thickness, thickness);
      // System.out.println("Drawn at: ("+points[i][0]+","+points[i][1]+")");
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
          g.drawLine((int)points[i-1][0], (int)points[i-1][1],(int)points[i][0], (int)points[i][1]);
          g.drawLine((int)(points[i-1][0] + thickness), (int)(points[i-1][1] + thickness),(int)(points[i][0] + thickness), (int)(points[i][1] + thickness));
        }
      }
    }
  }
}
