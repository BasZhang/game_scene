package zorg.game_scene.handler;

import java.util.Collection;

import zorg.game_scene.cmd.CommendExpression;
import zorg.game_scene.def.Scene;
import zorg.game_scene.proto.ProtoDefine.MoveOperation;

public class Movehandler extends ActionWorker<MoveOperation> {

	@Override
	public Collection<CommendExpression> doWork(long serverId, long playerId, MoveOperation message, Scene sceneData) {
		// TODO Auto-generated method stub
		return null;
	}

}
