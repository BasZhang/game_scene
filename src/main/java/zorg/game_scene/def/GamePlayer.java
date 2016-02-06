package zorg.game_scene.def;

import zorg.game_scene.proto.ProtoDefine.GamePlayerState;

public class GamePlayer extends ChangableSceneItem<GamePlayerState, GamePlayerState> {

	protected boolean changed = false;

	protected int x = -1;
	protected int y = -1;
	
	private long id;

	@Override
	public GamePlayerState getFinalState() {
		return GamePlayerState.newBuilder().setX(x).setY(y).build();
	}

	@Override
	public GamePlayerState getChangedState() {
		return GamePlayerState.newBuilder().setX(x).setY(y).build();
	}

	@Override
	public boolean isChanged() {
		return changed;
	}

	@Override
	public void markAsUnchanged() {
		changed = false;
	}

	@Override
	protected String getItemPrefix() {
		return "GamePlayer";
	}

	@Override
	protected String getId() {
		return String.valueOf(id);
	}

}
