# Smoothie Operator 🍊🍌🍓🥤

Smoothie Operator is a 2D artillery game written in Java. The player shoots fruit from a cannon
at a blender that appears in a new, random location on the right side of the screen after
every shot. The player controls the cannon's angle and power with the keyboard. If the player
hits the blender three times, they win the round. If the player misses six times, they lose the
round. Players may start a new round at any time.

## Usage
1. Download the 
[*smoothieoperator/*](https://github.com/m-siegel/bananaBombsquad/tree/main/smoothieoperator/)
 folder to get the source code and the byte code.

2. (Compile and) run the program:

   A. If your computer is running Java 11 or later, you can compile and run the source code.
   - Either open the folder in an IDE (like VS Code) and run smoothieoperator/src/main/Main.java
from there, or
   - Compile and run smoothieoperator/src/main/Main.java from the command line:
     1. *javac smoothieoperator/src/main/Main.java*
     2. *java smoothieoperator/src/main/Main*

   B. If your computer is running an older version of Java, you can run the pre-compiled byte code
from the command line: *java smoothieoperator/bin/main/Main*.

3. Enjoy!

## Program Structure

1. Our main method instantiates a GameWindowManager to open a window and begin the game.
2. The GameWindowManager has a TitlePanel, which it displays first, and a GamePanel.
3. The TitlePanel has a Sound, a JButton and a MouseListener, and uses an image from our media
folder. When the MouseListener registers a button click, it gives control back to the
GameWindowManager.
4. The GameWindowManager then displays its GamePanel and starts the GamePanel's game loop.
5. GamePanel. A GamePanel has one each of Background, Cannon, Lives, PowerBar, Target, Wall, 
   and EndMessage, which are all descendents of Sprite. GamePanel runs the game logic.
   1. GamePanel sets the screen dimensions for the program.
   2. When it's instantiated, the GamePanel reads in and processes images from the media folder,
   and either stores them in a SpriteList (for the Projectiles), or instantiates the corresponding
   Sprites immediately. GamePanel catches exceptions that arise from attempts to instantiate
   Sprites and displays an error screen if need be.
   3. GamePanel's run() method runs the game loop, the main game logic. It determines the frame
   rate for the game, and regularly calls sprites' update and draw methods. In the game loop, the
   GamePanel monitors the game's status, including whether the game is over, and displays its
   current state. The GamePanel facilitates interactions between Sprites (such as checking for
   collisions or instantiating Projectiles based on the position of the Cannon). The game loop
   also references a KeyHandler that indicates when to restart the game based on keyboard input.
   4. Each Sprite controls what happens when GamePanel calls the Sprite's update and draw methods.
   Sprites can change position onscreen (independently or in response to user input), can animate
   their images (e.g. to look like they're rotating), can play Sounds (often in response to events
   like collisions), and determine how (if at all) they can collide with other Sprites.
7. When the user closes the window, the program ends.

![Class Diagram w/o Sprite and Descendents](/img/classChartWOSprite.png)
![Class Diagram of Sprite and Descendents](/img/classChartSprite.png)

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
Finally, we got random numbers and used data structures from
[java.util](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/package-summary.html).

![List of libraries used](/img/LibrariesUsed1.png)
![List of libraries used](/img/LibrariesUsed2.png)


## Contributors
**Code:**
armen-s, c-lopez, m-siegel, and t-crawley collaboratively designed and created this program.

**Graphics:**
c-lopez created the images, with the exception of the title screen image, which m-siegel made.

**Music:**
armen-s created the music.

## Other Credits

**Sounds**


We used the following free, opensource, or public domain sound effects:
- [whoosh](https://mixkit.co/free-sound-effects/whoosh/)
  - Mixkit Sound Effects Free License: "You are licensed to use the Item to create an End Product that incorporates the Item as well as other things, so that it is larger in scope and different in nature than the Item. You’re permitted to download, copy, modify, distribute and publicly perform the Sound Effect Items on any web or social media platform, in podcasts and in video games, as well as in films and presentations distributed on CDs, DVDs, via TV or radio broadcast or internet based video on demand services."
- [boom](https://www.nps.gov/subjects/sound/sounds-cannon.htm)
  - Public Domain
- ["splash - swordofkings128"](https://pixabay.com/sound-effects/search/splash/)
  - “Free for commercial use, No attribution required.” "Crediting isn't required, but linking back is greatly appreciated and allows music authors to gain exposure.”
- [splat - "Squish Footstep Watery Grass 3"](https://www.dreamstime.com/stock-music-sound-effect/squish.html)
  - Royalty-free license.

**Learning Resources**


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
