package zorg.game_scene.cmd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import zorg.game_scene.def.GamePlayer;

public class PlayersSTT extends SendTargetType {

	public List<GamePlayer> targetPlayers = new ArrayList<>();

	public void addTargetPlayer(GamePlayer player) {
		this.targetPlayers.add(player);
	}

	public List<GamePlayer> getTargetPlayers() {
		return Collections.unmodifiableList(targetPlayers);
	}
}
