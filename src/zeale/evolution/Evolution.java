package zeale.evolution;

import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import zeale.evolution.bots.Bot;
import zeale.evolution.structures.Spawnpoint;
import zeale.evolution.structures.Structure;
import zeale.evolution.structures.resourcespawners.ResourceSpawner;

public final class Evolution {

	/**
	 * A {@link LinkedList} of all the {@link Bot}s in the game.
	 */
	private final LinkedList<Bot> bots = new LinkedList<>();

	/**
	 * The position of the camera.
	 */
	private int cx = 0, cy = 0;

	/**
	 * A {@link LinkedList} of all the {@link Structure}s in the game.
	 */
	private final LinkedList<Structure> structures = new LinkedList<>();

	/**
	 * The {@link JFrame} where the game's draw ({@link #pane}) canvas is held.
	 */
	public final EvolutionFrame frame = new EvolutionFrame();
	/**
	 * The drawing canvas of the game. This is held inside the game's
	 * {@link JFrame} ({@link #frame}).
	 */
	public final EvolutionPane pane = new EvolutionPane();

	/**
	 * <p>
	 * The current instance of {@link Evolution} that is running in the program.
	 * This is commonly used to retrieve the current instance statically, as
	 * that was what it was made for. See {@link #getCurrentInstance()}.
	 */
	private static Evolution CURRENT_INSTANCE;
	/**
	 * A {@link Random} for use around the class, where necessary.
	 */
	private static Random random = new Random();

	private Evolution() {
		GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
		structures.add(new ResourceSpawner(Evolution.random.nextInt(getWidth()), Evolution.random.nextInt(getHeight()),
				(short) 10));
		structures.add(new ResourceSpawner(Evolution.random.nextInt(getWidth()), Evolution.random.nextInt(getHeight()),
				(short) 25));
		bots.add(new Bot(Evolution.random.nextInt(getWidth()), Evolution.random.nextInt(getHeight())));
		bots.add(new Bot(Evolution.random.nextInt(getWidth()), Evolution.random.nextInt(getHeight())));
		structures.add(new Spawnpoint(getWidth() / 2, getHeight() / 2));
	}

	/**
	 * <p>
	 * The game loop...
	 * <p>
	 * Go figure...
	 */
	private void loop() {
		long delta = System.nanoTime(), past = 1000000000 / 60;
		while (true) {
			delta = System.nanoTime();
			if (delta - past < 1000000000 / 60)
				continue;
			{

				pane.repaint();
				for (final Structure s : structures)
					if (s.isAlive())
						s.work(delta - past);
				for (final Bot b : bots)
					if (b.isAlive())
						b.work(delta - past);

			}
			past = delta;
		}
	}

	/**
	 * <p>
	 * <strong>The rendering method.</strong>
	 * <p>
	 * Called when the game is rendered onto the screen. This calls the render
	 * method in all {@link Structure}s then all {@link Bot}s, as well.
	 *
	 * @param g
	 *            The graphics object; used to draw to the screen.
	 */
	private void render(final Graphics g) {
		for (final Structure s : structures)
			if (s.isAlive())
				s.render(g);
		for (final Bot b : bots)
			if (b.isAlive())
				b.render(g);
	}

	/**
	 * Adds a {@link Bot} to the game.
	 *
	 * @param bot
	 * @return
	 */
	public boolean addBot(final Bot bot) {
		return bots.add(bot);
	}

	/**
	 * <p>
	 * <strong>A getter for the {@link Bot}s in game.</strong>
	 * <p>
	 * This method returns an unmodifiable list containing all the {@link Bot}s
	 * in game. Dead or alive. See {@link Bot#isAlive()} for more information.
	 *
	 * @return A full, unmodifiable list of all the {@link Bot}s in game.
	 */
	public List<Bot> getBots() {
		return Collections.unmodifiableList(bots);
	}

	/**
	 * A getter for the camera x position.
	 *
	 * @return The camera's x position.
	 */
	public int getCx() {
		return cx;
	}

	/**
	 * A getter for the camera y position.
	 *
	 * @return The camera's y position.
	 */
	public int getCy() {
		return cy;
	}

	/**
	 * A height getter.
	 *
	 * @return The height of the game's draw panel.
	 */
	public int getHeight() {
		return pane.getHeight();
	}

	/**
	 * <p>
	 * Get's the <i>height ratio</i>. This is multiplied by {@link Object}'s
	 * height to scale them sufficiently for the screen size. {@link Object}s
	 * should be created with dimensions for the screen size of 1920x1080 and
	 * then have their height multiplied by this ratio.
	 *
	 * @return The <i>height ratio</i> of the current game.
	 */
	public double getHeightRatio() {
		return pane.getHeightRatio();
	}

