package minecraftplatformer;

import java.applet.*;
import javax.swing.*;
import java.awt.*;

public class Component extends Applet implements Runnable
{
	// setting the frame and image and size of the pixels.
	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	private Image screen;
	static int pixelSize = 2;
	// screen and object positions and sizes.
	public static int moveFromBorder = 0;
	public static double sX = moveFromBorder, sY = moveFromBorder;
	public static double dir = 0;
	public static Dimension realSize = new Dimension(0, 0);
	public static Dimension size = new Dimension(1200, 900);
	public static Dimension pixel = new Dimension(size.width / pixelSize, size.height / pixelSize);
	// mouse position.
	public static Point mse = new Point(0, 0);
	// title name.
	public static String name = "Tunnels";
	// setting listeners.
	public static boolean isRunning = false;
	public static boolean isMoving = false;
	public static boolean isJumping = false;
	public static boolean isMouseLeft = false;
	public static boolean isMouseRight = false;
	// making and naming objects.
	public static Level level;
	public static Character character;
	public static Inventory Inventory;
	public static Sky sky;

	// starting the game.
	public static void main(String args[])
	{
		Component component = new Component();

		frame = new JFrame();
		frame.add(component);
		frame.pack();

		realSize = new Dimension(frame.getWidth(), frame.getHeight());

		frame.setTitle(name);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		component.start();
	}

	// adding listeners and adding screen size.
	public Component()
	{
		setPreferredSize(size);
		addKeyListener(new Listening());
		addMouseListener(new Listening());
		addMouseMotionListener(new Listening());
		addMouseWheelListener(new Listening());
	}

	// adding objects and loading images.
	public void start()
	{
		// setting the screen boundaries.
		requestFocus();
		// Loading images.
		new Tile();
		// Defining objects etc.
		level = new Level();
		character = new Character(Tile.tileSize, Tile.tileSize * 2);
		Inventory = new Inventory();

		// starting game loop.
		isRunning = true;
		new Thread(this).start();
		sky = new Sky();
	}

	// stops the program.
	public void stop()
	{
		isRunning = false;
	}

	// setting the tick events from each class.
	public void tick()
	{
		if (frame.getWidth() != realSize.width || frame.getHeight() != realSize.height)
		{
			frame.pack();
		}

		character.tick();
		level.tick((int) sX, (int) sY, (pixel.width / Tile.tileSize) + 2, (pixel.height / Tile.tileSize + 2));
		sky.tick();
	}

	// rendering the game images and screen.
	public void render()
	{
		// rendering screen graphics.
		Graphics g = screen.getGraphics();

		// Drawing things
		sky.render(g);
		level.render(g, (int) sX, (int) sY, (pixel.width / Tile.tileSize) + 2, (pixel.height / Tile.tileSize) + 2);
		character.render(g);
		Inventory.render(g);

		g = getGraphics();

		g.drawImage(screen, 0, 0, size.width, size.height, 0, 0, pixel.width, pixel.height, null);
		g.dispose();
	}

	// running the game and screen.
	public void run()
	{
		screen = createVolatileImage(pixel.width, pixel.height);

		while (isRunning)
		{
			tick();
			render();

			try
			{
				Thread.sleep(5);
			}
			catch (Exception e)
			{
			}
		}
	}
}
