# GraphApprox
This repository provides a ` java.awt.Canvas ` on which a given graph is drawn, and the computer tries to estimate it.

**Please read `README.md` before usage!**

### Info:
- Created by ShZil.
- Was made in two full days.
- Uses `rgb` color-space.

### Instructions to Use:
1. Open `Main.java`.
2. You may edit only the following area in the code (If you do otherwise, it might lead to unexpected behavior):
```java
/* START of User-readable code. */
...
/* END of User-readable code. */
```
3. The variables and function you may change are listed below.
4. Once done with interacting and inserting your data points, follow the following:
- 1. Re-Compile all the java files using cmd command:

`C:/Users/You/WhereverYouSavedThisCode> javac *.java`
2. Execute the program:

`C:/Users/You/WhereverYouSavedThisCode> java Main`
- **Enjoy the show!** *Leftclick anywhere within the window to progress.*

### The code you may interact with:
##### Specified in the previous section, chapter 2.
```java
public static final int runsPerClick = 1;
```
```js
{
  min: 0,
  max: 10000, // You may use more at your own risk.
  prefered: 1
}
```
This defines the number of mutation cycles in one user mouse click.

```java
public static final int sizeOfWindow = 1000;
```
```js
{
  min: 100,
  max: 1080, // Depends on screen size.
  prefered: 1000
}
```
Defines the size (width and height) of the square window.
```java
public static final float[] inputGraphColor = new float[]{0f, 1f, 0f};
```
```js
{
  min: [0, 0, 0],
  max: [1, 1, 1],
  prefered: [0, 1, 0] // Lime
}
```
Float array to define the color of the constant graph you inputted.
```java
public static final float[] outputGraphColor = new float[]{1f, 0f, 0f};
```
```js
{
  min: [0, 0, 0],
  max: [1, 1, 1],
  prefered: [1, 0, 0] // Red
}
```
Float array to define the color of the changing computer-generated graph.
```java
public static final Color axisColor = Color.white;
```
```js
{
  min: Color.BLACK,
  max: Color.WHITE,
  prefered: Color.WHITE
}
```
The color of the axes and rendered text.
```java
public static final boolean drawInputConnected = true;
```
```js
{
  min: false,
  max: true,
  prefered: true
}
```
Specifies whether the code should draw lines between the sample points.
```java
public static final boolean drawOutputConnected = true;
```
```js
{
  min: false,
  max: true,
  prefered: true
}
```
Specifies whether the code should draw lines between the computer-generated points.
```java
public static final float[] bgColor = new float[]{0f, 0f, 0f};
```
```js
{
  min: [0, 0, 0],
  max: [1, 1, 1],
  prefered: [0, 0, 0] // Black
}
```
Float array to set the background color of the canvas.
```java
public static final float mutationFactor = 0.005f;
```
```js
{
  min: 0,
  max: 0.1, // You may use more at your own risk.
  prefered: 0.005
}
```
Defines the mutation factor for the function's parameters.
It has no units.
```java
public static final float costRandomizerScale = 2f;
```
```js
{
  min: 0,
  max: 10, // You may use more at your own risk.
  prefered: 2
}
```
Defines the randomized factor for the cost.
It has no units.
```java
public static final int parameterCount = 3;
```
```js
{
  min: 0,
  max: 7,
  prefered: 3
}
```
Number of mutatable parameters. For example, the line `ax + b` has 2 parameters.
```java
public static final float floatPrintAccurecy = 10000f;
```
```js
{
  min: 0,
  max: 1000000,
  prefered: 10000
}
```
The accurecy (decimal places) for printing numbers.
```java
public static final boolean displayCost = true;
```
```js
{
  min: false,
  max: true,
  prefered: true
}
```
Specifies whether to display the current cost in the window, or just in console.
```java
public static final boolean displayEquation = true;
```
```js
{
  min: false,
  max: true,
  prefered: true
}
```
Specifies whether to display the current equation (parameters) in the window, or just in console.
```java
private static String equation = "y = ax² + bx + c";
```
```js
{
  min: 0,
  max: 10000, // You may use more at your own risk.
  prefered: 1
}
```
The equation in `String` format. Used to display and print.
Any letter out of `a, b, c, d, e, f, g` will be replaced with its relative parameter's value.
```java
public static final float[][] samples = new float[][] {
  {0f, 0f},
  {1f, 0.5f},
  {2f, 2f},
  {3f, 4.5f},
  {4f, 5f},
  {4.1f, 5.2f}
};
```
```js
{
  min: {{0, 0}},
  max: {...},
  prefered: [{0, 0}, {1, 0.5}, {2, 2}, {3, 4.5}, {4, 5}, {4.1, 5.2}]
}
```
The input samples with the code tries to approximate.
You may use the given inputs if you have none of your own.
```java
private static float f(float x) {
  return a*x*x + b*x + c;
}
```
```js
{
  min: return 0,
  max: return ..., // You may use more at your own risk.
  prefered: return a*x*x + b*x + c;
}
```
This function is the function format of the equation, and you must input one. Use java code! You may use (not real code):
- Straight: `f(x) = k`
- Line: `f(x) = ax + b`
- Parabola: `f(x) = ax² + bx + c`
- Any polynom up to 6th degree: `f(x) = ax^6 + bx^5 + cx^4 + dx^3 + ex^2 + fx + g`
- Exponentials: `f(x) = a ^ x`
- `java.lang.Math` library.

Any letter out of `a, b, c, d, e, f, g` will be replaced with its relative parameter's value.
