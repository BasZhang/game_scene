package zorg.game_scene.def;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class Pos {

	public double x, y;

	public Vector2D projectTo2D() {
		return new Vector2D(x, y);
	}
}
