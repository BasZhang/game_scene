package zorg.game_scene.def;

public interface Changable<STATE_CHANGE, STATE_FINAL> {

	public STATE_CHANGE getChangedState();

	public STATE_FINAL getFinalState();

	public boolean isChanged();

	public void markAsUnchanged();
}
