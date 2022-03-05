package KH_3rd_team_project;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class GalagaGame extends JPanel implements KeyListener {
    private boolean running = true;//게임 진행 상태
    //적기 관련
    private ArrayList sprites = new ArrayList();
    protected Sprite starship;//플레이어의 x,y좌표를 쉴드에서 사용하기 위해 protected로 설정
    //이미지 관련
    private BufferedImage alienImage;
    private BufferedImage shotImage;
    private BufferedImage shipImage;
    private BufferedImage background;
    private BufferedImage lazerImage;
    private BufferedImage lifeImage;
    private BufferedImage multiMissile;
    private BufferedImage shieldImage;
    //오디오 관련
    private Clip clip;
    private AudioInputStream audioInputStream;
    private File audioBgm;
    private File audioShot;
    private File audioExplode;
    private File audioShield;
    //게임 카운트 관련
    static int stage = 1; // 스테이지 카운트 변수, 결과출력에서 사용하기 위해 static으로 설정
    private int freeFireCount = 1; // 필살기 카운트
    private int shieldCount = 1; // 보호막 카운트
    private int hp = 6; // 플레이어 목숨
    private static int count = 0; // 레이저 쏘는 타이밍 조절
    Score scoreP = new Score();//스코어 클래스 객체생성

    public GalagaGame() {
        //제목설정한 프레임 생성자 객체
        JFrame frame = new JFrame("Galaga Game");

        frame.setSize(800,600);//사이즈설정
        frame.add(this);//this는 패널(이클래스)
        frame.setResizable(false);//창크기 조절 불가
        frame.setVisible(true);//보이게 설정
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//창 닫으면 프로세스 종료
        frame.setLocationRelativeTo(null);//창을 화면 중앙으로 배치

        try {//버퍼이미지 변수에 해당 이미지들 삽입
            shotImage = ImageIO.read(new File("images/missile00.png"));
            multiMissile = ImageIO.read(new File("images/missile.png"));
            shipImage = ImageIO.read(new File("images/player.png"));
            lifeImage = ImageIO.read(new File("images/life.png"));
            alienImage = ImageIO.read(new File("images/enemy.png"));
            background = ImageIO.read(new File("images/background.jpeg"));
            lazerImage = ImageIO.read(new File("images/lazer.png"));
            shieldImage = ImageIO.read(new File("images/shield.png"));
        }catch (IOException e){//read사용을위한 예외처리
            e.printStackTrace();
        }
        this.requestFocus();//패널에 키보드입력감지를 위한 포커스
        this.initSprites();//패널 초기화
        addKeyListener(this);//패널에 키리스너 추가
        //오디오 파일 객체생성 , getAbsoluteFile은 절대 경로를 가지게 하는 것
        audioBgm = new File("images/spacewar.wav").getAbsoluteFile();
        audioShot = new File("images/shot.wav").getAbsoluteFile();
        audioExplode = new File("images/explode.wav").getAbsoluteFile();
        audioShield = new File("images/shield.wav").getAbsoluteFile();
        playSound();
        startGame();
    }
    private void initSprites() {//초기 설정 메소드
    	freeFireCount = 1;
        starship = new StarShipSprite(this, shipImage, 370, 500);//플레이어 위치와이미지포함 생성
        sprites.add(starship);//플레이어 추가
        for (int y=0;y<5;y++){//5행
            for (int x=0; x<12; x++){//12열
                Sprite alien = new AlienSprite(this, alienImage,100+(x*50), (0)+y*30);
                sprites.add(alien);
            }
        }
    }
    private void startGame() {//스테이지 증가에 따른 초기화 메소드
        sprites.clear();
        initSprites();
    }
    public void reset() {
        boolean flag = true;//리셋 기준
        for (int i = 0; i < sprites.size(); i++) {//적 수만큼 반복
            if (sprites.get(i) instanceof AlienSprite) {//수가0이라면
                flag = false;//거짓으로 바꾸고
                break;//반복문 탈출
            }
        }
        if (flag) {
            stage++;//스테이지를 1증가시키고
            shieldCount++;//쉴드 카운트 1증가
            startGame();//게임을 재시작
        }
    }
    public void endGame() {//게임종료 메소드
        hp--;
        if (hp == 0){
            if (stage==1 && scoreP.getScore()==0){//시작과 같이 증가값이 없을 경우
                JOptionPane.showMessageDialog(null,"스테이지 : "
                        +stage+"\n점수 : "+ scoreP.getScore()+"\n ㅋㅋ","ㅋㅋ",JOptionPane.INFORMATION_MESSAGE);

            }else {//1점이라도 오른다면
                JOptionPane.showMessageDialog(null,"스테이지 : "
                        +stage+"\n점수 : "+ scoreP.getScore(),"대단해요!",JOptionPane.INFORMATION_MESSAGE);
                endFrame.SCORE = scoreP.score;
                endFrame.STAGE = stage;
            }
            endFrame end = new endFrame();
        }


    }
    public void removeSprite(Sprite sprite){
        sprites.remove(sprite);//적 삭제
        scoreP.scoreplus();
    }
    public void removeSpriteLazer(Sprite sprite) {
        sprites.remove(sprite);
    }
    public void fire() {//미사일 발사 메소드
        //생성자 객채 생성 this는 ShotSprite에서 설정한 game
        ShotSprite shot = new ShotSprite(this,shotImage, starship.getX()+10, starship.getY()-30);
        sprites.add(shot);
        shotSound();
    }
    public void shield() {//보호막 생성 메소드
        if (shieldCount >= 1){//카운트가 존재해야 사용 가능
            ShieldSprite shieldSprite = new ShieldSprite(this,shieldImage, starship.getX()-15, starship.getY()-15);
            sprites.add(shieldSprite);//보호막 객체생성 및 추가
            shieldSound();
            shieldCount--;
        }
    }
    public void freeFire(){//필살기 메소드 특정좌표마다 6개씩 발표X4
        if (freeFireCount == 1){
            for (int z=0;z<6;z++){
                ShotSprite shot2 = new ShotSprite(this,multiMissile,50,600+(z*40));
                sprites.add(shot2);
            }
            for (int z=0;z<6;z++){
                ShotSprite shot3 = new ShotSprite(this,multiMissile,250,600+(z*40));
                sprites.add(shot3);
            }
            for (int z=0;z<6;z++){
                ShotSprite shot4 = new ShotSprite(this,multiMissile,430,600+(z*40));
                sprites.add(shot4);
            }
            for (int z=0;z<6;z++){
                ShotSprite shot5 = new ShotSprite(this,multiMissile,600,600+(z*40));
                sprites.add(shot5);
            }
            freeFireCount--;
        }


    }
    public void lazer() {
        for (int i = 0; i < (stage*4) + 1; i++) {//스테이지가 늘어날수록 반복 증가
            if (count == 100) {//카운트가 100이되면
                int j = (int) (1 + (Math.random() * (sprites.size())));//j에 난수를 대입
                for (int k = 0; k < sprites.size(); k++) {//적의 수 만큼 반복
                    if (k == j) { //난수와 k가 같아질 때
                        Sprite sprite = (Sprite) sprites.get(j);//적기들을 형변환
                        //sprite객체가 AlienSprite클래스형변환이 가능하다면(상,하위 클래스가 맞다면)
                        if (sprite instanceof AlienSprite) {//레이저 객체를 적기+좌표에 생성
                            LazerSprite lazer = new LazerSprite(this,
                                    lazerImage,
                                    sprite.x + 15, sprite.y + 30);
                            sprites.add(lazer);
                        }
                    }
                }
                count = 0;//너무 많이 생기지 않게 카운트를 초기화
            } else//카운트가 100이 될 때까지 계속 카운트 증가
                count++;
        }
    }
    @Override
    public void paint(Graphics g){
        super.paint(g);//부모도 그릴 수 있게 설정
        g.drawImage(background,0,0,null);
        g.setColor(Color.white);//하얀색으로 칠함
        for (int i =0;i<sprites.size();i++){//수많은 적 그리기
            Sprite sprite = (Sprite) sprites.get(i);
            sprite.draw(g);
        }
        g.drawString("스테이지 : " + stage,700,20);//스테이지 문구와 변수 위치 설정
        g.drawString("점수 : " + scoreP.score,700,40);
        g.drawString("필살기 : "+ freeFireCount,700,60);
        g.drawString("보호막 : "+ shieldCount,700,80);
        g.drawString("HP : ",20,20);
        if (hp==6){//목숨카운트가 6일경우 목숨 3개 표시
            g.drawImage(lifeImage,20,30,null);
            g.drawImage(lifeImage,60,30,null);
            g.drawImage(lifeImage,100,30,null);
        }else if (hp ==4){//목숨카운트가 4일경우 목숨 2개 표시
            g.drawImage(lifeImage,20,30,null);
            g.drawImage(lifeImage,60,30,null);
        }else {//이 외에 목숨카운트 1개만 표시
            g.drawImage(lifeImage,20,30,null);
        }
    }
    public void gameLoop(){//플레이어를 제외한 모든 오브젝트를 이동시킴
        while (running){
            for (int i=0; i<sprites.size(); i++){
                //ArrayList의 모든 요소를 가져와서 Sprite타입(슈퍼클래스)으로 변환
                Sprite sprite = (Sprite) sprites.get(i);
                sprite.move();
            }//두 스프라이트의 충돌을 확인
            for (int p =0;p<sprites.size();p++){
                for (int s =0;s<sprites.size();s++){
                    Sprite me = (Sprite) sprites.get(p);
                    //s번째 인덱스의 스프라이트를 가져옴
                    Sprite other = (Sprite) sprites.get(s);
                    //s번째 +1번째 인덱스부터의 스프라이트를 가져옴
                    if (me.checkCollision(other)){
                        me.handleCollision(other);
                        other.handleCollision(me);
                    }
                }
            }
            lazer();
            repaint();
            reset();
            try {//0.01초 슬립을주어 0.01초마다 작동하게 설정
                Thread.sleep(10);
            }catch (Exception e){
            }
        }
    }
    @Override//키 이벤트 발생시 해당 키일경우 이동정의(이동)
    public void keyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            starship.setDx(-3);
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            starship.setDx(+3);
        if (e.getKeyCode() == KeyEvent.VK_UP)
            starship.setDy(-3);
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
            starship.setDy(+3);
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            fire();
        if (e.getKeyCode() == KeyEvent.VK_C)
            freeFire();
        if (e.getKeyCode() == KeyEvent.VK_X)
            shield();
    }
    @Override//키 이벤트 발생시 해당 키일경우 이동정의(키를 떼었을 때 이동하지 않게)
    public void keyReleased(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            starship.setDx(0);
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            starship.setDx(0);
        if (e.getKeyCode() == KeyEvent.VK_UP)
            starship.setDy(0);
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
            starship.setDy(0);
    }
    @Override
    public void keyTyped(KeyEvent arg0){
    }
    public void playSound() {
        try {//java.sound.sampled 패키지 , SourceDataLine는 긴 음악에 사용할 때 메모리 최적화가 좋음
            audioInputStream = AudioSystem//오디오입력스트림에 오디오 파일 대입
                    .getAudioInputStream(audioBgm);
            clip = AudioSystem.getClip();//Clip : 짧은 사운드 파일을 사용할 때 효과적
            clip.open(audioInputStream);//클립에서 입력스트림 열기
            clip.start();//클립을통한 시작
            if (true)clip.loop(-1);//무한반복재생
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
    public void shotSound() {
        try {
            audioInputStream = AudioSystem
                    .getAudioInputStream(audioShot);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
    public void explodeSound() {
        try {
            audioInputStream = AudioSystem
                    .getAudioInputStream(audioExplode);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
    public void shieldSound() {
        try {
            audioInputStream = AudioSystem
                    .getAudioInputStream(audioShield);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
    //실행문
    public static void main(String[] args) {
        GalagaGame g = new GalagaGame();
        g.gameLoop();
    }
}