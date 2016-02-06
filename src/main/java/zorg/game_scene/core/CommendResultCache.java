package zorg.game_scene.core;

import java.util.HashMap;
import java.util.Map;

import com.google.protobuf.Message;

import zorg.game_scene.cmd.CommendExpression;
import zorg.game_scene.def.Scene;

public class CommendResultCache {

	Map<String, Message> cache = new HashMap<>();

	public boolean executed(CommendExpression cmd) {
		return cache.containsKey(uniqueKey(cmd));
	}

	public Message getLastResult(CommendExpression cmd) {
		return cache.get(uniqueKey(cmd));
	}

	public Message exec(CommendExpression cmd, Scene sceneData) {
		Message message = cmd.genMsg(sceneData);
		cache.put(uniqueKey(cmd), message);
		return message;
	}

	private String uniqueKey(CommendExpression cmd) {
		return cmd.getVisionType() + "&" + cmd.getStateType();
	}

}
