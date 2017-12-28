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
			 //用來設定隨機大小
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
		//r=1是向左游
		if(r==1) {
			this.dirflag = false;
		}
		this.setIcon(newIcon[r][r1 = rand.nextInt(3)]);
		this.setBounds(x, y, this.getIcon().getIconWidth(), this.getIcon().getIconHeight());
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//每隔一段時間處理一次的timer
		t1 = new Timer(speed, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//1/30機率不碰牆轉向
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
				//一定機率改變速度
				if(rand.nextInt(5)==1) {
					speed=rand.nextInt(500)+50;
				}
				//向右邊游
				if(fish.this.dirflag) {
					if((x+fish.this.getIcon().getIconWidth()+10<imgW)) {
						x+=5;
					}
					else {
						//折返回去 改變flag
						fish.this.dirflag = !fish.this.dirflag;
						r=1;
						//改成相反的圖片
						fish.this.setIcon(newIcon[r][r1]);
					}
				}
				//向左邊游
				else {
					if(x-5>0) {
						x-=5;
					}
					else {
						//折返
						fish.this.dirflag = !fish.this.dirflag;
						r=0;
						fish.this.setIcon(newIcon[r][r1]);
					}
				}
				//往上游
				if(fish.this.updownflag) {
					if(y-5>0) {
						y-=5;
					}
					else {
						fish.this.updownflag = !fish.this.updownflag;
					}
				}
				//往下游
				else {
					if((y+fish.this.getIcon().getIconWidth()+5)+90<imgH){
						y+=10;
					}
					else {
						fish.this.updownflag = !fish.this.updownflag;
					}
				}
				//重新設定位置
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
