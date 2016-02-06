package zorg.game_scene.cmd;

import java.util.List;

import com.google.protobuf.Message;

import zorg.game_scene.def.GamePlayer;
import zorg.game_scene.def.Scene;

public class FlatCommendExpression extends CommendExpression {

	public Message genMsg(Scene sceneData) {
		return null;
	}

	public VisionTargetType getVisionType() {
		return visionType;
	}

	public StateType getStateType() {
		return stateType;
	}

	public List<GamePlayer> getReceiverPlayers() {
		PlayersSTT pstt = (PlayersSTT) receiverType;
		return pstt.getTargetPlayers();
	}

}
