package KH_3rd_team_project;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;

class endFrame extends JFrame implements ActionListener {
    private JButton okayBt;
    private JTextField tf;
    private BufferedImage gameOver;

    private String playerName;

    static int SCORE;
    static int STAGE;

    endFrame() {
        setTitle("GalagaGame");
        setUndecorated(true);
        setResizable(false);
        setLayout(null);
        setBounds(100, 100, 780, 580);
        setLocationRelativeTo(null);

        try {
            gameOver = ImageIO.read(new File("images/gameover.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        tf = new JTextField(20);
        tf.setBounds(300, 480, 180, 50);
        tf.addActionListener(this);


        okayBt = new JButton("확인");
        okayBt.setBounds(500, 480, 70, 50);
        okayBt.addActionListener(this);

        add(tf);
        add(okayBt);

        setVisible(true);
    }
    @Override
    public void paint(Graphics g){
        g.drawImage(gameOver,100,100,null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        playerName = tf.getText();
        try (   //랭킹으로 사용할 Ranking.txt파일을 생성합니다
                FileWriter fw = new FileWriter("images/Ranking.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);//입력용 버퍼입니다
        ) {
            bw.write(playerName);//이름을 파일에 작성합니다
            bw.newLine();//칸을 한 칸 내립니다
            bw.write(Integer.toString(SCORE));//스코어를 작성합니다
            bw.newLine();
            bw.write(Integer.toString(STAGE));//스테이지를 작성합니다
            bw.newLine();
            bw.flush();
        } catch (IOException ie) {
            System.out.println(ie);
        }
        new Ranking();//작성 후 랭킹 페이지를 불러옵니다
    }

}