import java.sql.*;

public class extrDB {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/subway?serverTimezone=Asia/Seoul&useSSL=false";
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
                System.out.println("\n( 번호 )    ( 역 명 )  ** 마지막 번호 다음은 1번 역으로 연결됌. (ex. 경찰병원 - 오금 - 대화, 정왕-오이도-진접)");

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
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            String createTemp =
                    "CREATE TABLE game_score_temp AS " +
                            "SELECT ROW_NUMBER() OVER (ORDER BY score DESC) AS id, player, score " +
                            "FROM game_score ORDER BY score DESC LIMIT 5;";
            stmt.executeUpdate(createTemp);

            stmt.executeUpdate("TRUNCATE TABLE game_score;");

            String copyBack =
                    "INSERT INTO game_score (id, player, score) " +
                            "SELECT id, player, score FROM game_score_temp;";
            stmt.executeUpdate(copyBack);

            stmt.executeUpdate("DROP TABLE game_score_temp;");

            System.out.println("✅ DB 최적화 성공");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
