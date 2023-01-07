import javax.swing.JFrame;
/**
    ---------- Swing ----------
 * Is a GUI (Graphical User Interface) toolkit that includes the GUI components.
 Swing provides a rich set of widgets and packages to make sophisticated GUI components for java applications. Swing is a part of Java Foundation Classes (JFC) which is an API for java programs that provide GUI.
 The javax.swing package provides classes for java swing API such as JButton, JTextField, JColorChooser etc.
 * Container classes are classes that can have other components on it. There are 3 types of java swing container
 - Panel: pure container and is not a window in itself. The sole purpose of a panel is to organize the components on to a window.
 - Frame: it's a fully functioning window with its title and icons.
 - Dialog: it can be like a pop-up window that pops out when a message has to be displayed.

 */

/**
    ---------- JFrame ----------
 * Whenever we create a graphical user interface with Java Swing functionality, we need a container for our application. In the case of Swing, JFrame is the container. Without a container in which to put all other elements, we won't have a GUI (Graphical User Interface) application.
 
 * JFrame has its own methods such as setting the size or visibility and constructors. Constructors are run when the instance is created, one constructor can create a blank JFrame, while another can create it with a default title
 
 * To see a window, we need a size and location, also make it visible to the users 
 */


public class GameFrame extends JFrame{

    GameFrame() {
        GamePanel panel = new GamePanel();
        this.add(panel);

        // set a title
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // this way we can't resize the window
        this.setResizable(false);

        // if we add components to a JFrame this pack function is actually going to take our JFrame and fit it around all the components that we add to the frame
        this.pack();

        // to make it visible
        this.setVisible(true);

        // this allows us to have the window in the middle of the screen
        this.setLocationRelativeTo(null);
    }
}
