package zorg.game_scene.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zorg.game_scene.cmd.CommendExpression;
import zorg.game_scene.def.Scene;
import zorg.game_scene.handler.ActionWorker;
import zorg.game_scene.handler.EnterHandler;
import zorg.game_scene.handler.LeaveHandler;
import zorg.game_scene.handler.MoveHandler;

/**
 * 处理传入请求的工人。
 * 
 * @author zhangbo
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ActionWorkers {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	protected static Map<String, ActionWorker<?>> registration = new HashMap<>();

	/**
	 * 注册处理类。
	 * 
	 * @param cl
	 *            消息类型
	 * @param actionWorker
	 *            处理类
	 */
	public static void register(String className, ActionWorker actionWorker) {
		registration.put(className, actionWorker);
	}

	static {
		new EnterHandler();
		new LeaveHandler();
		new MoveHandler();
	}

	/**
	 * 处理传入请求，返回若干个待执行的取状态指令。
	 * 
	 * @param action
	 *            传入请求
	 * @param sceneData
	 *            场景数据
	 * @return 待执行的取状态指令。
	 */
	Collection<CommendExpression> doAction(ActionData action, Scene sceneData) {
		Collection<CommendExpression> doWorkRet = null;
		ActionWorker actionWorker = registration.get(action.getMessage().getClass().getName());
		if (actionWorker != null) {
			try {
				doWorkRet = actionWorker.doWork(action.getServerId(), action.getPlayerId(), action.getMessage(), sceneData);
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		if (doWorkRet != null) {
			return doWorkRet;
		} else {
			return new ArrayList<>();
		}
	}
}
