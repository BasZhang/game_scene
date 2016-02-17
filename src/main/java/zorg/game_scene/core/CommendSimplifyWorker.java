package zorg.game_scene.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.map.MultiKeyMap;

import com.google.protobuf.Message;

import zorg.game_scene.cmd.CommendExpression;
import zorg.game_scene.cmd.FlatCommendExpression;
import zorg.game_scene.cmd.PlayersSTT;
import zorg.game_scene.cmd.SceneItemsVTT;
import zorg.game_scene.cmd.SendTargetType;
import zorg.game_scene.cmd.StateType;
import zorg.game_scene.cmd.TilesSTT;
import zorg.game_scene.cmd.TilesVTT;
import zorg.game_scene.cmd.VisionTargetType;
import zorg.game_scene.def.ChangableSceneItem;
import zorg.game_scene.def.GamePlayer;
import zorg.game_scene.def.Tile;

/**
 * 简化指令的工人
 * 
 * @author zhangbo
 *
 */
public class CommendSimplifyWorker {

	/**
	 * 将所有的命令合并简化为{@code FlatCommendExpression}型。
	 * 
	 * @param cmds
	 *            各种各样的指令
	 * @return 简化后的指令列表
	 */
	public List<FlatCommendExpression> simplify(Collection<CommendExpression> cmds) {
		// 首先实现同类型的命令，给发送对象合并为一条
		MultiKeyMap<String, FlatCommendExpression> m1 = new MultiKeyMap<>();
		MultiKeyMap<String, FlatCommendExpression> m2 = new MultiKeyMap<>();
		for (CommendExpression cmd : cmds) {
			List<ChangableSceneItem<? extends Message, ? extends Message>> visions1 = new ArrayList<>();
			List<Tile> visions2 = new ArrayList<>();
			List<GamePlayer> receivers = new ArrayList<>();
			SendTargetType receiverType = cmd.getReceiverType();
			if (receiverType instanceof TilesSTT) {
				((TilesSTT) receiverType).getTargetTiles().forEach(t -> receivers.addAll(t.getGamePlayers()));
			} else if (receiverType instanceof PlayersSTT) {
				receivers.addAll(((PlayersSTT) receiverType).getTargetPlayers());
			}

			VisionTargetType visionType = cmd.getVisionType();
			if (visionType instanceof SceneItemsVTT) {
				visions1.addAll(((SceneItemsVTT) visionType).getTargets());
				for (GamePlayer sGamePlayer : receivers) {
					String key0 = sGamePlayer.getSceneUniqId();
					String key1 = cmd.getStateType().toString();
					if (!m1.containsKey(key0, key1)) {
						m1.put(key0, key1, FlatCommendExpression.ofSceneItemsVTT(visions1, cmd.getStateType(), Collections.singleton(sGamePlayer)));
					} else {
						SceneItemsVTT sceneItemsVTT = (SceneItemsVTT) m1.get(key0, key1).getVisionType();
						visions1.forEach(sceneItemsVTT::addItem);
					}
				}
			} else if (visionType instanceof TilesVTT) {
				visions2.addAll(((TilesVTT) visionType).getTiles());
				for (GamePlayer sGamePlayer : receivers) {
					String key0 = sGamePlayer.getSceneUniqId();
					String key1 = cmd.getStateType().toString();
					if (!m2.containsKey(key0, key1)) {
						m2.put(key0, key1, FlatCommendExpression.ofTilesVTT(visions2, cmd.getStateType(), Collections.singleton(sGamePlayer)));
					} else {
						TilesVTT tilesVTT = (TilesVTT) m2.get(key0, key1).getVisionType();
						visions2.forEach(tilesVTT::addTile);
					}
				}
			}

		}
		// 给同一个人发送的多条，最终状态合并单帧状态
		Set<String> key0Set1 = new HashSet<>();
		m1.keySet().forEach(a -> key0Set1.add(a.getKeys()[0]));
		for (String key0 : key0Set1) {
			if (m1.containsKey(key0, StateType.FINAL_STATE.toString()) && m1.containsKey(key0, StateType.CHANGE_STATE.toString())) {
				FlatCommendExpression finalExpression = m1.get(key0, StateType.FINAL_STATE.toString());
				List<ChangableSceneItem<? extends Message, ? extends Message>> finalVTs = new ArrayList<>(((SceneItemsVTT) finalExpression.getVisionType()).getTargets());
				FlatCommendExpression changeExpression = m1.get(key0, StateType.CHANGE_STATE.toString());
				List<ChangableSceneItem<? extends Message, ? extends Message>> changeVTs = new ArrayList<>(((SceneItemsVTT) changeExpression.getVisionType()).getTargets());
				boolean removed = changeVTs.removeAll(finalVTs);
				if (removed) {
					changeExpression = FlatCommendExpression.ofSceneItemsVTT(changeVTs, StateType.CHANGE_STATE, changeExpression.getReceiverPlayers());
					m1.put(key0, StateType.CHANGE_STATE.toString(), changeExpression);
				}
			}
		}
		Set<String> key0Set2 = new HashSet<>();
		m2.keySet().forEach(a -> key0Set2.add(a.getKeys()[0]));
		for (String key0 : key0Set2) {
			if (m2.containsKey(key0, StateType.FINAL_STATE.toString()) && m2.containsKey(key0, StateType.CHANGE_STATE.toString())) {
				FlatCommendExpression finalExpression = m2.get(key0, StateType.FINAL_STATE.toString());
				List<Tile> finalVTs = new ArrayList<>(((TilesVTT) finalExpression.getVisionType()).getTiles());
				FlatCommendExpression changeExpression = m2.get(key0, StateType.CHANGE_STATE.toString());
				List<Tile> changeVTs = new ArrayList<>(((TilesVTT) changeExpression.getVisionType()).getTiles());
				boolean removed = changeVTs.removeAll(finalVTs);
				if (removed) {
					changeExpression = FlatCommendExpression.ofTilesVTT(changeVTs, StateType.CHANGE_STATE, changeExpression.getReceiverPlayers());
					m2.put(key0, StateType.CHANGE_STATE.toString(), changeExpression);
				}
			}
		}

		List<FlatCommendExpression> products = new ArrayList<>();
		for (String key0 : key0Set1) {
			FlatCommendExpression finalExpression = m1.get(key0, StateType.FINAL_STATE.toString());
			if (finalExpression != null) {
				products.add(finalExpression);
			}
			FlatCommendExpression changeExpression = m1.get(key0, StateType.CHANGE_STATE.toString());
			if (changeExpression != null) {
				products.add(changeExpression);
			}
		}

		for (String key0 : key0Set2) {
			FlatCommendExpression finalExpression = m2.get(key0, StateType.FINAL_STATE.toString());
			if (finalExpression != null) {
				products.add(finalExpression);
			}
			FlatCommendExpression changeExpression = m2.get(key0, StateType.CHANGE_STATE.toString());
			if (changeExpression != null) {
				products.add(changeExpression);
			}

		}
		return products;
	}

}
