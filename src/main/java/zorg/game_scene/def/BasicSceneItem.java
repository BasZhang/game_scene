package zorg.game_scene.def;

/**
 * 游戏对象
 * 
 * @author zhangbo
 *
 */
public abstract class BasicSceneItem implements UniqueSceneItem {

	@Override
	public final String getSceneUniqId() {
		return getItemPrefix() + "$" + getId();
	}

	protected abstract String getItemPrefix();

	protected abstract Long getId();

}
