package DAO;

import common.Common;
import VO.ADUserVo;


import java.awt.*;
import java.net.URI;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.List;

public class AduserDao {
    Scanner sc = new Scanner(System.in);
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    public static int count = 0;


    //광고리스트 출력
    public List<ADUserVo> AduserSelect() {
        List<ADUserVo> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM ADUSER";
            rs = stmt.executeQuery(sql);
            //rs.값이 있다면 반복(DB 가져온 값이 있으면 행만큼 반복)
            while (rs.next()) {
                String company = rs.getString("COMPANY");
                String banner = rs.getString("BANNER");
                Date start_AD = rs.getDate("START_AD");
                Date end_AD = rs.getDate("END_AD");
                int AD_Views = rs.getInt("AD_VIEWS");
                int view_Price = rs.getInt("VIEW_PRICE");
                int company_maony = rs.getInt("PRICE");

                list.add(new ADUserVo(company, banner, start_AD, end_AD, AD_Views, view_Price, company_maony));
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }

        return list;
    }

    public void AduserPrint(List<ADUserVo> list) {
        System.out.println("=".repeat(10)+"광고 리스트"+"=".repeat(10));
        for (ADUserVo e : list) {
            System.out.print("" + e.getCompany() + "  ");
            System.out.print("" + e.getBanner() + "  ");
            System.out.print("" + e.getStart_AD() + "  ");
            System.out.print("" + e.getEnd_AD() + "  ");
            System.out.print("" + e.getAD_Views() + "  ");
            System.out.print("" + e.getView_Price() + "  ");
            System.out.println("" + e.getCompany_maony() + "  ");
        }
    }

// 광고 재생
    public void BannerSelect() {
        List<ADUserVo> list = new ArrayList<>();
        System.out.println("=".repeat(7)+"광고가 시작됩니다"+"=".repeat(7));
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM ADUSER WHERE END_AD > SYSDATE  AND SYSDATE>START_AD";
            rs = stmt.executeQuery(sql);
            //rs.값이 있다면 반복(DB 가져온 값이 있으면 행만큼 반복)
            while (rs.next()) {
                String banner = rs.getString("BANNER");
                list.add(new ADUserVo(banner));
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
            Random random = new Random();
            int randomIndex = random.nextInt(list.size());
            ADUserVo selectedLink = list.get(randomIndex);
            String you = selectedLink.getBanner();

            Desktop.getDesktop().browse(new URI(you));

        } catch (Exception e) {
            e.printStackTrace();
        }
        Timer m = new Timer();
        TimerTask task = new TimerTask() {
            int cnt = 0;  // 초기화값

            @Override
            public void run() {
                if(cnt == 20){ // 시간 설정 ( 100초 )
                    m.cancel(); // 설정된 시간 뒤 꺼짐
                } else if (cnt % 5 == 0) {
                    System.out.println("⠀⠀⠀⠀⠀⠀⠀⠀⣀⣀⣀⠀⠀⠀⠀⠀⢀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⢠⣶⣷⣦⠖⠋⠉⠉⠉⠉⠛⠲⢤⣾⣿⣿⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠸⣿⡿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢻⣿⡟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⢹⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢿⠧⣤⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⢸⠀⢀⣶⣿⣦⠀⠀⠀⣴⣷⣦⡄⠀⢸⠀⠀⠀⠉⠓⢦⠁⡆⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⢸⠀⢸⣿⣾⡟⠀⠀⠀⣿⣿⣿⣿⠀⢸⡇⠀⠀⠀⠀⠈⣷⠁⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠈⣇⠀⠛⠋⠀⠀⠀⠀⠙⢿⣿⠿⠀⢸⠀⠀⠀⠀⠀⠀⢸⡀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⣀⣸⣦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⣿⣷⡀⠀⠀⠀⠀⢸⣧⣦⡄⠀⠀\n" +
                            "⠀⠀⣾⣿⣿⣿⣿⠓⠦⠤⠤⠤⣤⣤⣶⣿⣿⣿⣿⡧⠤⠤⢤⣴⣿⣿⣿⡗⠀⠀\n" +
                            "⠀⠀⠈⠛⠛⠛⠁⠀⠀⠀⠀⠀⠸⣿⣿⣿⣿⣿⡿⠃⠀⠀⠀⠉⠙⠋");
                    cnt++;
                } else if (cnt % 5 == 1) {
                    System.out.println("⠀⠀⠀⠀⠀⠀⠀⠀⢠⣾⣿⣿⣷⡀⠀⠀⠀⠀⠀⠀⣴⣿⣿⣿⣷⡀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⠀⠀⠀⣠⣿⣿⣿⣿⡿⠛⠓⠲⠶⠦⣤⣀⣻⣿⣿⣿⣿⡇⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⠀⣠⠟⠁⠈⠉⠉⠁⠀⠀⠀⢠⠠⠀⠀⠈⠙⠿⣿⣿⣿⠁⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⢰⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⠀⠀⠀⠀⠀⠙⣿⡇⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⢸⣤⣶⣶⣄⣀⣀⣄⣀⣀⣀⣀⠀⠀⠀⠀⠀⠀⠀⢸⡇⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⢸⣿⣿⡿⠋⠉⠀⠀⠀⠀⠀⠉⠉⠛⠶⣤⣾⣿⣷⣾⡇⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⠘⣿⠏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢻⣿⣿⣿⠁⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⢀⡟⠀⢀⣠⣤⣄⠀⠀⠀⠀⣤⣤⣀⠀⠀⠀⣿⣿⠇⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⢸⠃⢠⣿⡿⣿⣿⠀⠀⠀⠘⣿⣿⣿⣿⣆⠀⣾⠋⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⢸⠀⢿⣿⣿⣿⠟⠀⠀⠀⠀⠹⣿⣿⣿⣿⢀⡏⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⣸⣇⠈⠉⠉⠁⠀⠀⠀⠀⠀⠀⠈⠛⠟⠋⣼⣁⣠⣤⣀⣀⠀⠀⠀⠀\n" +
                            "⠀⠀⣼⣿⣿⣿⣆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣴⣿⣿⣿⣿⣿⣿⣷⠀⠀⠀\n" +
                            "⠀⠀⠘⢿⣿⣿⣿⣿⣶⣤⣤⣤⣤⣀⣀⣤⣤⠴⠾⠿⢿⣿⣿⣿⣿⣿⠟⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⠈⠙⠛⠻⠿⠿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⠉⠀⠀⠀⠀⠀");
                    cnt++;
                } else if (cnt % 5 == 2) {
                    System.out.println("⠀⠀⠿⠿⠿⠿⠿⢿⣷⠀⠀⣿⡇⠀⠀⠀⠀⢰⣶⣶⣶⣶⣶⣶⣶⣶⣄⠀⠀⠀\n" +
                                        "⠀⠀⠀⠀⣀⡀⠀⢸⣿⠀⠀⣿⣇⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀ ⠀⢸⣿⠀⠀⠀\n" +
                                        "⠀⠀⠀⠀⣿⡇⠀⠘⠛⠀⢀⣿⡟⠛⠃⠀⠀⠀⠀⠀ ⢀⣀⠀⠀ ⢸⣿⠀⠀⠀\n" +
                                        "⠰⠶⠶⠶⠿⠿⠶⠶⠶⠶⠻⣿ ⠀⠀⠀⠀⠀⠀⠀⢸⣿⡇⠀⠀⢸⣿⠀⠀⠀\n" +
                                        "⠀⠀⠀⠀⢀⣤⣴⣶⣶⣦⣤ ⠀⠀⠀⠀ ⠀⠀⠀⠀⢸⣿⡇⠀⠀⠈⠉⠀⠀⠀\n" +
                                        "⠀⠀⠀⢠⣿⡏⠁⠀⠀⠀⠹⣿⡄⠀⠀⠰⣶⣶⣶⣶⣾⣿⣷⣶⣶⣶⣶⣶⡦⠀\n" +
                                        "⠀⠀⠀⠈⢿⣷⣄⣀⣀⣠⣼⡿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                                         "⠀⠀⠀⠀⠀⠈⠉⠉⠉⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀");
                    cnt++;
                } else if (cnt % 5 == 3) {
                    System.out.println("⠀⠀⠀⠀⠀⢀⣴⣶⣿⣿⣿⣷⣦⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⣰⣿⣿⣿⣿⣿⣿⣿⣿⠃⢀⣠⡤⠤⠶⠶⣤⣤⣀⡀⠀⠀⠀⠀⠀⠀\n" +
                            "⠀⠀⠀⢰⣿⣿⣿⣿⣿⣿⣿⣟⣡⠞⠋⠀⠀⠀⠀⠰⣿⣿⣿⡍⠳⣶⣶⡄⠀⠀\n" +
                            "⠀⠀⢠⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⠀⠀⠀⠀⠀⠀⠈⠙⠛⠁⠀⠈⢿⡿⠀⠀\n" +
                            "⠀⠀⣾⣿⣿⣿⣿⣿⠏⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⣠⣴⣶⣶⡀⠀⠀⠸⡇⠀⠀\n" +
                            "⠀⢰⡇⠈⠙⠛⠉⠀⠀⢿⣿⣿⡿⠋⠀⠀⠀⢀⣾⣿⣷⣾⣿⠇⠀⠀⠀⡇⠀⠀\n" +
                            "⠀⢸⡇⠀⠀⠀⠀⠀⠀⠀⠉⢹⠀⠀⠀⠀⠀⢸⣿⣿⣿⡿⠋⠀⠀⠀⠀⡇⠀⠀\n" +
                            "⠀⢨⣧⠀⠀⠀⠀⠀⠀⠀⠀⠘⡆⠀⠀⠀⠀⠀⠈⠉⠀⠀⠀⣀⡀⠀⣸⠃⠀⠀\n" +
                            "⠀⠠⣈⣷⣄⡀⠀⠀⠀⠀⠀⠀⠹⣄⠀⠀⠀⠀⠀⠀⠀⢰⣿⣿⣿⣿⡏⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⠈⠉⠛⠒⠲⠶⠒⠒⠛⠛⠳⣄⡀⠀⠀⠀⠀⠸⣿⣿⣿⣿⡇⠀⠀⠀\n" +
                            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠛⠓⠒⠒⠛⠙⠛⠛⠋⠀⠀⠀⠀⠀");
                    cnt++;
                } else if (cnt % 5 == 4) {
                    System.out.println(" ");
                    cnt++;
                }
            }
        };
        m.schedule(task, 1000, 1000);
        try{
            Thread.sleep(21000);
        }catch (Exception e){

        }
    }

    //광고 등록
    public void AdUserInsert() {
        System.out.println("=".repeat(7)+"관리자모드(광고등록)"+"=".repeat(7));
        try {
            System.out.println("광고 업체 등록.");
            String company=sc.next();
            System.out.print("광고 주소 ");
            String  banner = sc.next();
            System.out.println("광고 시작일 년/월/일");
            String start_AD = sc.next();
            System.out.println("광고 종료일 년/월/일 ");
            String end_AD = sc.next();
            System.out.print("계약금 :  ");
            int company_maony = sc.nextInt();
            String sql = "INSERT INTO ADUSER (COMPANY,BANNER,START_AD,END_AD,VIEW_PRICE,PRICE)VALUES(?,?,?,?,?,?)";

            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, company);
            pstmt.setString(2, banner);
            pstmt.setString(3, start_AD);
            pstmt.setString(4, end_AD);
            pstmt.setInt(5, 1000);
            pstmt.setInt(6, company_maony);

            int rst = pstmt.executeUpdate();//실행결과가 정수 값으로 변환(영향을 받은 행의 개수가 변환됨)

        } catch (Exception e) {
            System.out.println("잘못 입력했습니다");
            AdUserInsert();
        }
        Common.close(pstmt);
        Common.close(conn);
    }
}

