package KH_3rd_team_project;

public class Score {
    public int score = 0;
    public void scoreplus(){
        score += 5;//2번씩 호출되므로 증가값x2가 입력됨
    }
    public int getScore(){
        return this.score;
    }
}
