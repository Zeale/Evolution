package zeale.evolution;

import java.awt.Graphics;

public abstract class Object {
	/**
	 * <p>
	 * Whether or not this {@link Object} is alive. This defines whether or not
	 * it should exist in game.
	 */
	private boolean isAlive = true;
	/**
	 * <p>
	 * The position of this {@link Object} in game.
	 */
	protected double posx, posy;

	/**
	 * <strong>Constructs an {@link Object}.</strong>
	 *
	 * @param posx
	 *            The x position of the {@link Object}.
	 * @param posy
	 *            The y position of the {@link Object}.
	 */
	public Object(final double posx, final double posy) {
		this.posx = posx;
		this.posy = posy;
	}

	/**
	 * <p>
	 * This method is called when another {@link Object} is close enough to this
	 * one and it <i>activates</i> this one.
	 *
	 * @param activatingObj
	 */
	public void activate(final Object activatingObj) {

	}

	/**
	 * A getter for this {@link Object}'s x position.
	 *
	 * @return This {@link Object}'s {@link #posx}.
	 */
	public int getX() {
		return (int) posx;
	}

	/**
	 * A getter for this {@link Object}'s y position.
	 *
	 * @return This {@link Object}'s {@link #posy}.
	 */
	public int getY() {
		return (int) posy;
	}

	/**
	 * <p>
	 * A getter for {@link #isAlive}.
	 *
	 * @return Whether or not this {@link Object} is alive. See {@link #isAlive}
	 *         for more details.
	 */
	public boolean isAlive() {
		return isAlive;
	}

	/**
	 * This method kills this object and sets {@link #isAlive} to false.
	 *
	 * @return <code>true</code> if {@link #isAlive} was successfully
	 *         <i>changed</i> to <code>false</code>, and <code>false</code> if
	 *         {@link #isAlive} was already <code>false</code>.
	 */
	public boolean kill() {
		if (!isAlive)
			return false;
		isAlive = false;
		return true;
	}

	/**
	 * Moves this {@link Object} <code>units</code> units across the x axis.
	 *
	 * @param units
	 *            The distance to move this {@link Object}.
	 */
	public void moveX(final int units) {
		posx += units;
	}

	/**
	 * Moves this {@link Object} <code>units</code> units across the y axis.
	 *
	 * @param units
	 *            The distance to move this {@link Object}.
	 */
	public void moveY(final int units) {
		posy += units;
	}

	/**
	 * This method is called when this {@link Object} needs to be rendered. This
	 * method should be overridden in subclasses to provide unique rendering for
	 * each subclass.
	 *
	 * @param g
	 *            The {@link Graphics} object used to draw to the screen.
	 */
	public abstract void render(Graphics g);

	/**
	 * This method is called repeatedly as the game runs. This {@link Object}
	 * will do whatever it needs to in here.
	 *
	 * @param delta
	 *            The amount of time that has passed since the last call to this
	 *            method, in nanoseconds. See
	 *            {@link Evolution#nanosecToSec(long)} for conversion from
	 *            nanoseconds to seconds.
	 */
	public abstract void work(long delta);

}
