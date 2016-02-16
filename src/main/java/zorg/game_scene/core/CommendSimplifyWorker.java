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
		for (CommendExpression cmd : cmds) {
			List<GamePlayer> receivers = new ArrayList<>();
			SendTargetType receiverType = cmd.getReceiverType();
			if (receiverType instanceof TilesSTT) {
				((TilesSTT) receiverType).getTargetTiles().forEach(t -> receivers.addAll(t.getGamePlayers()));
			} else if (receiverType instanceof PlayersSTT) {
				receivers.addAll(((PlayersSTT) receiverType).getTargetPlayers());
			}

			List<ChangableSceneItem<? extends Message, ? extends Message>> visions = new ArrayList<>();
			VisionTargetType visionType = cmd.getVisionType();
			if (visionType instanceof TilesVTT) {
				((TilesVTT) visionType).getTiles().forEach(t -> visions.addAll(t.getGamePlayers()));
			} else if (visionType instanceof SceneItemsVTT) {
				visions.addAll(((SceneItemsVTT) visionType).getTargets());
			}

			for (GamePlayer sGamePlayer : receivers) {
				String key0 = sGamePlayer.getSceneUniqId();
				String key1 = cmd.getStateType().toString();
				if (!m1.containsKey(key0, key1)) {
					m1.put(key0, key1, new FlatCommendExpression(visions, cmd.getStateType(), Collections.singleton(sGamePlayer)));
				} else {
					FlatCommendExpression flatCommendExpression = m1.get(key0, key1);
					visions.forEach(flatCommendExpression.getVisionType()::addItem);
				}
			}
		}
		// 给同一个人发送的多条，最终状态合并单帧状态
		Set<String> key0Set = new HashSet<>();
		m1.keySet().forEach(a -> key0Set.add(a.getKeys()[0]));
		for (String key0 : key0Set) {
			if (m1.containsKey(key0, StateType.FINAL_STATE.toString()) && m1.containsKey(key0, StateType.CHANGE_STATE.toString())) {
				FlatCommendExpression finalExpression = m1.get(key0, StateType.FINAL_STATE.toString());
				List<ChangableSceneItem<? extends Message, ? extends Message>> finalVTs = new ArrayList<>(finalExpression.getVisionType().getTargets());
				FlatCommendExpression changeExpression = m1.get(key0, StateType.CHANGE_STATE.toString());
				List<ChangableSceneItem<? extends Message, ? extends Message>> changeVTs = new ArrayList<>(changeExpression.getVisionType().getTargets());
				boolean removed = changeVTs.removeAll(finalVTs);
				if (removed) {
					changeExpression = new FlatCommendExpression(changeVTs, StateType.CHANGE_STATE, changeExpression.getReceiverPlayers());
					m1.put(key0, StateType.CHANGE_STATE.toString(), changeExpression);
				}
			}
		}

		List<FlatCommendExpression> products = new ArrayList<>();
		for (String key0 : key0Set) {
			FlatCommendExpression finalExpression = m1.get(key0, StateType.FINAL_STATE.toString());
			if (finalExpression != null) {
				products.add(finalExpression);
			}
			FlatCommendExpression changeExpression = m1.get(key0, StateType.CHANGE_STATE.toString());
			if (changeExpression != null) {
				products.add(changeExpression);
			}

		}
		return products;
	}

}
