import java.net.*;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;

public class Server{
	
	static int port = 3000;
	ServerSocket SS = null;
	ExecutorService threadexec = Executors.newCachedThreadPool();
	
	public Server(){
		try{
			SS = new ServerSocket(port);
			System.out.println("Server is created and waiting for Client to connect...");
			System.out.println("IP address : " + InetAddress.getLocalHost().getHostAddress());
			
			while(true){
				threadexec.execute(new Serverthread(SS.accept()));
			}
		}catch(IOException e){
			System.out.println(e.getMessage());
			System.exit(-1);
		}finally{
			if(threadexec != null)
				threadexec.shutdown();
			if(SS != null){
				try{
					SS.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
			
	public static void main(String args[]){
		
		Server startserver = new Server();
        
	}
}


class Serverthread extends Thread{
    private Socket socket = null;

    static String messageout;
    
    public Serverthread(Socket socket) {
    	super("serverthread");
    	this.socket = socket;
    }
    
    @Override
    public void run() {

		try {			
			System.out.println("Connected from Client " + socket.getRemoteSocketAddress().toString().substring(1));
			
			DataInputStream instream = new DataInputStream(socket.getInputStream());
			String inputLine = "";
            messageout = "";
			
			inputLine = instream.readUTF();
			
			while(inputLine != null){
		    		
				char[] input = inputLine.toCharArray();
                
                int symbolcount = 0;
                int symbolloc[] = new int[100];
                
                int symloccount = 0;
                for(int i = 1 ; i < input.length ; i++){
                    if(input[i] == '+' || input[i] == '-' || input[i] == '*' || input[i] == '/')
                        if(input[i] == '-'){
                            //check if '-' presents 'minus' or 'negative'
                            if(input[i-1] == '+' || input[i-1] == '-' || input[i-1] == '*' || input[i-1] == '/')
                                ;
                            else{
                                symbolcount++;
                                symbolloc[symloccount] = i;
                                symloccount++;
                            }
                        }else{
                            symbolcount++;
                            symbolloc[symloccount] = i;
                            symloccount++;
                        }
                }
                
                if(symbolcount < 1){
                    if(input != null)
                        messageout = inputLine;
                    else
                        messageout = "Input Something!";
                }else{
                    //divide operators and operands
                    float[] num = new float[symbolcount + 1];
                    String[] symbol = new String[symbolcount];
                    int k = 0;
                    
                outer:
                    for(int i = 0 ; i < symbolcount; i++){
                        if(i < symbolcount)
                            symbol[k] = String.valueOf(input[symbolloc[i]]);
                        String number = "";
                        int containpoint = 0;       //check if any number contains two "."
                        
                        if(i == 0){
                            for(int j = 0 ; j < symbolloc[i] ; j++){
                                if(containpoint < 2){
                                    number = number.concat(String.valueOf(input[j]));
                                    if(input[j] == '.')
                                        containpoint++;
                                }else{
                                    messageout = "Syntax Error!";
                                    break outer;
                                }
                            }
                        }else{
                            for(int j = symbolloc[i-1] +1  ; j < symbolloc[i] ; j++){
                                if(containpoint < 2){
                                    number = number.concat(String.valueOf(input[j]));
                                    if(input[j] == '.')
                                        containpoint++;
                                }else{
                                    messageout = "Syntax Error!";
                                    break outer;
                                }
                            }
                        }
                        num[k] = Float.parseFloat(number);
                        k++;
                    }
                    
                    String number = inputLine.substring(symbolloc[symbolcount - 1] + 1,inputLine.length());
                    num[k] = Float.parseFloat(number);
                    
                    if(messageout == ""){
                        //To do "*" & "/" first
                        for(int i = 0 ; i < symbol.length ; i++){
                            if(symbol[i].equals("*")){
                                num[i + 1] = num[i] * num[i + 1];
                                num[i] = 0;
                                symbol[i] = "+";
                            }else if(symbol[i].equals("/")){
                                num[i + 1] = num[i] / num[i + 1];
                                num[i] = 0;
                                symbol[i] = "+";
                            }
                        }
                        
                        float ans = num[0];
                        
                        //Then do "=" & "-"
                        for(int i = 0 ; i < symbol.length ; i++){
                            if(symbol[i].equals("+")){
                                ans += num[i + 1];
                            }else if(symbol[i].equals("-")){
                                ans -= num[i + 1];
                            }
                        }
                        
                        if(ans < 0){
                            ans = 0 - ans;
                            ans = (float)(Math.round(ans * 100))/100;
                            ans = 0 - ans;
                        }else
                            ans = (float)(Math.round(ans * 100))/100;
                        DecimalFormat outformat = new DecimalFormat("##0.00");
                        String out = outformat.format(ans);
                        messageout = String.valueOf(out);
                    }
                }
								
				DataOutputStream outstream = new DataOutputStream(socket.getOutputStream());
				outstream.writeUTF(messageout);
				System.out.println("Client asks for the answer of : " + inputLine);
				System.out.println("Answer : " + messageout);
				System.out.println("Message is transferred to Client");
			
				inputLine = instream.readUTF();
				
		    }
		} catch (EOFException e){
			
		} catch (IOException e) {
		    e.printStackTrace();
		} 
    }
}
