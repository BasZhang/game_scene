package zorg.game_scene.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SwapableQueuePair<T> {

	private volatile List<Queue<T>> queuePair = null;

	public SwapableQueuePair() {
		super();
		ArrayList<Queue<T>> shortList = new ArrayList<>();
		shortList.add(new LinkedList<>());
		shortList.add(new LinkedList<>());
		this.queuePair = shortList;
	}

	public Queue<T> first() {
		return queuePair.get(0);
	}

	public Queue<T> second() {
		return queuePair.get(1);
	}

	public void swap() {
		Collections.swap(queuePair, 0, 1);
	}
}
