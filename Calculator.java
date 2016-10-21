import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;


public class Calculator extends JFrame implements ActionListener{
	
	static String serverip = "";
	static int port = 3000;

	JButton op = new JButton();
	JButton equ = new JButton("=");
	JTextField text = new JTextField();
	JTextArea info = new JTextArea("Please enter at least two operands\nand a operator in the correct format.\nThe answer will be a number\nwith two digits after the decimal point.\n",5,1);
	
	String[] name = {"1","2","3","+","4","5","6","-","7","8","9","*","C","0",".","/"};
	
	public Calculator(){
				
		setSize(360,360);
		setTitle("Calculator");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width/2 - getWidth()/2 ,size.height/2 - getHeight()/2);
		
		Container c = getContentPane();
		
		JPanel p = new JPanel();
		p.setLayout(new GridBagLayout());
		
		GridBagConstraints c0 = new GridBagConstraints();
		c0.gridx = 0;
		c0.gridy = 0;
		c0.weightx = 0;
		c0.weighty = 0;
		c0.gridheight = 1;
		c0.gridwidth = 4;
		c0.fill = GridBagConstraints.BOTH;
        c0.anchor = GridBagConstraints.CENTER;
		text.setBackground(Color.white);
		text.setFont(new Font("Serif", Font.BOLD, 28));
		text.setHorizontalAlignment(JTextField.RIGHT);
		text.setEnabled(false);
		text.setText("");
		p.add(text, c0);
		
		int namecount = 0;
		for(int i = 1 ; i < 5 ; i++){
			for(int j = 0 ; j < 4 ; j++){
				GridBagConstraints c1 = new GridBagConstraints();
				op = new JButton(name[namecount]);
				c1.gridx = j;
				c1.gridy = i;
				c1.weightx = 0;
				c1.weighty = 0;
				c1.gridheight = 1;
				c1.gridwidth = 1;
				c1.fill = GridBagConstraints.BOTH;
		        c1.anchor = GridBagConstraints.WEST;
				op.setActionCommand(name[namecount]);
		        op.setFont(new Font("Serif", Font.PLAIN, 18));
		        op.addActionListener(this);
		        p.add(op, c1);
				namecount ++;
			}
		}
        
        GridBagConstraints c1 = new GridBagConstraints();
		c1.gridx = 0;
		c1.gridy = 5;
		c1.gridwidth = 4;
		c1.gridheight = 1;
		c1.weightx = 0;
		c1.weighty = 0;
		c1.fill = GridBagConstraints.BOTH;
        c1.anchor = GridBagConstraints.CENTER;
        p.add(equ, c1);
        equ.setActionCommand("=");
        equ.setFont(new Font("Serif", Font.PLAIN, 18));
        equ.addActionListener(this);
        
        GridBagConstraints c3 = new GridBagConstraints();
		c3.gridx = 0;
		c3.gridy = 6;
		c3.gridwidth = 4;
		c3.gridheight = 4;
		c3.weightx = 0;
		c3.weighty = 0;
		c3.fill = GridBagConstraints.BOTH;
        c3.anchor = GridBagConstraints.CENTER;
		info.setBackground(Color.BLACK);
		info.setFont(new Font("Serif", Font.BOLD, 16));
		info.setForeground(Color.WHITE);
		info.setEnabled(false);
		p.add(info, c3);
        
