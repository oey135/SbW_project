import java.util.Scanner;

public class MenuScene extends extrDB{
    Scanner sc = new Scanner(System.in);

    public void menuScene() {

        System.out.println("\033[H\033[2J\n"+
                "=================================================\n\n" +
                "              <<      메뉴       >>\n\n" +

                "     1.지하철 노선도 보기\n" +
                "     2.메인으로 돌아가기\n" +
                "     최고 기록 | "+getBestPlayer()+" - "+getBestScore()+

                "\n\n================================================="
        );
        System.out.print("메뉴를 선택해주세요! (1, 2) >> ");
        int menu = sc.nextInt();

        switch (menu) {
            case 1:
                System.out.print("\n확인할 호선을 선택해주세요! (2, 3, 4, 7) >> ");
                menu = sc.nextInt();
                if(menu==2 || menu==3 || menu==4 || menu==7) {
                    getStationAbout(menu); break;
                }
                else {
                    System.out.println("ERROR - DB에 없는 호선입니다. 다시 입력해주세요!");
                }
            case 2:
                break;
            default:
                System.out.println("잘못된 접근입니다."); break;
        }
    }
}
