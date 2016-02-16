package zorg.game_scene.handler;

import java.util.Collection;

import com.google.protobuf.Message;

import zorg.game_scene.cmd.CommendExpression;
import zorg.game_scene.core.ActionWorkers;
import zorg.game_scene.def.Scene;

public abstract class ActionWorker<T extends Message> {

	/**
	 * 处理逻辑。
	 * 
	 * @param serverId
	 *            服务器Id
	 * @param playerId
	 *            玩家Id
	 * @param message
	 *            消息内容
	 * @param sceneData
	 *            场景数据
	 * @return 返回的命令（可以为{@code null}）。
	 */
	public abstract Collection<CommendExpression> doWork(long serverId, long playerId, T message, Scene sceneData);

	/**
	 * 构造方法内含按泛型参数注册子类。
	 */
	protected ActionWorker() {
		String typeName = getClass().getGenericSuperclass().getTypeName();
		ActionWorkers.register(typeName.split("[<|>]", 2)[1], this);
	}
}
