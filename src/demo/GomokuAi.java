package demo;
/**
 * Ai��,������ai
 * �Ե�ǰ�غ�����ϵ�ÿ����λ����һ�δ�֣������Լ���Է���������ӵĵ÷֣�����߷����ĵ�ΪAI���ӵ�
 * @author ë�� 2018��8��8��
 * 2018��9��1�� ����ע�ͣ��Ż���������
 */
public class GomokuAi {
	private int x;// �������֮������ӵ�
	private int y;// �������֮������ӵ�
	private int aiPlayerNum;// ��ǰ��AI����ұ��
	private int enemyPlayerNum;// ��ǰ�ֵ���(����)����ұ��
	private int mapScore[][];// ���浱ǰ��ͼ��ÿ�������
	//private int difficultLever;//�Ѷȵȼ�

	/**
	 * Ĭ�Ϲ��캯��������AI���2
	 */
	GomokuAi() {
		aiPlayerNum = 2;
		enemyPlayerNum = 3 - aiPlayerNum;
		//setdifficult(2);
		mapScore = new int[Game.size][Game.size];
		System.out.println("����AI�ɹ�...");
	}

	/**
	 * ���캯�������ݴ��� ��ұ�� �� �Ѷ� �趨AI
	 */
	GomokuAi(int playerNum, int difficult) {
		aiPlayerNum = playerNum;
		enemyPlayerNum = 3 - aiPlayerNum;
		//setdifficult(difficult);
		mapScore = new int[Game.size][Game.size];
		System.out.println("����AI�ɹ�...");
	}

	public void setAiPlayerNum(int num) {
		// �趨AI���
		System.out.println("AI�Ѷ�Ϊnum");
		aiPlayerNum = num;
	}

	public void setdifficult(int num) {
		/**
		 * �޸��Ѷ�
		 */
		if (num < 0) {
			num = 0;
		}
		if (num > 7) {
			num = 7;
		}
	}

	/**
	 * �Դ����situationMap[]������д�֣���������ֵ
	 * ����Player����(��ұ��)���ز�ͬ�ļ���ֵ AI(�Լ�)���AI(����)��ֲ�ͬ
	 * ����һ��situationMap[9]���飬������Ӧ������situationMap[4]Ϊ���ĵ�����(�������������)4��Χ�ڵ��������
	 */
	public int situation(int situationMap[]) {
		//��situationMap�������
		boolean[] die = { false, false };// ��¼�����Ƿ񱻶���
		int Player = situationMap[4];// ��ǰ������ΪsituationMap[4]Ԫ�أ�������Ԫ��
		int cnt = 1;// ����������������1
		int cntBlank = 0;// ��������������û���жϵĿո����� ����:0011 1 2000 ��ֻ��ǰ���00��cntBlank==2
		int[] dir = { -1, 1 };// ���ҷ�������
		for (int i = 0; i < 2; i++) {
			//����
			int tempPosition = 4;
			boolean flag = false; // �Ƿ��ж� true��ʾ�ж� �ж�֮��˷����������չ��ת����һ�߽�����չ
			for (int j = 0; j < 4; j++) {
				// ������������ĸ�
				tempPosition += dir[i];
				if (situationMap[tempPosition] == Player && !flag) {
					// ���������Ҵ�ǰû���ж�
					cnt++;// �����е�����
				} else if ((situationMap[tempPosition] == 3 - Player) || (situationMap[tempPosition] == -1) && !flag) {
					// �����ǰ���situationMap[4]���ӻ���Ϊ-1(�߽�)���Ҵ�ǰû���ж� �ж����ж�һ�Σ�����
					die[i] = true;
					flag = true;
				} else {
					// ����ǿյ�
					if (situationMap[tempPosition] == 0 && !flag) {
						cntBlank++;
					}
					break;
				}
			}
		}

		//�����϶η�������ۺϴ��
		int score = 1;
		if (cnt >= 5) {
			if (Player == aiPlayerNum) {
				return 150000;
			} else {
				return 100000;
			}
		} else if (cnt == 4) {
			if (!die[0] && !die[1]) {
				// ����
				if (Player == aiPlayerNum) {
					return 15000;
				} else {
					return 10000;
				}
			} else if (((die[0] && !die[1]) || (!die[0] && die[1]) && (cntBlank >= 1))) {
				// ����
				return 5000;
			} else if (die[0] && die[1]) {
				// ����
				return 4;
			}
		} else if (cnt == 3) {
			if (!die[0] && !die[1]) {
				// ����
				return 2600;
			} else if (((die[0] && !die[1]) || (!die[0] && die[1]) && (cntBlank >= 2))) {
				// ����
				return 500;
			} else if (die[0] && die[1]) {
				// ����
				return 3;
			}
		} else if (cnt == 2) {
			if (!die[0] && !die[1]) {
				// ���
				return 100;
			} else if (((die[0] && !die[1]) || (!die[0] && die[1]) && (cntBlank >= 3))) {
				// ���
				return 50;
			} else if (die[0] && die[1]) {
				// ����
				return 2;
			}
		}

		return score;
	}

