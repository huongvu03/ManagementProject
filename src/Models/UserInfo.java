package Models;

import java.util.Date;

public class UserInfo {
    private String userId, userName, userPassWord, userPosition, question,answer;
    private Date userDate;

    public UserInfo() {
    }

    public UserInfo(String userId, String userName, String userPassWord, String userPosition, String question, String answer, Date userDate) {
        this.userId = userId;
        this.userName = userName;
        this.userPassWord = userPassWord;
        this.userPosition = userPosition;
        this.question = question;
        this.answer = answer;
        this.userDate = userDate;
    }

    @Override
    public String toString() {
        return "UserInfo{" + "userId=" + userId + ", userName=" + userName + ", userPassWord=" + userPassWord + ", userPosition=" + userPosition + ", question=" + question + ", answer=" + answer + ", userDate=" + userDate + '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassWord() {
        return userPassWord;
    }

    public void setUserPassWord(String userPassWord) {
        this.userPassWord = userPassWord;
    }

    public String getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(String userPosition) {
        this.userPosition = userPosition;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getUserDate() {
        return userDate;
    }

    public void setUserDate(Date userDate) {
        this.userDate = userDate;
    }


    
}
