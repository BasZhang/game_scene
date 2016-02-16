package zorg.game_scene.handler;

import java.util.Collection;

import com.google.protobuf.Message;

import zorg.game_scene.cmd.CommendExpression;
import zorg.game_scene.def.Scene;

public abstract class ActionWorker<T extends Message> {

	public abstract Collection<CommendExpression> doWork(long serverId, long playerId, T message, Scene sceneData);

}
