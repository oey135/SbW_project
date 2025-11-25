import java.util.Scanner;

public class gameScene extends game {

    public void gamePlay() {
        Scanner sc = new Scanner(System.in);
        extrDB extrDB = new extrDB();
        game gm = new game();
        String ans = "";

        while(true) {
            gm.GameScene(); //문제 화면 띄우기
            System.out.print("   정답 입력 >      ");
            ans = sc.next();

            if(gm.CheckAns(ans)) { //정답이면 다음 문제, 아니면 끝
                gm.RightScene();
                try {
                    Thread.sleep(1200); // 1.2초 동안 일시 중지
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            else {
                gm.WrongScene();
                saveGame();
                remainTop5();
                try {
                    Thread.sleep(1500); // 1.5초 동안 일시 중지
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
