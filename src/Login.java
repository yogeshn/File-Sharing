import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import java.io.*;
import java.util.*;

import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp.*;
import com.jcraft.jsch.jcraft.*;

public class Login extends JFrame implements ActionListener {

	JButton bt1,bt2,bt3,bt4,bt5;
	 JLabel lbl1,lbl2,lbl3,lbl4;
	 JTextField txt1,txt2,txt3,txt4;
	 JPasswordField pass;
	 Container cp;
	 JPanel jp1,jp2;
	 JSch jsch = new JSch();
	 Session session = null;
	 
	 
	Login()
	{

		 
		 
		 Font f=new Font("Times new Roman", Font.PLAIN, 26);
		 Font f1=new Font("Times new Roman", Font.PLAIN, 18);
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
		bt3=new JButton("Advanced login");
		bt4=new JButton("Connect");
		bt5=new JButton("Select");
		bt4.setVisible(false);
		bt5.setVisible(false);

		lbl1=new JLabel("Server Name");
		lbl2=new JLabel("Port Number");
		lbl3=new JLabel("Username");
		lbl4=new JLabel("Password");
		lbl4.setVisible(true);
		txt1=new JTextField(80);
		txt2=new JTextField(80);
		txt3=new JTextField(80);
		txt4=new JTextField(80);
		txt4.setEditable(false);
		pass=new JPasswordField(80);
		txt1.setFont(f);
		txt2.setFont(f);
		txt3.setFont(f);
		txt4.setFont(f1);
		pass.setFont(f);
		
		
		lbl1.setBounds(5,113,100,35);
		txt1.setBounds(110,113,330,35);
		lbl2.setBounds(5,156,100,35);
		txt2.setBounds(110,156,330,35);
		lbl3.setBounds(5,199,100,35);
		txt3.setBounds(110,199,330,35);
		lbl4.setBounds(5,242,100,35);
		pass.setBounds(110,242,330,35);
		txt4.setBounds(110,242,230,35);
		bt5.setBounds(340,242,100,35);
		bt1.setBounds(60,383,100,35);
		bt2.setBounds(328,383,100,35);
		bt4.setBounds(328,383,100,35);
		bt3.setBounds(170,383,150,35);
		bt1.addActionListener(this);
		bt2.addActionListener(this);
		bt3.addActionListener(this);
		bt4.addActionListener(this);
		bt5.addActionListener(this);
		
	     dp.add(jp1,new Integer(350));
		jp1.add(bt1);jp1.add(bt2);jp1.add(txt3);jp1.add(txt1);jp1.add(txt2);jp1.add(pass);jp1.add(lbl1);jp1.add(lbl2);jp1.add(lbl3);jp1.add(lbl4);
		jp1.add(bt3);jp1.add(bt4);jp1.add(txt4);jp1.add(bt5);
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
	        }
			catch(Exception e)
				{
				System.out.println(e);
				}
	}
		if(ae.getSource()==bt1)
		{
			this.dispose();
		}
		
		if(ae.getSource()==bt3)
		{
			
			
			if(lbl4.isVisible()==true)
			{
				lbl4.setVisible(false);
				pass.setVisible(false);
				bt2.setVisible(false);
				bt4.setVisible(true);
				txt4.setVisible(true);
				bt5.setVisible(true);
			}
			else {
				lbl4.setVisible(true);
				pass.setVisible(true);
				bt2.setVisible(true);
				bt4.setVisible(false);
				txt4.setVisible(false);
				bt5.setVisible(false);
			}
			
		}
		
		if(ae.getSource()==bt5)
		{
			
			JFileChooser j = new JFileChooser();
			j.setFileSelectionMode(JFileChooser.FILES_ONLY);
			j.setFileFilter(new FileFilter() {
				
				@Override
				public String getDescription() {
					// TODO Auto-generated method stub
					return "PPK File (*.ppk)";
				}
				
				@Override
				public boolean accept(File f) {
					// TODO Auto-generated method stub
					String filename = f.getName().toLowerCase();
			           return filename.endsWith(".ppk") ;
				}
			});
			j.showOpenDialog(this);
			
			txt4.setText(j.getSelectedFile().getName());
			
			try
			{
				String username=txt3.getText().toString();
				String servername=txt1.getText().toString();
				int port=Integer.parseInt(txt2.getText());
				String password=j.getSelectedFile().getAbsolutePath();
				
				 session = jsch.getSession(username, servername, port);
		            session.setConfig("StrictHostKeyChecking", "no");
		           jsch.addIdentity(password);
		            session.connect();

		            Channel channel = session.openChannel("sftp");
		            channel.connect();
		            ChannelSftp sftpChannel = (ChannelSftp) channel;
		            sftpChannel.exit();
		            session.disconnect();
		            
		            
		              String SFTPWORKINGDIR = "/home"+"/"+txt3.getText().toString().toLowerCase();
		              serverOpen so=new serverOpen(username,servername,port,password);
		             
		            this.dispose();
		         
		        

				}
			 catch (JSchException e) {
	             
				 JOptionPane.showMessageDialog(null,"Error connecing to the remote server","Error",JOptionPane.ERROR_MESSAGE);
	           
	        }
		}
}
}
