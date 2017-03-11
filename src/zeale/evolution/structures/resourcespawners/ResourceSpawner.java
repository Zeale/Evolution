package zeale.evolution.structures.resourcespawners;

import java.awt.Graphics;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import zeale.evolution.Evolution;
import zeale.evolution.Object;
import zeale.evolution.bots.Bot;
import zeale.evolution.resources.Resource;
import zeale.evolution.structures.Structure;

public class ResourceSpawner extends Structure {
	/**
	 * The {@link LinkedList} that holds this {@link ResourceSpawner}'s
	 * resources.
	 */
	private LinkedList<Resource> resources = new LinkedList<>();

	/**
	 * A private {@link Random} for use around the class.
	 */
	private static Random rand = new Random();

	/**
	 * Constructs a {@link ResourceSpawner} using an x and y position and a
	 * maximum capacity.
	 *
	 * @param posx
	 *            The X position of this {@link ResourceSpawner}.
	 * @param posy
	 *            The Y position of this {@link ResourceSpawner}.
	 * @param maxCapacity
	 *            The maximum amount of {@link Resource}s that this
	 *            {@link ResourceSpawner} can hold.
	 */
	public ResourceSpawner(final double posx, final double posy, final short maxCapacity) {
		super(posx, posy);
		resources = new LinkedList<Resource>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void add(final int index, final Resource element) {
				if (size() < maxCapacity)
					super.add(index, element);
			}

			@Override
			public boolean add(final Resource e) {
				return size() < maxCapacity ? super.add(e) : false;
			}

			@Override
			public boolean addAll(final Collection<? extends Resource> c) {
				return c.size() + size() < maxCapacity ? super.addAll(c) : false;
			}

			@Override
			public boolean addAll(final int index, final Collection<? extends Resource> c) {
				return c.size() + size() < maxCapacity ? super.addAll(index, c) : false;
			}
		};

		for (int i = 0; i < maxCapacity; i++)
			resources.add(new Resource());
	}

	@Override
	public void activate(final Bot bot) {
		if (!isAlive())
			return;
		bot.giveResource(takeRandomResource());
		bot.addWaitTime(100);
		if (resources.isEmpty())
			kill();
	}

	@Override
	public void activate(final Object activatingObj) {

	}

	public Resource getRandomResource() {
		if (!isAlive() || resources.isEmpty())
			return null;
		if (resources.size() == 1)
			return resources.getFirst();
		return resources.get(ResourceSpawner.rand.nextInt(resources.size()));
	}

	/**
	 * A getter for this {@link ResourceSpawner}'s {@link Resource}s.
	 *
	 * @return An unmodifiable list of the {@link Resource}s in this
	 *         {@link ResourceSpawner}.
	 */
	public List<Resource> getResources() {
		return Collections.unmodifiableList(resources);
	}

	@Override
	public void render(final Graphics g) {
		g.fillRect(getX() - Evolution.getCurrentInstance().getCx(), getY() - Evolution.getCurrentInstance().getCy(),
				Evolution.calculateSize(20, true), Evolution.calculateSize(20, false));
	}

	/**
	 * This method will take random {@link Resource}s from this spawner and
	 * return them. The returned {@link Resource}s will be erased from the
	 * spawner once taken.
	 *
	 * @return The {@link Resource}s removed from this spawner and null if this
	 *         spawner is empty.
	 */
	public Resource takeRandomResource() {
		if (!isAlive() || resources.isEmpty())
			return null;
		if (resources.size() == 1)
			return resources.removeFirst();
		return resources.remove(ResourceSpawner.rand.nextInt(resources.size()));
	}

	@Override
	public void work(final long delta) {

	}
}
