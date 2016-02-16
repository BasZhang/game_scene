package zorg.game_scene.cmd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.protobuf.Message;

import zorg.game_scene.def.ChangableSceneItem;

public class SceneItemsVTT extends VisionTargetType {

	private List<ChangableSceneItem<? extends Message, ? extends Message>> items = new ArrayList<>();

	public List<ChangableSceneItem<? extends Message, ? extends Message>> getTargets() {
		return Collections.unmodifiableList(items);
	}

	public void addItem(ChangableSceneItem<? extends Message, ? extends Message> e) {
		if (!items.contains(e)) {
			items.add(e);
		}
	}

	@Override
	public String toString() {
		List<ChangableSceneItem<? extends Message, ? extends Message>> ss = new ArrayList<>(items);
		ss.sort((o1, o2) -> o1.getSceneUniqId().compareTo(o2.getSceneUniqId()));
		return ss.toString();
	}

}