	/**
	 * <p>
	 * A get method for all the living {@link Bot}s.
	 * <p>
	 * This method iterates through all the {@link Bot}s and adds them to a
	 * {@link LinkedList}. Once iteration is complete, the {@link LinkedList} is
	 * returned.
	 * <p>
	 * NOTE: <strong>There is no guarantee that the returned list is not
	 * empty.</strong>
	 *
	 * @return A new {@link LinkedList} containing all the living {@link Bot}s
	 *         in the game.
	 */
	public LinkedList<Bot> getLivingBots() {
		final LinkedList<Bot> list = new LinkedList<>();
		for (final Bot b : bots)
			if (b.isAlive())
				list.add(b);
		return list;
	}

	/**
	 * <p>
	 * A get method for all the living {@link Structure}s.
	 * <p>
	 * This method iterates through all the {@link Structure}s and adds them to
	 * a {@link LinkedList}. Once iteration is complete, the {@link LinkedList}
	 * is returned.
	 * <p>
	 * NOTE: <strong>There is no guarantee that the returned list is not
	 * empty.</strong>
	 *
	 * @return A new {@link LinkedList} containing all the living
	 *         {@link Structure}s in the game.
	 */
	public LinkedList<Structure> getLivingStructures() {
		final LinkedList<Structure> list = new LinkedList<>();
		for (final Structure s : structures)
			if (s.isAlive())
				list.add(s);
		return list;
	}

	/**
	 * <p>
	 * <strong>A getter for the {@link Structure}s in game.</strong>
	 * <p>
	 * This method returns an unmodifiable list containing all the
	 * {@link Structure}s in game. Dead or alive. See
	 * {@link Structure#isAlive()} for more information.
	 *
	 * @return A full, unmodifiable list of all the {@link Structure}s in game.
	 */
	public List<Structure> getStructures() {
		return Collections.unmodifiableList(structures);
	}

	/**
	 * A width getter.
	 *
	 * @return The width of the game's draw panel.
	 */
	public int getWidth() {
		return pane.getWidth();
	}

	/**
	 * <p>
	 * Get's the <i>width ratio</i>. This is multiplied by {@link Object}'s
	 * width to scale them sufficiently for the screen size. {@link Object}s
	 * should be created with dimensions for the screen size of 1920x1080 and
	 * then have their width multiplied by this ratio.
	 *
	 * @return The <i>width ratio</i> of the current game.
	 */
	public double getWidthRatio() {
		return pane.getWidthRatio();
	}

	/**
	 * <p>
	 * <strong>Moves the camera the specified amount of units on the x
	 * axis.</strong>
	 *
	 * @param units
	 *            The units to move the camera across the X axis. This can be
	 *            negative.
	 */
	public void moveCamX(final int units) {
		cx += units;
	}

	/**
	 * <p>
	 * <strong>Moves the camera the specified amount of units on the y
	 * axis.</strong>
	 *
	 * @param units
	 *            The units to move the camera across the Y axis. This can be
	 *            negative.
	 */
	public void moveCamY(final int units) {
		cy += units;
	}

	/**
	 * <p>
	 * A useful method for calculating and return the position of something as
	 * it appears on screen (if it does). This will factor in the camera's
	 * position to return where something should be drawn to the screen.
	 *
	 * @param input
	 *            The x or y input.
	 * @param x
	 *            <code>true</code> if the input is on the <code>x</code> axis,
	 *            <code>false</code> if the input is on the y axis.
	 * @return The specified position (<code>input</code>) as it appears on
	 *         screen.
	 */
	public static int calculatePosition(final int input, final boolean x) {
		return x ? input - Evolution.getCurrentInstance().getCx() : input - Evolution.getCurrentInstance().getCy();
	}

	/**
	 * <p>
	 * A useful method used for calculating the width or height, as it should
	 * be, scaled to the screen size. This gets the size given and multiplies it
	 * by the correct ratios as needed. (See {@link #getHeightRatio()} and
	 * {@link #getWidthRatio()} for more details.)
	 *
	 * @param input
	 *            the width/height that will be scaled then returned.
	 * @param width
	 *            <code>true</code> if <code>input</code> is a width value,
	 *            <code>false</code> if <code>input</code> is a height value.
	 * @return The multiplied width/height value.
	 */
	public static int calculateSize(final int input, final boolean width) {
		return width ? (int) (input * Evolution.getCurrentInstance().getWidthRatio())
				: (int) (input * Evolution.getCurrentInstance().getHeightRatio());
	}

