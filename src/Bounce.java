
public class Bounce implements Runnable {
	private static final int X = 0;
	private static final int Y = 1;
	private static final String BALL = "O";
	private static final String NOTHINGNESS = " ";

	public void run() {
		int[][] vector = { { 3, 1 }, { 2, 0 }, { 1, 1 } };
		int[][] pos = { { 0, 1 }, { 0, 0 }, { 1, 0 } };

		int[] vector1 = { 3, 1 };
		int[] pos1 = { 0, 1 };

		int count = 0;

		for (int frame = 80; frame > 0; frame--) {
			if (count == 5) {
				count = 0;
				vector1[Y] = invert(vector1[Y]);
			}
			if (vector1[X] < 1)
				vector1[X] = 1;
			pos1[X] = pos1[X] + vector1[X];
			vector1[X]--;

			pos1[Y] = pos1[Y] + vector1[Y];
			count++;
			render(pos1);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
	}

	private static int invert(int v) {
		return v + (2 * -v);
	}

	private static void render(int[] pos) {
		for (int y = 13; y > 0; y--) {
			for (int x = 0; x < 80; x++) {
				if (pos[X] == x && pos[Y] == y) {
					System.out.print(BALL);
				} else {
					System.out.print(NOTHINGNESS);
				}
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		new Bounce().run();
	}
}

class Ball implements Runnable {
	public int[] vector;
	public int[] pos;
	
	public void run() {
		
	}
}
