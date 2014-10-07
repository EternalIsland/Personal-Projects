package {

	import flash.display.MovieClip;
	import flash.utils.Timer;
	import flash.events.TimerEvent;

	public class AvoiderGame extends MovieClip {

		public var enemy:Enemy;
		public var gameTimer:Timer;
		public var avatar:Avatar;

		public function AvoiderGame() {
			enemy = new Enemy();
			addChild(enemy);

			avatar = new Avatar();
			addChild(avatar);
			avatar.x=mouseX;
			avatar.y=mouseY;

			gameTimer=new Timer(25);
			gameTimer.addEventListener(TimerEvent.TIMER, onTick);
			gameTimer.start();
		}
		public function onTick(timerEvent: TimerEvent):void {
			enemy.moveDownABit();
			avatar.x=mouseX;
			avatar.y=mouseY;

			if (avatar.hitTestObject(enemy)) {
				gameTimer.stop();
			}
		}
	}
}