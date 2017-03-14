package zeale.evolution.resources;

import java.awt.Graphics;

import zeale.evolution.Object;
import zeale.evolution.bots.Bot;

class ResourceObject extends Object {

	private final Resource resource;

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	public ResourceObject(double posx, double posy, Resource resource) {
		super(posx, posy);
		this.resource = resource;
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub

	}

	public Resource onPickup(Bot bot) {
		kill();
		return resource;
	}

	@Override
	public void work(long delta) {
		// TODO Auto-generated method stub

	}

}
