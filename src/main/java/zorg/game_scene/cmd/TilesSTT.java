package zorg.game_scene.cmd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import zorg.game_scene.def.Tile;

/**
 * 向格子中所有玩家发送
 * 
 * @author zhangbo
 *
 */
public class TilesSTT extends SendTargetType {

	private List<Tile> targetTiles = new ArrayList<>();

	public void addTile(Tile tile) {
		targetTiles.add(tile);
	}

	public List<Tile> getTargetTiles() {
		return Collections.unmodifiableList(targetTiles);
	}

}
