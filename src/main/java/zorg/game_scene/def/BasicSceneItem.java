package zorg.game_scene.def;

public abstract class BasicSceneItem implements UniqueSceneItem {
	
	@Override
	public final String getSceneUniqId() {
		return getItemPrefix() + "$" + getId();
	}

	protected abstract String getItemPrefix();

	protected abstract String getId();


}