	/**
	 * Gets the closest {@link Bot} of the specified type.
	 * <p>
	 * <strong>This method takes in a Type Parameter.</strong>
	 * <p>
	 * The Type Parameter must be of the same class as the <code>botClass</code>
	 * parameter.
	 * <p>
	 * This method will iterate over all the bots (that are alive. See
	 * {@link Bot#isAlive()}.) and will return the one that is:
	 * <ul>
	 * <li>Closest to the given <code>object</code> parameter.</li>
	 * <li>An instance of the <code>botClass</code> parameter provided.</li>
	 * </ul>
	 * <p>
	 * Failure to pass in a Type Parameter may have unknown/unwanted
	 * consequences.
	 *
	 * @param object
	 *            Any {@link Object}. The returned {@link Bot} will be the
	 *            closest of its type, to this {@link Object}.
	 * @param botClass
	 *            The Type of {@link Bot} to look for.
	 * @return The {@link Bot} of Type <code>botClass</code> which is closest to
	 *         <code>object</code>.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Bot> T getClosestBot(final Object object, final Class<T> botClass) {

		T closestBot = null;
		for (final Bot bot : Evolution.CURRENT_INSTANCE.bots)
			try {
				if (!bot.isAlive() || bot.getClass() != botClass)
					continue;
				if (Evolution.getDistance(object, bot) < Evolution.getDistance(object, closestBot))
					closestBot = (T) bot;
			} catch (final NullPointerException e) {
				closestBot = (T) bot;
			}

		return closestBot;
	}

	/**
	 * <p>
	 * Note: This has been deprecated. It will be removed soon. Call
	 * {@link #getClosestStructure(Object, Class)} with
	 * <code>ResourceSpawner</code> as a Type Parameter.
	 * <p>
	 * This method gets the closest {@link ResourceSpawner} to the specified
	 * {@link Object}.
	 *
	 * @param object
	 *            The {@link Object}. The returned {@link ResourceSpawner} will
	 *            be the closest {@link ResourceSpawner} to this {@link Object}.
	 * @return The closest {@link ResourceSpawner} to the specified
	 *         {@link Object}.
	 */
	@Deprecated
	public static ResourceSpawner getClosestResourceSpawner(final Object object) {
		if (Evolution.CURRENT_INSTANCE.structures.size() <= 0)
			return null;

		ResourceSpawner spawner = (ResourceSpawner) Evolution.CURRENT_INSTANCE.structures.getFirst();

		for (final Structure structure : Evolution.getCurrentInstance().getStructures())
			if (!(structure instanceof ResourceSpawner))
				continue;
			else if (Evolution.getDistance(object, structure) < Evolution.getDistance(object, spawner))
				spawner = (ResourceSpawner) structure;

		return spawner;
	}

	/**
	 * Gets the closest {@link Structure} of the specified type.
	 * <p>
	 * <strong>This method takes in a Type Parameter.</strong>
	 * <p>
	 * The Type Parameter must be of the same class as the
	 * <code>structClass</code> parameter.
	 * <p>
	 * This method will iterate over all the structures (that are alive. See
	 * {@link Structure#isAlive()}.) and will return the one that is:
	 * <ul>
	 * <li>Closest to the given <code>object</code> parameter.</li>
	 * <li>An instance of the <code>structClass</code> parameter provided.</li>
	 * </ul>
	 * <p>
	 * Failure to pass in a Type Parameter may have unknown/unwanted
	 * consequences.
	 *
	 * @param object
	 *            Any {@link Object}. The returned {@link Structure} will be the
	 *            closest of its type, to this {@link Object}.
	 * @param structClass
	 *            The Type of {@link Structure} to look for.
	 * @return The {@link Structure} of Type <code>structClass</code> which is
	 *         closest to <code>object</code>.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Structure> T getClosestStructure(final Object object, final Class<T> structClass) {

		if (Evolution.CURRENT_INSTANCE.structures.size() == 0)
			return null;

		T closestStruct = null;
		for (final Structure struct : Evolution.CURRENT_INSTANCE.structures)
			try {
				if (!struct.isAlive() || struct.getClass() != structClass)
					continue;

				if (Evolution.getDistance(object, struct) < Evolution.getDistance(object, closestStruct))
					closestStruct = (T) struct;
			} catch (final NullPointerException e) {
				closestStruct = (T) struct;
			}

		return closestStruct;
	}

	/**
	 * A getter for the {@link #CURRENT_INSTANCE} of the program.
	 *
	 * @return The currently running instance of {@link Evolution}, stored in
	 *         {@link #CURRENT_INSTANCE}.
	 */
	public static Evolution getCurrentInstance() {
		return Evolution.CURRENT_INSTANCE;
	}

