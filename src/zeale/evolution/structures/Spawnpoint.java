package zeale.evolution.structures;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import zeale.evolution.Evolution;
import zeale.evolution.Object;
import zeale.evolution.bots.Bot;
import zeale.evolution.resources.Resource;
import zeale.evolution.structures.resourcespawners.ResourceSpawner;

public final class Spawnpoint extends Structure {

	/**
	 * A private static random for wherever/whenever it's needed in this class.
	 */
	private static Random rand = new Random();

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
		List<Resource> list = bot.takeResources();
		for (Resource r : list)
			bot.addLife(r.getValue() * 6);
		resources.addAll(list);
	}

	public LinkedList<Resource> removeResources(int count) {
		LinkedList<Resource> list = new LinkedList<>();
		for (int i = 0; i < count; i++)
			if (!resources.isEmpty())
				list.add(resources.removeFirst());
			else
				return list;
		return list;
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
		if (resources.size() >= 15)
			if (rand.nextInt(20) == 0) {
				if (rand.nextBoolean())
					Evolution.getCurrentInstance()
							.addStruct(new ResourceSpawner(rand.nextDouble() * Evolution.calculateSize(1920, true),
									rand.nextDouble() * Evolution.calculateSize(1080, false), (short) 50));
				else
					Evolution.getCurrentInstance()
							.addBot(new Bot(rand.nextDouble() * Evolution.calculateSize(1920, true),
									rand.nextDouble() * Evolution.calculateSize(1080, false), (short) 2));
				removeResources(15);

			}

	}

}
