package zorg.game_scene.core;

import java.util.HashMap;
import java.util.Map;

import com.google.protobuf.Message;

import zorg.game_scene.cmd.Commend;
import zorg.game_scene.def.Scene;

public class CommendResultCache {

	Map<String, Message> cache = new HashMap<>();

	public boolean executed(Commend cmd) {
		return cache.containsKey(uniqueKey(cmd));
	}

	public Message getLastResult(Commend cmd) {
		return cache.get(uniqueKey(cmd));
	}

	public Message exec(Commend cmd, Scene sceneData) {
		Message message = cmd.exec(sceneData);
		cache.put(uniqueKey(cmd), message);
		return message;
	}

	private String uniqueKey(Commend cmd) {
		return cmd.getVisionType() + "&" + cmd.getStateType();
	}

}
