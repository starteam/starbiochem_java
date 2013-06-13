package utils;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JPanel;

public class GlassPane extends JPanel
{
    private static final long serialVersionUID = 1L;

	private GlassPaneContent content = new GlassPaneContent();  
  
    public GlassPane()
    {  
        setLayout(null);  
        setOpaque(false);
		setSize(10000, 10000);
		addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent me)
			{
				me.consume();
			}
			public void 	mouseDragged(MouseEvent me)
			{
				me.consume();
			}
			public void 	mouseEntered(MouseEvent me)
			{
				me.consume();
			}
			public void 	mouseExited(MouseEvent me)
			{
				me.consume();
			}
			public void 	mouseMoved(MouseEvent me)
			{
				me.consume();
			}
			public void 	mousePressed(MouseEvent me)
			{
				me.consume();
			}
			public void 	mouseReleased(MouseEvent me)
			{
				me.consume();
			}
			public void 	mouseWheelMoved(MouseWheelEvent me)
			{
				me.consume();
			}
		});
		this.addKeyListener(new KeyAdapter()
		{
	        public void keyTyped(KeyEvent ke)
	        {
	        	ke.consume();
	        }
	        public void keyPressed(KeyEvent ke)
	        {
	        	ke.consume();
	        }
	        public void keyReleased(KeyEvent ke)
	        {
	        	ke.consume();
	        }
		});
        add(content);  
    }  
  
	@Override  
    protected void paintComponent(java.awt.Graphics g)
    {  
        g.setColor(new java.awt.Color(.5f, .5f, .5f, .5f));  
        g.fillRect(0, 0, getWidth(), getHeight());  
        int x = (getWidth() - content.getWidth()) / 2;  
        int y = (getHeight() - content.getHeight()) / 2;  
        content.setLocation(x, y);  
        super.paintComponent(g);  
    }  
}

class GlassPaneContent extends JPanel
{
    private static final long serialVersionUID = 1L;

	GlassPaneContent()
    {  
        setOpaque(false);
        setSize(10000, 10000);
    }  
}  
  
