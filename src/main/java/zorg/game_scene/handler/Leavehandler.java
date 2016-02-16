package zorg.game_scene.handler;

import java.util.Collection;

import zorg.game_scene.cmd.CommendExpression;
import zorg.game_scene.def.Scene;
import zorg.game_scene.proto.ProtoDefine.LeaveOperation;

public class Leavehandler extends ActionWorker<LeaveOperation> {

	@Override
	public Collection<CommendExpression> doWork(long serverId, long playerId, LeaveOperation message, Scene sceneData) {
		// TODO Auto-generated method stub
		return null;
	}

}