	/**
	 * ��map[ny][nx]��� �����ؽ�� ��map[ny][nx]Ϊ�������ӵ��9*9��Χ nowPlayer��ai����ұ��
	 */
	private int caculatePerPoint(int nx, int ny, int map[][]) {
		// ��ά�����¼�ᣬ�ݣ���б����б
		int dir[][][] = { { { -1, 0 }, { 1, 0 } }, { { 0, -1 }, { 0, 1 } }, { { -1, -1 }, { 1, 1 } },
				{ { 1, -1 }, { -1, 1 } } };
		int dir2[] = { -1, 1 };
		int temp;
		int tempY;
		int tempX;
		int score = 0;
		int situationMap[] = new int[9];// ����ÿ�������������� [4+1+4] ����situation()�����������д��
		if (map[ny][nx] != 0) {// �������㲻�ǿյķ���0
			return 0;
		}
		if (nx > 3 && nx < 11 && ny > 3 && ny < 11) {
			//���ķ�Χ�� ������ֵ��
			score += 50;
		}
		// ���ܱ�û���������������㣬ֱ�ӷ��ص�ǰλ�õĻ�����ֵ
		boolean hasChess = false;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 2; j++) {
				for (int k = 0; k < 1; k++) {
					if (ny + dir[i][j][0] > 0 && ny + dir[i][j][0] < Game.size && nx + dir[i][j][1] > 0
							&& nx + dir[i][j][1] < Game.size) {
						if (map[ny + dir[i][j][0]][nx + dir[i][j][1]] > 0) {
							hasChess = true;
							break;
						}
					}
				}
				if (hasChess) {
					break;
				}
			}
			if (hasChess) {
				break;
			}
		}
		if (!hasChess) {
			return score;
		}

		// ��¼4�������ϵ��������(�ᣬ�ݣ���б����б)���ֱ𽻸�������������
		for (int i = 0; i < 4; i++) {
			// ����
			for (int j = 0; j < 2; j++) {
				tempY = ny;
				tempX = nx;
				temp = 4;
				// һ���������ĸ�
				for (int k = 0; k < 4; k++) {
					tempX += dir[i][j][0];
					tempY += dir[i][j][1];
					temp += dir2[j];
					if (tempX >= 0 && tempX < Game.size && tempY >= 0 && tempY < Game.size) {
						situationMap[temp] = map[tempY][tempX];
					} else {
						situationMap[temp] = -1;
					}
				}
			}

			int scoreAi = 0, scoreEnemy = 0;
			// �ڴ˵���輺����Է��������Σ��������ֵ������Զ���ض��Լ��������ķ���
			situationMap[4] = aiPlayerNum;
			scoreAi = situation(situationMap);
			situationMap[4] = enemyPlayerNum;
			scoreEnemy = situation(situationMap);
			score += (scoreAi >= scoreEnemy ? scoreAi : scoreEnemy);
		}
		return score;
	}

	/**
	 * ��ÿ���������
	 */
	void caculate(int map[][]) {
		int maxScore = 0;//�������ÿ��������е�����ֵ�����½����ӵ� x,y
		int temp = 0;
		for (int i = 0; i < Game.size; i++) {
			for (int j = 0; j < Game.size; j++) {

				// ���������ĵ÷�
				temp = caculatePerPoint(j, i, map);
				mapScore[i][j] = temp;
				if (temp > maxScore && map[i][j] == 0) {
					maxScore = temp;
					y = i;
					x = j;
				} else if (temp == maxScore && map[i][j] == 0) {
					if ((int) (Math.random() * 5) < 1) {
						//����ǰ������߷������������䰴���ʾ��� �Ƿ��������
						y = i;
						x = j;
					}
				}
			}
		}

		System.out.println();
		System.out.println("�÷���� : ");
		System.out.print("��߷�ֵ : " + maxScore);
		System.out.println("  λ�� ( " + x + " , " + y + " )");
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}