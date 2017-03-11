package zeale.evolution.structures;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import zeale.evolution.Object;
import zeale.evolution.bots.Bot;
import zeale.evolution.resources.Resource;

public final class Spawnpoint extends Structure {

	/**
	 * A {@link LinkedList} of the {@link Resource}s that this
	 * {@link Spawnpoint} currently holds.
	 */
	private final LinkedList<Resource> resources = new LinkedList<>();

	/**
	 * The size value of this {@link Spawnpoint}. Defaults to 36.
	 */
	private final int width = 36, height = 36;

	/**
	 * Constructs a new {@link Spawnpoint} using an x and y position.
	 *
	 * @param posx
	 *            The X position of this {@link Spawnpoint}.
	 * @param posy
	 *            The Y position of this {@link Spawnpoint}.
	 */
	public Spawnpoint(final double posx, final double posy) {
		super(posx, posy);
	}

	@Override
	public void activate(final Bot bot) {
		resources.addAll(bot.takeResources());
	}

	@Override
	public void activate(final Object activatingObj) {

	}

	@Override
	public void render(final Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(getX() - width / 2, getY() - height / 2, width, height);
		g.setColor(Color.YELLOW);
		g.drawString("" + resources.size(), getX() - 4, getY() + 5);
	}

	@Override
	public void work(final long delta) {

	}

}
