import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.security.SecureRandom;
import java.util.Random;
import javax.swing.Timer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class fish extends JLabel implements Runnable, MouseListener{
	private ImageIcon[][] imgIcon = {
			{new ImageIcon("1.png"), new ImageIcon("3.png"), new ImageIcon("5.png")},
			{new ImageIcon("2.png"), new ImageIcon("4.png"), new ImageIcon("6.png")} 
	};
	private Random rand = new Random();
	private int size=rand.nextInt(100)+50;
	private int imgH, imgW, x, y, r, r1,index, speed=rand.nextInt(100)+50;
	private boolean dirflag=true;
	private boolean updownflag=true;
	private Timer t1;
	private Image[][] tempImg = {
			 //�Ψӳ]�w�H���j�p
				{imgIcon[0][0].getImage().getScaledInstance(size,size,Image.SCALE_DEFAULT),
			 	 imgIcon[0][1].getImage().getScaledInstance(size,size,Image.SCALE_DEFAULT),
			 	 imgIcon[0][2].getImage().getScaledInstance(size,size,Image.SCALE_DEFAULT)},
			
				{imgIcon[1][0].getImage().getScaledInstance(size,size,Image.SCALE_DEFAULT),
				 imgIcon[1][1].getImage().getScaledInstance(size,size,Image.SCALE_DEFAULT),
				 imgIcon[1][2].getImage().getScaledInstance(size,size,Image.SCALE_DEFAULT)},
			};
	
	private ImageIcon[][] newIcon = {
			{
				new ImageIcon(tempImg[0][0]),
				new ImageIcon(tempImg[0][1]),
				new ImageIcon(tempImg[0][2]),
			},
			{
				new ImageIcon(tempImg[1][0]),
				new ImageIcon(tempImg[1][1]),
				new ImageIcon(tempImg[1][2]),
			}
	};
	
	public fish(int x, int y, int imgW, int imgH) {
		this.x = x;
		this.y = y;
		this.imgW = imgW;
		this.imgH = imgH;
		r = rand.nextInt(2);
		int n = rand.nextInt(2);
		if(n==1) {
			this.updownflag = false;
		}
		//r=1�O�V����
		if(r==1) {
			this.dirflag = false;
		}
		this.setIcon(newIcon[r][r1 = rand.nextInt(3)]);
		this.setBounds(x, y, this.getIcon().getIconWidth(), this.getIcon().getIconHeight());
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//�C�j�@�q�ɶ��B�z�@����timer
		t1 = new Timer(speed, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//1/30���v���I����V
				if(rand.nextInt(30)==1) {
					fish.this.dirflag = !fish.this.dirflag;
					if(r==0) {
						r=1;
						fish.this.setIcon(newIcon[r][r1]);
					}
					else {
						r=0;
						fish.this.setIcon(newIcon[r][r1]);
					}
				}
				//�@�w���v���ܳt��
				if(rand.nextInt(5)==1) {
					speed=rand.nextInt(500)+50;
				}
				//�V�k���
				if(fish.this.dirflag) {
					if((x+fish.this.getIcon().getIconWidth()+10<imgW)) {
						x+=5;
					}
					else {
						//���^�h ����flag
						fish.this.dirflag = !fish.this.dirflag;
						r=1;
						//�令�ۤϪ��Ϥ�
						fish.this.setIcon(newIcon[r][r1]);
					}
				}
				//�V�����
				else {
					if(x-5>0) {
						x-=5;
					}
					else {
						//���
						fish.this.dirflag = !fish.this.dirflag;
						r=0;
						fish.this.setIcon(newIcon[r][r1]);
					}
				}
				//���W��
				if(fish.this.updownflag) {
					if(y-5>0) {
						y-=5;
					}
					else {
						fish.this.updownflag = !fish.this.updownflag;
					}
				}
				//���U��
				else {
					if((y+fish.this.getIcon().getIconWidth()+5)+90<imgH){
						y+=10;
					}
					else {
						fish.this.updownflag = !fish.this.updownflag;
					}
				}
				//���s�]�w��m
				fish.this.setLocation(x, y);
			}					
		});
		t1.start();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
