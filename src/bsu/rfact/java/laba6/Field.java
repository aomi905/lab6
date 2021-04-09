package bsu.rfact.java.laba6;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

public class Field extends JPanel {
    private boolean paused;
    private boolean selectBall;
    private double startTime;
    private double endTime;
    private double startX;
    private double endX;
    private double startY;
    private double endY;
    private static double time;
    private BouncingBall initBall = null;
    private List<BouncingBall> balls = new ArrayList<>(10);

    private Timer repaintTimer = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            time++;
            repaint();
        }
    });

    public Field() {
        setBackground(Color.WHITE);
        addMouseListener(new MouseHandler());
        addMouseMotionListener(new MouseMotionHandler());
        repaintTimer.start();
        selectBall = false;
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D canvas = (Graphics2D) graphics;
        for (BouncingBall ball : balls) {
            ball.paint(canvas);
        }
    }

    public void addBall() {
        balls.add(new BouncingBall(this));
    }

    public synchronized void pause() {
        paused = true;
    }

    public synchronized void resume() {
        paused = false;
        notifyAll();
    }

    public synchronized void canMove(BouncingBall bouncingBall) throws InterruptedException {
        if (paused) {
            wait();
        }
    }

    public class MouseHandler extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            for (BouncingBall ball : balls) {
                Ellipse2D.Double newBall = new Ellipse2D.Double(
                        ball.getX() - ball.getRadius(), ball.getY() - ball.getRadius(),
                        2 * ball.getRadius(), 2 * ball.getRadius());
                if (newBall.contains(e.getPoint())) {
                    pause();
                    startTime = time;
                    initBall = ball;
                    startX = e.getX();
                    startY = e.getY();
                    selectBall = true;
                    break;
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);
            if (selectBall) {
                endTime = time;
                endX = e.getX();
                endY = e.getY();
                if ((endX - startX) != 0 || (endY - startY) != 0) {
                    initBall.setSpeedX((endX - startX) / (endTime - startTime));
                    initBall.setSpeedY((endY - startY) / (endTime - startTime));
                }
                resume();
                selectBall = false;
            }
        }
    }

    public class MouseMotionHandler implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
            if (selectBall) {
                initBall.setX(e.getX());
                initBall.setY(e.getY());
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            throw new NotImplementedException();
        }
    }

}
