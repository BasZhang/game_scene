package zorg.game_scene.core;

import java.util.LinkedList;
import java.util.Queue;

import zorg.game_scene.util.SwapableQueuePair;

public class JobGroup {

	// 指令接收队列
	private volatile SwapableQueuePair<ActionData> incomings = new SwapableQueuePair<>();

	// 指令发送队列
	private Queue<ActionData> sending = new LinkedList<>();

	public void addToReceivings(ActionData receivingJob) {
		incomings.second().add(receivingJob);
	}

	public ActionData pollFromReceivings() {
		return incomings.first().poll();
	}

	public void swap() {
		incomings.swap();
	}

	public void addToSending(ActionData cmd) {
		sending.add(cmd);
	}

	public ActionData pollFromSending() {
		return sending.poll();
	}
}
