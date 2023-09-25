package DAO;
import common.Common;
import VO.FubaoUserVo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FubaoUserDao {
    List<FubaoUserVo> list = new ArrayList<>();
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    public static String nick;
    Scanner sc = new Scanner(System.in);

    public List<FubaoUserVo> signIn() { // 로그인 메소드
        MenuListDao menuListDao = new MenuListDao();
        System.out.println("=".repeat(10)+"L O G I N"+"=".repeat(10));
        System.out.print("id :");
        String inputID = sc.next();
        System.out.print("pw :");
        String inputPW = sc.next();
        if(inputID == "S2222" && inputPW == "2222"){
            menuListDao.MasterMenu();
        }
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM FUBAOUSER WHERE ID = '" + inputID + "' AND PASSWORD = '" + inputPW+"'";
            rs = stmt.executeQuery(sql); // executeQuery() SELECT 문에서 사용
            while (rs.next()) {
                String uname = rs.getString("UNAME");
                String gender = rs.getString("GENDER");
                Date birth = rs.getDate("BIRTH");
                String contact = rs.getString("CONTACT");
                String nick = rs.getString("NICK");
                String id = rs.getString("ID");
                String password = rs.getString("PASSWORD");
                Date enter_date = rs.getDate("ENTER_DATE");
                int token = rs.getInt("TOKEN");
                list.add(new FubaoUserVo(uname, gender, birth, contact, nick, id, password, enter_date, token));

            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("!!!!아이디/비밀번호를 다시 입력해주세요!!!!");
            signIn();
        }
        return list;
    }


        public void SignUp() {  //회원 가입 메소드
            System.out.println("=".repeat(10) + "회원가입" + "=".repeat(10));
            System.out.print("성명 : ");
            String name = sc.next();
            System.out.print("성별 (남 / 여) : ");
            String gender = sc.next();
            System.out.print("생년월일 (YYYY/MM/DD) : ");
            String date1 = sc.next();
            System.out.print("연락처 (- 포함) : ");
            String contact = sc.next();
            System.out.print("닉네임 입력 (한글, 알파벳, 숫자 포함 10자 제한) : ");
            String nick = sc.next();
            System.out.print("ID 입력 (알파벳, 숫자 포함 10자 제한) : ");
            String id = sc.next();
            String pwd = null;
            while (true){
                System.out.print("PASSWORD 입력 (알파벳, 숫자, 특수기호 포함 15자 제한) : ");
                pwd = sc.next();
                System.out.println(checkPassword(pwd,id));
                if(checkPassword(pwd,id) == ""){break;}
            }

            String sql = "INSERT INTO FUBAOUSER(UNAME, GENDER, BIRTH, CONTACT, NICK, ID, PASSWORD, ENTER_DATE, TOKEN) VALUES(?,?,?,?,?,?,?,SYSDATE,30)";
            try {
                conn = Common.getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, name);
                pstmt.setString(2, gender);
                pstmt.setString(3, date1);
                pstmt.setString(4, contact);
                pstmt.setString(5, nick);
                pstmt.setString(6, id);
                pstmt.setString(7, pwd);
                int rst = pstmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("회원가입이 완료되었습니다. 환영합니다. "+nick+"님");
            Common.close(pstmt);
            Common.close(conn);
        }
    public void tokenUP(String nick) { // id를 입력하면 토큰up
        System.out.println("=".repeat(7)+"토큰이 +1 추가 되었습니다. "+"=".repeat(7));
        int num = 1;
        String sql = "UPDATE FUBAOUSER SET TOKEN = TOKEN +? WHERE NICK = ?";
        try {
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, num);
            pstmt.setString(2, nick);

            int rst = pstmt.executeUpdate(); //실행결과가 정수 값으로 반환 됨 영향을 받은 행이 몇개인지, 받을게 없으면 0, false처리 해줘
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(stmt);
        Common.close(conn);

    }
    public void tokenDown(String nick) {//id를 입력하면 토큰down
        System.out.println("=".repeat(7)+"토큰이 -1 감소 되었습니다. "+"=".repeat(7));
        int num = 1;
        String sql = "UPDATE FUBAOUSER SET TOKEN = TOKEN -? WHERE NICK = ?";
        try {
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, num);
            pstmt.setString(2, nick);

            int rst = pstmt.executeUpdate(); //실행결과가 정수 값으로 반환 됨 영향을 받은 행이 몇개인지, 받을게 없으면 0, false처리 해줘
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(stmt);
        Common.close(conn);
    }

    public void signUpView(List<FubaoUserVo> list){
        System.out.println("=".repeat(10)+"나 의 정 보"+"=".repeat(10));
        for(FubaoUserVo e : list){
            System.out.println("ID: " + e.getId());
            System.out.println("PW: " + e.getPassword());
            System.out.println("닉네임: " + e.getNick());
            System.out.println("이름: " + e.getUname());
            System.out.println("생년월일: " + e.getBirth());
            System.out.println("연락처: " + e.getContact());
            System.out.println("토큰수: " + e.getToken());
            System.out.println("생성일자 : " + e.getEnter_date());
        }
    }
    public void userChange(List<FubaoUserVo> list){
        System.out.println("=".repeat(7)+"회 원 정 보 변 경 "+"=".repeat(7));
        System.out.println("변경할 회원정보를 입력해주세요 .");
        System.out.println("이름 :");
        String name = sc.next();
        System.out.println("성별 :");
        String gender = sc.next();
        System.out.println("연락처 :");
        String contact = sc.next();
        System.out.println("닉네임 :");
        String nick = sc.next();
        System.out.println("비밀번호 :");
        String pw = sc.next();
        System.out.println("아이디 :");
        String id = sc.next();

        String sql = "UPDATE FUBAOUSER SET UNAME = ?,GENDER = ?,CONTACT =?,NICK = ?,PASSWORD = ? WHERE ID = ?";
        try {
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, gender);
            pstmt.setString(3, contact);
            pstmt.setString(4, nick);
            pstmt.setString(5, pw);
            pstmt.setString(6, id);
            int rst = pstmt.executeUpdate(); //실행결과가 정수 값으로 반환 됨 영향을 받은 행이 몇개인지, 받을게 없으면 0, false처리 해줘
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(stmt);
        Common.close(conn);
    }

    public List<FubaoUserVo> UserDelete() {
        System.out.println("=".repeat(7)+"관리자모드(유저삭제) "+"=".repeat(7));
        System.out.println("삭제할 유저 닉네임을 입력해 주세요 :");
        String nick = sc.next();
        String sql = "DELETE FROM FUBAOUSER WHERE NICK = '" +nick+"'";
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

    private String checkPassword(String pwd, String id){
        // 비밀번호 포맷 확인(영문, 특수문자, 숫자 포함 8자 이상)
        Pattern passPattern1 = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");
        Matcher passMatcher1 = passPattern1.matcher(pwd);

        if(!passMatcher1.find()){
            return "비밀번호는 영문과 특수문자 숫자를 포함하며 8자 이상이어야 합니다.";
        }

        // 반복된 문자 확인
        Pattern passPattern2 = Pattern.compile("(\\w)\\1\\1\\1");
        Matcher passMatcher2 = passPattern2.matcher(pwd);

        if(passMatcher2.find()){
            return "비밀번호에 동일한 문자를 과도하게 연속해서 사용할 수 없습니다.";
        }

        // 아이디 포함 확인
        if(pwd.contains(id)){
            System.out.println("비밀번호에 ID를 포함할 수 없습니다.");
        }

        // 특수문자 확인
        Pattern passPattern3 = Pattern.compile("\\W");
        Pattern passPattern4 = Pattern.compile("[!@#$%^*+=-]");

        for(int i = 0; i < pwd.length(); i++){
            String s = String.valueOf(pwd.charAt(i));
            Matcher passMatcher3 = passPattern3.matcher(s);

            if(passMatcher3.find()){
                Matcher passMatcher4 = passPattern4.matcher(s);
                if(!passMatcher4.find()){
                    return "비밀번호에 특수문자는 !@#$^*+=-만 사용 가능합니다.";
                }
            }
        }

        //연속된 문자 확인
        int ascSeqCharCnt = 0; // 오름차순 연속 문자 카운트
        int descSeqCharCnt = 0; // 내림차순 연속 문자 카운트

        char char_0;
        char char_1;
        char char_2;
        int diff_0_1;
        int diff_1_2;

        for(int i = 0; i < pwd.length()-2; i++){
            char_0 = pwd.charAt(i);
            char_1 = pwd.charAt(i+1);
            char_2 = pwd.charAt(i+2);

            diff_0_1 = char_0 - char_1;
            diff_1_2 = char_1 - char_2;

            if(diff_0_1 == 1 && diff_1_2 == 1){
                ascSeqCharCnt += 1;
            }

            if(diff_0_1 == -1 && diff_1_2 == -1){
                descSeqCharCnt -= 1;
            }
        }

        if(ascSeqCharCnt > 1 || descSeqCharCnt > 1){
            return "비밀번호에 연속된 문자열을 사용할 수 없습니다.";
        }

        return "";
    }
}



