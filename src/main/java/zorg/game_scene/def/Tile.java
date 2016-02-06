package zorg.game_scene.def;

import java.util.HashMap;
import java.util.Map;

import zorg.game_scene.proto.ProtoDefine.SceneState;

public class Tile implements Changable<SceneState, SceneState> {

	protected Map<Object, GamePlayer> players = new HashMap<>();

	@Override
	public SceneState getChangedState() {
		SceneState.Builder changeStateBuilder = SceneState.newBuilder();
		for (GamePlayer child : players.values()) {
			if (child.isChanged()) {
				changeStateBuilder.addPlayerStates(child.getChangedState());
			}
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
		return false;
	}

	@Override
	public void markAsUnchanged() {
		for (GamePlayer child : players.values()) {
			child.markAsUnchanged();
		}
	}

}
