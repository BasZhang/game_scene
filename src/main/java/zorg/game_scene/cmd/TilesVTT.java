package zorg.game_scene.cmd;

import java.util.ArrayList;
import java.util.List;

import zorg.game_scene.def.Tile;

/**
 * 以格子为单元的视野
 * 
 * @author zhangbo
 *
 */
public class TilesVTT extends VisionTargetType {

	private List<Tile> tiles = new ArrayList<>();

	public void addTile(Tile tile) {
		tiles.add(tile);
	}

}
