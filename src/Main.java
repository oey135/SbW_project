import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        gameScene gs = new gameScene();
        MenuScene ms = new MenuScene();

        int menu = 0;
        while(menu!=3) {
            System.out.println(
                    """
                        
                        =================================================
                        
                                   <<      지하철 마스터!!       >>
                        
                              1.시작하기
                              2.메뉴
                              3.종료하기
                        
                        ================================================="""
            );
            System.out.print("메뉴를 선택해주세요! (1,2,3) >> ");
            try {
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
                        System.out.println("잘못된 입력입니다.");
                }
            } catch (InputMismatchException Ip_e) {
                System.out.println("잘못된 입력입니다.");
                throw new InputMismatchException();
            }

        }
    }
}