package KH_3rd_team_project;

import java.awt.*;
//우주선 클래스
public class StarShipSprite extends Sprite{
    private GalagaGame game;

    public StarShipSprite(GalagaGame game, Image image, int x, int y){
        super(image, x, y);//이미지,x,y를 생성자로 갖는 부모클래스의 변수 사용(Sprite)
        this.game = game;
        dx = 0;//좌우이동속도 0으로 설정
        dy = 0;//상하이동속도 0으로 설정
    }
    @Override//우주선 이동 재정의
    public void move(){
        if ((dx<0) && (x<10)){//왼쪽벽에 위치하고 이동속도가 0보다 작을경우
            return;//움직임을 제한(진행을 멈춤,그 위치에 있게 함)
        }
        if ((dx>0) && (x>800)){//오른쪽벽보다 적게 위치하고 이동속도가 0보다 클 경우
            return;//움직임을 제한(진행을 멈춤,그 위치에 있게 함)
        }
        if ((dy>0) && (y>530)){//아래의 벽을 설치
            return;
        }
        super.move();//부모의 메소드의 멤버변수 x += dx;y += dy;갖고 옴
    }
    @Override
    public void handleCollision(Sprite other){//other은 적
        //other가 에일리언의 인스턴스 또는 레이저의 인스턴스일 경우
        if (other instanceof AlienSprite || other instanceof LazerSprite) {
            game.endGame();
        }
    }
}
