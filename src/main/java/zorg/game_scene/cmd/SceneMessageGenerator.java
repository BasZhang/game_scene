package zorg.game_scene.cmd;

import com.google.protobuf.Message;

import zorg.game_scene.def.Scene;

public interface SceneMessageGenerator<MESSAGE_TYPE extends Message> {
	/**
	 * 生成需要发送的状态信息。
	 * 
	 * @param sceneData
	 *            场景数据
	 * @return 状态消息。
	 */
	public MESSAGE_TYPE genMsg(Scene sceneData);
}
