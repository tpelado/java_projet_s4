import java.awt.EventQueue;

import javax.print.DocFlavor.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Panel;
import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.ButtonGroup;

public class gui
{

	private JFrame frame;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					gui window = new gui();
					window.frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public gui()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setBounds(100, 100, 826, 634);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setMaximumSize(new Dimension(200, 100));
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		JLabel david = new JLabel("Sponsoris\u00E9 par ");
		david.setHorizontalTextPosition(SwingConstants.LEFT);
		david.setHorizontalAlignment(SwingConstants.RIGHT);
		david.setVerticalAlignment(SwingConstants.BOTTOM);
		david.setIcon(new ImageIcon(gui.class.getResource("/ressources/david.jpg")));
		//david.setIcon(null);
		panel.add(david);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("815px"),
				ColumnSpec.decode("410px"),},
			new RowSpec[] {
				RowSpec.decode("216px"),}));
		
		JLabel lblLogo = new JLabel("DuckCoinCoin Blockchain Generator");
		lblLogo.setHorizontalTextPosition(SwingConstants.RIGHT);
		lblLogo.setFont(new Font("Tinos for Powerline", Font.PLAIN, 27));
		lblLogo.setVerticalAlignment(SwingConstants.TOP);
		lblLogo.setHorizontalAlignment(SwingConstants.LEFT);
		lblLogo.setIcon(new ImageIcon(gui.class.getResource("/ressources/duckCoinCoin(2).jpg")));
		panel_1.add(lblLogo, "1, 1, default, top");
		
		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3, BorderLayout.NORTH);
		
		JRadioButton rdbtnNiveau = new JRadioButton("Niveau 1");
		buttonGroup.add(rdbtnNiveau);
		rdbtnNiveau.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_3.add(rdbtnNiveau);
		
		JSeparator separator = new JSeparator();
		panel_3.add(separator);
		
		JRadioButton rdbtnNiveau_1 = new JRadioButton("Niveau 2");
		buttonGroup.add(rdbtnNiveau_1);
		rdbtnNiveau_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_3.add(rdbtnNiveau_1);
		rdbtnNiveau_1.setHorizontalAlignment(SwingConstants.RIGHT);
	}
}
