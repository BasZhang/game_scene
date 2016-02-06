package zorg.game_scene.def;

import zorg.game_scene.proto.ProtoDefine.GamePlayerState;

/**
 * 游戏玩家
 * 
 * @author zhangbo
 *
 */
public class GamePlayer extends ChangableSceneItem<GamePlayerState, GamePlayerState> {

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
	protected String getItemPrefix() {
		return "GamePlayer";
	}

	@Override
	public Long getId() {
		return id;
	}

}
