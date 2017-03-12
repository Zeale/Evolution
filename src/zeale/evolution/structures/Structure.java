package zeale.evolution.structures;

import zeale.evolution.Object;
import zeale.evolution.bots.Bot;

public abstract class Structure extends Object {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new {@link Structure} given an x and y position.
	 *
	 * @param posx
	 *            The x position of this {@link Structure}.
	 * @param posy
	 *            The y position of this {@link Structure}.
	 */
	public Structure(final double posx, final double posy) {
		super(posx, posy);
	}

	/**
	 * <p>
	 * This method is called when a {@link Bot} activates this
	 * {@link Structure}.
	 *
	 * @param activatingBot
	 *            The {@link Bot} that activated this {@link Structure}.
	 */
	public void activate(final Bot activatingBot) {

	}

	@Override
	public void moveX(final int units) {
	}

	@Override
	public void moveY(final int units) {
	}
}
