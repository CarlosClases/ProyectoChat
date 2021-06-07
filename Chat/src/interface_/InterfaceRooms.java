package interface_;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Canvas;

public class InterfaceRooms extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceRooms frame = new InterfaceRooms();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InterfaceRooms() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 485, 330);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btn_Sport = new JButton("");
		btn_Sport.setIcon(new ImageIcon(InterfaceRooms.class.getResource("/Iconos/Deportes.png")));
		btn_Sport.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_Sport.setBounds(10, 100, 130, 70);
		contentPane.add(btn_Sport);
		
		JButton btn_Anime = new JButton("");
		btn_Anime.setIcon(new ImageIcon(InterfaceRooms.class.getResource("/Iconos/Animes.png")));
		btn_Anime.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_Anime.setBounds(170, 100, 130, 70);
		contentPane.add(btn_Anime);
		
		JButton btn_Juegos = new JButton("");
		btn_Juegos.setVerticalAlignment(SwingConstants.BOTTOM);
		btn_Juegos.setIcon(new ImageIcon(InterfaceRooms.class.getResource("/Iconos/Juegos.png")));
		btn_Juegos.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_Juegos.setBounds(330, 100, 130, 70);
		contentPane.add(btn_Juegos);
		
		JButton btn_Series = new JButton("");
		btn_Series.setIcon(new ImageIcon(InterfaceRooms.class.getResource("/Iconos/Series.png")));
		btn_Series.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_Series.setBounds(10, 200, 130, 70);
		contentPane.add(btn_Series);
		
		JButton btn_Computing = new JButton("");
		btn_Computing.setIcon(new ImageIcon(InterfaceRooms.class.getResource("/Iconos/Informatica.png")));
		btn_Computing.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_Computing.setBounds(170, 200, 130, 70);
		contentPane.add(btn_Computing);
		
		JButton btn_Wepon = new JButton("");
		btn_Wepon.setIcon(new ImageIcon(InterfaceRooms.class.getResource("/Iconos/revolver.png")));
		btn_Wepon.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_Wepon.setBounds(330, 200, 130, 70);
		contentPane.add(btn_Wepon);
		
		JLabel lbl_Title = new JLabel("CHOSE A ROOM");
		lbl_Title.setBounds(86, 11, 268, 40);
		lbl_Title.setForeground(new Color(102, 205, 170));
		lbl_Title.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
		contentPane.add(lbl_Title);
		
		Canvas Line1 = new Canvas();
		Line1.setBackground(new Color(102, 205, 170));
		Line1.setBounds(100, 50, 240, 5);
		contentPane.add(Line1);
		
		Canvas Line2 = new Canvas();
		Line2.setBackground(new Color(102, 205, 170));
		Line2.setBounds(60, 70, 340, 5);
		contentPane.add(Line2);
	}
}
