package KH_3rd_team_project;

import java.awt.Image;

public class LazerSprite extends Sprite {
    private GalagaGame game;
    public LazerSprite(GalagaGame game, Image image, int x, int y) {
        super(image, x, y);
        this.game = game;
        dx = 0;
        dy = 5;
    }
    @Override
    public void move() {
        if (y > 700) {//레이저가 하단과 충돌되면 삭제
            game.removeSpriteLazer(this);
        }
        super.move();
    }
    public void handleCollision(Sprite other) {
            //other가 우주선 인스턴스일 경우 충돌감지하여 두 객체 삭제
        if (other instanceof StarShipSprite) {
            game.removeSpriteLazer(this);
            game.endGame();
        }
    }
}