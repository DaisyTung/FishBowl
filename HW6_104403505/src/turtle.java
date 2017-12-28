import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

public class turtle extends JLabel implements Runnable{
	private ImageIcon[] imgIcon = {new ImageIcon("w.png"), new ImageIcon("w2.png")};
	private Random rand = new Random();
	private int size=rand.nextInt(50)+80;
	private int imgW, imgH, x, y, r, speed=rand.nextInt(100)+50;;
	private boolean dirflag=true;
	private Timer t1;
	private Image[] tempImg = {
			//用來隨機設定圖片大小
		imgIcon[0].getImage().getScaledInstance(size,size,Image.SCALE_DEFAULT),
		imgIcon[1].getImage().getScaledInstance(size,size,Image.SCALE_DEFAULT),
	
	};
	private ImageIcon[] newIcon = {
		new ImageIcon(tempImg[0]),
		new ImageIcon(tempImg[1])
	};
	
	public turtle(int x, int y, int imgW, int imgH) {
		this.x = x;
		this.y = y;
		this.imgW = imgW;
		this.imgH = imgH;
		r = rand.nextInt(2);
		if(r==1) {
			this.dirflag = false;
		};
		this.setIcon(newIcon[r]);
		this.setBounds(x, y, this.getIcon().getIconWidth(), this.getIcon().getIconHeight());

	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		t1 = new Timer(speed, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(y<imgH-turtle.this.getIcon().getIconHeight()-60) {
					y+=20;
				}
				else {
					//1/30機率不碰牆轉向
					if(rand.nextInt(30)==1) {
						turtle.this.dirflag = !turtle.this.dirflag;
						if(r==0) {
							r=1;
							turtle.this.setIcon(newIcon[r]);
						}
						else {
							r=0;
							turtle.this.setIcon(newIcon[r]);
						}
					}
					//一定機率改變速度
					if(rand.nextInt(5)==1) {
						speed=rand.nextInt(500)+50;
					}
					//向右邊游
					if(turtle.this.dirflag) {
						if((x+turtle.this.getIcon().getIconWidth()+10<imgW)) {
							x+=5;
						}
						else {
							turtle.this.dirflag = !turtle.this.dirflag;
							r=1;
							turtle.this.setIcon(newIcon[r]);
						}
					}
					//向左邊游
					else {
						if(x-5>0) {
							x-=5;
						}
						else {
							turtle.this.dirflag = !turtle.this.dirflag;
							r=0;
							turtle.this.setIcon(newIcon[r]);
						}
					}
				}
				turtle.this.setLocation(x, y);
			}					
		});
		t1.start();
	}

}
