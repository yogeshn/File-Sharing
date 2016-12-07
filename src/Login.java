import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp.*;
import com.jcraft.jsch.jcraft.*;

public class Login extends JFrame implements ActionListener {

	JButton bt1,bt2;
	 JLabel lbl1,lbl2,lbl3,lbl4;
	 JTextField txt1,txt2,txt3;
	 JPasswordField pass;
	 Container cp;
	 JPanel jp1,jp2;
	 JSch jsch = new JSch();
	 Session session = null;
	 
	 
	Login()
	{

		 
		 
		 Font f=new Font("Times new Roman", Font.PLAIN, 26);
		setSize(new Dimension(500,550));

		setLayout(null);
		 Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	    setBounds((screen.width - 490) / 2, (screen.height - 500) / 2,500, 500);
		setTitle(" Login ");
		ImageIcon ic = new   ImageIcon("images/wi.png");
		ImageIcon ic1 = new   ImageIcon("images/wi.png");
		JDesktopPane dp = new JDesktopPane();
		
		jp1=new JPanel();
		jp1.setBackground(Color.black);
		jp1.setOpaque(false);
		jp1.setLayout(null);
	    jp1.setBounds(0,0,500,500);
		


		bt1=new JButton(" Cancel ");
		bt2=new JButton(" Login ");

		lbl1=new JLabel("Server Name");
		lbl2=new JLabel("Port Number");
		lbl3=new JLabel("Username");
		lbl4=new JLabel("Password");
		txt1=new JTextField(80);
		txt2=new JTextField(80);
		txt3=new JTextField(80);
		pass=new JPasswordField(80);
		txt1.setFont(f);
		txt2.setFont(f);
		txt3.setFont(f);
		pass.setFont(f);
		
		
		lbl1.setBounds(5,143,100,35);
		txt1.setBounds(85,143,330,35);
		lbl2.setBounds(5,186,100,35);
		txt2.setBounds(85,186,330,35);
		lbl3.setBounds(5,229,100,35);
		txt3.setBounds(85,229,330,35);
		lbl4.setBounds(5,272,100,35);
		pass.setBounds(85,272,330,35);
		bt1.setBounds(90,383,100,35);
		bt2.setBounds(308,383,100,35);
		bt1.addActionListener(this);
		bt2.addActionListener(this);
		
	     dp.add(jp1,new Integer(350));
		jp1.add(bt1);jp1.add(bt2);jp1.add(txt3);jp1.add(txt1);jp1.add(txt2);jp1.add(pass);jp1.add(lbl1);jp1.add(lbl2);jp1.add(lbl3);jp1.add(lbl4);

		setLayeredPane(dp);
		setVisible(true);
	}

	
	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		

		if(ae.getSource()==bt2)
		{
			try
			{
				String username=txt3.getText().toString();
				String servername=txt1.getText().toString();
				int port=Integer.parseInt(txt2.getText());
				String password=pass.getText().toString();
				 session = jsch.getSession(username, servername, port);
		            session.setConfig("StrictHostKeyChecking", "no");
		            session.setPassword(password);
		            session.connect();

		            Channel channel = session.openChannel("sftp");
		            channel.connect();
		            ChannelSftp sftpChannel = (ChannelSftp) channel;
		        //    sftpChannel.get("hello.c", "yogesh.c");
		            sftpChannel.exit();
		            session.disconnect();
		            
		            
		              String SFTPWORKINGDIR = "/home"+"/"+txt3.getText().toString().toLowerCase();
		              serverOpen so=new serverOpen(username,servername,port,password,SFTPWORKINGDIR);
		             
		            this.dispose();
		         
		        

				}
			 catch (JSchException e) {
	             
				 JOptionPane.showMessageDialog(null,"Error connecing to the remote server","Error",JOptionPane.ERROR_MESSAGE);
	               
	                pass.setText("");
	        }/* catch (SftpException e) {
	            e.printStackTrace();
	        }*/
			catch(Exception e)
				{
				System.out.println(e);
				}
	}
		if(ae.getSource()==bt1)
		{
			
		}
}
}
