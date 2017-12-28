import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class frame extends JFrame{
	
	private Container cp;
	private imagePanel jpn = new imagePanel(); //��I���Ϥ���Panel
	private ArrayList<fish> fishList = new ArrayList<fish>();//�s�񳽪���
	private ArrayList<Thread> fThreadList = new ArrayList<Thread>();//�s�񳽪������
	private ArrayList<turtle> turtleList = new ArrayList<turtle>();//�s��Q�t����
	private ArrayList<Thread> tThreadList = new ArrayList<Thread>();//�s��Q�t�����
	private panel panel = new panel();//���s��Panel
	//private JPanel waterPanel = new JPanel(); //�쥻�O�Ŧ⪺�I�� ��ӥ�ImagePanel��I���F
	private int nowSt=0,imgW,imgH;
	private boolean selFlag=false; //�P�_�O�_������R�����A
	
	public frame() {
		super("FishBowl");
		imgH = jpn.getImgH();
		imgW = jpn.getImgW();//�I���Ϫ��j�p
		this.setBounds(300,100,imgW,imgH+60);//�ھڭI���Ϫ��j�p�]�wframe�j�p
		cp = this.getContentPane();
		cp.setLayout(new BorderLayout(3,3));
		cp.add(jpn,BorderLayout.CENTER);
		cp.add(panel,BorderLayout.NORTH);
		jpn.setLayout(null); //���ڽc�����S���S�w��Layout
//		add(panel,BorderLayout.NORTH);
//		add(waterPanel,BorderLayout.CENTER);
//		waterPanel.setBackground(new Color(137, 192, 255));
//		waterPanel.setLayout(null);		
//		waterPanel.addMouseListener(MouseHandler);
//		waterPanel.addMouseMotionListener(MouseHandler);
		MouseHandler MouseHandler = new MouseHandler();
		jpn.addMouseListener(MouseHandler);
	}
	private class imagePanel extends JPanel{
		private BufferedImage image;
		private int imgH,imgW;
		//Ū�I���Ϥ�
		public imagePanel(){
			try {
				image = ImageIO.read(new File("WHDQ-513185574.jpg"));
				imgW = image.getWidth();
				imgH = image.getHeight();
			}catch(IOException ex) {
				javax.swing.JOptionPane.showMessageDialog(this, "wrong");
			}
		}
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(image,0,0,null);
		}
		public int getImgH(){
			return imgH;
		}
		public int getImgW() {
			return imgW;
		}
	}
	
	//���s��Panel �@����s
	private class panel extends JPanel{
		private JButton bt1, bt2, bt3, bt4;
		private JLabel state, space, fishNum, turtleNum;
		private JPanel btPanel, stPanel;
		
		public panel() {
			btPanel = new JPanel();
			stPanel = new JPanel();
			bt1 = new JButton("�s�W��");
			bt2 = new JButton("�������");
			bt3 = new JButton("�s�W�Q�t");
			bt4 = new JButton("��������");
			state = new JLabel("�ثe�\��: ");
			space = new JLabel("         ");
			fishNum = new JLabel("���ƶq: ");
			turtleNum = new JLabel("�Q�t�ƶq: ");
			state.setForeground(Color.BLUE);
			fishNum.setForeground(Color.BLUE);
			turtleNum.setForeground(Color.BLUE);
			
			btHandler btHandler = new btHandler();
			bt1.addActionListener(btHandler);
			bt2.addActionListener(btHandler);
			bt3.addActionListener(btHandler);
			bt4.addActionListener(btHandler);
			
			btPanel.setLayout(new GridLayout(2,2));
			btPanel.add(bt1);
			btPanel.add(bt2);
			btPanel.add(bt3);
			btPanel.add(bt4);
			
			stPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			stPanel.add(state);
			stPanel.add(space);
			stPanel.add(fishNum);
			stPanel.add(turtleNum);
			
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.add(btPanel);
			this.add(stPanel);
			
			
		}
		
		private class btHandler implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource()==bt1) {
					state.setText("�ثe�\��: �s�W��");
					nowSt=1;
					selFlag=false;
				}
				else if(e.getSource()==bt2) {
					state.setText("�ثe�\��: �������");
					nowSt=2;
					selFlag=true;
				}
				else if(e.getSource()==bt3) {
					state.setText("�ثe�\��: �s�W�Q�t");
					nowSt=3;
					selFlag=false;
				}
				else if(e.getSource()==bt4) {
					state.setText("�ثe�\��: ��������");
					nowSt=4;
					selFlag=false;
					for(int i=0;i<fThreadList.size();i++) {
						fThreadList.get(i).interrupt();
					}
					for(int i=0;i<tThreadList.size();i++) {
						tThreadList.get(i).interrupt();
					}
					fishList.clear();
					turtleList.clear();
					fThreadList.clear();
					tThreadList.clear();
					jpn.removeAll();
					jpn.repaint();
					panel.fishNum.setText("���ƶq: "+fishList.size());
					panel.turtleNum.setText("�Q�t�ƶq: "+turtleList.size());
				}
				
			}
			
		}
	}
	
	//�B�z����R����Handler
	private class selHandler extends MouseAdapter{
		
		public void mousePressed(MouseEvent e){
			if(selFlag) {
				//�Q�����JLabel  
				JLabel pressed = (JLabel)e.getSource();
				//�~�ӳ����O������
				if(e.getSource() instanceof fish){
					panel.state.setText("�ثe�\��: ��ܧR����");
					fishList.remove(e.getSource());
					fThreadList.remove(e.getSource());
				}
				//�~�ӯQ�t
				else if(e.getSource() instanceof turtle){
					panel.state.setText("�ثe�\��: ��ܧR���Q�t");
					turtleList.remove(e.getSource());
					tThreadList.remove(e.getSource());
				}
				jpn.remove(pressed);
				jpn.repaint();
				//���s�p��ƶq
				panel.fishNum.setText("���ƶq: "+fishList.size());
				panel.turtleNum.setText("�Q�t�ƶq: "+turtleList.size());
			}
			
		}
	}
	
	private class MouseHandler implements MouseListener,MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
		
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
			if(nowSt==1) {
				fishList.add(new fish(e.getX(), e.getY(), imgW, imgH));
				jpn.add(fishList.get(fishList.size()-1)).addMouseListener(new selHandler());
				fThreadList.add(new Thread(fishList.get(fishList.size()-1)));
				fThreadList.get(fThreadList.size()-1).start();
				panel.fishNum.setText("���ƶq: "+fishList.size());
			}
			else if(nowSt==2) {
				
			}
			else if(nowSt==3) {
				turtleList.add(new turtle(e.getX(),e.getY(), imgW, imgH));
				jpn.add(turtleList.get(turtleList.size()-1)).addMouseListener(new selHandler());
				tThreadList.add(new Thread(turtleList.get(turtleList.size()-1)));
				tThreadList.get(tThreadList.size()-1).start();
				panel.turtleNum.setText("�Q�t�ƶq: "+turtleList.size());
			}
			else if(nowSt==4) {
				
			}
			else {
				
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
