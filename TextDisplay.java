import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TextDisplay implements Runnable{
    private JFrame text_display;
    private JPanel canvas;

    private Dialogue d;

    private Thread job;

    public TextDisplay(Dialogue d){
        this.d = d;

        text_display = new JFrame("Text Display");
        text_display.setUndecorated(true);

        canvas = new JPanel(){
            public void paint(Graphics g){
                super.paint(g);
                g.drawImage(d.getDisplay(), 0, 0, null);
            }
        };
        canvas.setBackground(Color.black);
        canvas.setMinimumSize(new Dimension(1280, 720));
        canvas.setPreferredSize(new Dimension(1280, 720));
        text_display.add(canvas);
        text_display.pack();
        text_display.setLocationRelativeTo(d.window());
        show();

        job = new Thread(this);
        job.start();
    }

    public void terminate(){
        text_display.dispose();
    }

    public void show(){
        text_display.setVisible(true);
    }

    public void disappear(){
        text_display.setVisible(false);
    }

    public void run(){
        while(d.running()){
            try{
                text_display.setLocation(0, 0);
                text_display.repaint();
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        terminate();
    }
}