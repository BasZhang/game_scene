package zorg.game_scene.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.protobuf.Message;

import zorg.game_scene.cmd.CommendExpression;
import zorg.game_scene.cmd.FlatCommendExpression;
import zorg.game_scene.def.GamePlayer;
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

		List<CommendExpression> returnCmds = new ArrayList<>();
		ActionData incomingAction = null;
		while ((incomingAction = jobGroup.pollFromReceivings()) != null) {
			Collection<CommendExpression> cmds = cmdWorker.doAction(incomingAction, sceneData);
			returnCmds.addAll(cmds);
		}

		CommendSimplifyWorker commendMerger = new CommendSimplifyWorker();
		List<FlatCommendExpression> mergedCmds = commendMerger.simplify(returnCmds);

		CommendResultCache resultCache = new CommendResultCache();
		for (FlatCommendExpression fcmd : mergedCmds) {
			List<GamePlayer> receiverPlayers = fcmd.getReceiverPlayers();
			Message message = resultCache.executed(fcmd) ? resultCache.getLastResult(fcmd) : resultCache.exec(fcmd, sceneData);
			for (GamePlayer gamePlayer : receiverPlayers) {
				jobGroup.addToSending(new ActionData(gamePlayer.getId(), sceneData.getServerId(), message));
			}
		}
	}
}
