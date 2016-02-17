package zorg.game_scene.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zorg.game_scene.cmd.CommendExpression;
import zorg.game_scene.cmd.PlayersSTT;
import zorg.game_scene.cmd.SceneItemsVTT;
import zorg.game_scene.cmd.StateType;
import zorg.game_scene.cmd.TilesSTT;
import zorg.game_scene.cmd.TilesVTT;
import zorg.game_scene.def.GamePlayer;
import zorg.game_scene.def.Pos;
import zorg.game_scene.def.Scene;
import zorg.game_scene.def.Tile;
import zorg.game_scene.proto.ProtoDefine.EnterOperation;

/**
 * 进入场景
 * <p>
 * 根据玩家基础数据，（需要数据节点提前准备好），提取出生点，发给相应房间去响应信息。<br>
 * 之后根据需要生成视野内所有玩家数据（包括自己），发给入场玩家；同时也需要将自己玩家数据广播给视野内其他玩家。
 * 
 * @author zhangbo
 *
 */
public class EnterHandler extends ActionWorker<EnterOperation> {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public Collection<CommendExpression> doWork(long serverId, long playerId, String sceneUniqId, EnterOperation message, Scene sceneData) {

		// TODO 出生点不是这么找的
		Pos bornPos = sceneData.getBornPos();
		Tile bornTile = sceneData.getSurroundingTile(bornPos);
		Objects.requireNonNull(bornTile);

		Tile memTile = sceneData.getTile(sceneUniqId);
		Tile gamePlayerTile = memTile == null ? bornTile : memTile;
		GamePlayer p = memTile != null ? (GamePlayer) gamePlayerTile.getItem(sceneUniqId) : loadGamePlayer(playerId);
		// 玩家进格
		if (memTile == null) {
			bornTile.addGamePlayer(p);
		}

		{

			Collection<Tile> multicastTiles = sceneData.getVisionTiles(p.getPos());

			List<CommendExpression> ans = new ArrayList<>();

			{
				SceneItemsVTT visionType = new SceneItemsVTT();
				visionType.addItem(p);
				TilesSTT targetType = new TilesSTT();
				multicastTiles.forEach(targetType::addTile);
				CommendExpression expr = new CommendExpression(visionType, StateType.CHANGE_STATE, targetType);
				ans.add(expr);
			}
			{
				TilesVTT visionType = new TilesVTT();
				multicastTiles.forEach(visionType::addTile);
				PlayersSTT targetType = new PlayersSTT();
				targetType.addTargetPlayer(p);
				CommendExpression exprSelf = new CommendExpression(visionType, StateType.CHANGE_STATE, targetType);
				ans.add(exprSelf);
			}
			log.trace("玩家{}已入场", playerId);
			return ans;
		}
	}

	private GamePlayer loadGamePlayer(long playerId) {
		// TODO Auto-generated method stub
		GamePlayer gamePlayer = new GamePlayer();
		gamePlayer.setId(playerId);
		return gamePlayer;
	}

}
