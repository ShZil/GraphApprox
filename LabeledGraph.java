import java.awt.*;

class LabeledGraph extends Graph {
  String[] labels;

  enum Direction {
    HORIZONTAL, VERTICAL
  }

  public LabeledGraph(float[][] data, Main m, Color c, String[] la) {
    super(data, m, c);
    labels = la;
  }

  public void paint(Graphics g, boolean connect, boolean label, Direction dir) {
    if (!connect) {
      super.paint(g);
    } else {
      g.setColor(super.color);
      for (int i = 0; i < points.length; i++) {
        int x = (int)points[i][0];
        int y = (int)points[i][1];
        g.fillRect(x, y, thickness, thickness);
        if (i > 0) {
          g.drawLine((int)points[i-1][0],
                    (int)points[i-1][1],
                    x,
                    y
                    );
          g.drawLine((int)(points[i-1][0] + thickness),
                    (int)(points[i-1][1] + thickness),
                    x + thickness,
                    y + thickness
                    );
        }
        if (label) {
          g.setFont(new Font("Arial", Font.PLAIN, thickness));
          try {
            if (dir == Direction.VERTICAL) {
              g.drawString(labels[i],
                          (int)(x + thickness * 1.5),
                          y + thickness);
            }
            if (dir == Direction.HORIZONTAL) {
              g.drawString(labels[i],
                          x,
                          y + thickness * 2);
            }
          } catch (IndexOutOfBoundsException e) {
            break;
          }
        }
      }
    }
  }
}
