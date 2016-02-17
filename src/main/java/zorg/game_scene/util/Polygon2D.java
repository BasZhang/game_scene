package zorg.game_scene.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 * 多边形基类，用的时候需要保证是三角形或长方形。
 * 
 * @author zhangbo
 *
 */
public class Polygon2D {

	protected final List<Vector2D> verts;

	private final double area;

	public Polygon2D(Vector2D vert1, Vector2D vert2, Vector2D vert3) {
		ArrayList<Vector2D> list = new ArrayList<>(3);
		list.add(vert1);
		list.add(vert2);
		list.add(vert3);
		this.verts = Collections.unmodifiableList(list);
		this.area = MathUtilz.area(vert1, vert2, vert3);
	}

	public Polygon2D(Vector2D vert1, Vector2D vert2, Vector2D vert3, Vector2D vert4) {
		ArrayList<Vector2D> list = new ArrayList<>(4);
		list.add(vert1);
		list.add(vert2);
		list.add(vert3);
		list.add(vert4);
		this.verts = Collections.unmodifiableList(list);
		this.area = MathUtilz.area(vert1, vert2, vert3) + MathUtilz.area(vert1, vert3, vert4);
	}

	public boolean adjacentTo(Polygon2D another) {
		Objects.requireNonNull(another);
		for (Vector2D vert : verts) {
			for (Vector2D otherVert : another.verts) {
				if (vert.distance(otherVert) < MathUtilz.DOUBLE_EPSILON)
					return true;
			}
		}
		return false;
	}

	public boolean geoContains(Vector2D p) {
		// 思路：面积法
		double areaSum = 0d;
		for (int i = 0, sz = this.verts.size(); i < sz; i++) {
			Vector2D a = this.verts.get(i);
			Vector2D b = this.verts.get(i + 1 == sz ? 0 : i + 1);
			areaSum += MathUtilz.area(a, b, p);
		}
		return Math.abs(areaSum - this.area) < MathUtilz.DOUBLE_EPSILON;
	}
}
