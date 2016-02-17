package zorg.game_scene.cmd;

import java.util.Collection;
import java.util.List;

import com.google.protobuf.Message;

import zorg.game_scene.def.ChangableSceneItem;
import zorg.game_scene.def.GamePlayer;
import zorg.game_scene.def.Scene;
import zorg.game_scene.def.Tile;
import zorg.game_scene.proto.ProtoDefine;
import zorg.game_scene.proto.ProtoDefine.GamePlayerState;

public class FlatCommendExpression extends CommendExpression implements SceneMessageGenerator<ProtoDefine.SceneState> {

	public static FlatCommendExpression ofSceneItemsVTT(Collection<ChangableSceneItem<? extends Message, ? extends Message>> visionTypePlayers, StateType stateType,
			Collection<GamePlayer> sendTargetPlayers) {
		SceneItemsVTT _visionType = new SceneItemsVTT();
		if (visionTypePlayers != null && !visionTypePlayers.isEmpty()) {
			visionTypePlayers.forEach(_visionType::addItem);
		}
		PlayersSTT _receiverType = new PlayersSTT();
		if (sendTargetPlayers != null && !sendTargetPlayers.isEmpty()) {
			sendTargetPlayers.forEach(_receiverType::addTargetPlayer);
		}
		return new FlatCommendExpression(_visionType, stateType, _receiverType);
	}

	public static FlatCommendExpression ofTilesVTT(Collection<Tile> visionTypeTiles, StateType stateType, Collection<GamePlayer> sendTargetPlayers) {
		TilesVTT _visionType = new TilesVTT();
		if (visionTypeTiles != null && !visionTypeTiles.isEmpty()) {
			visionTypeTiles.forEach(_visionType::addTile);
		}
		PlayersSTT _receiverType = new PlayersSTT();
		if (sendTargetPlayers != null && !sendTargetPlayers.isEmpty()) {
			sendTargetPlayers.forEach(_receiverType::addTargetPlayer);
		}
		return new FlatCommendExpression(_visionType, stateType, _receiverType);
	}

	protected FlatCommendExpression(VisionTargetType visionType, StateType stateType, SendTargetType receiverType) {
		super(visionType, stateType, receiverType);
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

		VisionTargetType _visionType = getVisionType();
		if (_visionType instanceof SceneItemsVTT) {
			List<ChangableSceneItem<? extends Message, ? extends Message>> targets = ((SceneItemsVTT) _visionType).getTargets();
			if (getStateType() == StateType.CHANGE_STATE) {
				targets.forEach((t) -> {
					Message changedState = t.getChangedState();
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
		} else if (_visionType instanceof TilesVTT) {
			List<Tile> tiles = ((TilesVTT) _visionType).getTiles();
			tiles.forEach(t -> {
				if (getStateType() == StateType.CHANGE_STATE) {
					ret.mergeFrom(t.getChangedState());
				} else if (getStateType() == StateType.FINAL_STATE) {
					ret.mergeFrom(t.getFinalState());
				}
			});
		}
		return ret.build();
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
