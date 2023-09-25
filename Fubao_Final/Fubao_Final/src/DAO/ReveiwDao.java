package DAO;

import VO.ReviewVo;
import common.Common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReveiwDao {
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Scanner sc = new Scanner(System.in);

    public void TalkON(int sNum,String nick){ // 댓글생성 (스트리밍 넘 , 회원 닉)
        System.out.println("=".repeat(10)+"댓글 입력"+"=".repeat(10));
        System.out.print("댓글 : ");
        String talk = sc.next();
        Connection conn = null;
        Statement stmt = null;
        String sql = "INSERT INTO REVIEW VALUES("+sNum+",'"+nick+"',ST_SEQUENCE.NEXTVAL,'"+talk+"')";
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            int ret = stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(stmt);
        Common.close(conn);
    }

    public void TalkDelete(String nick){ // 댓글삭제 (스트리밍 넘 , 회원 닉)
        System.out.println("=".repeat(10)+"댓글 삭제"+"=".repeat(10));
        System.out.println("삭제할 댓글 번호를 입력해주세요 .");
        String tNum = sc.next();
        Connection conn = null;
        Statement stmt = null;
        String sql = "DELETE FROM REVIEW WHERE T_NUMBER="+tNum+"AND NICK  = '"+nick+"'";
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            int ret = stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(stmt);
        Common.close(conn);
    }

    public void TalkChange(String nick) { // 댓글 변경
        System.out.println("=".repeat(10)+"댓글 변경"+"=".repeat(10));
        System.out.println("변경할 번호");
        int t_number=sc.nextInt();
        System.out.println("댓글 입력");
        String talk=sc.next();
        String sql = "UPDATE REVIEW SET TALK = '"+ talk+"' WHERE T_NUMBER="+t_number+" AND NICK = '"+nick+"'";
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            int ret = stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(stmt);
        Common.close(conn);

    }

    public void ReviewPrint(List<ReviewVo> list) {
        System.out.println("=".repeat(7)+"댓글 리스트"+"=".repeat(7));
        if(list.size()==0) {
            System.out.println("나의 리뷰가 없습니다.");
        }else{
            for (ReviewVo e : list) {
                System.out.println("["+e.getT_number() + "]  " + e.getNick()+"님의 댓글 : "+e.getTalk() + "  ");
            }
            list.clear();
        }

    }
    public void ReviewSelect(int s_num,String nick) {
        List<ReviewVo> list = new ArrayList<>();
        String sql = "SELECT T_NUMBER,NICK,TALK FROM REVIEW WHERE  S_NUMBER ="+s_num;

        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            //rs.값이 있다면 반복(DB 가져온 값이 있으면 행만큼 반복)
            while (rs.next()) {
                int t_number = rs.getInt("T_NUMBER");
                String U_nick = rs.getString("NICK");
                String talk = rs.getString("TALK");
                list.add(new ReviewVo(U_nick,t_number,talk));
            }

            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
        ReviewPrint(list);
        return;
    }


}
