package DAO;

import VO.ADUserVo;
import VO.FubaoUserVo;
import VO.ReviewVo;
import VO.StreamingVo;

import java.util.List;
import java.util.Scanner;

public class MenuListDao {
    Scanner sc = new Scanner(System.in);
    StreamingDao streamingDao = new StreamingDao();
    FubaoUserDao fubaoUserDao = new FubaoUserDao();
    AduserDao aduserDao = new AduserDao();
    ReveiwDao reveiwDao = new ReveiwDao();
    LikeDisDao likeDisDao = new LikeDisDao();
    List<FubaoUserVo> u_list = null;

    public void LoginMenu() {
        System.out.println("=".repeat(10) + "L O G I N" + "=".repeat(10));
        System.out.println("[1] 로그인 [2] 회원 가입 [3]종료 "); //관리자 모드 따로
        System.out.print("=".repeat(25) + "입력:");
        int num = sc.nextInt();
        switch (num) {
            case 1:
                u_list = fubaoUserDao.signIn();
                MovieMenu(u_list);
                break;//로그인 화면
            case 2:
                fubaoUserDao.SignUp();
                LoginMenu();
                break;//회원가입 화면
            case 3:
                System.out.println("시스템을 종료 합니다.");
                break;
            default:
                System.out.println("재선택해주세요 !");
                LoginMenu();
        }
    }

    public void MovieMenu(List<FubaoUserVo> list) {
        List<FubaoUserVo> U_list = list;
        System.out.println("반갑습니다 ." + U_list.get(0).getNick() + "회원님 원하시는 메뉴를 선택해주세요");
        System.out.println("=".repeat(10) + "영 화 메 뉴" + "=".repeat(10));
        System.out.print("[1]추천 영화 [2]신작 영화 [3]장르별 영화 [4]제목 검색 ");
        System.out.print("[5]토큰 쌓기(광고) [6]회원 정보 [7]로그아웃 ");
        int num = sc.nextInt();
        switch (num) {
            case 1:
            case 2:
            case 3:
            case 4:
                List<StreamingVo> s_list = streamingDao.StreamingSelect(num);// 추천,신작,장르,제목검색 영화 검색
                streamingDao.StreamingPrint(s_list); //영화 리스트 출력
                MovieChoice(U_list.get(0).getNick(), s_list.get(0).getsNum(), s_list.get(0).getViews());
                MovieMenu(list);
                break;
            case 5:
                System.out.println("광고가 재생 됩니다.");
                aduserDao.BannerSelect(); // 광고 재생 매소드
                fubaoUserDao.tokenUP(U_list.get(0).getNick()); // 토큰 1개 추가
                MovieMenu(list);
                break;
            case 6:
                fubaoUserDao.signUpView(U_list); // 회원정보 뷰어
                UserChange(U_list); //회원정보 변경
                MovieMenu(list);
                break;
            case 7:
                System.out.println("로그아웃!");
                u_list=null;
                LoginMenu(); // 로그인화면
                break;
            default:
                System.out.println("재선택해주세요 !");
                MovieMenu(list); // 메인 메뉴 화면
                break;
        }
    }


    public void UserChange(List<FubaoUserVo> list) {
        FubaoUserDao FBuser = new FubaoUserDao();
        System.out.print("변경 하시겠습니까?:[1]예 [2]아니요");
        int num = sc.nextInt();
        switch (num) {
            case 1:
                FBuser.userChange(list); //회원정보 변경
                FBuser.signUpView(list);
                UserChange(list); // 회원 리스트 출력후 변경 문구
                break;
            case 2:
                System.out.println("메인 메뉴로 돌아갑니다");
                MovieMenu(list); //메인 메뉴
                break;
            default:
                System.out.println("잘못 입력하셨습니다 . 메뉴로 돌아가겠습니다.");
                MovieMenu(list); //메인 메뉴
                break;
        }
    }

