import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;

/**
    ---------- JPanel ----------
 * Is a simplest lightweight container class that is a part of the package java.swing. It can group or store a set of components together, mainly for creating a user interface. FlowLayout is the default layout manager of JPanel and it inherits the class JComponents. JPanel can't be complete without constructors. We have to make sure to add the components by the .add() method.
 */

 /**
    ---------- ActionListener ----------
    ActionListener is an interface notified whenever you click on the button or menu item. Is located in java.awt.event package and has only one method: actionPerformed(), that is invoked automatically whenever we click on the registered component
  */

  /**
    ---------- Graphics ----------
   * It's an abstract class present in java.awt.package that extends the Object class of java.lang package that serves as a superclass for all graphics contexts, which allow drawing various components in an application that can be easily realized onto various devices or many real images. Every single object of the Graphics class is a complete package of all the methods needed for the implementation of basic functions of an applet, and with this, its state contains information related to the component on which to draw, current clip, current color, XOR alternation color, font or translations of origin. Graphics class is used to draw different visualized components on the screen that is considered as a drawing board composed of the infinite number of pixels that lie between the pixels of the output device. All the coordinates that are given as arguments to the function are considered relative to the origin that has been translated before triggering the method.
   */

public class GamePanel extends JPanel implements ActionListener {

    // max size of screen width
    static final int SCREEN_WIDTH = 600;

    // max size of screen height
    static final int SCREEN_HEIGHT = 600;

    // the size of every object, like apple and snake's part
    static final int UNIT_SIZE = 25;

    // calculate how many objects we can fit on the screen
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;

    // delay for our timer, the higher is the delay, the slower will be our game
    static final int DELAY = 75;

    // create two array that are going to hold all of the coordinates (x, y) for all the body parts of our snake, includint the head. For the size of those arrays we will pass our GAME_UNITS because the snake isn't going to be bigger than the game itself
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];

    // initial amount of body parts of our snake
    int bodyParts = 6;

    // this is a counter for every apple eaten, initialized to zero
    int applesEaten;

    // x and y coordinates for where the apple will spawn each time the snake eats an apple, it will be generated randomly everytime
    int appleX;
    int appleY;

    // direction from where the snake will start to move (R right L left U up D down)
    char direction = 'R';

    // this indicates if the game is running or not, so we initialize it to 0
    boolean running = false;

    // create the timer and the random generator
    Timer timer;
    Random random;

    // constructor
    GamePanel() {

        // instance of the random class
        random = new Random();

        // set a preferred size of this gamepanle, where we pass a new dimension
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

        // set the background
        this.setBackground(Color.black);

        /**
            ---------- Focusable ----------
         * Focusable indicates whether a component can gain the focus if it is requested to do so. The JPanel component is focusable by default.
          For example, let's say we have implemented a dialog with several text fields and we want the user to enter some text. When the user starts typing, one text field needs to have the focus of the application: it will be the field that receives the keyboard input.
         */
        this.setFocusable(true);

        // set the listener for our keyboard and create a new instance of our class MyKeyAdapter
        this.addKeyListener(new MyKeyAdapter());

        // call the start game methd
        startGame();
    }

    public void startGame() {

        // when we starts the game, the first thing we want to do i generate an apple
        newApple();

        // change our running to true
        running = true;

        // create a new instance of the timer where we pass our DELAY, and we pass into THIS cause we use the ActionListener interface
        timer = new Timer(DELAY, this);

        // start our timer using the start function
        timer.start();
    }

    public void paintComponent(Graphics g) {

        // we call che constructor of JPanel and we use the paintComponent function where we pass the g parameter
        super.paintComponent(g);

        // call the draw function where we pass the g parameter
        draw(g);
    }

    // the draw method essentially DRAW the snake
    public void draw(Graphics g) {

        // condition, if our game is running(true) or not
        if (running) {

            // set the apple color
            g.setColor(Color.red);

            // make the apple like a circle, pass the coordinate (appleX and Y) and pass the width and height size, so our UNIT_SIZE
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            // with this iteration we draw the body parts
            for (int i = 0; i < bodyParts; i++) {
                //  if i == 0 so we have the head of the snake
                if (i == 0) {

                    // set a color for the head
                    g.setColor(Color.green);

                    // with this method we create a rectangle
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {

                    // set the color of the body
                    g.setColor(new Color(45, 180, 0));

                    // create a rectangle for every body parts
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2,
                    g.getFont().getSize());
        } else {
            // if the game is not running, let's call the gameOver method where we pass our g (Graphics)
            gameOver(g);
        }

    }


    public void newApple() {

        // set a random x and y coordinate and cast them to int, so the apples will spawn randomly
        appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void move() {

        // create a for loop to iterate through all the body parts of the snake, and we continuer this till the bodyparts is greater than 0 and we decrement. What we will doing is to shifting the body parts of our snake around, so we take x and y arrays and index i that will be equal to i - 1, because like this we basically shifting all the coordinates in this array ober by one spot
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        // switch to change the direction of our snake, we pass the direction variable, the index 0 of the arrays match the head of the snake
        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkApple() {

        // check if the snake's head touch the apple, if so increase the bodyparts of the snake and the applesEaten counter
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions() {

        // iterate through all the body parts and check if the head of the snake touch one of the bodyparts
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }

        // check if head touches left border
        if (x[0] < 0) {
            running = false;
        }

        // chef if head touches right border
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }

        // check if head touches top border
        if (y[0] < 0) {
            running = false;
        }

        // check if head touches bottom border
        if (y[0] > SCREEN_HEIGHT) {
            running = false;
        }

        // if running is false, let's stop the timer
        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {

        // set up the game over score color
        g.setColor(Color.red);

        // and font
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());

        // write the string displayed and positionate it in the screen
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2,
                g.getFont().getSize());

            
        // set the game over text color
        g.setColor(Color.red);

        // and the font
        g.setFont(new Font("Ink Free", Font.BOLD, 75));

        /**
            ---------- FontMetrics ----------
         * FontMetrics class defines a font metrics object, which encapsulates information about the rendering of a particula font on a particular screen
         */
        
        FontMetrics metrics2 = getFontMetrics(g.getFont());

        // write the string displayed and positionate it in the screen
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // if our game is running , we call those function
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }

        /**
            ---------- repaint ----------
         * we cannot override the repaint method because it is a final method. This method take care of update to the paint() pattern of the applet. At the point when we believe that a part should repaint itself, we have to call the repaint method.
         */
        repaint();

    }

    /**
        ---------- Inner Class -----------
        In Java is possible to nest classes, so we will have a class within a class. The purpose is to group classes that belong together, which makes our code more readable and maintainable. To access the inner class, create an object of the outer class, and then create an object of the inner class, for example:
        class OuterClass {
            int x = 10;

            class InnerClass {
                int y = 5;
            }
        }

        public class Main {
            public static void main(String[] args) {
                OuterClass myOuter = new OuterClass();
                OuterClass.InnerClass myInner = myOuter.new InnerClass();
            }
        }
     */


     /**
        ---------- KeyAdapter ----------
     * Is an abstract class for receiving keyboard eventr. All methods of this class are empty. This class is convenience class for creating listener objects.
      */
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            // capture the key input of the arrow key and we make sure that the snake can't do a 180 turn but a 90 turn
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }

}