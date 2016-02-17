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
	protected boolean online = true;

	private long id;

	@Override
	public GamePlayerState getFinalState() {
		return GamePlayerState.newBuilder().setX(x).setY(y).setOnline(online).build();
	}

	@Override
	public GamePlayerState getChangedState() {
		return GamePlayerState.newBuilder().setX(x).setY(y).setOnline(online).build();
	}

	@Override
	protected String getItemPrefix() {
		return "GamePlayer";
	}

	@Override
	public Long getId() {
		return id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public Pos getPos() {
		Pos pos = new Pos();
		pos.x = getX();
		pos.y = getY();
		return pos;
	}

}
