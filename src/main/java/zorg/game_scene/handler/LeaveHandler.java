package zorg.game_scene.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zorg.game_scene.cmd.CommendExpression;
import zorg.game_scene.cmd.PlayersSTT;
import zorg.game_scene.cmd.SceneItemsVTT;
import zorg.game_scene.cmd.StateType;
import zorg.game_scene.cmd.TilesSTT;
import zorg.game_scene.def.GamePlayer;
import zorg.game_scene.def.Scene;
import zorg.game_scene.def.Tile;
import zorg.game_scene.proto.ProtoDefine.LeaveOperation;

/**
 * 离场
 * <p>
 * 给其他人发送玩家消失消息。
 * 
 * @author zhangbo
 *
 */
public class LeaveHandler extends ActionWorker<LeaveOperation> {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public Collection<CommendExpression> doWork(long serverId, long playerId, String sceneUniqId, LeaveOperation message, Scene sceneData) {
		Tile playerTile = sceneData.getTile(sceneUniqId);

		if (playerTile == null) {
			return null;
		}
		// 移除出格
		GamePlayer player = playerTile.removePlayer(sceneUniqId);
		player.setOnline(false);

		Collection<Tile> multicastTiles = sceneData.getVisionTiles(player.getPos());

		List<CommendExpression> ans = new ArrayList<>();

		// 广播离场消息
		{
			SceneItemsVTT visionType = new SceneItemsVTT();
			visionType.addItem(player);
			TilesSTT targetType = new TilesSTT();
			multicastTiles.forEach(targetType::addTile);
			CommendExpression expr = new CommendExpression(visionType, StateType.CHANGE_STATE, targetType);
			ans.add(expr);
		}
		// 给自己也发一个
		{
			SceneItemsVTT visionType = new SceneItemsVTT();
			visionType.addItem(player);
			PlayersSTT targetType = new PlayersSTT();
			targetType.addTargetPlayer(player);
			CommendExpression exprSelf = new CommendExpression(visionType, StateType.CHANGE_STATE, targetType);
			ans.add(exprSelf);
		}
		log.trace("玩家{}已离场", playerId);
		return ans;
	}

}
