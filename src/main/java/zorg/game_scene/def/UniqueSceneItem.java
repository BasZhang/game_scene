package zorg.game_scene.def;

/**
 * 表示该游戏对象需要一个场景内唯一的Id。
 * 
 * @author zhangbo
 *
 */
public interface UniqueSceneItem {

	/**
	 * @return 游戏对象的场景内唯一Id。
	 */
	String getSceneUniqId();
}
