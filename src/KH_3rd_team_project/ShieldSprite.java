package KH_3rd_team_project;

import java.awt.*;

public class ShieldSprite extends Sprite{
    private GalagaGame game;
    private int shieldCount = 10;
    //보호막 생성자
    public ShieldSprite(GalagaGame game, Image image, int x, int y){
        super(image,x,y);//이미지,x,y를 생성자로 갖는 부모클래스의 변수 사용(Sprite)
        this.game = game;//갤러그게임 클래스
    }
    @Override
    public void move(){
        super.move();//부모클래스(Sprite)의 move메소드의 멤버변수 사용 x += dx;y += dy;
        //x의 값에 플레이어의 x위치를 대입 y의 값에 플레이어의 y위치를 대입 시켜 플레이어와 같이 이동하게 설정
        x = game.starship.getX()-15;
        y = game.starship.getY()-15;
    }
    @Override//충돌감지를 위한 오버라이딩
    public void handleCollision(Sprite other){
        //other가 적의 인스턴라면
        if (other instanceof LazerSprite){//instanceof : 형변환이 가능한지 논리값으로 리턴
            game.removeSprite(other);//적의 레이저와 닿았을 때 적의 레이저를 삭제한다
            shieldCount--;
            if (shieldCount == 0){
                game.removeSprite(this);
            }
        }
    }
}
