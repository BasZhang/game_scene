package zorg.game_scene.util;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class MathUtilz {

	public static final double DOUBLE_EPSILON = 1E-4D;

	public static double area(Vector3D p1, Vector3D p2, Vector3D p3) {
		double a = p1.distance(p2);
		double b = p2.distance(p3);
		double c = p3.distance(p1);
		double p = (a + b + c) / 2;
		return Math.sqrt(p * (p - a) * (p - b) * (p - c));
	}

	public static double area(Vector2D p1, Vector2D p2, Vector2D p3) {
		double a = p1.distance(p2);
		double b = p2.distance(p3);
		double c = p3.distance(p1);
		double p = (a + b + c) / 2;
		return Math.sqrt(p * (p - a) * (p - b) * (p - c));
	}

}
