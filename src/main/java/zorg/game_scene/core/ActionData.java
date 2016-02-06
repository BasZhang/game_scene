package zorg.game_scene.core;

import com.google.protobuf.Message;

public class ActionData {

	private long playerId;

	private long serverId;

	private Message message;

	public ActionData(long playerId, long serverId, Message message) {
		super();
		this.playerId = playerId;
		this.serverId = serverId;
		this.message = message;
	}

	public long getPlayerId() {
		return playerId;
	}

	public long getServerId() {
		return serverId;
	}

	public Message getMessage() {
		return message;
	}

}
