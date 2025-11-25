import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        gameScene gs = new gameScene();
        menuScene ms = new menuScene();

        int menu = 0;
        while(menu!=3) {
            System.out.println(
                    "\n=================================================\n\n" +
                            "          <<      지하철 마스터!!       >>\n\n" +
                            "     1.시작하기\n" +
                            "     2.메뉴\n" +
                            "     3.종료하기\n\n" +
                            "================================================="
            );
            System.out.print("메뉴를 선택해주세요! (1,2,3) >> ");
            menu = sc.nextInt();
            switch (menu) {
                case 1:
                    gs.gamePlay();
                    break;
                case 2:
                    ms.menuScene();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("잘못된 입력입니다."); break;
            }
        }
    }
}