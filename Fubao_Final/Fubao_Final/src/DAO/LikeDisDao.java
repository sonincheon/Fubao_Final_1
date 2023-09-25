package DAO;

import VO.Like_DisVo;
import common.Common;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class LikeDisDao {
    Scanner sc = new Scanner(System.in);
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    List<Like_DisVo> list = new ArrayList<>();
    int like;
    int dis;


    public void LikeDisCount(int s_num) { //좋아요 싫어요 카운트
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = " SELECT SUM(S_LIKE),SUM(S_DIS) FROM like_dis WHERE s_number ="+ s_num;
            rs = stmt.executeQuery(sql);
            //rs.값이 있다면 반복(DB 가져온 값이 있으면 행만큼 반복)
            while (rs.next()) {

                int s_like = rs.getInt("SUM(S_like)");
                int s_dis = rs.getInt("SUM(S_dis)");
                System.out.println("좋아요"+s_like);
                System.out.println("싫어요"+s_dis);

            }
            LikeDisPrint(list);
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    public void LikeDisPrint(List<Like_DisVo> list) { // 좋싫 프린트
        for (Like_DisVo e : list) {
            System.out.print(" 좋아요 "+e.getS_like()+"      ");
            System.out.print(" 싫어요 "+e.getS_dis());
        }
    }

    //-------------------------------------------------------------------------------------------------------------
    public  void MyLIkeDis(int s_num,String nick){ // 좋씷 보여주는메소드
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT S_LIKE, S_DIS  FROM LIKE_DIS   where s_number="+s_num+"  AND NICK ='"+nick+"'";
            rs = stmt.executeQuery(sql);
            //rs.값이 있다면 반복(DB 가져온 값이 있으면 행만큼 반복)
            while (rs.next()) {
                like = rs.getInt("S_LIKE");
                dis = rs.getInt("S_DIS");
            }
            LikeDisChangeChoice();
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);

        } catch (Exception e) {

        }
    }


    public void LikeDisChangeChoice() {

        if (like == 1) {
            System.out.print("좋아요 [ V ]");
        } else {
            System.out.print("좋아요 [   ]");
        }
        ;
        if (dis == 1) {
            System.out.println("싫어요 [ V ]");
        } else {
            System.out.println("싫어요 [   ]");
        }
        ;
    }
//    --------------------------
    public void LikeDisChangeChoice2(int ld_num,int s_num,String nick) {
        if(ld_num==1){ // 1번일떄 좋아요로 변경
            if (like == 1) {
                like = 0;
            } else if (like == 0) {
                like = 1;
            }
        }
        if(ld_num==2){ // 2번일떄 싫어요 변경
            if (dis == 1) {
                dis = 0;
            } else if (dis == 0) {
                dis = 1;
            }
        }
        LikeDisChange(s_num,nick); // 변경 메소드 작동


    }

    public void LikeDisChange(int s_num,String nick) {

        String sql = "UPDATE LIKE_DIS SET S_LIKE = "+like+",S_DIS= "+dis+"  WHERE S_NUMBER ="+ s_num+ "AND NICK= '"+nick+"' ";
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            int ret = stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MyLIkeDis(s_num,nick);
        Common.close(stmt);
        Common.close(conn);
    }

}