
public class Command {
	int tenthsOfASecond;				// total time command runs for
	int elapsedTime;					// total time elapsed since starting in 1/10th of second
	double motor1Speed, motor2Speed;	// in rotations per second
	boolean started = false;
	boolean finished = false;
	
	public Command(double a, double b, int i) {
		motor1Speed = a;
		motor2Speed = b;
		tenthsOfASecond = i;
		elapsedTime = 0;
	}
	
	public void run() {
		if (finished) return;
		if (!started) {
			System.out.println("command started");
			started = true;
		}
		
		if (elapsedTime >= tenthsOfASecond) {
			System.out.println("command ended");
			finished = true;
		}
		
		elapsedTime++;	
	}
}
