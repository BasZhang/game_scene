package zorg.game_scene.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import zorg.game_scene.cmd.Commend;

public class CommendMerger {

	public List<Commend> merge(Collection<Commend> cmds) {
//		// TODO Auto-generated method stub
//		MultiKeyMap<Enum<?>, Commend> mkm = MultiKeyMap.multiKeyMap(new HashedMap<>());
//		for (Commend cmd : cmds) {
//			VisionType visionType = cmd.getVisionType();
//			StateType stateType = cmd.getStateType();
//			Commend commend = mkm.get(visionType, stateType);
//		}
		List<Commend> products = new ArrayList<>();
		products.addAll(cmds);
		return products;
	}

}
