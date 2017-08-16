package Puzzle8;

import javax.swing.*;

import java.awt.*;

import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.text.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;

import javax.swing.UIManager;
import javax.swing.border.Border;

public class GameBoard extends JFrame {
	private int boardSize = 400;
	private int cellSize = 40;
	private int[][] array = new int[3][3];
	int s1r=-1;
    int s1c=-1;
	int s2r=-1, s2c=-1;
	public GameBoard()
	{
		super("8 PUZZLE");
		final Container cp = getContentPane();
		setDesign();
		setSize(600,700);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MenuBar mb = new MenuBar();
		Menu game = new Menu("Game");
		MenuItem m1 = new MenuItem("   NEW 			");
		
		game.add(m1);
		setMenuBar(mb);
		mb.add(game);
		m1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
				SwingUtilities.invokeLater(new Runnable()
				{
					@Override 
					public void run()
					{
						new GameBoard();
					}
				});
			}
		});
		
		
		final JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main,BoxLayout.Y_AXIS));
		main.setBackground(new Color(253,226,200));
		main.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		main.setPreferredSize(new Dimension(boardSize,boardSize));
		final JPanel display = new JPanel();
		GridLayout layout = new GridLayout(3,3);
		display.setPreferredSize(new Dimension(boardSize-180,boardSize-180));
		layout.setHgap(2);
		layout.setVgap(2);
		display.setBackground(Color.BLACK);
		display.setLayout(layout);
		display.setBorder(BorderFactory.createLineBorder(new Color(253,226,200),10));
		final JLabel[] l = new JLabel[9];
		final JTextField[] t = new JTextField[9];
		
		FocusListener highlighter = new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                e.getComponent().setBackground(Color.GREEN);
            }

            @Override
            public void focusLost(FocusEvent e) {
                e.getComponent().setBackground(UIManager.getColor("TextField.background"));
            }
        };
		
		for(int i = 0 ; i < 9 ; i++)
		{
			t[i] = new JTextField();
			t[i].setHorizontalAlignment(SwingConstants.CENTER);
			t[i].setFont(new Font("Snap ITC",Font.BOLD,54));
			t[i].setPreferredSize(new Dimension(cellSize,cellSize));
			t[i].addFocusListener(highlighter);
			display.add(t[i]);
		}
		JButton solve = new JButton("Solve");
		Font font = new Font("Georgia",Font.BOLD,20);
		solve.setFont(font);
		solve.setBackground(new Color(250,250,210));
		solve.setAlignmentX(Component.CENTER_ALIGNMENT);
		Dimension dm = new Dimension(50,40);
		solve.setPreferredSize(dm);
		solve.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(!(new Board(array,3).isSolvable()))
					JOptionPane.showMessageDialog(null,"Puzzle is Unsolvable");
				else{
				Solver s = new Solver(new Board(array,3));
				
				final Iterator<Board> it =  s.solution_Table().iterator();
				JOptionPane.showMessageDialog(null,"Total No. Of Moves : "+s.totalMoves);
				//s.print_Solution();
					
				Thread worker = new Thread(){
						
						@Override
						public void run()
						{
							while(it.hasNext())
							{
								Board b1 = it.next();
								//b1.print();
								
								
								for(int i = 0 ; i < 3 ; i++)
									for(int j = 0 ; j < 3 ; j++)
									{
										if(array[i][j] == 0)
										{
											s1r = i;
											s1c = j;
										}
									}
								
								for(int i = 0 ; i < 3 ; i++)
									for(int j = 0 ; j < 3 ; j++)
									{
										if(b1.b[i][j] == 0 && array[i][j] != 0){
											s2r=i;
											s2c=j;
										}
									}
								
								int temp = array[s1r][s1c];
								if(s1r == -1 || s1c == -1 || s2r == -1 || s2c == -1)
									continue;
								array[s1r][s1c] = array[s2r][s2c];
								array[s2r][s2c] = temp;
								
								SwingUtilities.invokeLater(new Runnable(){
									@Override
									public void run()
									{
										t[s1r*3+s1c].setText(""+array[s1r][s1c]);
										t[s1r*3+s1c].setBackground(Color.white);
											
										t[s2r*3+s2c].setText(" ");
										t[s2r*3+s2c].setBackground(Color.yellow);
									}
									
								});
								
								try{
									Thread.sleep(1300);
								}catch(Exception ex)
								{
									ex.printStackTrace();
								}
								//System.out.println("1. "+s1r+" "+s1c+"   2.  "+s2r+" "+s2c);
							}	
							JOptionPane.showMessageDialog(null,"Done");
							
						}
				};
				worker.start();
				}
				
			}
		});
		JButton accept = new JButton("Accept");
		accept.setFont(font);
		accept.setBackground(new Color(250,250,210));
		accept.setAlignmentX(Component.CENTER_ALIGNMENT);
		accept.setPreferredSize(dm);
		accept.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				for(int i = 0 ; i < 9 ; i++)
				{
					int row = (int)i / 3;
					int col = i-row*3;
					array[row][col] = Integer.parseInt(t[i].getText());
					t[i].setEditable(false);
					if(array[row][col] == 0){
						t[i].setText(" ");
						t[i].setBackground(Color.yellow);
					}
					else
						t[i].setBackground(Color.white);
				}
				
				
				
				/*for(int i = 0 ; i < 3 ; i++)
				{
					for(int j = 0 ; j < 3 ; j++)
						System.out.print(array[i][j]+" ");
					System.out.println();
				}*/
			}
		});
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons,BoxLayout.X_AXIS));
		buttons.setPreferredSize(new Dimension(100,40));
		buttons.setBackground(new Color(253,226,200));
		buttons.add(Box.createRigidArea(new Dimension(25,40)));
		buttons.add(accept);
		buttons.add(Box.createRigidArea(new Dimension(50,40)));
		buttons.add(solve);
		buttons.add(Box.createRigidArea(new Dimension(25,40)));
		main.add(display,BorderLayout.CENTER);
		main.add(buttons,BorderLayout.CENTER);
		main.add(Box.createRigidArea(new Dimension(15,12)));
		cp.add(main);
		pack();
		setVisible(true);
		
	}

	public final void setDesign()
	{
		try
		{
			for(LookAndFeelInfo info :UIManager.getInstalledLookAndFeels())
			{
				if("Nimbus".equals(info.getName()))
				{
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
