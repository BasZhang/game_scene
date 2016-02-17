package zorg.game_scene.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zorg.game_scene.cmd.CommendExpression;
import zorg.game_scene.cmd.SceneItemsVTT;
import zorg.game_scene.cmd.StateType;
import zorg.game_scene.cmd.TilesSTT;
import zorg.game_scene.cmd.TilesVTT;
import zorg.game_scene.def.GamePlayer;
import zorg.game_scene.def.Pos;
import zorg.game_scene.def.Scene;
import zorg.game_scene.def.Tile;
import zorg.game_scene.proto.ProtoDefine.MoveOperation;

/**
 * 移动
 * <p>
 * 如果新旧坐标是在同一个格子里移动，需要广播新的位置；<br>
 * 否则是在不同的格子里，根据视野，非别发送移动或进入或离开三种消息；<br>
 * 另外，若目标点出格，想想怎么办（TODO 目前是报异常不做处理）。
 * 
 * @author zhangbo
 *
 */
public class MoveHandler extends ActionWorker<MoveOperation> {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public Collection<CommendExpression> doWork(long serverId, long playerId, String sceneUniqId, MoveOperation message, Scene sceneData) {
		// TODO Auto-generated method stub
		Tile earlierTile = sceneData.getTile(sceneUniqId);

		if (earlierTile == null) {
			log.warn("玩家{}未进入场景", playerId);
			return null;
		}

		Pos toPos = new Pos();
		toPos.x = message.getToPosX();
		toPos.y = message.getToPosY();
		Vector2D toPos2D = toPos.projectTo2D();
		GamePlayer player = (GamePlayer) earlierTile.getItem(sceneUniqId);
		Tile nextTile = earlierTile.getPolygon().geoContains(toPos2D) ? earlierTile : sceneData.getSurroundingTile(toPos);

		if (nextTile == null) {
			log.warn("玩家{}错误的移动坐标", playerId, toPos);
			// 悬崖勒马
			return null;
		}
		List<CommendExpression> ret = new ArrayList<>();
		if (nextTile == earlierTile) {
			doMoveInTheSameTile(player, toPos, sceneData, ret);
		} else {
			doTransferTiles(player, toPos, earlierTile, nextTile, sceneData, ret);
		}
		return ret;
	}

	private void doTransferTiles(GamePlayer player, Pos toPos, Tile earlierTile, Tile nextTile, Scene sceneData, final List<CommendExpression> ret) {
		Pos oldPos = player.getPos();
		long playerId = player.getId();
		Set<Long> playerIdSet = new HashSet<>();
		playerIdSet.add(playerId);

		Collection<Tile> oldTiles = sceneData.getVisionTiles(oldPos);

		earlierTile.removePlayer(player.getSceneUniqId());
		player.setX((int) toPos.x);
		player.setY((int) toPos.y);
		nextTile.addGamePlayer(player);

		Collection<Tile> newTiles = sceneData.getVisionTiles(toPos);

		Collection<Tile> disappearTiles = CollectionUtils.subtract(oldTiles, newTiles);
		Collection<Tile> appearTiles = CollectionUtils.subtract(newTiles, oldTiles);
		Collection<Tile> sameTiles = CollectionUtils.intersection(oldTiles, newTiles);
		{
			SceneItemsVTT visionType = new SceneItemsVTT();
			visionType.addItem(player);
			TilesSTT targetType = new TilesSTT();
			sameTiles.forEach(targetType::addTile);
			CommendExpression expr = new CommendExpression(visionType, StateType.CHANGE_STATE, targetType);
			ret.add(expr);
		}
		{
			TilesVTT visionType = new TilesVTT();
			disappearTiles.forEach(visionType::addTile);
			appearTiles.forEach(visionType::addTile);
			TilesSTT targetType = new TilesSTT();
			disappearTiles.forEach(targetType::addTile);
			CommendExpression expr = new CommendExpression(visionType, StateType.CHANGE_STATE, targetType);
			ret.add(expr);
		}
		log.debug("玩家{}与其他玩家离开彼此视野。", playerId);
		log.debug("玩家{}与其他玩家进入彼此视野。", playerId);
	}

	private void doMoveInTheSameTile(GamePlayer player, Pos toPos, Scene sceneData, final List<CommendExpression> ret) {
		player.setX((int) toPos.x);
		player.setY((int) toPos.y);

		Collection<Tile> oldTiles = sceneData.getVisionTiles(toPos);
		{
			SceneItemsVTT visionType = new SceneItemsVTT();
			visionType.addItem(player);
			TilesSTT targetType = new TilesSTT();
			oldTiles.forEach(targetType::addTile);
			CommendExpression expr = new CommendExpression(visionType, StateType.CHANGE_STATE, targetType);
			ret.add(expr);
		}
	}

}
