import java.awt.event.*;
import java.util.ArrayList;
import java.awt.Graphics;
public class TextReader implements KeyListener, MouseListener{

    private static int lineSize = 30;

    private ArrayList<StringBuilder> text;
    private StringBuilder line;
    private int index;
    private int lineNo;

    private int startX;
    private int startY;
    
    public TextReader(){
        text = new ArrayList<StringBuilder>();
        line = new StringBuilder("|");
        text.add(line);
        index = 0;
        lineNo = 0;

        startX = 5;
        startY = 25;
    }

    public ArrayList<StringBuilder> getText(){return text;}

    public int getIndex(){
        return index;
    }

    public int getLineNo(){
        return lineNo;
    }

    public void draw(Graphics g, boolean drawCursor){
        for(int i = 0;i<text.size()-1;i++){
            g.drawString(text.get(i).toString(), startX, startY + lineSize*i);
        }
        StringBuilder toDraw = text.get(text.size()-1);
        if(drawCursor){
            g.drawString(toDraw.toString(), startX, startY + lineSize*(text.size()-1));
        }
        else{
            g.drawString(toDraw.substring(0,toDraw.length()-1).toString(), startX, startY + lineSize*(text.size()-1));
        }
    }

    public void keyPressed(KeyEvent e){
        
    }

    public void keyReleased(KeyEvent e){

    }

    public void keyTyped(KeyEvent e){
        if(e.getKeyChar() == '\b'){
            if(index > 0){
                line.deleteCharAt(index-- - 1);
            }
            else if(lineNo > 0){
                text.remove(lineNo--);
                line = text.get(text.size()-1);
                index = line.length();
                line.append('|');
            }
        }
        else if(e.getKeyChar() == '\n'){
            line.deleteCharAt(index);
            line = new StringBuilder("|");
            text.add(line);
            index = 0;
            lineNo++;
        }
        else if(e.getKeyChar() != KeyEvent.CHAR_UNDEFINED){
            line.deleteCharAt(index);
            line.append(e.getKeyChar());
            line.append('|');
            index++;
        }
        else {
            //TODO, add more controls so it becomes like a text editor
        }
    }

    public void mouseClicked(MouseEvent e){
    }

    public void mouseEntered(MouseEvent e){
        
    }
    public void mouseExited(MouseEvent e){
        
    }
    public void mousePressed(MouseEvent e){
        startX = e.getX();
        startY = e.getY() + 25;
        
    }
    public void mouseReleased(MouseEvent e){
        
    }
}