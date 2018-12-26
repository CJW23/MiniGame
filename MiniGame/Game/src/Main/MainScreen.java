package Main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import rank.*;




public class MainScreen extends JFrame {

	private Image screenImage;
	private Graphics screenGraphic;
	
	private ImageIcon exitButtonImage = new ImageIcon(Main.class.getResource("../images/exitbutton.png")); 
	private ImageIcon exitButton2Image = new ImageIcon(Main.class.getResource("../images/exitbutton2.png")); 
	private ImageIcon startButtonImage = new ImageIcon(Main.class.getResource("../images/startbutton.png"));
	private ImageIcon startButton2Image = new ImageIcon(Main.class.getResource("../images/startbutton2.png"));
	private ImageIcon rankingButtonImage = new ImageIcon(Main.class.getResource("../images/rankingbutton.png"));
	private ImageIcon rankingButton2Image = new ImageIcon(Main.class.getResource("../images/rankingbutton2.png"));
	private ImageIcon howtoButtonImage = new ImageIcon(Main.class.getResource("../images/howto.png"));
	private ImageIcon howtoButton2Image = new ImageIcon(Main.class.getResource("../images/howto2.png"));
	private ImageIcon game1ButtonImage = new ImageIcon(Main.class.getResource("../images/game1.png"));
	private ImageIcon game2ButtonImage = new ImageIcon(Main.class.getResource("../images/game2.png"));
	private ImageIcon game3ButtonImage = new ImageIcon(Main.class.getResource("../images/game3.png"));
	private ImageIcon backButtonImage = new ImageIcon(Main.class.getResource("../images/backbutton.png"));
	
	
	private Image background = new ImageIcon(Main.class.getResource("../images/mainscreen.jpg")).getImage();
	private JLabel menuBar = new  JLabel(new ImageIcon(Main.class.getResource("../images/menubar.png")));
	
	
	private JButton backButton = new JButton(backButtonImage);
	private JButton exitButton = new JButton(exitButtonImage);
	private JButton startButton = new JButton(startButtonImage);
	private JButton rankingButton = new JButton(rankingButtonImage);
	private JButton howtoButton = new JButton(howtoButtonImage);
	private JButton game1Button = new JButton(game1ButtonImage);
	private JButton game2Button = new JButton(game2ButtonImage);
	private JButton game3Button = new JButton(game3ButtonImage);
	
	private int mouseX, mouseY;
	
	
	//private boolean isMainScreen = false;
	
