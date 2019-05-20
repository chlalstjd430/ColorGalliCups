package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import etc.ChangePanelService;


public class MainView extends JPanel {
   private JButton menuArr[];
   private JLabel leftCursorArr[];
   private JLabel rightCursorArr[];
   private int cor;
   private RankView rank;
   
   public MainView() {
	   this.addComponentListener( new ComponentAdapter() {
	        @Override
	        public void componentShown( ComponentEvent e ) {
	        	 MainView.this.requestFocusInWindow();
	        }
	    });
	   this.addKeyListener(new Handler());
	   setLayout(new BorderLayout());
	   
	   makeUI();
	   this.setSize(1363, 714);
   }
   
   private void makeUI() {
	      menuArr = new JButton[5];
	      menuArr[0] = new JButton("Game Start");
	      menuArr[1] = new JButton("Single Rank");
	      menuArr[2] = new JButton("Option");
	      menuArr[3] = new JButton("Help");
	      menuArr[4] = new JButton("Exit");
	      rank = new RankView();
	      
	      ImageIcon leftCursorImage = new ImageIcon("image/LeftCursor.png");
	      ImageIcon rightCursorImage = new ImageIcon("image/RightCursor.png");
	      
	      leftCursorArr = new JLabel[] {new JLabel(leftCursorImage),new JLabel(leftCursorImage),new JLabel(leftCursorImage),new JLabel(leftCursorImage),new JLabel(leftCursorImage)};
	      rightCursorArr = new JLabel[] {new JLabel(rightCursorImage),new JLabel(rightCursorImage),new JLabel(rightCursorImage),new JLabel(rightCursorImage),new JLabel(rightCursorImage)};
	      
	      for(int i = 0 ; i < 5 ; i++ ){
	         leftCursorArr[i].setBounds(450, 280 + 60 * i, 100, 100);
	         leftCursorArr[i].setVisible(false);
	         add(leftCursorArr[i]);
	      }
	      for(int i = 0 ; i < 5 ; i++ ){
	         rightCursorArr[i].setBounds(790, 280 + 60 * i, 100, 100);
	         rightCursorArr[i].setVisible(false);
	         add(rightCursorArr[i]);
	      }
	      Font font = new Font("Nanum Brush Script",Font.PLAIN, 60);
	      JPanel panel = new JPanel(new GridLayout(5,0));
	      Handler l = new Handler();
	      MouseHandler ml = new MouseHandler();
	      for(JButton b : menuArr) {
				b.setBorderPainted(false);
				b.setContentAreaFilled(false);
				b.setFocusPainted(false);
				b.setFont(font);
				b.setForeground(new Color(80, 80, 180));
				b.addActionListener(l);
				b.addKeyListener(l);
				b.addMouseListener(ml);
				panel.add(b);
	      }
	      ImageIcon img = new ImageIcon("image/MainBackground.png");
	      
	      JPanel background = new JPanel() {
	         public void paintComponent(Graphics g) {
	            g.drawImage(img.getImage(), 0, 0, null);
	            setOpaque(false);
	         }
	      };
	      leftCursorArr[0].setVisible(true);
	      rightCursorArr[0].setVisible(true);
	      
	      
//	      for(JLabel a : labelArr) {
//	         panel.add(a);
//	         //a.addKeyListener(new Handler());
//	      }
	      panel.setOpaque(false);      
	      background.add(panel);
	      background.setLayout(null);
	      panel.setBounds(500, 300, 350, 300);
	      background.setOpaque(false);      
	      panel.addKeyListener(new Handler());
	      this.add(background);
   }
   class MouseHandler extends MouseAdapter{
	   public void mouseEntered(MouseEvent e) {
		   for(int i = 0; i < 5; i++) {
				if(e.getSource() == menuArr[i]) {
					leftCursorArr[cor].setVisible(false);
					rightCursorArr[cor].setVisible(false);
					leftCursorArr[i].setVisible(true);
					rightCursorArr[i].setVisible(true);
					cor = i;
					break;
				}
			}
	   }
   }
   class Handler extends KeyAdapter implements ActionListener {
	   public void actionPerformed(ActionEvent e) {
		   ChangePanelService cps = ChangePanelService.getInstance();
			if(cor == 0) cps.changePanel("GameMode");
			else if(cor == 1) rank.setVisible(true);
			else if(cor == 2) cps.changePanel("Option");
			else if(cor == 3) cps.changePanel("Help");
			else if(cor == 4) System.exit(0);
		}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				System.out.println("down");
				if(cor != 4) {
					leftCursorArr[cor].setVisible(false);
					rightCursorArr[cor].setVisible(false);
					cor = cor+1;
					leftCursorArr[cor].setVisible(true);
					rightCursorArr[cor].setVisible(true);
				}
			} else if(e.getKeyCode() == KeyEvent.VK_UP) {
				System.out.println("up");
				if(cor != 0) {
					leftCursorArr[cor].setVisible(false);
					rightCursorArr[cor].setVisible(false);
					cor = cor-1;
					leftCursorArr[cor].setVisible(true);
					rightCursorArr[cor].setVisible(true);
				}
			} else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				actionPerformed(new ActionEvent(e.getSource(), e.getID(), Character.toString(e.getKeyChar())));
			}
		}
   }
   
}