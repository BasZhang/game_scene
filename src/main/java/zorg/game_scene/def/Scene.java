package zorg.game_scene.def;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 场景，包含若干地图格。
 * 
 * @author zhangbo
 *
 */
public class Scene {
	/** 服务器Id */
	protected int serverId;
	/** 地图格 */
	protected Map<Object, Tile> tiles = new LinkedHashMap<>();

	public Map<Object, Tile> getTiles() {
		return tiles;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

}
