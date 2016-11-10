package hyweb.core.aop;

public interface EventHandler {
	public void onBegin();
	public void onTaskFinish();
	public void onEnd();
	public void onError(Exception e);
}