	public MainScreen() {
		
		setUndecorated(true);
		setTitle("JAVA");
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	    setBackground(new Color(0, 0, 0, 0));
	    setLayout(null);
	    
	    menuBar.setBounds(0, 0, 1280, 30);
	    menuBar.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mousePressed(MouseEvent e) {
	    		mouseX = e.getX();
	    		mouseY = e.getY();
	    	}
	    });
	    menuBar.addMouseMotionListener(new MouseMotionAdapter() {
	    	@Override
	    	public void mouseDragged(MouseEvent e) {
	    		int x = e.getXOnScreen();
	    		int y = e.getYOnScreen();
	    		setLocation(x - mouseX, y - mouseY);
	    	}
	    });
	    add(menuBar);
	    
	    howtoButton.setBounds(50, 50, 30, 30);
	    howtoButton.setBorderPainted(false);
	    howtoButton.setContentAreaFilled(false);
	    howtoButton.setFocusPainted(false);
	    howtoButton.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseEntered(MouseEvent e) {
	    		howtoButton.setIcon(howtoButton2Image);
	    	    howtoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    	}
	    	@Override
	    	public void mouseExited(MouseEvent e) {
	    		howtoButton.setIcon(howtoButtonImage);
	    	    howtoButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	    	}
	    	@Override
	    	public void mousePressed(MouseEvent e) {
	    		startButton.setVisible(false);
	    		rankingButton.setVisible(false);
	    		howtoButton.setVisible(false);
	    		backButton.setVisible(true);
	    		add(backButton);
	    		
	    		background = new ImageIcon(Main.class.getResource("../images/howtoscreen.jpg")).getImage();
	    	}
	    });
	    add(howtoButton);
	    backButton.setBounds(750, 970, 400, 100);
	    backButton.setBorderPainted(false);
	    backButton.setContentAreaFilled(false);
	    backButton.setFocusPainted(false);
	    
	    backButton.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseEntered(MouseEvent e) {
	    		backButton.setIcon(backButtonImage);	
	    	    backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    		
	    	}
	    	@Override
	    	public void mouseExited(MouseEvent e) {
	    		backButton.setIcon(backButtonImage);
	    	    backButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	    	}
	    	@Override
	    	public void mousePressed(MouseEvent e) {
	    		backButton.setVisible(false);
	    		startButton.setVisible(true);
	    		rankingButton.setVisible(true);
	    		howtoButton.setVisible(true);
	    		background = new ImageIcon(Main.class.getResource("../images/mainscreen.jpg")).getImage();
	    		
	    	}
	    });
	    
	    
	    
	    
	    exitButton.setBounds(1840, 50, 30, 30);
	    exitButton.setBorderPainted(false);
	    exitButton.setContentAreaFilled(false);
	    exitButton.setFocusPainted(false);
	    exitButton.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseEntered(MouseEvent e) {
	    		exitButton.setIcon(exitButton2Image);
	    	    exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    	}
	    	@Override
	    	public void mouseExited(MouseEvent e) {
	    		exitButton.setIcon(exitButtonImage);
	    	    exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	    	}
	    	@Override
	    	public void mousePressed(MouseEvent e) {
	    		System.exit(0);
	    	}
	    });
	    add(exitButton);
	    
	    startButton.setBounds(1400, 800, 400, 100);
	    startButton.setBorderPainted(false);
	    startButton.setContentAreaFilled(false);
	    startButton.setFocusPainted(false);
	    startButton.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseEntered(MouseEvent e) {
	    		startButton.setIcon(startButton2Image);
	    	    startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    	}
	    	@Override
	    	public void mouseExited(MouseEvent e) {
	    		startButton.setIcon(startButtonImage);
	    	    startButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	    	}
	    	@Override
	    	public void mousePressed(MouseEvent e) {
	    		startButton.setVisible(false);
	    		rankingButton.setVisible(false);
	    		howtoButton.setVisible(false);
	    		add(game1Button);
	    		//game1Button.setVisible(true);
	    		add(game2Button);
	    		//game2Button.setVisible(true);
	    		add(game3Button);  
	    		//game3Button.setVisible(true);
	    		background = new ImageIcon(Main.class.getResource("../images/selectscreen.png")).getImage();
	    		//isMainScreen = true;
	    	}
	    });
	    add(startButton);
	    
	    rankingButton.setBounds(1400, 880, 400, 100);
	    rankingButton.setBorderPainted(false);
	    rankingButton.setContentAreaFilled(false);
	    rankingButton.setFocusPainted(false);
	    rankingButton.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseEntered(MouseEvent e) {
	    		rankingButton.setIcon(rankingButton2Image);
	    	    rankingButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    	}
	    	@Override
	    	public void mouseExited(MouseEvent e) {
	    		rankingButton.setIcon(rankingButtonImage);
	    	    rankingButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	    	}
	    	@Override
	    	public void mousePressed(MouseEvent e) {
	    		new Rank();
	    		dispose();
	    	}
	    });
	    add(rankingButton);
	    
	    game1Button.setBounds(150, 500, 500, 500);
	    game1Button.setBorderPainted(false);
	    game1Button.setContentAreaFilled(false);
	    game1Button.setFocusPainted(false);
	    game1Button.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseEntered(MouseEvent e) {
	    		game1Button.setIcon(game1ButtonImage);
	    	    game1Button.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    	}
	    	@Override
	    	public void mouseExited(MouseEvent e) {
	    		game1Button.setIcon(game1ButtonImage);
	    	    game1Button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	    	}
	    	@Override
	    	public void mousePressed(MouseEvent e) {
	    		startButton.setVisible(false);
	    		rankingButton.setVisible(false);
	    		howtoButton.setVisible(false);
	    		
	    		Main.g1 = true;
	    		dispose();
	    		//gamestart(nowselected, "game1");
	    	}
	    });
	    //add(game1Button);
	    
	    game2Button.setBounds(720, 500, 500, 500);
	    game2Button.setBorderPainted(false);
	    game2Button.setContentAreaFilled(false);
	    game2Button.setFocusPainted(false);
	    game2Button.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseEntered(MouseEvent e) {
	    		game2Button.setIcon(game2ButtonImage);
	    	    game2Button.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    	}
	    	@Override
	    	public void mouseExited(MouseEvent e) {
	    		game2Button.setIcon(game2ButtonImage);
	    	    game2Button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	    	}
	    	@Override
	    	public void mousePressed(MouseEvent e) {
	    		startButton.setVisible(false);
	    		rankingButton.setVisible(false);
	    		howtoButton.setVisible(false);
	    		
	    		Main.g3 = true;
	    		dispose();
	    		
	    		//gamestart(nowselected, "game2");
	    	}
	    });
	    //add(game2Button);
	    
	    game3Button.setBounds(1300, 500, 500, 500);
	    game3Button.setBorderPainted(false);
	    game3Button.setContentAreaFilled(false);
	    game3Button.setFocusPainted(false);
	    game3Button.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseEntered(MouseEvent e) {
	    		game3Button.setIcon(game3ButtonImage);
	    	    game3Button.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    	}
	    	@Override
	    	public void mouseExited(MouseEvent e) {
	    		game3Button.setIcon(game3ButtonImage);
	    	    game3Button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	    	}
	    	@Override
	    	public void mousePressed(MouseEvent e) {
	    		startButton.setVisible(false);
	    		rankingButton.setVisible(false);
	    		howtoButton.setVisible(false);
	    		
	    		Main.g2 = true;
	    		dispose();
	    		//gamestart(nowselected, "game3");
	    	}
	    });
	    //add(game3Button);   
	}
	
	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}
	
	public void screenDraw(Graphics g){
		g.drawImage(background, 0, 0, null);
		/*if(isMainScreen)
		{
			g.drawImage(selectedImage, 100, 600, null);
		}*/
		paintComponents(g);
		this.repaint();
	}
	
	public void gamestart(int nowselected, String menu) {
		game1Button.setVisible(false);
		game2Button.setVisible(false);
		game3Button.setVisible(false);
		background = new ImageIcon(Main.class.getResource("../images/selectscreen.jpg")).getImage();
	}
	
}