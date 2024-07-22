package src.gym.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class testWindow extends JFrame{
    private JTextArea textArea;
    public testWindow() {
        super("Query Result");
        textArea = new JTextArea("");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void updateInfo(String newText) {
        textArea.setText(newText);
    }

    public void showFrame() {
        JPanel contentPane = new JPanel();
		this.setContentPane(contentPane);

        GridBagLayout gb = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.setPreferredSize(new Dimension(700,400));

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(textArea, c);
		contentPane.add(textArea);
        
         // anonymous inner class for closing the window
//		this.addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e) {
//				System.exit(0);
//			}
//		});

        // size the window to obtain a best fit for the components
		this.pack();

		// center the frame
		Dimension d = this.getToolkit().getScreenSize();
		Rectangle r = this.getBounds();
		this.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

		// make the window visible
		 this.setVisible(true);
    }
}
