package DAO;
import common.Common;
import VO.StreamingVo;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class StreamingDao {
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Scanner sc = new Scanner(System.in);

    public List<StreamingVo> StreamingSelect(int num){ //영화 리스트 출력 구문
        List<StreamingVo> list = new ArrayList<>();
        String menuNum =null;

        switch (num) {
            case 1: // 추천영화 (조회수 30개 이상)
                menuNum = "";
                break;
            case 2: // 신작영화 (올해 영화)
                menuNum = " WHERE EXTRACT(YEAR FROM RELEASE) = EXTRACT(YEAR FROM SYSDATE) ORDER BY RELEASE";
                break;
            case 3: // 장르별 조회
                menuNum = " WHERE GENRE = '"+Genre ()+"'";
                break;
            case 4: // 원하는 제목 검색 , 제목내에 존재하면 출력
                System.out.print("원하는 영화제목 : ");
                String name = sc.next();
                menuNum = " WHERE TITLE LIKE"+"'%" + name+ "%'";
        }
        try{ // 영화 테이블에서 list로 가져오는 구문
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM STREAMING"+menuNum; //
            rs = stmt.executeQuery(sql);

            while (rs.next()){
                int sNum =rs.getInt("S_NUMBER");
                String genre=rs.getString("GENRE");
                String title=rs.getString("TITLE");
                Date date=rs.getDate("RELEASE");
                String cast=rs.getString("CAST");
                String producer=rs.getString("PRODUCER");
                String runningTime=rs.getString("RUNNING_TIME");
                int views=rs.getInt("VIEWS");

                list.add(new StreamingVo(sNum, genre, title, date, cast, producer, runningTime, views));
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);

        } catch (Exception e) {
            e.printStackTrace();
        }
         return list;
    }
    public String Genre (){ //장르 메소드
        String [] genre ={"","액션","범죄","로맨스","코미디","공포","스포츠","아포칼립스","무협","성인"};
        System.out.println("[1]액션 [2]범죄 [3]로맨스 [4]코미디 [5]공포 [6]스포츠 [7]아포칼립스 [8]무협 [9]성인");
        System.out.print("시청하실 장르를 선택해주세요 : ");
        String s_genre = genre[sc.nextInt()];
        return s_genre;
    }


    // 영화 리스트 출력 메소드
    public void StreamingPrint(List<StreamingVo> list){

        System.out.println("=".repeat(100));

        for(StreamingVo e : list){
            System.out.print("["+e.getsNum()+"]"+e.getTitle());
            System.out.print("  [장르] : " +e.getGenre());
            System.out.print("  [개봉일] :" +e.getDate());
            System.out.printf("  [출연진] : " +e.getCast());
            System.out.printf("  [감독] :  " +e.getProducer());
            System.out.printf("  [상영시간] : "+e.getRunningTime());
            System.out.printf("  [누적조회수] : "+e.getViews());
            System.out.println("");
        }
    }

    //영화 선택 메소드
    public List<StreamingVo> MovieSelect(int num){ //영화 선택 후 그영화의 리스트 반환
        List<StreamingVo> list = new ArrayList<>();
        try{ // 영화 테이블에서 list로 가져오는 구문
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM STREAMING WHERE S_NUMBER = "+ num ; //
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int sNum = rs.getInt("S_NUMBER");
                String genre = rs.getString("GENRE");
                String title = rs.getString("TITLE");
                Date date = rs.getDate("RELEASE");
                String cast = rs.getString("CAST");
                String producer = rs.getString("PRODUCER");
                String runningTime = rs.getString("RUNNING_TIME");
                int views = rs.getInt("VIEWS");
                list.add(new StreamingVo(sNum, genre, title, date, cast, producer, runningTime, views));
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);

        } catch (Exception e) {
          e.printStackTrace();

        }
        return list;
    }

    //영화 재생 메소드
    public void WatchingTv()  { // 영화 반복문의 메소드
        StreamingDao STDao = new StreamingDao();
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() { // timerTast 불러오기
            int cnt = 0;  // 초기화값
            @Override
            public void run() {
                if (cnt == 10) { // 시간 설정 ( 100초 )
                    timer.cancel(); // 설정된 시간 뒤 꺼짐
                } else if (cnt % 5 == 0) {
                    System.out.println("⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⠀⠀⢶⣄⠀⠀⠀⠀⠀⠀⠀      ⠀⠀⠀⢀⣴⠆⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⠀⠀⠀⠀⠻⣷⣄⠀⠀⠀⠀⠀⠀   ⢀⣴⡿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⢀⣤⣤⣤⣤⣬⣿⣷⣤⣤⣤⣤⣴⣿⣯⣤⣤⣤⣤⣤⣤⡀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⢠⣿⣿⠟⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⢿⣿⣿⣿⣿⣆⠀⠀⠀\n" +
                            "⠀⠀⠀⢸⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀    ⠀⣿⣿⣿⣿⣿⠀⠀⠀\n" +
                            "⠀⠀⠀⢸⣿⡇⠀⠀⠀⠀⠀⠀⣤⣤⣀⠀⠀⠀⠀⠀    ⠀⠀⣿⣿⣿⣿⣿⠀⠀⠀\n" +
                            "⠀⠀⠀⢸⣿⡇⠀⠀⠀⠀⠀⠀⣿⣿⣿⣷⡆⠀⠀⠀⠀    ⠀⣿⣿⣿⣿⣿⠀⠀⠀\n" +
                            "⠀⠀⠀⢸⣿⡇⠀⠀⠀⠀⠀⠀⣿⣿⠿⠋⠁⠀⠀⠀   ⠀ ⠀⣿⣿⣿⣿⣿⠀⠀⠀\n" +
                            "⠀⠀⠀⢸⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀     ⠀⣿⣿⣿⣿⣿⠀⠀⠀\n" +
                            "⠀⠀⠀⢸⣿⣧⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀   ⣠⣿⣿⣿⣿⡿⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠟⠁⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀");
                    cnt++;
                } else if (cnt % 5 == 1) {
                    System.out.println("⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⠀⠀⢶⣄⠀⠀⠀⠀⠀⠀⠀      ⠀⠀⠀⢀⣴⠆⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⠀⠀⠀⠀⠻⣷⣄⠀⠀⠀⠀⠀   ⠀⢀⣴⡿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⢀⣤⣤⣤⣤⣬⣿⣷⣤⣤⣤⣤⣴⣿⣯⣤⣤⣤⣤⣤⣤⡀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⢠⣿⣿⠟⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⢿⣿⣿⣿⣿⣆⠀⠀⠀\n" +
                            "⠀⠀⠀⢸⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀     ⣿⣿⣿⣿⣿⠀⠀⠀\n" +
                            "⠀⠀⠀⢸⣿⡇⠀⠀⠀⠀⠀⠀영상 시청중⠀     ⣿⣿⣿⣿⣿⠀⠀⠀\n" +
                            "⠀⠀⠀⢸⣿⡇⠀⠀⠀⠀⠀⠀영상 시청중⠀   ⠀⠀⣿⣿⣿⣿⣿⠀⠀⠀\n" +
                            "⠀⠀⠀⢸⣿⡇⠀⠀⠀⠀⠀⠀영상 시청중⠀    ⠀⣿⣿⣿⣿⣿⠀⠀⠀\n" +
                            "⠀⠀⠀⢸⣿⡇⠀⠀⠀⠀⠀⠀영상 시청중⠀   ⠀⠀⣿⣿⣿⣿⣿⠀⠀⠀\n" +
                            "⠀⠀⠀⢸⣿⣧⡀⠀⠀⠀⠀           ⠀  ⣠⣿⣿⣿⣿⡿⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠟⠁⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀");
                    cnt++;
                } else if (cnt % 5 == 2) {
                    System.out.println("⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⠀⠀⠀⢶⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀     ⠀⢀⣴⠆⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⠀⠀⠀⠀ ⠻⣷⣄⠀⠀⠀⠀⠀  ⠀⢀⣴⡿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⢀⣤⣤⣤⣤⣬⣿⣷⣤⣤⣤⣤⣴⣿⣯⣤⣤⣤⣤⣤⣤⡀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⢠⣿⣿⠟⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⢿⣿⣿⣿⣿⣆⠀⠀⠀\n" +
                            "⠀⠀⠀⢸⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀    ⠀⣿⣿⣿⣿⣿⠀⠀⠀\n" +
                            "⠀⠀⠀⢸⣿⡇⠀⠀⠀⠀⠀⠀푸 바 오 ♥⠀    ⠀⣿⣿⣿⣿⣿⠀⠀⠀\n" +
                            "⠀⠀⠀⢸⣿⡇⠀⠀⠀⠀⠀⠀푸 바 오 ♥⠀⠀⠀   ⣿⣿⣿⣿⣿⠀⠀⠀\n" +
                            "⠀⠀⠀⢸⣿⡇⠀⠀⠀⠀⠀⠀푸 바 오 ♥   ⠀ ⠀⣿⣿⣿⣿⣿⠀⠀⠀\n" +
                            "⠀⠀⠀⢸⣿⡇⠀⠀⠀⠀⠀⠀푸 바 오 ♥⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⠀⠀⠀\n" +
                            "⠀⠀⠀⢸⣿⣧⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀   ⣠⣿⣿⣿⣿⡿⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠟⠁⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀");
                    cnt++;
                } else if (cnt % 5 == 3) {
                    System.out.println("⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⠀⠀⢶⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀      ⠀⢀⣴⠆⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⠀⠀⠀⠀⠻⣷⣄⠀⠀⠀⠀⠀⠀   ⢀⣴⡿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⢀⣤⣤⣤⣤⣬⣿⣷⣤⣤⣤⣤⣴⣿⣯⣤⣤⣤⣤⣤⣤⡀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⢠⣿⣿⠟⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⢿⣿⣿⣿⣿⣆⠀⠀⠀\n" +
                            "⠀⠀⠀⢸⣿⡇⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀\n" +
                            "⠀⠀⠀⢸⣿⡇⠀⠀⠀⠀⠀⠀         ⠀     ⣿⣿⣿⣿⣿⠀⠀⠀\n" +
                            "⠀⠀⠀⢸⣿⡇⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀\n" +
                            "⠀⠀⠀⢸⣿⡇⠀⠀⠀⠀⠀⠀⠀    ⠀          ⣿⣿⣿⣿⣿⠀⠀⠀\n" +
                            "⠀⠀⠀⢸⣿⡇⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀\n" +
                            "⠀⠀⠀⢸⣿⣧⡀⠀⠀⠀⠀           ⠀  ⣠⣿⣿⣿⣿⡿⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠟⠁⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀");
                    cnt++;
                } else if (cnt % 5 == 4) {
                    System.out.println(" ");
                    cnt++;
                }
            }
        };
        timer.schedule(timerTask, 1000, 1000); // 딜레이 및 cnt 추가시간  1000 당 1초

        try{
            Thread.sleep(10000);
        }catch (Exception e){}
    }


    //영화 추가 메소드
    public List<StreamingVo> StreamingPlus(){
        System.out.println("제목을 입력해 주세요");
        String title = sc.nextLine();
        System.out.println("장르를 입력해 주세요");
        String genre = sc.nextLine();
        System.out.println("개봉일을 입력해 주세요");
        String RELEASE=sc.nextLine();
        System.out.println("주연배우를 입력해 주세요");
        String cast =sc.nextLine();
        System.out.println("연출자를 입력해 주세요");
        String producer = sc.nextLine();
        System.out.println("상여시간을 입력해 주세요");
        String running_time = sc.nextLine();

        Connection conn = null;
        Statement stmt = null;
        String query = "INSERT INTO STREAMING VALUES (ST_SEQUENCE.NEXTVAL, '"+genre+"','"+title+"',TO_DATE('+"+RELEASE+"','YYYY/MM/DD'),'"+cast+"','"+producer+"','"+running_time+"',0)";
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            int ret = stmt.executeUpdate(query);
            System.out.println("Return : " + ret);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(stmt);
        Common.close(conn);
        return null;
    }


    //영화 삭제 메소드
    public List<StreamingVo> StreamingDelete() {
        System.out.println("삭제할 제목을 입력해 주세요 :");
        String title = sc.next();
        String sql = "DELETE FROM STREAMING WHERE TITLE = '"+ title+"'";
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            int ret = stmt.executeUpdate(sql);
            // 0보다크면  이프넣고 와일문을써야햇나?? ...
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(stmt);
        Common.close(conn);
        return null;
    }

    public void StreamingViewPlus(int s_num,int s_veiw){
        String sql ="UPDATE STREAMING SET VIEWS=("+ s_veiw +"+1) WHERE S_NUMBER = " +s_num ;
        try{
            conn = Common.getConnection();
            stmt = conn.createStatement();
            int ret = stmt.executeUpdate(sql);
            } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(stmt);
        Common.close(conn);
    }
}


















