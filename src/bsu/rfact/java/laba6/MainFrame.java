package bsu.rfact.java.laba6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;
    private JMenuItem pauseMenuItem;
    private JMenuItem resumeMenuItem;
    private Field field = new Field();

    public MainFrame(){
        super("Thread synchronising");
        setSize(WIDTH, HEIGHT);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        setLocation((toolkit.getScreenSize().width - WIDTH) / 2,
                (toolkit.getScreenSize().height - HEIGHT) / 2);
        setExtendedState(MAXIMIZED_BOTH);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu ballMenu = new JMenu("Balls");
        Action addBallAction = new AbstractAction("Add ball") {
            @Override
            public void actionPerformed(ActionEvent e) {
                field.addBall();
                if (!pauseMenuItem.isEnabled() && !resumeMenuItem.isEnabled()){
                    pauseMenuItem.setEnabled(true);
                }
            }
        };
        menuBar.add(ballMenu);
        ballMenu.add(addBallAction);

        JMenu controlMenu = new JMenu("Control");
        menuBar.add(controlMenu);
        Action pauseAction = new AbstractAction("Pause"){
            public void actionPerformed(ActionEvent event) {
                field.pause();
                pauseMenuItem.setEnabled(false);
                resumeMenuItem.setEnabled(true);
            }
        };
        pauseMenuItem = controlMenu.add(pauseAction);
        pauseMenuItem.setEnabled(false);
        Action resumeAction = new AbstractAction("Resume") {
            public void actionPerformed(ActionEvent event) {
                field.resume();
                pauseMenuItem.setEnabled(true);
                resumeMenuItem.setEnabled(false);
            }
        };
        resumeMenuItem = controlMenu.add(resumeAction);
        resumeMenuItem.setEnabled(false);

        getContentPane().add(field, BorderLayout.CENTER);
    }

    public static void main(String[] args){
        MainFrame mainFrame = new MainFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }
}
