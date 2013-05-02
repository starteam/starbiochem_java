package app;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import utils.UIHelpers;
import app.Messages;

public class QuitDialog extends JDialog
{
    private static final long serialVersionUID = 1L;
    String url = "http://www.surveymonkey.com/s/GSCGGHG"; //$NON-NLS-1$
    int ret = JOptionPane.CANCEL_OPTION ;

    public QuitDialog(Frame parent, String title)
    {
        super(parent, title, true);
		setLocationRelativeTo(parent);
        final QuitDialog self = this ;
        final JButton quit = new JButton(Messages.getString("QuitDialog.1")); //$NON-NLS-1$
        quit.setMnemonic('Q');
        quit.addActionListener( new ActionListener()
        {
                public void actionPerformed(ActionEvent e)
                {
                        self.ret = JOptionPane.YES_OPTION ;
                        self.setVisible(false);
                        self.dispose();
                }
        });
        final JButton cancel = new JButton(Messages.getString("QuitDialog.2")); //$NON-NLS-1$
        cancel.setMnemonic('C');
        cancel.addActionListener( new ActionListener()
        {
                public void actionPerformed(ActionEvent e)
                {
                        self.ret = JOptionPane.CANCEL_OPTION ;
                        self.setVisible(false);
                        self.dispose();
                }
        });
        final JLabel thankyou = new JLabel(Messages.getString("QuitDialog.3"),SwingConstants.CENTER); //$NON-NLS-1$
        thankyou.setVisible(false);
        final JButton feedback = new JButton(Messages.getString("QuitDialog.4")); //$NON-NLS-1$
        feedback.setMnemonic('F');
        feedback.addActionListener( new ActionListener()
        {
                public void actionPerformed(ActionEvent e)
                {
                        thankyou.setVisible(true);
                        feedback.setVisible(false);
                        UIHelpers.openWebBrowser(url);
                        pack();
                }
        });


        setLayout(new BoxLayout(this.getContentPane(),BoxLayout.PAGE_AXIS));
        JPanel p1 = new JPanel();
        p1.setLayout( new BorderLayout() ) ;
        add(p1);
        p1.add(BorderLayout.NORTH , new JLabel("<html><font size=+1><center>" + Messages.getString("QuitDialog.6") + "</center></font></html>",SwingConstants.CENTER)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        JLabel textArea = new JLabel("<html><center>" + Messages.getString("QuitDialog.9") + "<br>" + Messages.getString("QuitDialog.11") + "</center></html>"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        p1.add( BorderLayout.CENTER , textArea);
        JPanel p2 = new JPanel();
        add(p2);
        p2.add(feedback);
        p2.add(thankyou);
        JPanel p4 = new JPanel();
        add(p4);
        p4.setLayout( new BorderLayout()) ;
        p4.add( BorderLayout.NORTH, new JLabel(" ",SwingConstants.CENTER)); //$NON-NLS-1$
        p4.add( BorderLayout.SOUTH , new JLabel(title,SwingConstants.CENTER));
        JPanel p3 = new JPanel();
        add(p3);
        p3.add(quit);
        p3.add(cancel);
        pack();
        UIHelpers.centerOnParent(this);
        setVisible(true);
    }

    public int get()
    {
            return ret ;
    }

}