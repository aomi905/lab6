package bsu.rfact.java.laba6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Field extends JPanel {
    private boolean paused;
    private List<BouncingBall> balls = new ArrayList<>(10);

    private Timer repaintTimer = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    });

    public Field(){
        setBackground(Color.WHITE);
        repaintTimer.start();
    }

    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Graphics2D canvas = (Graphics2D) graphics;
        for (BouncingBall ball : balls){
            ball.paint(canvas);
        }
    }
    public void addBall(){
        balls.add(new BouncingBall(this));
    }

    public synchronized void pause(){
        paused = true;
    }

    public synchronized void resume(){
        paused = false;
        notifyAll();
    }

    public synchronized void canMove(BouncingBall bouncingBall) throws InterruptedException{
        if (paused){
            wait();
        }
    }
}
