package zorg.game_scene.def;

/**
 * 可变的游戏基本对象。
 * 
 * @author zhangbo
 *
 * @param <STATE_CHANGE>
 *            取当前帧变化了的状态返回类型
 * @param <STATE_FINAL>
 *            取最终状态返回类型
 */
public abstract class ChangableSceneItem<STATE_CHANGE, STATE_FINAL> extends BasicSceneItem implements Changable<STATE_CHANGE, STATE_FINAL> {
	/** 这帧是否变了 */
	protected boolean changed = false;

	public void change() {
		changed = true;
	}

	@Override
	public boolean isChanged() {
		return changed;
	}

	@Override
	public void markAsUnchanged() {
		changed = false;
	}

}
