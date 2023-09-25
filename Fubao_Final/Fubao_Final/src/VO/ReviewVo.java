package VO;

public class ReviewVo {
  private int s_number;
  private String nick;
  private int t_number;
  private String talk;

  public ReviewVo(String nick, int t_number, String talk) {
    this.nick = nick;
    this.t_number = t_number;
    this.talk = talk;
  }

  public int getS_number() {
    return s_number;
  }

  public void setS_number(int s_number) {
    this.s_number = s_number;
  }

  public String getNick() {
    return nick;
  }

  public void setNick(String nick) {
    this.nick = nick;
  }

  public int getT_number() {
    return t_number;
  }

  public void setT_number(int t_number) {
    this.t_number = t_number;
  }

  public String getTalk() {
    return talk;
  }

  public void setTalk(String talk) {
    this.talk = talk;
  }
}