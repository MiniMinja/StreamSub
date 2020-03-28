import java.awt.event.*;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.FontMetrics;
public class TextReader implements KeyListener, MouseListener{

    public static final int CUSTOM = 0, LEFT = 1, RIGHT = 2, MIDDLE = 3, TOP = 4, BOTTOM = 5;

    private ArrayList<StringBuilder> text;
    private StringBuilder line;
    private int index;
    private int lineNo;

    private int startX;
    private int startY;

    private int alignHor, alignVer;
    
    public TextReader(){
        text = new ArrayList<StringBuilder>();
        line = new StringBuilder();
        text.add(line);
        index = 0;
        lineNo = 0;

        startX = 5;
        startY = 25;

        alignHor = RIGHT;
        alignVer = BOTTOM;
    }

    public ArrayList<StringBuilder> getText(){return text;}

    public int getIndex(){
        return index;
    }

    public int getLineNo(){
        return lineNo;
    }

    public void drawString(Graphics g, boolean drawCursor, String str, int topY, int strHeight, int strAscent, FontMetrics fm){
        int strWidth = fm.stringWidth(str);

        //find the 2 corner points to draw the line depending on alignment
        int corner1X = startX, corner1Y = topY;
        int corner2X = startX + strWidth, corner2Y = topY + strHeight;
        if(alignHor == LEFT){
            corner1X = 5;
            corner2X = corner1X + strWidth;
        }else if(alignHor == RIGHT){
            corner2X = 1280 - 5;
            corner1X = corner2X - strWidth;
        }
        else if(alignHor == MIDDLE){
            int middle = 1280 / 2;
            corner1X = middle - strWidth / 2;
            corner2X = middle + strWidth / 2;
        }

        //draw the text according to the corner points
        if(drawCursor){
            g.drawRect(corner1X, corner1Y, strWidth, strHeight);
        }
        g.drawString(str, corner1X, corner1Y + strAscent);
    }

    public void draw(Graphics g, boolean drawCursor){
        //get the fontmetrics
        FontMetrics fm = g.getFontMetrics();
        int strHeight = fm.getAscent() + fm.getDescent();
        int strAscent = fm.getAscent();
        if(alignVer == TOP || alignVer == CUSTOM){
            for(int i = 0;i<text.size();i++){
                //get each line as a string and determine its metrics
                String to_display = text.get(i).toString();
                drawString(g, drawCursor, to_display, (alignVer == CUSTOM ? startY : 0) + strHeight * i, strHeight, strAscent, fm);
            }
        }
        else if(alignVer == BOTTOM){
            for(int i = 0;i<text.size();i++){
                //get each line as a string and determine its metrics
                String to_display = text.get(text.size() - 1 - i).toString();
                int topY = 720 - 5 - strHeight * (i+1);
                drawString(g, drawCursor, to_display, topY, strHeight, strAscent, fm);
            }
        }
    }

    public void setAlignment(int alignHor, int alignVer){
        this.alignHor = alignHor;
        this.alignVer = alignVer;
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
            }
        }
        else if(e.getKeyChar() == '\n'){
            line = new StringBuilder();
            text.add(line);
            index = 0;
            lineNo++;
        }
        else if(e.getKeyChar() != KeyEvent.CHAR_UNDEFINED){
            line.append(e.getKeyChar());
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
        startY = e.getY();
        
    }
    public void mouseReleased(MouseEvent e){
        
    }
}