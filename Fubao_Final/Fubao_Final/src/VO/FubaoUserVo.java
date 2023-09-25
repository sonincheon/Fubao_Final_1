package VO;
import java.sql.Date;
public class FubaoUserVo {
    private  String uname;
    private String gender;
    private Date birth;
    private String contact;
    private String nick;
    private String id;
    private String password;
    private Date enter_date;
    private int token;
    public FubaoUserVo() {

    }
    public FubaoUserVo(String uname, String gender, Date birth, String contact, String nick, String id, String password, Date enter_date, int token) {
        this.uname = uname;
        this.gender = gender;
        this.birth = birth;
        this.contact = contact;
        this.nick = nick;
        this.id = id;
        this.password = password;
        this.enter_date = enter_date;
        this.token = token;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getEnter_date() {
        return enter_date;
    }

    public void setEnter_date(Date enter_date) {
        this.enter_date = enter_date;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }
}
