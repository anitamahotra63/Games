import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class othello extends JFrame implements ActionListener {	//JFrame is a class which helps to create the window.
																//action listener 
	private static final int board_size=8;
	
	private JButton[][] board=new JButton[board_size][board_size];
	
	private boolean cross_turn;
	
	private int found=0;
	
	public othello()
	{
		//super("OTHELLO");
		super.setVisible(true);
		super.setSize(600,600);
		super.setResizable(false);
		
		GridLayout layout=new GridLayout(8,8);
		super.setLayout(layout);
		
		for(int row=0;row<board_size;row++)
		{
			for(int col=0;col<board_size;col++)
			{
				JButton button=new JButton();
				//button.setFont(new Font("Times New Roman",0,70));
				
				button.addActionListener(this);
				super.add(button);
				button.setBackground(Color.GREEN);
				board[row][col]=button;
			}
		}
	}
	
	public void start_game()
	{
		board[3][3].setBackground(Color.WHITE);
		board[3][4].setBackground(Color.BLACK);
		board[4][3].setBackground(Color.BLACK);
		board[4][4].setBackground(Color.WHITE);
		board[3][2].setBackground(Color.BLUE);
		board[2][3].setBackground(Color.BLUE);
		board[5][4].setBackground(Color.BLUE);
		board[4][5].setBackground(Color.BLUE);
		cross_turn=true;
	}
	
	public void actionPerformed(ActionEvent e) {
	
		this.found=0;
		JButton button_clicked=(JButton)e.getSource();
		if(button_clicked.getBackground()==(cross_turn?Color.BLUE:Color.RED))
		{
			
			super.setTitle(cross_turn?"white ki turn":"black ki turn");
			button_clicked.setBackground(cross_turn?Color.BLACK:Color.WHITE);
			//first move, clicked button changes to either black or white
			
			make_move2();					
			//second move. remaining blues or reds to green
			
			make_move3(button_clicked);		
			//flip
			
			this.found=0;
			
			make_move4();		//to put either blue or red boxes
			
			int status=check_game_status();
			System.out.println(status);
			if(status==0){
				cross_turn=!cross_turn;
			}
			else if(status==1)
			{
				cross_turn=!cross_turn;
				JOptionPane.showMessageDialog(null,cross_turn?"turn of white again":"turn of black again");
				make_move4();
				status=check_game_status();
				System.out.println(status);
				if(status==1)
				{
					declare_winner();
					dispose();
				}
				cross_turn=!cross_turn;
			}
			if(final_check()==false)
			{
				declare_winner();
				dispose();
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null,"invalid argument");
		}
		
	}
	
	public boolean final_check()
	{
		boolean retval=false;
		for(int row=0;row<board_size;row++)
		{
			for(int col=0;col<board_size;col++)
			{
				if(board[row][col].getBackground()==Color.GREEN || board[row][col].getBackground()==Color.BLUE || board[row][col].getBackground()==Color.RED)
				{
					retval=true;
					break;
				}
			}
		}
		return retval;
	}
	
	public void declare_winner()
	{
		int bsum=0,wsum=0;
		for(int row=0;row<board_size;row++)
		{
			for(int col=0;col<board_size;col++)
			{
				if(board[row][col].getBackground()==Color.WHITE)
				{
					wsum+=1;
				}
				else if(board[row][col].getBackground()==Color.BLACK)
				{
					bsum+=1;
				}
			}
		}
		if(bsum>wsum)
		{
			JOptionPane.showMessageDialog(null, "winner is black");
		}
		else if(wsum>bsum)
		{
			JOptionPane.showMessageDialog(null, "winner is white");
		}
		else
		{
			JOptionPane.showMessageDialog(null,"tie");
		}
	}
	
	public int check_game_status()
	{
		int row=0;
		int col=0;
		int got=0;
		Color the_color=(cross_turn?Color.RED:Color.BLUE);
		for(row=0;row<board_size;row++)
		{
			for(col=0;col<board_size;col++)
			{
				if(board[row][col].getBackground()==the_color)
				{
					got++;
					break;
				}
			}
		}
		if(got==0)
		{
			JOptionPane.showMessageDialog(null, cross_turn?"white color has no moves":"black color has no moves");
			return 1;			//half complete
		}
		/*if(row==board_size-1 && col==board_size-1 && board[row][col].getBackground()!=the_color)
		{
			JOptionPane.showMessageDialog(null, cross_turn?"white color has no moves":"black color has no moves");
			return 1;			//half complete
		}*/
		else
		{
			return 0;          //continue as it is
		}
		
	}

	public void make_move2()
	{
		for(int row=0;row<board_size;row++)
		{
			for(int col=0;col<board_size;col++)
			{
				if(board[row][col].getBackground()==(cross_turn?Color.BLUE:Color.RED))
				{
					board[row][col].setBackground(Color.GREEN);
				}
			}
		}
	}
	
	public void make_move3(JButton button)
	{
		int got=0;							// for getting the indices of the clicked button
		int row=0;
		int col=0;
		for( row=0;row<board_size;row++)
		{
			for(col=0;col<board_size;col++)
			{
				if(board[row][col]==button)
				{
					got++;
					break;
				}
			}
			if(got!=0)
			{
				break;
			}
			
		}
		
		
		flipping(row,col,'n');
		this.found=0;
		flipping(row,col,'s');
		this.found=0;
		flipping(row,col,'e');
		this.found=0;
		flipping(row,col,'w');
		this.found=0;
		flipping(row,col,'x');
		this.found=0;
		flipping(row,col,'y');
		this.found=0;
		flipping(row,col,'z');
		this.found=0;
		flipping(row,col,'a');
		this.found=0;
	}
	
	private class pair{
		int i;
		int j;
		
		public pair(int i,int j)
		{
			this.i=i;
			this.j=j;
		}
	}
	
	public void flipping(int i,int j,char ch)
	{
		pair old_pair=this.new_values(i, j, ch);
		if((old_pair.i<0 || old_pair.i>board_size-1) || (old_pair.j<0 || old_pair.j>board_size-1))
		{
			return;
		}
		else if(board[old_pair.i][old_pair.j].getBackground()==Color.GREEN)
		{
			return;
		}
		else if(board[old_pair.i][old_pair.j].getBackground()==(cross_turn?Color.BLACK:Color.WHITE))
		{
			this.found++;
			return;
		}
		else if(board[old_pair.i][old_pair.j].getBackground()==(cross_turn?Color.WHITE:Color.BLACK))
		{
			//old_pair=this.new_values(old_pair.i,old_pair.j ,ch);
			flipping(old_pair.i,old_pair.j,ch);
			if(this.found!=0)
			{
				board[old_pair.i][old_pair.j].setBackground(cross_turn?Color.BLACK:Color.WHITE);
			}
		}
		
		return;
	}
	
	public pair new_values(int i,int j,char ch)
	{
		pair old_pair=new pair(0,0);
		switch(ch)
		{
			case 'n':old_pair.i=i-1;
					 old_pair.j=j;
					 break;
			case 's':old_pair.i=i+1;
					 old_pair.j=j;
					 break;
			case 'e':old_pair.j=j+1;
					 old_pair.i=i;
					 break;
			case 'w':old_pair.j=j-1;
					 old_pair.i=i;
					 break;
			case 'x':old_pair.i=i-1;	//north east
					 old_pair.j=j+1;
					 break;
			case 'y':old_pair.i=i+1;	//south east
					 old_pair.j=j+1;
					 break;
			case 'z':old_pair.i=i-1;	//north west
					 old_pair.j=j-1;
					 break;
			case 'a':old_pair.i=i+1;	//south west
					 old_pair.j=j-1;
					 break;
			default:System.out.println("invalid choice"); 
		}
		
		return old_pair;
	}
	


	public void make_move4()
	{
		for(int row=0;row<board_size;row++)
		{
			for(int col=0;col<board_size;col++)
			{
				if(board[row][col].getBackground()==(cross_turn?Color.WHITE:Color.BLACK))
				{
					check2(board[row][col],row,col,'n');
					check2(board[row][col],row,col,'s');
					check2(board[row][col],row,col,'e');
					check2(board[row][col],row,col,'w');
					check2(board[row][col],row,col,'x');
					check2(board[row][col],row,col,'y');
					check2(board[row][col],row,col,'z');
					check2(board[row][col],row,col,'a');
				}
			}
		}

	}
	
	public void check2(JButton button,int i, int j,char ch)
	{
		pair new_pair=this.new_values(i,j,ch);	//immediate value according to the direction
		if((new_pair.i<0 || new_pair.i>board_size-1) || ( new_pair.j<0 || new_pair.j>board_size-1))
		{
			return;
		}
		else
		{
			if(board[new_pair.i][new_pair.j].getBackground()==(cross_turn?Color.BLACK:Color.WHITE))
			{
				pair the_pair=new pair(new_pair.i,new_pair.j);
				while((the_pair.i>=0 && the_pair.i<=board_size-1) && (the_pair.j>=0 && the_pair.j<=board_size-1))
				{
					if(board[the_pair.i][the_pair.j].getBackground()==Color.GREEN)
					{
						board[the_pair.i][the_pair.j].setBackground(cross_turn?Color.RED:Color.BLUE);
						break;
					}
					else if(board[the_pair.i][the_pair.j].getBackground()==(cross_turn?Color.RED:Color.BLUE)||board[the_pair.i][the_pair.j].getBackground()==(cross_turn?Color.WHITE:Color.BLACK))
					{
						break;
					}
					the_pair=this.new_values(the_pair.i, the_pair.j, ch);
				}
			}
			else
			{
				return;
			}
		}
		
	}
	
		
}
