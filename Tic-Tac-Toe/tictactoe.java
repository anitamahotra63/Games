import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TTT extends JFrame implements ActionListener  {

	private static final int board_size=3;
	private JButton[][] board=new JButton[board_size][board_size];
	private int x_wins=0;
	private int o_wins=1;
	private int tie=2;
	private int incomplete=3;
	
	private boolean cross_turn;
	
	public TTT()
	{
		super("tic tac toe");
		super.setVisible(true);
		super.setSize(600, 600);
		super.setResizable(false);
		
		GridLayout layout=new GridLayout(3,3);
		super.setLayout(layout);
		
		for(int row=0;row<board_size;row++)
		{
			for(int col=0;col<board_size;col++)
			{
				JButton button=new JButton();
				button.setFont(new Font("Times New Roman",0,225));
				
				//super.add(button);
				button.addActionListener(this);
				
				this.board[row][col]=button;
				super.add(button);
				
				
			}
		}
		
		
		
	}
	
	public void start_game()
	{
		cross_turn=true;
	}

	public void actionPerformed(ActionEvent e) {
		
		JButton button=(JButton)e.getSource(); 
		String message=button.getText();
		if(message.equals(""))
		{
			make_move(button);
			int game_status=get_game_status();
			
			if(game_status==3)	//incomplete
			{
				cross_turn=!cross_turn;
				super.setTitle(cross_turn?"x ki turn":"0 ki turn");
			}
			else
			{
				declare_winner(game_status);
				dispose();
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null,"Invalid Argument");
		}
		
		
	}
	
	public void make_move(JButton button)
	{
		String message=cross_turn?"x":"0";
		button.setText(message);
	}
	
	public int get_game_status()
	{
		//rows
		String text1="";
		String text2="";
		for(int row=0;row<board_size;row++)
		{
			int col=0;
			while(col<board_size-1)
			{
				 text1=this.board[row][col].getText();
				 text2=this.board[row][col+1].getText();
				if(text1.equals("") || !text1.equals(text2))
				{
					break;
				}
				else
					{
						col++;
					}
			}
			if(col==board_size-1)
			{
				return text1.equals("x")?x_wins:o_wins;
			}
		}
		
		//columns
				for(int col=0;col<board_size;col++)
				{
					int row=0;
					while(row<board_size-1)
					{
						 text1=this.board[row][col].getText();
						 text2=this.board[row+1][col].getText();
						if(text1.equals("") ||!text1.equals(text2))
						{
							break;
						}
						row++;
					}
					if(row==board_size-1)
					{
						return text1.equals("x")?x_wins:o_wins;
						 
					}
				}
				
			//diagonal 1
				int col=0;
				for(int row=0;row<board_size-1;)
				{
				
					
						 text1=this.board[row][col].getText();
						 text2=this.board[row+1][col+1].getText();
						if(text1.equals("") ||!text1.equals(text2))
						{
							break;
						}
						col++;
						row++;
				}
				if(col==board_size-1)
					{
						 return text1.equals("x")?x_wins:o_wins;
						
					}
				
				
				
			//diagonal 2	
				 col=board_size-1;
				 int row;
				for(row=0;row<board_size-1;)
				{
				
					
						 text1=this.board[row][col].getText();
						 text2=this.board[row+1][col-1].getText();
						if(text1.equals("") ||!text1.equals(text2))
						{
							break;
						}
						col--;
						row++;
				}
				if(row==board_size-1)
					{
					return text1.equals("x")?x_wins:o_wins;
					
					}
				//incomplete
				for(row=0;row<board_size;row++)
				{
					for( col=0;col<board_size;col++)
					{
						if(this.board[row][col].getText().equals(""))
						{
							return 3;
						}
					}
				}
				
				return 2;
	}
	
	public void declare_winner(int game_status)
	{
		if(game_status==0)
		{
			JOptionPane.showMessageDialog(null,"X WINS");

		}
		else if(game_status==1)
		{
			JOptionPane.showMessageDialog(null,"0 WINS");

		}
		else
		{
			JOptionPane.showMessageDialog(null,"TIE");

		}
	}



}
