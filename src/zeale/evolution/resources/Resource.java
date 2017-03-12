package zeale.evolution.resources;

import java.util.Random;

public class Resource {
	/**
	 * Attributes of this {@link Resource} that define how it works in game. The
	 * {@link #weight} will define how hard this {@link Resource} is to carry,
	 * and the {@link #value} defines how rewarding this {@link Resource} is.
	 */
	private int value = 1, weight = 25;

	/**
	 * A private static {@link Random} for use around the class.
	 */
	private static final Random rand = new Random();

	/**
	 * Constructs a new {@link Resource}. (<b>Will be changed by the next
	 * version...</b>)
	 */
	public Resource() {
		value = rand.nextInt(5) + 1;
		weight = rand.nextInt(201) + 50;
	}

	/**
	 * A getter for the value of this {@link Resource}.
	 *
	 * @return The {@link #value} of this {@link Resource}.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * A getter for the weight of this {@link Resource}.
	 *
	 * @return The {@link #weight} of this {@link Resource}.
	 */
	public int getWeight() {
		return weight;
	}
}
