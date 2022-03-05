package KH_3rd_team_project;

import java.awt.*;

public class Sprite {
    protected  int x; // 현재 위치의 x좌표
    protected  int y; // 현재 위치의 y좌표
    protected int dx; // 단위시간에 움직이는 x방향 거리
    protected int dy; // 단위시간에 움직이는 y방향 거리
    private Image image; // 스프라이트가 가지고 있는 이미지

    //생성자
    public Sprite(Image image,int x,int y){
        this.image = image;
        this.x = x;
        this.y = y;
    }
    //스프라이트의 가로 길이를 반환한다
    public int getWidth() {
        return image.getWidth(null);
    }
    //스프라이트의 세로 길이를 반환한다
    public int getHeight(){
        return image.getHeight(null);
    }
    //스프라이트를 화면에 그린다
    public void draw(Graphics g){
        g.drawImage(image,x,y,null);
    }
    //스프라이트를 움직인다.
    public void move(){
        x += dx;
        y += dy;
    }
    // dx를 설정한다.
    public void setDx(int dx){
        this.dx = dx;
    }
    //dy를 설정한다
    public void setDy(int dy){
        this.dy = dy;
    }
    //dx를 반환한다
    public int getDx() {
        return dx;
    }
    //dy를 반환한다
    public int getDy() {
        return dy;
    }
    //x를 반환한다
    public int getX() {
        return x;
    }
    //y를 반환한다
    public int getY() {
        return y;
    }
    //다른 스프라이트와의 충돌 여부를 계산한다. 충돌이면 true를 반환한다.
    public boolean checkCollision(Sprite other) {
        Rectangle myRect = new Rectangle();
        Rectangle otherRect = new Rectangle();
        //나의 위치에 사각형 영역 위치시킴
        myRect.setBounds(x,y,getWidth(),getHeight());
        //적의 위치에 사각형 영역 위치시킴
        otherRect.setBounds(other.getX(),other.getY(),other.getWidth(),other.getHeight());
        //myRect가 otherRect와 교차하는지 확인( 교차 = 충돌 )
        return myRect.intersects(otherRect);//나의 사각형이 다른 사각형과 교집합이 될경우 트루 리턴
    }
    //충돌 이벤트 처리 메소드
    public void handleCollision(Sprite other){
    }
}
