package zorg.game_scene.def;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zorg.game_scene.proto.ProtoDefine.SceneState;

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
		for (GamePlayer gamePlayer : disappearing) {
			finalStateBuilder.addDisappearingPlayers(gamePlayer.getId());
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

}
