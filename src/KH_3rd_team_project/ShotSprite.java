package KH_3rd_team_project;

import java.awt.*;
//미사일 클래스
public class ShotSprite extends Sprite{
    private GalagaGame game;
    //미사일 속도 설정
    public ShotSprite(GalagaGame game,Image image,int x,int y){
        super(image,x,y);//이미지,x,y를 생성자로 갖는 부모클래스의 변수 사용(Sprite)
        this.game = game;//갤러그게임 클래스
        dy = -6;
    }
    @Override//움직임을 제한하기위한 재정의(y값이 -100되면 미사일 삭제)
    public void move(){
        super.move();//부모클래스(Sprite)의 move메소드의 멤버변수 사용 x += dx;y += dy;
        //y += dy; 에 의해 y값에 위에설정한 dy = -6 의 값으로 계속 작동하게 의
        if (y < -100){
            game.removeSprite(this);//미사일을 삭제하게함
        }
    }
    @Override//충돌감지를 위한 오버라이딩
    public void handleCollision(Sprite other){
        //other가 적의 인스턴라면
        if (other instanceof AlienSprite){//instanceof : 형변환이 가능한지 논리값으로 리턴
            game.removeSprite(this);//적과 닿았을 때 미사일을 삭제하고
            game.removeSprite(other);//적과 닿았을 때 적을 삭제한다
            game.explodeSound();
        }
    }
}
