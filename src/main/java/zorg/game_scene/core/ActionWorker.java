package zorg.game_scene.core;

import java.util.Collection;

import zorg.game_scene.cmd.Commend;
import zorg.game_scene.def.Scene;

public interface ActionWorker {

	Collection<Commend> doAction(ActionData action, Scene sceneData);

}