	/**
	 * <p>
	 * Gets the distance between two objects.
	 *
	 * @param obj1
	 *            The first object.
	 * @param obj2
	 *            The second object.
	 * @return The distance between the two objects. (Always positive.)
	 */
	public static double getDistance(final Object obj1, final Object obj2) {

		// Values are squared afterwards, so there is no need to get the
		// absolute value to find the difference.
		final double vx = obj1.posx - obj2.posx, vy = obj1.posy - obj2.posy;

		return StrictMath.sqrt(vx * vx + vy * vy);
	}

	/**
	 * <p>
	 * This method returns a new {@link LinkedList} of the type specified that
	 * contains all of the {@link Bot}s (of a specific type) from the game. This
	 * method will iterate over all the {@link Bot}s in the game and select all
	 * that...
	 * <ul>
	 * <li>Are instances of the class (<code>structClass</code>) and Type
	 * (<code>T</code>) passed into the method.</li>
	 * <li>Are alive. See {@link Bot#isAlive()} for more details.</li>
	 * </ul>
	 *
	 * @param botClass
	 *            The type of {@link Bot} to search for.
	 * @return A new {@link LinkedList} with all the found {@link Bot}s.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Bot> LinkedList<T> getSpecBots(final Class<T> botClass) {
		final LinkedList<T> list = new LinkedList<>();
		for (final Bot bot : Evolution.CURRENT_INSTANCE.bots) {
			if (!bot.isAlive() || bot.getClass() != botClass)
				continue;
			list.add((T) bot);
		}

		return list;
	}

	public static void main(final String[] args) {
		Evolution.start();
	}

	/**
	 * <p>
	 * <strong>Converts nanoseconds to seconds.</strong>
	 * <p>
	 * This method literally divides the input (<code>nanoseconds</code>) by a
	 * billion, (<code>1</code> with <code>9</code> zeros), but it is still a
	 * pretty useful method for code clarity.
	 * <p>
	 * See {@link #secondsToNanosec(short)} for conversion back.
	 *
	 * @param nanoseconds
	 *            The amount of nanoseconds that will be converted to seconds.
	 * @return The time of <code>nanoseconds</code>, converted to seconds (and
	 *         cast to a short).
	 */
	public static short nanosecToSec(final long nanoseconds) {
		return (short) (nanoseconds / 1000000000);
	}

	/**
	 * <p>
	 * <b>Converts seconds to nanoseconds.</b>
	 * <p>
	 * This method simply multiplies its input (<code>seconds</code>) by a
	 * billion (10<sup>9</sup>), but is useful for clarifying code.
	 * <p>
	 * See {@link #nanosecToSec(long)} for conversion back.
	 *
	 * @param seconds
	 *            The amount of seconds to convert.
	 * @return <code>seconds</code> converted to nanoseconds.
	 */
	public static long secondsToNanosec(final short seconds) {
		return seconds * 1000000000;
	}

	/**
	 * The start method of the program. This should be used to start the
	 * program.
	 */
	public static void start() {
		Evolution.CURRENT_INSTANCE = new Evolution();
		Evolution.CURRENT_INSTANCE.loop();
	}

	/**
	 * The class of {@link Evolution#frame}.
	 *
	 * @author Zeale
	 *
	 */
	private class EvolutionFrame extends JFrame {
		private static final long serialVersionUID = 1L;

		private EvolutionFrame() {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setUndecorated(true);
			setResizable(false);

		}

	}

	/**
	 * The class of {@link Evolution#pane}.
	 *
	 * @author Zeale
	 *
	 */
	private class EvolutionPane extends JPanel {
		private static final long serialVersionUID = 1L;

		private EvolutionPane() {
			frame.add(this);
			frame.pack();
		}

		@Override
		protected void paintComponent(final Graphics g) {
			super.paintComponent(g);
			render(g);

		}

		public double getHeightRatio() {
			return (double) getHeight() / 1080;
		}

		public double getWidthRatio() {
			return (double) getWidth() / 1920;
		}
	}

	public static class ClassTypeMismatchException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		private ClassTypeMismatchException(final String className) {
			super("The passed generic type did not match the Class passed: " + className);
		}

		@Override
		public String getMessage() {
			return super.getMessage();
		}
	}

}