        c.add(p, BorderLayout.CENTER);        		
	}
	
	public void actionPerformed(ActionEvent e) {
		
		String comm = e.getActionCommand();
		
        
        if(text.getText().equals("Error!"))
            text.setText("");
        
		if(comm == "1"){
			 text.setText(text.getText()+"1");
		}else if(comm == "2"){
			text.setText(text.getText()+"2");
		}else if(comm == "3"){
			text.setText(text.getText()+"3");
		}else if(comm == "4"){
			text.setText(text.getText()+"4");
		}else if(comm == "5"){
			text.setText(text.getText()+"5");
		}else if(comm == "6"){
			text.setText(text.getText()+"6");
		}else if(comm == "7"){
			text.setText(text.getText()+"7");
		}else if(comm == "8"){
			text.setText(text.getText()+"8");
		}else if(comm == "9"){
			text.setText(text.getText()+"9");
		}else if(comm == "0"){
			text.setText(text.getText()+"0");
		}else if(comm == "."){
            if(text.getText().length() == 0 ||
               text.getText().substring(text.getText().length()-1,text.getText().length()).equals("+") ||
               text.getText().substring(text.getText().length()-1,text.getText().length()).equals("-") ||
               text.getText().substring(text.getText().length()-1,text.getText().length()).equals("*") ||
               text.getText().substring(text.getText().length()-1,text.getText().length()).equals("/"))
                text.setText(text.getText()+"0.");
            else{
                text.setText(text.getText()+".");
            }
		}else if(comm == "+"){
            if(text.getText().length() == 0)
                text.setText("");
            else{
                if(!text.getText().substring(text.getText().length()-1,text.getText().length()).equals("+") &&
                   !text.getText().substring(text.getText().length()-1,text.getText().length()).equals("-") &&
                   !text.getText().substring(text.getText().length()-1,text.getText().length()).equals("*") &&
                   !text.getText().substring(text.getText().length()-1,text.getText().length()).equals("/")){
                    text.setText(text.getText()+"+");
                }
            }
		}else if(comm == "-"){
            if(text.getText().length() == 0)
                text.setText("-");
            else{
                if(text.getText().length() == 1){
                    if(!text.getText().substring(text.getText().length()-1,text.getText().length()).equals("+") &&
                       !text.getText().substring(text.getText().length()-1,text.getText().length()).equals("-") &&
                       !text.getText().substring(text.getText().length()-1,text.getText().length()).equals("*") &&
                       !text.getText().substring(text.getText().length()-1,text.getText().length()).equals("/")){
                        text.setText(text.getText()+"-");
                    }else
                        ;
                }else if(!text.getText().substring(text.getText().length()-2,text.getText().length()).equals("+") &&
                   !text.getText().substring(text.getText().length()-2,text.getText().length()).equals("-") &&
                   !text.getText().substring(text.getText().length()-2,text.getText().length()).equals("*") &&
                   !text.getText().substring(text.getText().length()-2,text.getText().length()).equals("/")){
                    text.setText(text.getText()+"-");
                }
            }
		}else if(comm == "*"){
            if(text.getText().length() == 0)
                text.setText("");
            else{
                if(!text.getText().substring(text.getText().length()-1,text.getText().length()).equals("+") &&
                   !text.getText().substring(text.getText().length()-1,text.getText().length()).equals("-") &&
                   !text.getText().substring(text.getText().length()-1,text.getText().length()).equals("*") &&
                   !text.getText().substring(text.getText().length()-1,text.getText().length()).equals("/")){
                    text.setText(text.getText()+"*");
                }
            }
		}else if(comm == "/"){
            if(text.getText().length() == 0)
                text.setText("");
            else{
                if(!text.getText().substring(text.getText().length()-1,text.getText().length()).equals("+") &&
                   !text.getText().substring(text.getText().length()-1,text.getText().length()).equals("-") &&
                   !text.getText().substring(text.getText().length()-1,text.getText().length()).equals("*") &&
                   !text.getText().substring(text.getText().length()-1,text.getText().length()).equals("/")){
                    text.setText(text.getText()+"/");
                }
            }
		}else if(comm == "C"){
			text.setText("");
            info.setText("Please enter at least two operands\nand a operator in the correct format.\nThe answer will be a number\nwith two digits after the decimal point.\n");
		}else if(comm == "="){
            if(text.getText().substring(text.getText().length()-1,text.getText().length()).equals("."))
                text.setText(text.getText()+"0");
            if(text.getText().length() == 0)
                info.setText("You need to input something!/nPlease enter at least two operands\nand a operator in the correct format.\nThe answer will be a number\nwith two digits after the decimal point.\n");
            else if(!text.getText().substring(text.getText().length()-1,text.getText().length()).equals("+") &&
               !text.getText().substring(text.getText().length()-1,text.getText().length()).equals("-") &&
               !text.getText().substring(text.getText().length()-1,text.getText().length()).equals("*") &&
               !text.getText().substring(text.getText().length()-1,text.getText().length()).equals("/") &&
               !text.getText().substring(0,1).equals("I") &&
               !text.getText().substring(0,1).equals("S")){
                Socket socket = null;
                BufferedReader in = null;
                
                try{
                    socket = new Socket(InetAddress.getByName(serverip),port);
                    info.setText("Connect to Server...\n");
                    
                    DataInputStream instream = new DataInputStream(socket.getInputStream());
                    DataOutputStream outstream = new DataOutputStream(socket.getOutputStream());
                    
                    outstream.writeUTF(text.getText());
                    
                    String messagein = instream.readUTF();
                    text.setText(messagein);
                    info.setText(info.getText() + "Get a message from the Server.\n");
                    
                    socket.close();
                }catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }else{
                text.setText(text.getText());
                info.setText("You miss an operand!/n");
            }
		}
		
	}
	
	
	public static void main(String[] args) {
		  Scanner input = new Scanner(System.in);
		  System.out.println("Please input the IP address of the server:");
		  serverip = input.nextLine();
		  Calculator view = new Calculator();
		  view.setVisible(true);
	}
	
}
