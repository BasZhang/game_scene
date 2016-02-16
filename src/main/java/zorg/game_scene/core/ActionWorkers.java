package zorg.game_scene.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import zorg.game_scene.cmd.CommendExpression;
import zorg.game_scene.def.Scene;
import zorg.game_scene.handler.ActionWorker;

/**
 * 处理传入请求的工人。
 * 
 * @author zhangbo
 *
 */
public class ActionWorkers {

	protected Map<Class<?>, ActionWorker<?>> registration = new HashMap<>();

	/**
	 * 处理传入请求，返回若干个待执行的取状态指令。
	 * 
	 * @param action
	 *            传入请求
	 * @param sceneData
	 *            场景数据
	 * @return 待执行的取状态指令。
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	Collection<CommendExpression> doAction(ActionData action, Scene sceneData) {
		ActionWorker actionWorker = registration.get(action.getMessage().getClass());
		if (actionWorker != null) {
			return actionWorker.doWork(action.getServerId(), action.getPlayerId(), action.getMessage(), sceneData);
		}
		return new ArrayList<>();
	}

}
