package zorg.game_scene.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 一对队列
 * 
 * @author zhangbo
 *
 * @param <T>
 *            元素泛型
 */
public class SwapableQueuePair<T> {

	private volatile List<Queue<T>> queuePair = null;

	public SwapableQueuePair() {
		super();
		ArrayList<Queue<T>> shortList = new ArrayList<>();
		shortList.add(new LinkedList<>());
		shortList.add(new LinkedList<>());
		this.queuePair = shortList;
	}

	/**
	 * @return 取第一个队列。
	 */
	public Queue<T> first() {
		return queuePair.get(0);
	}

	/**
	 * @return 取第二个队列。
	 */
	public Queue<T> second() {
		return queuePair.get(1);
	}

	/**
	 * 两个队列交换。
	 */
	public void swap() {
		Collections.swap(queuePair, 0, 1);
	}
}
