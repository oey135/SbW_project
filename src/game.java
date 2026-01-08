import java.util.Random;
import java.util.Scanner;

public class game extends extrDB{
    Scanner sc = new Scanner(System.in);
    Random rd = new Random();
    AnswerData ansData = new AnswerData();
    static int count = 0;

    protected void GameScene() {
        System.out.println("\033[H\033[2J");
        count++;
        System.out.println(
                "정답은 띄어쓰기 없이 작성해주세요!\n"+
                "=================================================\n" +
                        " 문제 "+count+"번");
        MakeProb();
        System.out.println(
                "\n\n================================================="
        );

        System.out.println();
    }

    protected void MakeProb() {
        int line = rd.nextInt(3)+2;
        int id = 0;

        switch (line) {

            case 2:
                id = rd.nextInt(43)+1; break;
            case 3:
                id = rd.nextInt(44)+1; break;
            case 4:
                id = rd.nextInt(51)+1; break;

            default:
                System.out.print("잘못된 호선이 선택되었습니다. 문제 생성 실패."); break;
        }

        ansData.setAnswer(getStationName(line, id));
        MakeBA(line, id);

        System.out.print("           "+ansData.before+"---[    ]---"+ansData.after);
    }

    protected void MakeBA(int line, int id) {

        switch (line) {
            case 2:
                if(id==1) {
                    ansData.setBefore(getStationName(line, 43));
                    ansData.setAfter(getStationName(line, id + 1));
                }
                else if(id==44) {
                    ansData.setBefore(getStationName(line, id-1));
                    ansData.setAfter(getStationName(line, 1));
                }
                else {
                    ansData.setBefore(getStationName(line, id-1));
                    ansData.setAfter(getStationName(line, id+1));
                }
                break;
            case 3:
                if(id==1) {
                    ansData.setBefore(getStationName(line, 44));
                    ansData.setAfter(getStationName(line, id + 1));
                }
                else if(id==44) {
                    ansData.setBefore(getStationName(line, id-1));
                    ansData.setAfter(getStationName(line, 1));
                }
                else {
                    ansData.setBefore(getStationName(line, id-1));
                    ansData.setAfter(getStationName(line, id+1));
                }
                break;
            case 4:
                if(id==1) {
                    ansData.setBefore(getStationName(line, 51));
                    ansData.setAfter(getStationName(line, id + 1));
                }
                else if(id==51) {
                    ansData.setBefore(getStationName(line, id-1));
                    ansData.setAfter(getStationName(line, 1));
                }
                else {
                    ansData.setBefore(getStationName(line, id-1));
                    ansData.setAfter(getStationName(line, id+1));
                }
                break;
            default:
                System.out.print("잘못된 접근입니다. 호선을 확인해주세요.");
        }
    }

    protected boolean CheckAns(String answer) {
        return answer.equals(ansData.answer);
    }
    public static final String green    = "\u001B[32m" ;
    public static final String red      = "\u001B[31m" ;
    public static final String exit     = "\u001B[0m" ;

    protected void RightScene() {
        System.out.println("\033[H\033[2J");
        System.out.println(green+
                "=================================================\n\n"+
                "                 ▩▩▩▩▩▩▩\n" +
                "               ▩▩▩      ▩▩▩\n"+
                "               ▩▩         ▩▩\n"+
                "               ▩▩         ▩▩\n"+
                "               ▩▩▩      ▩▩▩\n"+
                "                 ▩▩▩▩▩▩▩\n" +
                "\n"+
                "================================================="+exit
        );
    }

    protected void WrongScene() {
        System.out.println("\033[H\033[2J");
        System.out.println(red+
                "=================================================\n\n"+

                "              ▩▩▩         ▩▩▩\n" +
                "                ▩▩▩     ▩▩▩\n"+
                "                  ▩▩▩ ▩▩▩\n"+
                "                    ▩▩▩▩\n"+
                "                  ▩▩▩ ▩▩▩\n"+
                "                ▩▩▩     ▩▩▩\n"+
                "              ▩▩▩         ▩▩▩\n\n" +

                "=================================================\n"+exit+

                "✅ "+ansData.getAnswer()
        );
    }

    protected void saveGame() {
        String player;
        System.out.print("\n<<게임 기록 저장>>\n플레이어(공백X, 최대 25글자) : ");
        player = sc.next();
        saveScore(player, count-1);
        count = 0;
    }
}
