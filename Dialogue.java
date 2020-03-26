import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Dialogue implements Runnable{

    private JFrame input_window;
    private JPanel entire_container;
    private JPanel text_area;
    private JPanel options_panel;

    private Font font_in_use;
    private Color text_color_in_use;
    private TextReader text;

    private Thread job;
    private boolean job_flag;

    public Dialogue(){
        //initialize the text style to use
        font_in_use = new Font(Font.DIALOG, Font.PLAIN, 20);
        text_color_in_use = Color.WHITE;
        text = new TextReader();

        //create JFrame
        input_window = new JFrame("Text Input");

        //create the container that contains everything
        entire_container = new JPanel();

        //create text area and add to Jframe
        text_area = new JPanel(){
            public void paint(Graphics g){
                //System.out.println("Repainting!");
                super.paint(g);
                g.setColor(text_color_in_use);
                g.setFont(font_in_use);
                int startX = 50;
                int startY = 50;
                int lineSize = 30;
                ArrayList<StringBuilder> text_data = text.getText();
                for(int i = 0;i<text_data.size();i++)
                    g.drawString(text_data.get(i).toString(), startX, startY + lineSize*i);
            }
        };
        text_area.setPreferredSize(new Dimension(1280, 720));
        text_area.setBackground(Color.black);
        text_area.addKeyListener(text);
        entire_container.add(text_area);

        //create the area where there can be options
        options_panel = new JPanel();
        
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

    public void run(){
        while(job_flag){
            try{
                text_area.repaint();

                Thread.sleep(30);
            }catch(InterruptedException e){
                System.out.println(e);
            }
        }
    }

    public static void main(String[] args){
        Dialogue d = new Dialogue();
    }
}