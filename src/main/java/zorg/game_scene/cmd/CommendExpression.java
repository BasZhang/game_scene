package zorg.game_scene.cmd;

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

	public CommendExpression(VisionTargetType visionType, StateType stateType, SendTargetType receiverType) {
		super();
		this.visionType = visionType;
		this.stateType = stateType;
		this.receiverType = receiverType;
	}

	public CommendExpression() {
		super();
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
