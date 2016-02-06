package zorg.game_scene.def;

/**
 * 表示它是逐帧可变的
 * 
 * @author zhangbo
 *
 * @param <STATE_CHANGE>
 *            取当前帧变化了的状态返回类型
 * @param <STATE_FINAL>
 *            取最终状态返回类型
 */
public interface Changable<STATE_CHANGE, STATE_FINAL> {

	/**
	 * @return 当前帧变化信息。
	 */
	public STATE_CHANGE getChangedState();

	/**
	 * @return 当前整体状态。
	 */
	public STATE_FINAL getFinalState();

	/**
	 * @return 单帧是否变化了。
	 */
	public boolean isChanged();

	/**
	 * 将当前状态标记为未变化。
	 */
	public void markAsUnchanged();
}
