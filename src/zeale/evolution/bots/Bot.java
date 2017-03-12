package zeale.evolution.bots;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

import zeale.evolution.Evolution;
import zeale.evolution.Object;
import zeale.evolution.resources.Resource;
import zeale.evolution.structures.Spawnpoint;
import zeale.evolution.structures.Structure;
import zeale.evolution.structures.resourcespawners.ResourceSpawner;

/**
 * The {@link Bot} class is the super class for all Bots in <i>Evolution</i>. It
 * contains basic methods and functionality that most {@link Bot}s should have.
 * It is also instantiable for the purpose of getting a generic {@link Bot} for
 * gameplay.
 *
 * @author Zeale
 *
 */
public class Bot extends Object {

	/**
	 * A private static {@link Random} object, for wherever it's needed in this
	 * class.
	 */

	/**
	 * The life that this {@link Bot} has remaining before it dies (in seconds).
	 */
	private double life = 100.0;
	/**
	 * The maximum amount of {@link Resource}s that this {@link Bot} can carry.
	 * (AKA its inventory size.)
	 */
	private short maxResources = 5;

	/**
	 * The {@link LinkedList} of {@link Resource}s that this {@link Bot}
	 * carries. This is sometimes referred to as its <i>inventory</i> in
	 * documentation.
	 */
	private final LinkedList<Resource> resources = new LinkedList<>();

	/**
	 * The speed at which this {@link Bot} travels. The speed is multiplied by
	 * the normal distance that a {@link Bot} travels: 1 unit.
	 */
	private short speed = 1;

	/**
	 * The {@link Object} that this {@link Bot} is attempting to head towards.
	 */
	private transient Object target;

	/**
	 * The current amount of wait time that this {@link Bot} has. See
	 * {@link #addWaitTime(double)} for more details.
	 */
	private double waitTime = 0.0;

	/**
	 * The {@link Color} of this {@link Bot}. Defaults to {@link Color#PINK}.
	 */
	protected Color botColor = Color.PINK;
	/**
	 * The size of this {@link Bot}. Defaulted to 25.
	 */
	protected int width = 25, height = 25;

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Makes a new {@link Bot} with the specified attributes and position.
	 *
	 * @param posx
	 *            The X position of this {@link Bot}.
	 * @param posy
	 *            The Y position of this {@link Bot}.
	 */
	public Bot(final double posx, final double posy) {
		super(posx, posy);
	}

	/**
	 * Makes a new {@link Bot} with the specified attributes and position.
	 *
	 * @param posx
	 *            The X position of this {@link Bot}.
	 * @param posy
	 *            The Y position of this {@link Bot}.
	 * @param speed
	 *            The speed at which this {@link Bot} will travel. This works as
	 *            a multiplier to this {@link Bot}s normal speed of 1 unit per
	 *            second. A unit is equivalent to a pixel.
	 */
	public Bot(final double posx, final double posy, final short speed) {
		this(posx, posy);
		this.speed = speed;
	}

	/**
	 * Makes a new {@link Bot} with the specified attributes and position.
	 *
	 * @param posx
	 *            The X position of this {@link Bot}.
	 * @param posy
	 *            The Y position of this {@link Bot}.
	 * @param maxResources
	 *            The maximum amount of {@link Resource}s that this {@link Bot}
	 *            can carry before having to return to the nearest
	 *            {@link Spawnpoint}.
	 * @param speed
	 *            The speed at which this {@link Bot} will travel. This works as
	 *            a multiplier to this {@link Bot}s normal speed of 1 unit per
	 *            second. A unit is equivalent to a pixel.
	 */
	public Bot(final double posx, final double posy, final short maxResources, final short speed) {
		super(posx, posy);
		this.maxResources = maxResources;
		this.speed = speed;
	}

	/**
	 * Makes a new {@link Bot} with the specified attributes and position.
	 *
	 * @param posx
	 *            The X position of this {@link Bot}.
	 * @param maxResources
	 *            The maximum amount of {@link Resource}s that this {@link Bot}
	 *            can carry before having to return to the nearest
	 *            {@link Spawnpoint}.
	 * @param posy
	 *            The Y position of this {@link Bot}.
	 */
	public Bot(final double posx, final short maxResources, final double posy) {
		super(posx, posy);
		this.maxResources = maxResources;
	}

	/**
	 * Adds life to this {@link Bot}. See {@link #life} for more details.
	 *
	 * @param life
	 *            The amount of life to add to this {@link Bot}.
	 * @return The total amount of life that this {@link Bot} has left.
	 */
	public double addLife(double life) {
		return this.life += life;
	}

	/**
	 * Adds time to this {@link Bot} where it will sit and do nothing. If this
	 * {@link Bot} has 50,000 milliseconds of wait time, it will wait 50 seconds
	 * before performing any of its normal activities. Basically, the
	 * {@link #work(long)} method is halted until the time is finished.
	 *
	 * @param miliseconds
	 */
	public void addWaitTime(final double miliseconds) {
		waitTime += miliseconds;
	}

	/**
	 * Decrements the life of this {@link Bot} by the specified amount.
	 *
	 * @param life
	 *            The amount of life to take from this {@link Bot}.
	 * @return The amount of life that this {@link Bot} has left over.
	 */
	public double decrementLife(double life) {
		return this.life -= life;
	}

