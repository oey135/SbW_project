import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class game extends extrDB{
    Scanner sc = new Scanner(System.in);
    Random rd = new Random();
    AnswerData ansData = new AnswerData();
    Map<Integer,Integer> lineMap = Map.of(
            2,43,
            3,44,
            4,51,
            5,49,
            6,39,
            7,53,
            8,24,
            9,38
    );
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
        int line = rd.nextInt(2,10);
        int id = 0;

        if(lineMap.containsKey(line)){
            id = rd.nextInt(lineMap.get(line))+1;
        }
        else {
            System.out.print("잘못된 호선이 호출되었습니다. 문제 생성 실패.");
        }

        ansData.setAnswer(getStationName(line, id));
        MakeBA(line, id);

        System.out.print("           "+ansData.before+"---[    ]---"+ansData.after);
    }

    protected void MakeBA(int line, int id) {
        int last = lineMap.get(line);

        if(line <= 1 || line >= 10) {
            System.out.print("잘못된 접근입니다. 호선을 확인해주세요. At MakeBA");
            return;
        }
        int beforeId = (id == 1) ? last : id - 1;
        int afterId = (id == last) ? 1 : id + 1;

        ansData.setBefore(getStationName(line, beforeId));
        ansData.setAfter(getStationName(line, afterId));
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
