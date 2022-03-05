package KH_3rd_team_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class Ranking extends JFrame{
    //Y 값만 랭크에 따라 바뀝니다
    private final int X_RANK = 150;
    private final int X_NAME = 250;
    private final int X_SCORE = 350;
    private final int X_STAGE = 450;
    private final int Y = 75;
    private final int WIDTH_LABEL = 100;
    private final int HEIGHT_LABEL = 50;


    private JButton endBt;
    private JLabel labelRank, labelName, labelistcore, labelStage;

    private ArrayList list = new ArrayList();

    Ranking() {
        setTitle("Ranking");
        setUndecorated(true);
        setResizable(false);
        setLayout(null);
        setBounds(100, 100, 800, 600);
        setLocationRelativeTo(null);

        endBt = new JButton("게임종료");
        endBt.setFont(new Font(null, 0, 20));
        endBt.setBounds(330, 500, 150, 100);
        endBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        printRanking();
        add(endBt);
        setVisible(true);
    }

    private void printRanking() {
        printRankingPaint();
        printRank();
    }

    private void printRankingPaint() {
        labelRank = new JLabel("순위");
        labelRank.setBounds(X_RANK, Y, WIDTH_LABEL, HEIGHT_LABEL);
        add(labelRank);

        labelName = new JLabel("이름");
        labelName.setBounds(X_NAME, Y, WIDTH_LABEL, HEIGHT_LABEL);
        add(labelName);

        labelistcore = new JLabel("점수");
        labelistcore.setBounds(X_SCORE, Y, WIDTH_LABEL, HEIGHT_LABEL);
        add(labelistcore);

        labelStage = new JLabel("스테이지");
        labelStage.setBounds(X_STAGE, Y, WIDTH_LABEL, HEIGHT_LABEL);
        add(labelStage);

    }

    private void printRank() {
        try (FileReader fr = new FileReader("images/Ranking.txt");
             BufferedReader br = new BufferedReader(fr);)
        {// 공백값이 없을때까지 글자를 불러옵니다(랭킹에 등록된 모든 정보 입력)
            String readLine = null;
            while ((readLine = br.readLine()) != null) {
                list.add(readLine);// 리스트에 파일의 정보들을 추가합니다
            }
        } catch (IOException e) {
        }
        //리스트에서 스코어 값을 가져옵니다
        ArrayList<Integer> scoreList = new ArrayList<Integer>();
        for (int i = 1; i <= list.size() / 3; i++) {
            scoreList.add(Integer.valueOf((String) list.get(3 * i - 2)));
        }
        Collections.sort(scoreList); //정렬
        //리스트에서 플레이어 이름 값을 가져옵니다
        ArrayList<String> scoreList2 = new ArrayList<String>();
        for (int i = 0; i < scoreList.size(); i++) { //int타입 -> string타입
            scoreList2.add(String.valueOf(scoreList.get(i)));
        }
        //랭크를 정렬하기위한 로직입니다
        int rank = 0;
        for (int i = scoreList2.size(); i >= 1; i--) { //배열의 크기만큼 반복(크기가 1이 될 때까지)
            int x = list.indexOf(scoreList2.get(i - 1)); //list의 인덱스값 1, 4,7..찾기
            rank++;
            makeArray(x, rank);
        }
    }

    private void makeArray(int x, int rank) { // x는 스코어가 높은 점수대로 넣어줍니다
        genName(x - 1, rank);
        genScore(x, rank);
        genStage(x + 1, rank);
        genRank(Integer.toString(rank), rank);
    }

    private void genRank(String number, int rank) {
        labelRank = new JLabel(number);
        labelRank.setBounds(X_RANK, Y + 25 * rank, WIDTH_LABEL, HEIGHT_LABEL);
        add(labelRank);
    }

    private void genName(int index, int rank) {
        labelName = new JLabel((String) list.get(index));
        labelName.setBounds(X_NAME, Y + 25 * rank, WIDTH_LABEL, HEIGHT_LABEL);
        add(labelName);
    }

    private void genScore(int index, int rank) {
        labelistcore = new JLabel((String) list.get(index));
        labelistcore.setBounds(X_SCORE, Y + 25 * rank, WIDTH_LABEL, HEIGHT_LABEL);
        add(labelistcore);
    }

    private void genStage(int index, int rank) {
        labelStage = new JLabel((String) list.get(index));
        labelStage.setBounds(X_STAGE, Y + 25 * rank, WIDTH_LABEL, HEIGHT_LABEL);
        add(labelStage);
    }
}
