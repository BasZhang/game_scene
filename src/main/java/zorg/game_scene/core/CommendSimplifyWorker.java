package zorg.game_scene.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import zorg.game_scene.cmd.CommendExpression;
import zorg.game_scene.cmd.FlatCommendExpression;

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
		// // TODO Auto-generated method stub
		// MultiKeyMap<Enum<?>, Commend> mkm = MultiKeyMap.multiKeyMap(new
		// HashedMap<>());
		// for (Commend cmd : cmds) {
		// VisionType visionType = cmd.getVisionType();
		// StateType stateType = cmd.getStateType();
		// Commend commend = mkm.get(visionType, stateType);
		// }
		List<FlatCommendExpression> products = new ArrayList<>();
		// products.addAll(cmds);
		return products;
	}

}
