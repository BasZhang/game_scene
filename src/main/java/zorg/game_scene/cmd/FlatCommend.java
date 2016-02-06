package zorg.game_scene.cmd;

import com.google.protobuf.Message;

import zorg.game_scene.def.Scene;

public class FlatCommend {

	private VisionTargetType visionType;

	private StateType stateType;

	private ReceiverType receiverType;

	public Message exec(Scene sceneData) {
		return null;
	}

	public VisionTargetType getVisionType() {
		return visionType;
	}

	public StateType getStateType() {
		return stateType;
	}

	public ReceiverType getReceiverType() {
		return receiverType;
	}

}
