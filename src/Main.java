import java.util.ArrayList;

import processing.core.PApplet;

//  This is a different comment
public class Main extends PApplet {
	Point circle1center, circle2center;
	Point peg1, peg2;
	double circleRadius = 40;
	double pegRadius = 2;

	Point penLocationxy;
	Point penLocationab;

	ArrayList<Point> drawing;
	ArrayList<Command> commands;
	double d;

	public void setup() {
		size(600, 600);
		circle1center = new Point(150, 100);
		circle2center = new Point(width-150, 100);
		peg1 = new Point(50, 150);
		peg2 = new Point(width-50, 150);

		d = peg2.x - peg1.x;

		penLocationxy = new Point(300, 300);
		penLocationab = this.xyToab(penLocationxy);

		drawing = new ArrayList<Point>();
		commands = new ArrayList<Command>();
		commands.add(new Command(0, 3, 100));
		commands.add(new Command(2, 0, 60));
		commands.add(new Command(-2, -2, 60));
		commands.add(new Command(-3, -1, 60));
	}

	public void draw() {
		background(255);
		drawEverything();
		moveMotors();
		System.out.println(this.penLocationxy + " " + this.penLocationab);
	}

	private void moveMotors() {
		if (commands.size() > 0) {
			Command command = commands.get(0);
			if (command.finished) {
				commands.remove(0);
				return;
			}

			command.run();

			//   string released = x rpm * ( 2 pi r / 1 rot) * (1 min / 60 sec) * ( 1 sec / 10 (1/10ths of sec) )
			double m1StringDiff = command.motor1Speed*(2*Math.PI*circleRadius)/(600.0);
			double m2StringDiff = command.motor2Speed*(2*Math.PI*circleRadius)/(600.0);

			System.out.println("diff a: " + m1StringDiff + " diff b: " + m2StringDiff);

			penLocationab.x += m1StringDiff;
			penLocationab.y += m2StringDiff;

			penLocationxy = this.abToxy(penLocationab);
			drawing.add(penLocationxy);
		}
	}

	public Point abToxy(Point abpoint) {
		double a = abpoint.x;
		double b = abpoint.y;

		double x = (double)(a*a+d*d-b*b)/(2*d);
		double y = Math.sqrt(a*a - x*x);

		return new Point(x, y);
	}

	public Point xyToab(Point xypoint) {
		double x = xypoint.x;
		double y = xypoint.y;

		double a = (Math.sqrt(x*x + y*y));
		double b = (Math.sqrt(y*y + (d-x)*(d-x)));
		return new Point(a, b);
	}

	private void drawEverything() {
		// draw existing drawing
		for (Point p : drawing) {
			stroke(0);
			strokeWeight(3);
			point((float)p.x, (float)p.y);
		}
		strokeWeight(1);

		// draw circles
		ellipse((float)circle1center.x, (float)circle1center.y, (float)(circleRadius * 2),
				(float)(circleRadius * 2));
		ellipse((float)circle2center.x, (float)circle2center.y, (float)(circleRadius * 2),
				(float)(circleRadius * 2));

		// draw pegs
		ellipse((float)peg1.x, (float)peg1.y, (float)(pegRadius * 2), (float)(pegRadius * 2));
		ellipse((float)peg2.x, (float)peg2.y, (float)(pegRadius * 2), (float)(pegRadius * 2));

		// draw string
		line((float)peg1.x, (float)peg1.y, (float)penLocationxy.x, (float)penLocationxy.y);
		line((float)peg2.x, (float)peg2.y, (float)penLocationxy.x, (float)penLocationxy.y);
	}

	public float distance(int x1, int y1, int x2, int y2) {
		int dx = x1 - x2;
		int dy = y1 - y2;
		return (float) (Math.sqrt(dx * dx + dy * dy));
	}
}