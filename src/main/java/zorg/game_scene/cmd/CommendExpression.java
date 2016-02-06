package zorg.game_scene.cmd;

import com.google.protobuf.Message;

import zorg.game_scene.def.Scene;

/**
 * 执行的指令
 * <p>
 * 语意大概是：<br>
 * 取[当前视野|当前场景|单个人|...]的[完整状态|当前帧变化状态]发给[某几个区域|某几个人]。
 * 
 * @author zhangbo
 *
 */
public class CommendExpression {

	/** 视野类型 */
	protected VisionTargetType visionType;
	/** 状态类型 */
	protected StateType stateType;
	/** 发给者类型 */
	protected SendTargetType receiverType;

	/**
	 * 生成需要发送的状态信息。
	 * 
	 * @param sceneData
	 *            场景数据
	 * @return 状态消息。
	 */
	public Message genMsg(Scene sceneData) {
		return null;
	}

	public VisionTargetType getVisionType() {
		return visionType;
	}

	public StateType getStateType() {
		return stateType;
	}

	public SendTargetType getReceiverType() {
		return receiverType;
	}

}
