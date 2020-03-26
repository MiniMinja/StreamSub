import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
public class TextReader implements KeyListener{

    private ArrayList<StringBuilder> text;
    private StringBuilder line;
    private int index;
    private int lineNo;
    
    public TextReader(){
        text = new ArrayList<StringBuilder>();
        line = new StringBuilder();
        text.add(line);
        index = 0;
        lineNo = 0;
    }

    public ArrayList<StringBuilder> getText(){return text;}

    public void keyPressed(KeyEvent e){
        
    }

    public void keyReleased(KeyEvent e){

    }

    public void keyTyped(KeyEvent e){
        if(e.getKeyChar() == '\b'){
            if(index > 0){
                line.deleteCharAt(--index);
            }
            else if(lineNo > 0){
                line = text.remove(lineNo);
                lineNo--;
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
}