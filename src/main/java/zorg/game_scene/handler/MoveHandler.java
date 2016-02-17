package zorg.game_scene.handler;

import java.util.Collection;

import zorg.game_scene.cmd.CommendExpression;
import zorg.game_scene.def.Scene;
import zorg.game_scene.proto.ProtoDefine.MoveOperation;

/**
 * 移动
 * <p>
 * 如果新旧坐标是在同一个格子里移动，需要广播新的位置；<br>
 * 否则是在不同的格子里，根据视野，非别发送移动或进入或离开三种消息；<br>
 * 另外，若目标点出格，想想怎么办（TODO 目前是报异常不做处理）。
 * 
 * @author zhangbo
 *
 */
public class MoveHandler extends ActionWorker<MoveOperation> {

	@Override
	public Collection<CommendExpression> doWork(long serverId, long playerId, String sceneUniqId, MoveOperation message, Scene sceneData) {
		// TODO Auto-generated method stub
		return null;
	}

}
