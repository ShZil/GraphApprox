/*
* GraphApprox
* by ShZil
* -- Methods used:
* ---- Directional determained cost-proportional mutation.
* ---- Render by evaluating points in jumps.
* ---- Cost evaluation by sum of absolute differences.
* ---- Function evaluation by Quadratic Parabola.
*/

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.text.MessageFormat;

class Main extends Canvas {
  // This code mutates every parameter to minimize the cost,
  // but doesn't think about parameters affecting each other.

  /* START of User-readable and changable code. */
  public static final int runsPerClick = 1;
  public static final int sizeOfWindow = 1000;
  public static final float[] inputGraphColor = new float[]{0f, 1f, 0f};
  public static final float[] outputGraphColor = new float[]{1f, 0f, 0f};
  public static final Color axisColor = Color.white;
  public static final boolean drawInputConnected = true;
  public static final boolean drawOutputConnected = true;
  public static final float[] bgColor = new float[]{0f, 0f, 0f};
  public static final float mutationFactor = 0.005f;
  public static final float costRandomizerScale = 2f;
  public static final int parameterCount = 2;
  public static final float floatPrintAccurecy = 10000f;
  public static final boolean displayCost = true;
  public static final boolean displayEquation = true;
  private static String equation = "y = ax² + bx + c";

  public static final float[][] samples = new float[][] {
    {0f, 0f},
    {1f, 0.5f},
    {2f, 2f},
    {3f, 4.5f},
    {4f, 5f},
    {4.1f, 5.2f}
  };

  private static float f(float x) {
    return a*x*x + b*x + c;
  }

  /* END of User-readable and changable code. */
  public static float a = 0f;
  public static float b = 0f;
  public static float c = 0f;
  public static float d = 0f;
  public static float e = 0f;
  public static float f = 0f;
  public static float g = 0f;
  Dimension size;
  Graph graph;
  float[] parameters = new float[parameterCount];
  private static float cost;

  public Main() {
    this.size = new Dimension(Main.sizeOfWindow, Main.sizeOfWindow);
    this.graph = new Graph(Main.samples, this, new Color(Main.inputGraphColor[0], Main.inputGraphColor[1], Main.inputGraphColor[2]));
  }

  public static void main(String[] args) {
    Main m = new Main();
    JFrame f = new JFrame("GraphApprox by ShZil");
    f.getContentPane().setBackground(new Color(Main.bgColor[0], Main.bgColor[1], Main.bgColor[2]));
    f.add(m);
    f.setSize(new Dimension(m.size.width, m.size.height));
    f.setVisible(true);
    m.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        for (int q = 0; q < Main.runsPerClick; q++) {
          m.mouseListener(e.getX(), e.getY());
          m.repaint();
        }
      }
    });
  }

  public void paint(Graphics g) {
    paintAxis(g);
    graph.paint(g, drawInputConnected);
    Graph current = new Graph(parameters, this, new Color(Main.outputGraphColor[0], Main.outputGraphColor[1], Main.outputGraphColor[2]));
    current.paint(g, drawOutputConnected);
    g.setColor(axisColor);
    if (displayCost) {
      g.setFont(new Font("Arial", Font.PLAIN, 30));
      g.drawString("Cost = "+numberFormat(cost), 20, 40);
    }
    if (displayEquation) {
      g.setFont(new Font("Arial", Font.PLAIN, 20));
      g.drawString(equationFormat(parameters), 10, 70);
    }
  }

  public void mouseListener(int x, int y) {
    // System.out.println("Click registered at X: "+x+", Y:"+y);
    boolean[] parameterMutationDirection = new boolean[parameters.length];
    float[] currentMutated = parameters.clone();
    float currentCost = evaluateCost(currentMutated, Main.samples);
    Main.cost = currentCost;
    System.out.println("The cost is: "+currentCost);
    for (int i = 0; i < parameters.length; i++) {
      currentCost += mutateALittle(Main.costRandomizerScale);
      currentMutated[i] += mutate(parameterMutationDirection[i] ? -1 : 1, currentCost);
      float cost = evaluateCost(currentMutated, Main.samples);
      if (cost > currentCost) {
        parameterMutationDirection[i] = !parameterMutationDirection[i];
      }
      // System.out.println("The cost is: "+cost);
    }
    for (int i = 0; i < parameters.length; i++) {
      parameters[i] += mutate(parameterMutationDirection[i] ? -1 : 1, currentCost);
    }
    System.out.println(equationFormat(parameters));
  }

  private float evaluateCost(float[] parameters, float[][] s) {
    float sum = 0;
    for (int i = 0; i < s.length; i++) {
      sum += Math.abs((s[i][1]-f(parameters, s[i][0])));
    }
    return sum;
  }

  public float f(float[] parameters, float x) {
    float a = 0 < parameterCount ? parameters[0] : 0f;
    float b = 1 < parameterCount ? parameters[1] : 0f;
    float c = 2 < parameterCount ? parameters[2] : 0f;
    float d = 3 < parameterCount ? parameters[3] : 0f;
    float e = 4 < parameterCount ? parameters[4] : 0f;
    float f = 5 < parameterCount ? parameters[5] : 0f;
    float g = 6 < parameterCount ? parameters[6] : 0f;

    Main.a = a;
    Main.b = b;
    Main.c = c;
    Main.d = d;
    Main.e = e;
    Main.f = f;
    Main.g = g;

    return f(x);
  }

  private float mutate(int dir, float cost) {
    return Main.mutationFactor * (float)dir * cost * (float)Math.random();
  }

  private float mutateALittle(float scale) {
    return scale * (float)((Math.random() * -1)+0.5);
  }

  public String equationFormat(float[] parameters) {
    float a = 0 < parameterCount ? parameters[0] : 0f;
    float b = 1 < parameterCount ? parameters[1] : 0f;
    float c = 2 < parameterCount ? parameters[2] : 0f;
    float d = 3 < parameterCount ? parameters[3] : 0f;
    float e = 4 < parameterCount ? parameters[4] : 0f;
    float f = 5 < parameterCount ? parameters[5] : 0f;
    float g = 6 < parameterCount ? parameters[6] : 0f;

    a = numberFormat(a);
    b = numberFormat(b);
    c = numberFormat(c);
    d = numberFormat(d);
    e = numberFormat(e);
    f = numberFormat(f);
    g = numberFormat(g);

    return MessageFormat.format(equation
    .replaceAll("a", "{0}")
    .replaceAll("b", "{1}")
    .replaceAll("c", "{2}")
    .replaceAll("d", "{3}")
    .replaceAll("e", "{4}")
    .replaceAll("f", "{5}")
    .replaceAll("g", "{6}")
    , a, b, c, d, e, f, g);

    /*return equation
    .replaceAll("a", String.valueOf(a))
    .replaceAll("b", String.valueOf(b))
    .replaceAll("c", String.valueOf(c))
    .replaceAll("d", String.valueOf(d))
    .replaceAll("e", String.valueOf(e))
    .replaceAll("f", String.valueOf(f))
    .replaceAll("g", String.valueOf(g));*/
  }

  private float numberFormat(float x) {
    return ((float)((int)(x * Main.floatPrintAccurecy)) / Main.floatPrintAccurecy);
  }

  private void paintAxis(Graphics g) {
    Graph x = new Graph(new float[][]{
      {-10, 0},
      {10, 0}
    }, this, axisColor);
    Graph y = new Graph(new float[][]{
      {0, -10},
      {0, 10}
    }, this, axisColor);
    x.paint(g, true);
    y.paint(g, true);
  }
}