    public void MovieChoice(String nick, int s1_num, int v_num) {
        System.out.println("영화선택 : (번호)                             [X]돌아가기");
        String num = sc.next();
        if (!num.equalsIgnoreCase("x")) {
            int s_num = Integer.parseInt(num);
            List<StreamingVo> s_list = streamingDao.MovieSelect(s_num);
            while (true) {
                System.out.print("[1]영화 재생  [2]리뷰 보기 [3]좋아요,싫어요 [4]종료");
                int num2 = sc.nextInt();
                switch (num2) {
                    case 1:
                        streamingDao.WatchingTv(); // 영상 재생 메소드
                        fubaoUserDao.tokenDown(nick); //토큰 1개 제거
                        streamingDao.StreamingViewPlus(s1_num, v_num); // 누적 조회 추가
                        break;
                    case 2:
                        MyReviewChoice(s_list.get(0).getsNum(), nick);
                        break;
                    case 3:
                        LDChoice(s_num, nick); // 좋아요,싫어요
                        break;
                    case 4:
                        MovieMenu(u_list);
                        break;
                }
            }
        }
    }

    public void MasterMenu() {
        StreamingDao streamingDao = new StreamingDao();
        System.out.println("=".repeat(10) + "관리자 메뉴" + "=".repeat(10));
        System.out.println("[1]영화 관리 [2]회원 관리 [3]광고 관리 [4]관리자 모드 종료");
        int num = sc.nextInt();
        switch (num) {
            case 1:
                MovieMaster();
                MasterMenu();
                break;
            case 2:
                UserMaster();
                MasterMenu();
                break;
            case 3:
                AdMaster();
                MasterMenu();
                break;
            case 4:
                LoginMenu();
                break;
            default:
                System.out.println("재입력하세요 .");
                MasterMenu();
                break;
        }
    }

    public void MovieMaster() {
        StreamingDao streamingDao = new StreamingDao();
        System.out.println("[1]영화 추가 [2]영화 삭제 [3] 돌아가기");
        int num = sc.nextInt();
        switch (num) {
            case 1:
                streamingDao.StreamingPlus();
                MovieMaster();
                break;
            case 2:
                streamingDao.StreamingDelete();
                MovieMaster();
                break;
            case 3:
                MasterMenu();
                break;
            default:
                System.out.println("재입력하세요 .");
                MovieMaster();
                break;
        }
    }

    public void UserMaster() {
        FubaoUserDao fubaoUserDao = new FubaoUserDao();
        System.out.println("[1]회원 추가 [2]회원 삭제 [3] 돌아가기");
        int num = sc.nextInt();
        switch (num) {
            case 1:
                fubaoUserDao.SignUp();
                UserMaster();
                break;
            case 2:
                fubaoUserDao.UserDelete();
                UserMaster();
                break;
            case 3:
                MasterMenu();
                break;
            default:
                System.out.println("재입력하세요 .");
                UserMaster();
                break;
        }
    }

    public void AdMaster() {
        System.out.println("[1]광고 추가 [2]광고 변경 [3]돌아가기");
        List<ADUserVo> a_list = aduserDao.AduserSelect();
        int num = sc.nextInt();
        switch (num) {
            case 1:
                aduserDao.AduserPrint(a_list);
                aduserDao.AdUserInsert();
                AdMaster();
                break;
            case 2:
                aduserDao.AduserPrint(a_list);
                AdMaster();
                break;
            case 3:
                MasterMenu();
                break;
            default:
                System.out.println("재입력하세요 .");
                AdMaster();
                break;
        }
    }

    public void MyReviewChoice(int s_num, String nick) {
        while (true) {
            reveiwDao.ReviewSelect(s_num, nick);
            System.out.print("[1]리뷰 추가 [2]리뷰 수정 [3]삭제 [4]상위 메뉴로");
            int num = sc.nextInt();
            switch (num) {
                case 1:
                    reveiwDao.TalkON(s_num, nick);//리뷰 추가
                    break;
                case 2:
                    reveiwDao.ReviewSelect(s_num, nick); // 영화 리뷰 리스트 보여주기
                    reveiwDao.TalkChange(nick);//리뷰 수정
                    break;
                case 3:
                    reveiwDao.ReviewSelect(s_num, nick); // 영화 리뷰 리스트 보여주기
                    reveiwDao.TalkDelete(nick);//리뷰 삭제
                    break;
                case 4:
                    MovieMenu(u_list);
                    break;
            }
        }
    }

    public void LDChoice(int s_num, String nick) {
        e:
        while (true) {
            likeDisDao.LikeDisCount(s_num);
            System.out.println("[1]좋아요 [2]싫어요 [3]나가기");
            int ld_num = sc.nextInt();
            switch (ld_num) {
                case 1:
                case 2:
                    likeDisDao.LikeDisChangeChoice2(ld_num, s_num, nick);
                case 3:
                    MovieMenu(u_list);
                    break e;
            }

        }
    }

}