import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;

public class Dialogue implements Runnable{

    private JFrame input_window;
    private JPanel entire_container;

    private JPanel text_area;

    private JPanel options_panel;
    private JButton upload_button;

    private Font font_in_use;
    private Color text_color_in_use;
    private TextReader text;

    private Thread job;
    private boolean job_flag;

    private boolean upload_flag;
    private BufferedImage upload_holder;

    public Dialogue(){
        //initialize the text style to use
        font_in_use = new Font(Font.DIALOG, Font.PLAIN, 40);
        text_color_in_use = new Color(242, 238, 0);
        text = new TextReader();

        //create JFrame
        input_window = new JFrame("Text Input");
        input_window.setBackground(Color.black);
        input_window.setAlwaysOnTop(true);

        //create the container that contains everything
        entire_container = new JPanel();
        entire_container.setBackground(Color.black);

        //create text area and add to Jframe
        text_area = new JPanel(){
            public void paint(Graphics g){
                //System.out.println("Repainting!");
                super.paint(g);
                g.setColor(text_color_in_use);
                g.setFont(font_in_use);
                text.draw(g, true);
            }
        };
        text_area.setPreferredSize(new Dimension(1280, 720));
        text_area.setBackground(Color.black);
        text_area.addKeyListener(text);
        text_area.addMouseListener(text);
        entire_container.add(text_area);

        //create the area where there can be options
        options_panel = new JPanel();
        options_panel.setBackground(Color.black);
        upload_button = new JButton("Upload!");
        upload_button.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e){}
            public void mouseEntered(MouseEvent e){}
            public void mouseExited(MouseEvent e){}
            public void mousePressed(MouseEvent e){
                upload_flag = true;
                upload_holder = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_ARGB);
                Graphics g = upload_holder.createGraphics();
                g.setColor(Color.black);
                g.fillRect(0, 0, 1280, 720);
                g.setColor(text_color_in_use);
                g.setFont(font_in_use);
                text.draw(g, false);
            }
            public void mouseReleased(MouseEvent e){}
        });
        options_panel.add(upload_button);
        entire_container.add(options_panel);

        //finalize JFrame 
        input_window.add(entire_container);
        input_window.pack();
        text_area.requestFocus();
        input_window.setResizable(false);
        input_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        input_window.setVisible(true);

        //will update the Jframe when needed
        job = new Thread(this);
        job_flag = true;
        job.start();
    }

    public boolean running(){
        return job_flag;
    }

    public BufferedImage getDisplay(){
        return upload_holder;
    }

    public JFrame window(){
        return input_window;
    }

    public boolean upload_flag(){ return upload_flag;}
    public void read_upload_flag(){upload_flag = false;}

    public void run(){
        while(job_flag){
            try{
                if(input_window.getFocusOwner() != text_area)
                    text_area.requestFocusInWindow();
                text_area.repaint();

                Thread.sleep(30);
            }catch(InterruptedException e){
                System.out.println(e);
            }
        }
    }

}