	/**
	 * A getter for {@link Bot#life}.
	 *
	 * @return The life remaining for this {@link Bot}.
	 */
	public double getLife() {
		return life;
	}

	/**
	 * Adds a {@link Resource} to this {@link Bot}'s inventory/storage. <br>
	 * <br>
	 * This method will not add {@link Resource}s over this {@link Bot}'s
	 * {@link #maxResources} limit. Once this {@link Bot}'s inventory is full,
	 * the method will do nothing but return false.
	 *
	 * @param resource
	 *            The {@link Resource} to add to this {@link Bot}'s inventory.
	 * @return <code>true</code> if this method successfully added the
	 *         {@link Resource} to this {@link Bot}. <code>false</code>, likely
	 *         if this {@link Bot}'s inventory is full.
	 */
	public boolean giveResource(final Resource resource) {
		if (resources.size() >= maxResources)
			return false;
		return resources.add(resource);
	}

	@Override
	public void render(final Graphics g) {
		g.setColor(botColor);
		g.fillRect(getX() - Evolution.getCurrentInstance().getCx(), getY() - Evolution.getCurrentInstance().getCy(),
				Evolution.calculateSize(width, true), Evolution.calculateSize(height, false));
		g.setColor(Color.BLACK);
		g.drawString("" + resources.size(), Evolution.calculateSize(getX() + 7, true),
				Evolution.calculateSize(getY() + height - 7, false));
	}

	/**
	 * Sets the amount of life that this {@link Bot} has left.
	 *
	 * @param life
	 *            The amount of life that will be set to this {@link Bot}.
	 */
	public void setLife(double life) {
		this.life = life;
	}

	/**
	 * Removes and returns all of this {@link Bot}'s {@link Resource}s.
	 *
	 * @return A new {@link LinkedList} full of this {@link Bot}'s
	 *         {@link Resource}s.
	 */
	public LinkedList<Resource> takeResources() {
		final LinkedList<Resource> list = new LinkedList<>();
		list.addAll(resources);
		resources.clear();
		return list;
	}

	@Override
	public void work(final long delta) {
		// Handle wait time...

		if (life > 0) {
			life -= Evolution.nanosecToSec(delta);
			if (life <= 0) {
				life = 0;
				kill();
			}
		}

		if (waitTime > 0) {
			waitTime -= delta / 1000000;
			if (waitTime < 0)
				waitTime = 0;
			return;
		}

		if (resources.size() >= maxResources)// Check if this bot's inventory is
												// full.
			// If it is, go to spawn to deposit resources.
			target = Evolution.<Spawnpoint>getClosestStructure(this, Spawnpoint.class);
		else
			// Set this bot's next goal. It needs to get resources to survive,
			// so...
			target = Evolution.<ResourceSpawner>getClosestStructure(this, ResourceSpawner.class);

		// This happens if there are no Resource Spawners left.
		if (target == null)
			if (resources.size() > 0)
				target = Evolution.<Spawnpoint>getClosestStructure(this, Spawnpoint.class);
			else
				return;

		// This is the distance between this bot and its target.
		final double distance = Evolution.getDistance(this, target);

		// This means that the bot is in range to take things from its target.
		// (Or activate its target. Whatever it needs to do.)
		if (distance <= speed)
			((Structure) target).activate(this);

		// Difference between the target destination and the current position.
		final double dx = Math.abs(posx - target.getX()), dy = Math.abs(posy - target.getY());

		// X and Y ratios. IF (rx == 1/4) THEN (ry == 3/4).
		// The following always evaluates to true.
		// (rx + ry == 1)
		final double rx = dx / (dx + dy), ry = dy / (dx + dy);

		if (Double.isNaN(rx)) {
			posy += speed;
			return;
		}
		if (Double.isNaN(ry)) {
			posx += speed;
			return;
		}

		if (target.getX() > posx)
			posx += rx * speed;
		else
			posx -= rx * speed;

		if (target.getY() > posy)
			posy += ry * speed;
		else
			posy -= ry * speed;

	}

	/**
	 * This method returns a {@code new} {@link LinkedList} which contains all
	 * the {@link Resource}s in this {@link Bot}.
	 *
	 * @return A new {@link LinkedList} with all the {@link Resource}s in this
	 *         {@link Bot}.
	 */
	public LinkedList<Resource> getResources() {
		return new LinkedList<>(resources);
	}

	/**
	 * This method removes and returns {@link Resource}s at random from this
	 * {@link Bot}.
	 *
	 * @param count
	 *            The amount of {@link Resource}s to remove from this
	 *            {@link Bot}.
	 * @return A {@code new} {@link LinkedList} with the {@link Resource}s that
	 *         were removed from this {@link Bot}.
	 */
	public LinkedList<Resource> removeRandomResources(short count) {
		LinkedList<Resource> list;

		if (count >= resources.size()) {
			list = new LinkedList<>(resources);
			resources.clear();
			return list;
		}

		list = new LinkedList<>();

		for (short i = 0; i < count; i++)
			list.add(resources.remove((int) Math.round(random.nextDouble() * resources.size())));

		return list;
	}

}
