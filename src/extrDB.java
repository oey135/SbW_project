import java.sql.*;

public class extrDB {

    private static final String URL = "jdbc:mysql://localhost:3306/subway?serverTimezone=Asia/Seoul&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "mysql1234";

    public static String getStationName(int line, int id) {
        String query = "SELECT name FROM line? WHERE id = ?";
        String resultName = null;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, line);
            pstmt.setInt(2, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                resultName = rs.getString("name");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ 불러오기 실패");
        }

        return resultName;
    }

    public static void saveScore(String player, int score) {
        String query = "INSERT INTO game_score (player, score) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, player);

            pstmt.setInt(2, score);
            pstmt.executeUpdate();
            System.out.println("✅ 게임 결과가 성공적으로 저장되었습니다.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ 결과 저장 실패");
        }
    }

    public static int getBestScore() {
        String query = "SELECT MAX(score) AS max_score FROM game_score";
        int bestScore = 0;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                bestScore = rs.getInt("max_score");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ 불러오기 실패");
        }

        return bestScore;
    }

    public static String getBestPlayer() {
        String query = "select player from game_score where score = (select Max(score) from game_score);";
        String bestplayer = "";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                bestplayer = rs.getString("player");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ 불러오기 실패");
        }

        return bestplayer;
    }

    public void getStationAbout(int line) {
        String query = "select id, name from line"+line+";";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                System.out.println("""
                        
                        \u001B[32m** 모든 호선은 가장 긴 노선으로 문제를 출제합니다.
                        ** 마지막 번호의 다음역은 1번 역으로 제시됩니다.\u001B[0m
                        
                        ( 번호 )    ( 역 명 ) \s""");

                do {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    if(id<10)
                        System.out.println("   "+id+"         "+name);
                    else
                        System.out.println("   "+id+"        "+name);

                } while (rs.next());

            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ 불러오기 실패");
        }

    }

    public void remainTop5() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            // 수동 커밋 모드로 전환 (안정성 확보)
            conn.setAutoCommit(false);

            try (Statement stmt = conn.createStatement()) {
                // 1. 혹시 남아있을지 모를 유령 임시 테이블 삭제
                stmt.executeUpdate("DROP TABLE IF EXISTS game_score_temp");

                // 2. 상위 5명을 뽑아 새 ID(1~5)를 부여하며 임시 테이블 생성
                String createTemp =
                        "CREATE TABLE game_score_temp AS " +
                                "SELECT ROW_NUMBER() OVER (ORDER BY score DESC) AS id, player, score " +
                                "FROM game_score ORDER BY score DESC LIMIT 5";
                stmt.executeUpdate(createTemp);

                // 3. 기존 테이블 비우기 (주의: 여기서 락이 걸릴 수 있으니 빠르게 실행)
                stmt.executeUpdate("DELETE FROM game_score"); // TRUNCATE보다 DELETE가 트랜잭션 내에서 안전할 때가 많습니다.

                // 4. 임시 테이블 데이터를 본 테이블로 복사 (ID 1~5 포함)
                String copyBack =
                        "INSERT INTO game_score (id, player, score) " +
                                "SELECT id, player, score FROM game_score_temp";
                stmt.executeUpdate(copyBack);

                // 5. 임시 테이블 삭제
                stmt.executeUpdate("DROP TABLE game_score_temp");

                // 모든 과정이 성공하면 한 번에 반영!
                conn.commit();
                System.out.println("✅ 최적화 성공");

            } catch (SQLException e) {
                if (conn != null) conn.rollback(); // 중간에 실패하면 원상복구
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (conn != null) conn.setAutoCommit(true); } catch (SQLException e) {
                System.out.println("❌ 최적화 실패");
            }
        }
    }
}
