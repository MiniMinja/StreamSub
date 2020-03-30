import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TextDisplay implements Runnable{
    private static final BufferedImage blank_image = makeBlank();

    public static BufferedImage makeBlank(){
        BufferedImage image = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.createGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, 1280, 720);
        return image;
    }

    private JFrame text_display;
    private JPanel canvas;

    private Dialogue d;

    private Thread job;

    private boolean display_flag;
    private BufferedImage last_image;
    private double last_tick;

    public TextDisplay(Dialogue d){
        this.d = d;

        text_display = new JFrame("Text Display");
        text_display.setUndecorated(true);

        canvas = new JPanel(){
            public void paint(Graphics g){
                super.paint(g);
                if(display_flag) {
                    g.drawImage(last_image, 0, 0, null);
                }
                else {
                    g.drawImage(blank_image, 0, 0, null);
                }
            }
        };
        canvas.setBackground(Color.black);
        canvas.setMinimumSize(new Dimension(1280, 720));
        canvas.setPreferredSize(new Dimension(1280, 720));
        text_display.add(canvas);
        text_display.pack();
        text_display.setLocationRelativeTo(d.window());
        show();

        display_flag = false;

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

    public void new_text(){
        last_image = d.getDisplay();    
    }

    public void reset_timer(){
        display_flag = true;
        last_tick = System.nanoTime() / 1e9;
    }

    public void run(){
        last_tick = System.nanoTime() / 1e9;
        while(d.running()){
            try{
                double current_time = System.nanoTime() / 1e9;
                if(current_time - last_tick > 10){
                    display_flag = false;
                }
                new_text();
                if(d.upload_flag()){
                    reset_timer();
                    d.read_upload_flag();
                }
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