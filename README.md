# Smoothie Operator 🍊🍌🍓🥤

Smoothie Operator is a 2D artillery game written in Java. The player shoots fruit from a cannon
at a blender that appears in a new, random location on the right side of the screen after
every shot. The player controls the cannon's angle and power with the keyboard. If the player
hits the blender three times, they win the round. If the player misses six times, they lose the
round. Players may start a new round at any time.

## Usage
If your computer is running Java 11 or later, you can download the source code to compile and run
the program.
1. Download the 
[*smoothieoperator/src*](https://github.com/m-siegel/bananaBombsquad/tree/main/smoothieoperator/src)
folder.
2. Either open the folder in an IDE (like VS Code) and run src/main/Main.java
from there, or compile and run src/main/Main.java from the command line:
  2. a. *javac src/main/Main.java*
  2. b. *java src/main/Main*
3. Enjoy!

If your computer is running an older version of Java, you can download the Java byte code to run
the program.
1. Download the
[*smoothieoperator/bin*](https://github.com/m-siegel/bananaBombsquad/tree/main/smoothieoperator/bin)
folder.
2. From the command line, run the command *java bin/main/Main*.

## Program Structure

TODO

## Packages Used

The [java.awt](https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/java/awt/package-summary.html)
and [javax.swing](https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/javax/swing/package-summary.html)
packages provide the foundation for this program. We used AWT classes to control the layout of
panels, colors, fonts, and to enable drawing to the screen. The core components of our GUI,
the window (a JFrame), and different title and game screens (JPanels) within it, as well as a
button (JButton) all come from Swing. We used
[java.awt.event](https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/java/awt/event/package-summary.html)
to listen for, and process, keyboard and mouse events. 
We read in files with
[java.io](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/io/package-summary.html) and
[javax.imageio](https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/javax/imageio/package-summary.html).
We transformed and stored images using [java.awt.geom](https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/java/awt/geom/package-summary.html) 
and 
[java.awt.image](https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/java/awt/image/package-summary.html).
We read in and played music and sound effects using
[javax.sound.sampled](https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/javax/sound/sampled/package-summary.html).
Finally, we got random numbers and used HashMaps and ArrayLists from
[java.util](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/package-summary.html).


## Contributors
**Code**
armen-s, c-lopez, m-siegel, and t-crawley collaboratively designed and created this program.

**Graphics**
c-lopez created the images.

**Sound**
armen-s created the sounds.

## Other Credits -- Learning Resources
In addition to the documentation for each library, we found the following resources helpful:
- [Bob Myers' notes on GUIs at Florida State University](https://www.cs.fsu.edu/~myers/cgs3416/notes/gui_intro.html)
- [Chee Yap's "Java Swing GUI Tutorial" at NYU](https://cs.nyu.edu/~yap/classes/visual/03s/lect/l7/)
- [COMP 310's "Java GUI Programming Primer" at Rice University](https://www.clear.rice.edu/comp310/JavaResources/GUI/)
- [David J Eck's *Introduction to Programming Using Java, Seventh Edition* chapter on GUI programming](https://math.hws.edu/eck/cs124/javanotes7/c6/)
- [Java 2D graphics tutorial](https://docs.oracle.com/javase/tutorial/2d/)
- [Java Sound tutorial](https://docs.oracle.com/javase/tutorial/sound/index.html)
- [Java Swing tutorial](https://docs.oracle.com/javase/tutorial/uiswing/index.html)
- [Projectile motion equations from the University of Tennessee Knoxville](http://labman.phys.utk.edu/phys221core/modules/m3/projectile_motion.html)
- [RyiSnow 2D game tutorial playlist on YouTube](https://www.youtube.com/watch?v=om59cwR7psI&list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq)
- [This Stack Overflow exchange on flipping images with Graphics2D](https://stackoverflow.com/questions/9558981/flip-image-with-graphics2d)
