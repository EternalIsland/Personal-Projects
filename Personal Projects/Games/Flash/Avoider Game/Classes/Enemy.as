package{
	
	import flash.display.MovieClip;
	public class Enemy extends MovieClip{
		
		public function Enemy(){
			x = 100;
			y = 20 ;
			}
			
		public function moveDownABit():void{
				y += 3;
			}
		
		}
	}