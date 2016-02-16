package zorg.game_scene.handler;

import java.util.Collection;

import zorg.game_scene.cmd.CommendExpression;
import zorg.game_scene.def.Scene;
import zorg.game_scene.proto.ProtoDefine.EnterOperation;

/**
 * 进入场景
 * <p>
 * 根据玩家基础数据，（需要数据节点提前准备好），提取出生点，发给相应房间去响应信息。<br>
 * 之后根据需要生成视野内所有玩家数据（包括自己），发给入场玩家；同时也需要将自己玩家数据广播给视野内其他玩家。
 * 
 * @author zhangbo
 *
 */
public class EnterHandler extends ActionWorker<EnterOperation> {

	@Override
	public Collection<CommendExpression> doWork(long serverId, long playerId, EnterOperation message, Scene sceneData) {
		// TODO Auto-generated method stub
		return null;
	}

}
