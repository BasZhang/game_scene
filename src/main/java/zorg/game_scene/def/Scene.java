package zorg.game_scene.def;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 * 场景，包含若干地图格。
 * 
 * @author zhangbo
 *
 */
public class Scene implements SceneItemContainer {
	/** 服务器Id */
	protected int serverId;
	/** 地图格 */
	protected Map<Object, Tile> tiles = new LinkedHashMap<>();
	
	/** 出生点 */
	protected Pos bornPos;

	public Map<Object, Tile> getTiles() {
		return tiles;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	@Override
	public BasicSceneItem getItem(String sceneUniqId) {
		Collection<Tile> _tiles = tiles.values();
		for (Tile t : _tiles) {
			BasicSceneItem item = t.getItem(sceneUniqId);
			if (item != null) {
				return item;
			}
		}
		return null;
	}

	public Tile getTile(String sceneUniqId) {
		Collection<Tile> _tiles = tiles.values();
		for (Tile t : _tiles) {
			BasicSceneItem item = t.getItem(sceneUniqId);
			if (item != null) {
				return t;
			}
		}
		return null;
	}

	public Collection<Tile> getVisionTiles(Pos pos) {
		List<Tile> result = new ArrayList<>();
		Tile center = getSurroundingTile(pos);
		if (center != null) {
			for (Tile tile : tiles.values()) {
				if (tile.getPolygon().adjacentTo(center.getPolygon())) {
					result.add(tile);
				}
			}
		}
		return result;
	}

	public Tile getSurroundingTile(Pos pos) {
		Vector2D pp = pos.projectTo2D();
		Tile center = null;
		for (Tile tile : tiles.values()) {
			if (tile.getPolygon().geoContains(pp)) {
				center = tile;
				break;
			}
		}
		return center;
	}

	public Pos getBornPos() {
		// TODO Auto-generated method stub
		return bornPos;
	}

}
