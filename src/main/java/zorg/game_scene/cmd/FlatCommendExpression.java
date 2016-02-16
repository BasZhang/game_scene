package zorg.game_scene.cmd;

import java.util.Collection;
import java.util.List;

import com.google.protobuf.Message;

import zorg.game_scene.def.ChangableSceneItem;
import zorg.game_scene.def.GamePlayer;
import zorg.game_scene.def.Scene;
import zorg.game_scene.proto.ProtoDefine;
import zorg.game_scene.proto.ProtoDefine.GamePlayerState;

public class FlatCommendExpression extends CommendExpression implements SceneMessageGenerator<ProtoDefine.SceneState> {

	public FlatCommendExpression(Collection<ChangableSceneItem<? extends Message, ? extends Message>> visionTypePlayers, StateType stateType, Collection<GamePlayer> sendTargetPlayers) {
		super();

		SceneItemsVTT _visionType = new SceneItemsVTT();
		if (visionTypePlayers != null && !visionTypePlayers.isEmpty()) {
			visionTypePlayers.forEach(_visionType::addItem);
		}
		this.visionType = _visionType;
		
		this.stateType = stateType;

		PlayersSTT _receiverType = new PlayersSTT();
		if (sendTargetPlayers != null && !sendTargetPlayers.isEmpty()) {
			sendTargetPlayers.forEach(_receiverType::addTargetPlayer);
		}
		this.receiverType = _receiverType;
	}

	/**
	 * 生成需要发送的状态信息。
	 * 
	 * @param sceneData
	 *            场景数据
	 * @return 状态消息。
	 */
	@Override
	public ProtoDefine.SceneState genMsg(Scene sceneData) {
		ProtoDefine.SceneState.Builder ret = ProtoDefine.SceneState.newBuilder();

		SceneItemsVTT _visionType = getVisionType();
		List<ChangableSceneItem<? extends Message, ? extends Message>> targets = _visionType.getTargets();
		if (getStateType() == StateType.CHANGE_STATE) {
			targets.forEach((vTarget) -> {
				Message changedState = vTarget.getChangedState();
				if (changedState instanceof GamePlayerState) {
					ret.addPlayerStates((GamePlayerState) changedState);
				}
			});
		} else if (getStateType() == StateType.FINAL_STATE) {
			targets.forEach((vTarget) -> {
				Message finalState = vTarget.getFinalState();
				if (finalState instanceof GamePlayerState) {
					ret.addPlayerStates((GamePlayerState) finalState);
				}
			});
		}
		return ret.build();
	}

	@Override
	public SceneItemsVTT getVisionType() {
		return (SceneItemsVTT) visionType;
	}

	public StateType getStateType() {
		return stateType;
	}

	@Override
	public PlayersSTT getReceiverType() {
		return (PlayersSTT) receiverType;
	}

	public List<GamePlayer> getReceiverPlayers() {
		PlayersSTT pstt = getReceiverType();
		return pstt.getTargetPlayers();
	}

	public void addReceiverPlayers(GamePlayer e) {
		List<GamePlayer> receiverPlayers = getReceiverPlayers();
		if (!receiverPlayers.contains(e)) {
			receiverPlayers.add(e);
		}
	}

}
