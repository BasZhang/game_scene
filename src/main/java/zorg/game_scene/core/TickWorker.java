package zorg.game_scene.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.protobuf.Message;

import zorg.game_scene.cmd.Commend;
import zorg.game_scene.def.Scene;

public class TickWorker implements Runnable {

	protected JobGroup jobGroup;

	private ScheduledExecutorService es = Executors.newScheduledThreadPool(1);

	protected PushWorker pusher;

	protected ActionWorker cmdWorker;

	protected Scene sceneData;

	public void startJobRepeatly(int interval) {
		es.scheduleAtFixedRate(this, 0, 100, TimeUnit.MILLISECONDS);
	}

	public void receiveJob(ActionData actionData) {
		jobGroup.addToReceivings(actionData);
	}

	@Override
	public void run() {
		jobGroup.swap();

		ActionData outgoingAction = null;
		while ((outgoingAction = jobGroup.pollFromSending()) != null) {
			pusher.push(outgoingAction);
		}

		List<Commend> returnCmds = new ArrayList<>();
		ActionData incomingAction = null;
		while ((incomingAction = jobGroup.pollFromReceivings()) != null) {
			Collection<Commend> cmds = cmdWorker.doAction(incomingAction, sceneData);
			returnCmds.addAll(cmds);
		}
		CommendMerger commendMerger = new CommendMerger();
		List<Commend> mergedCmds = commendMerger.merge(returnCmds);
		CommendResultCache resultCache = new CommendResultCache();
		for (Commend cmd : mergedCmds) {
			Message message = resultCache.executed(cmd) ? resultCache.getLastResult(cmd) : resultCache.exec(cmd, sceneData);
			jobGroup.addToSending(new ActionData(0, 0, message));
		}
	}
}