    //광고 업체 변경
//    public void AdUserUpdate() {
//        System.out.println("=".repeat(7)+"관리자모드(광고변경)"+"=".repeat(7));
//        try {
//            System.out.println("광고 업체명 변경 (1입력시 변경 하지 않음)");
//            String company=sc.next();
//            System.out.print("광고 주소 변경 (1입력시 변경 하지 않음)");
//            String  banner = sc.next();
//            System.out.println("광고 시작일 변경 (1입력시 변경 하지 않음) ");
//            String start_AD = sc.next();
//            System.out.println("광고 종료일 변경 (1입력시 변경 하지 않음)");
//            String end_AD = sc.next();
//            System.out.print("계약금 (변경) (1입력시 변경 하지 않음)");
//            int company_maony = sc.nextInt();
//            String sql = "INSERT INTO ADUSER (COMPANY,BANNER,START_AD,END_AD,VIEW_PRICE,COMPANY_MONEY)VALUES(?,?,?,?,?,?)";
//
//            conn = Common.getConnection();
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, company);
//            pstmt.setString(2, banner);
//            pstmt.setString(3, start_AD);
//            pstmt.setString(4, end_AD);
//            pstmt.setInt(5, 1000);
//            pstmt.setInt(6, company_maony);
//
//            int rst = pstmt.executeUpdate();//실행결과가 정수 값으로 변환(영향을 받은 행의 개수가 변환됨)
//
//        } catch (Exception e) {
//            System.out.println("잘못 입력했습니다");
//        }
//        Common.close(pstmt);
//        Common.close(conn);
//    }

