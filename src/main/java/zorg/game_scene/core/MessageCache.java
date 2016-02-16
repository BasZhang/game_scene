package zorg.game_scene.core;

import java.util.HashMap;
import java.util.Map;

import com.google.protobuf.Message;

import zorg.game_scene.cmd.FlatCommendExpression;
import zorg.game_scene.def.Scene;

public class MessageCache {

	Map<String, Message> cache = new HashMap<>();

	public boolean executed(FlatCommendExpression cmd) {
		return cache.containsKey(uniqueKey(cmd));
	}

	public Message getLastResult(FlatCommendExpression cmd) {
		return cache.get(uniqueKey(cmd));
	}

	public Message exec(FlatCommendExpression cmd, Scene sceneData) {
		Message message = cmd.genMsg(sceneData);
		cache.put(uniqueKey(cmd), message);
		return message;
	}

	private String uniqueKey(FlatCommendExpression cmd) {
		return cmd.getVisionType().toString() + "&" + cmd.getStateType();
	}

}
