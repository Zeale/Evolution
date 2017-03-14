package zeale.evolution;

import java.awt.Graphics;
import java.io.Serializable;

import zeale.evolution.bots.Bot;

public abstract class Object implements Serializable {
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
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

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
	 * <b>Setter for this {@link Object}'s {@link #posx}.</b>
	 * <p>
	 * This method sets the x position of this {@link Bot}.
	 *
	 * @param posx
	 *            The new x position for this {@link Object}.
	 */
	public void setX(double posx) {
		this.posx = posx;
	}

	/**
	 * <p>
	 * <b>Setter for this {@link Object}'s {@link #posy}.</b>
	 * <p>
	 * This method sets the y position of this {@link Object}.
	 *
	 * @param posy
	 *            The new y position of this {@link Object}.
	 */
	public void setY(double posy) {
		this.posy = posy;
	}

	/**
	 * <p>
	 * This method adds the specified amount to this {@link Object}'s x
	 * position.
	 *
	 * @param amount
	 *            The amount to incrememnt this {@link Object}'s x position by.
	 */
	public void addX(double amount) {
		posx += amount;
	}

	/**
	 * <p>
	 * This method decrements this {@link Object}'s x position by the specified
	 * amount.
	 *
	 * @param amount
	 *            The amount to decrement this {@link Object}'s x position by.
	 */
	public void decX(double amount) {
		posx -= amount;
	}

	/**
	 * <p>
	 * This method adds the specified amount to this {@link Object}'s x
	 * position.
	 *
	 * @param amount
	 *            The amount to increment this {@link Object}'s x position by.
	 */
	public void addY(double amount) {
		posy += amount;
	}

	/**
	 * <p>
	 * This method decrements this {@link Object}'s y position by the specified
	 * amount.
	 *
	 * @param amount
	 *            The amount to decrement this {@link Object}'s y position by.
	 */
	public void decY(double amount) {
		posy -= amount;
	}

	/**
	 * <p>
	 * This method increments this {@link Object}'s x position by the specified
	 * amount.
	 *
	 * @param amount
	 *            The distance to increase this {@link Object}'s x position by.
	 */
	public void incrementX(double amount) {
		posx += amount;
	}

	/**
	 * <p>
	 * This method increments this {@link Object}'s y position by the specified
	 * amount.
	 *
	 * @param amount
	 *            The amount to increase this {@link Object}'s y position by.
	 */
	public void incrementY(double amount) {
		posy += amount;
	}

	/**
	 * <p>
	 * This method decrements this {@link Object}'s x position by the specified
	 * amount.
	 *
	 * @param amount
	 *            The amount to decrease this {@link Object}'s x position by.
	 */
	public void decrementX(double amount) {
		posx -= amount;
	}

	/**
	 * <p>
	 * This method decrements this {@link Object}'s y position by the specified
	 * amount.
	 *
	 * @param amount
	 *            The amount to decrease this {@link Object}'s y position by.
	 */
	public void decrementY(double amount) {
		posy -= amount;
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
