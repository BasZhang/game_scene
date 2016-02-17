package zorg.game_scene.def;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zorg.game_scene.proto.ProtoDefine.SceneState;
import zorg.game_scene.util.Polygon2D;

/**
 * 地图格，包含若干个游戏对象。
 * 
 * @author zhangbo
 *
 */
public class Tile implements Changable<SceneState, SceneState>, SceneItemContainer {
	/** 格中的玩家 */
	protected Map<Object, GamePlayer> players = new HashMap<>();
	/** 消失的影子 */
	protected List<GamePlayer> disappearing = new ArrayList<>();
	/** 边界 */
	protected final Polygon2D polygon;

	public Tile(Polygon2D polygon) {
		super();
		this.polygon = polygon;
	}

	public Polygon2D getPolygon() {
		return polygon;
	}

	@Override
	public SceneState getChangedState() {
		SceneState.Builder changeStateBuilder = SceneState.newBuilder();
		for (GamePlayer child : players.values()) {
			if (child.isChanged()) {
				changeStateBuilder.addPlayerStates(child.getChangedState());
			}
		}
		for (GamePlayer gamePlayer : disappearing) {
			changeStateBuilder.addDisappearingPlayers(gamePlayer.getId());
		}
		return changeStateBuilder.build();
	}

	@Override
	public SceneState getFinalState() {
		SceneState.Builder finalStateBuilder = SceneState.newBuilder();
		for (GamePlayer child : players.values()) {
			finalStateBuilder.addPlayerStates(child.getChangedState());
		}
		return finalStateBuilder.build();
	}

	@Override
	public boolean isChanged() {
		for (GamePlayer child : players.values()) {
			if (child.isChanged()) {
				return true;
			}
		}
		if (!disappearing.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public void markAsUnchanged() {
		for (GamePlayer child : players.values()) {
			child.markAsUnchanged();
		}
		disappearing.clear();
	}

	@Override
	public BasicSceneItem getItem(String sceneUniqId) {
		GamePlayer gamePlayer = players.get(sceneUniqId);
		return gamePlayer;
	}

	public Collection<GamePlayer> getGamePlayers() {
		return new ArrayList<>(players.values());
	}

	public GamePlayer removePlayer(String sceneUniqId) {
		GamePlayer gamePlayer = players.remove(sceneUniqId);
		if (gamePlayer != null) {
			disappearing.add(gamePlayer);
		}
		return gamePlayer;
	}

	public void addGamePlayer(GamePlayer p) {
		players.put(p.getSceneUniqId(), p);
	}

}
