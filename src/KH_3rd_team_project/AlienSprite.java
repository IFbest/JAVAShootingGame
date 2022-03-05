package KH_3rd_team_project;

import java.awt.*;
//적 클래스
public class AlienSprite extends Sprite{
    private GalagaGame game;
    //적 이동속도 설정
    public AlienSprite(GalagaGame game, Image image,int x,int y){
        super(image, x, y);//이미지,x,y를 생성자로 갖는 부모클래스의 변수 사용(Sprite)
        this.game = game;//갤러그게임 클래스
        dx = -6;//움직일 x값(적의 이동속도)
    }
    @Override//적 움직임 재정의
    public void move(){//이동속도가 -1이하(왼쪽움직임) x10이하(왼쪽벽) ,
        // 이동속도가 1이상(오른쪽움직임)이고 x800(오른쪽벽)
        if (((dx<0) && (x<10)) || ((dx >0) && (x>800))){
            dx = -dx;//이동속도를 0으로 만든다는 의미,or조건이기 때문에 벽에서 튕겨나가는 역할
            y += 10;//한 칸 아래로 이동하게 됨
            if (y>550){//적기(Alien)가 아래 밑에 도달하면
                game.endGame();//게임오버
            }
        }
        super.move();//부모의 메소드의 멤버변수 x += dx;y += dy;갖고 옴
    }
}
