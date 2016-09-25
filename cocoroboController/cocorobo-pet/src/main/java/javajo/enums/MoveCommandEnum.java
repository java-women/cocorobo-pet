package javajo.enums;

/**
 * Cocoroboの移動コマンド名.
 * Created by Eriko on 2016/09/25.
 */
public enum MoveCommandEnum {
	FORWARD("forward"),
	LEFT("left"),
	RIGHT("right"),
	STOP("stop"),
	GOHOME("gohome");

	private final String moveCommand;

	private MoveCommandEnum(final String moveCommand) {
		this.moveCommand = moveCommand;
	}

	public String getName() {
		return moveCommand;
	}
}
