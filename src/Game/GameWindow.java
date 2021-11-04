package Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;


public class GameWindow extends JFrame {
    private static GameWindow gameWindow;
    private static Image backGround;
    private static Image drop;
    private static Image gameOver;
    private static Image restart;
    private static long lastFrameTime;
    private static float dropY = -150;
    private static float dropX;
    private static float dropV = 220;
    private static int score = 0;
    private static float restartX = 420;
    private static float restartY = 400;

    public static void main(String[] args) throws IOException {
        backGround = ImageIO.read(GameWindow.class.getResourceAsStream("/Icons/back_ground.jpg"));
        drop = ImageIO.read(GameWindow.class.getResourceAsStream("/Icons/burger.png"));
        gameOver = ImageIO.read(GameWindow.class.getResourceAsStream("/Icons/game_over.png"));
        restart = ImageIO.read(GameWindow.class.getResourceAsStream("/Icons/restart.png"));
        gameWindow = new GameWindow();
        JLabel record = new JLabel("Рекорд: " + score);
        record.setSize(220, 150);
        record.setPreferredSize(new Dimension(100,20));
        record.setFont(new Font("Рекорд" + score, Font.PLAIN, 19));
        record.setOpaque(true);
        record.setBackground(Color.WHITE);
        gameWindow.setSize(900,600);
        gameWindow.setResizable(false);
        lastFrameTime = System.nanoTime();
        gameWindow.setTitle("Бургеры от Sheriff Venom");
        gameWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
        gameWindow.setLocation(200,150);

        GameField gameField = new GameField();
        gameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                float dropXRight = dropX + drop.getWidth(null);
                float dropTButton = dropY + drop.getHeight(null);
                boolean isDrop = x >= dropX && x <= dropXRight && y >= dropY && y <= dropTButton;

               if (isDrop){
                   dropY = -100;
                   dropX = (int) (Math.random() * (gameField.getWidth() - drop.getHeight(null)));
                   gameWindow.setTitle("Рекорд: " + score);
                   record.setText("Рекорд: " + score);
                   score++;
                   dropV = dropV + 20;
               }
               else if (isDrop == false){
                   if (score <= 0){
                       score--;
                   }

               }
               float restartXLeft = restartX + restart.getWidth(null);
               float restartYButton = restartY + restart.getHeight(null);
               boolean ifRestart = x >= restartX && x <= restartXLeft && y >= restartY && y <= restartYButton;
               if (ifRestart){
                   dropY = -100;
                   dropX = ((int)(Math.random()) * (gameField.getWidth() - drop.getHeight(null)));
                   score = 0;
                   dropV = 200;
                   gameWindow.setTitle("Рекорд: " + score);
                   record.setText("Рекорд: " + score);


               }
            }
        });
        gameWindow.add(gameField);
        gameField.add(record);
        gameWindow.setVisible(true);



    }

    public static void onRepaint(Graphics graphics){
        graphics.drawImage(backGround, 0, 0, null);
        long currentTime = System.nanoTime();
        float deltaTime = (currentTime - lastFrameTime) * 0.000000001f;
        lastFrameTime = currentTime;
        dropY = dropY + dropV * deltaTime;
        graphics.drawImage(drop,(int) dropX, (int) dropY, null);

        if (dropY > gameWindow.getHeight()){
            graphics.drawImage(gameOver, 70, 50, null);
            graphics.drawImage(restart, (int) restartX, (int) restartY, null);
        }
    }
    private static class GameField extends  JPanel{
        @Override
        protected void paintComponent(Graphics graphics){
            super.paintComponent(graphics);
            onRepaint(graphics);
            repaint();

        }

    }
}

