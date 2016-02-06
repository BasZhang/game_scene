package zorg.game_scene.core;

import java.util.Collection;

import zorg.game_scene.cmd.CommendExpression;
import zorg.game_scene.def.Scene;

/**
 * 处理传入请求的工人。
 * 
 * @author zhangbo
 *
 */
public interface ActionWorker {

	/**
	 * 处理传入请求，返回若干个待执行的取状态指令。
	 * 
	 * @param action
	 *            传入请求
	 * @param sceneData
	 *            场景数据
	 * @return 待执行的取状态指令。
	 */
	Collection<CommendExpression> doAction(ActionData action, Scene sceneData);

}
