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

  /* START of User-readable code. */
  public static final int runsPerClick = 100;
  public static final int sizeOfWindow = 1000;
  public static final float[] inputGraphColor = new float[]{0f, 1f, 0f};
  public static final float[] outputGraphColor = new float[]{1f, 0f, 0f};
  public static final Color axisColor = Color.white;
  public static final boolean drawInputConnected = true;
  public static final boolean drawOutputConnected = false;
  public static final float[] bgColor = new float[]{0f, 0f, 0f};
  public static final float mutationFactor = 0.0005f;
  public static final float costRandomizerScale = 2f;
  public static final int parameterCount = 4;
  public static final float floatPrintAccurecy = 10000f;
  public static final float GUIIndent = 15f; // TODO: Add this to README.md
  public static final float GUILineHeight = 45f; // TODO: Add this to README.md
  public static final boolean displayCost = true;
  public static final boolean displayEquation = true;
  public static final boolean displayMethod = true; // TODO: Add this to README.md
  public static final float GUIScale = 1f;
  public static final float textSize = 35f; // TODO: Add this to README.md
  public static final Font defaultFont = new Font("Arial", Font.BOLD, (int)(textSize*GUIScale)); // TODO: Add this to README.md
  public static final EvaluationMethod method = EvaluationMethod.SUBSTITUTION;
  private static String equation = "y = ax^3 + bx^2 + cx + d";
  // TODO: Update defaults at README.md

  public static final float[][] samples = new float[][] {
    {0f, 5f},
    {1f, 2f},
    {2.6f, -1f},
    {3f, 0.2f},
    {4f, 5f},
    {4.1f, 1f}
  };

  private static float f(float x) {
    return a*x*x*x + b*x*x + c*x + d;
  }

  /* END of User-readable code. */

  enum EvaluationMethod {
    SUBSTITUTION, LINE
  }

  public static float a = 0f;
  public static float b = 0f;
  public static float c = 0f;
  public static float d = 0f;
  public static float e = 0f;
  public static float f = 0f;
  public static float g = 0f;
  Dimension size;
  Graph graph;
  Graph current;
  float[] parameters = new float[parameterCount];
  private static float cost;

  public static final Slider scaleSlider = new Slider("Scale", 120*GUIScale, 90*GUIScale, 240*GUIScale, 40*GUIScale, Color.white, 25f, 250f, 25f, Graph.convertionFactor);

  public Main() {
    this.size = new Dimension(Main.sizeOfWindow, Main.sizeOfWindow);
    graphsInit();
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
    current.paint(g, drawOutputConnected);
    this.drawStrings(g, axisColor, new String[]{
      "Cost = "+Main.numberFormat(cost),
      equationFormat(parameters),
      "Scale:",
      "Method: "+method.name()
    }, new boolean[]{
      displayCost,
      displayEquation,
      true,
      displayMethod
    });
    scaleSlider.render(g);
  }

  public void mouseListener(int x, int y) {
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
    }
    for (int i = 0; i < parameters.length; i++) {
      parameters[i] += mutate(parameterMutationDirection[i] ? -1 : 1, currentCost);
    }
    System.out.println(equationFormat(parameters));


    float val = scaleSlider.click(x, y);
    if (!Float.isNaN(val)) {
      Graph.convertionFactor = val;
      System.out.println("Val = "+ val);
    }
    graphsInit();
  }

  public void graphsInit() {
    current = new Graph(parameters, this, new Color(Main.outputGraphColor[0], Main.outputGraphColor[1], Main.outputGraphColor[2]));
    graph = new Graph(Main.samples, this, new Color(Main.inputGraphColor[0], Main.inputGraphColor[1], Main.inputGraphColor[2]));
  }

  private void drawStrings(Graphics g, Color c, String[] a, boolean[] b) {
    g.setFont(defaultFont);
    g.setColor(c);
    for (int i = 0; i < a.length; i++) {
      if (b[i]) {
        g.drawString(a[i], (int)(GUIIndent*GUIScale), (int)((30+GUILineHeight*i)*GUIScale));
      }
    }
  }

  private float evaluateCost(float[] parameters, float[][] s) {
    float sum = 0;
    if (method == EvaluationMethod.SUBSTITUTION) {
      for (int i = 0; i < s.length; i++) {
        sum += Math.abs((s[i][1]-f(parameters, s[i][0])));
      }
    }
    if (method == EvaluationMethod.LINE) {
      int i = 0;
      sum += Math.abs((s[i][1]-f(parameters, s[i][0])));
      i = s.length - 1;
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
    float af = 0 < parameterCount ? parameters[0] : 0f;
    float bf = 1 < parameterCount ? parameters[1] : 0f;
    float cf = 2 < parameterCount ? parameters[2] : 0f;
    float df = 3 < parameterCount ? parameters[3] : 0f;
    float ef = 4 < parameterCount ? parameters[4] : 0f;
    float ff = 5 < parameterCount ? parameters[5] : 0f;
    float gf = 6 < parameterCount ? parameters[6] : 0f;

    String a = Main.numberFormat(af);
    String b = Main.numberFormat(bf);
    String c = Main.numberFormat(cf);
    String d = Main.numberFormat(df);
    String e = Main.numberFormat(ef);
    String f = Main.numberFormat(ff);
    String g = Main.numberFormat(gf);

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

  public static String numberFormat(float x) {
    return "" + (float)((int)(x * Main.floatPrintAccurecy)) / Main.floatPrintAccurecy;
  }

  private void paintAxis(Graphics g) {
    final int len = 41;

    float[][] pointsX = new float[len][2];
    float[][] pointsY = new float[len][2];
    String[] textX = new String[len];
    String[] textY = new String[len];

    for (int l = 0; l < len; l++) {
      float v = (float)(l - Math.floor(len/2));
      pointsX[l] = new float[]{v, 0};
      pointsY[l] = new float[]{0, v};
      textX[l] = String.valueOf((int)v);
      textY[l] = String.valueOf((int)v);
    }

    LabeledGraph x = new LabeledGraph(pointsX, this, axisColor, textX);
    LabeledGraph y = new LabeledGraph(pointsY, this, axisColor, textY);
    x.paint(g, true, true, LabeledGraph.Direction.HORIZONTAL);
    y.paint(g, true, true, LabeledGraph.Direction.VERTICAL);
  }
}
