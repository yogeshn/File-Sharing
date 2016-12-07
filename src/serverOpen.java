import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.filechooser.FileView;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

public class serverOpen extends JFrame implements ActionListener, ItemListener {

	TextArea textarea; 
	List list;
	Panel buttons;
	TextField details,properties;
	Button up, download,reconnect,home,delete,newdir;
	String username,servername,password,key;
	String SFTPWORKINGDIR="";
	String WORKINGDIR;
	int port;
	String[] files;
	File currentDir;
	Vector filelist ;
	ChannelSftp sftpChannel ;
	
	
	 JSch jsch = new JSch();
	 Session session = null;
	public serverOpen(String username,String servername,int port,String password,String SFTPWORKINGDIR) {
		// TODO Auto-generated constructor stub
		try{
		this.username=username;
		this.password=password;
		this.servername=servername;
		this.port=port;
		
		 WORKINGDIR=SFTPWORKINGDIR;
		session = jsch.getSession(username, servername, port);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setPassword(password);
        session.connect();

        Channel channel = session.openChannel("sftp");
        channel.connect();
       sftpChannel = (ChannelSftp) channel;
        
       this.SFTPWORKINGDIR=sftpChannel.getHome(); 
        
		
	    list = new List(12, false); // Set up the list
	    list.setFont(new Font("MonoSpaced", Font.PLAIN, 14));
	    list.addActionListener(this);
	    list.addItemListener(this);
	    

	    details = new TextField(); // Set up the details area
	    details.setFont(new Font("MonoSpaced", Font.PLAIN, 12));
	    details.setEditable(false);
	    details.setText(sftpChannel.getHome());
	    JPanel jp=new JPanel();
	    jp.setLayout(new BorderLayout());
	    
	    properties = new TextField(); // Set up the details area
	    properties.setFont(new Font("MonoSpaced", Font.PLAIN, 12));
	    properties.setEditable(false);
	    
	    buttons = new Panel(); // Set up the button box
	    buttons.setLayout(new BorderLayout());
	    buttons.setFont(new Font("SansSerif", Font.BOLD, 14));

	    up = new Button("Upload"); // Set up the two buttons
	    download = new Button("Download");
	    reconnect=new Button("Reconnect");
	    home=new Button("home");
	    newdir=new Button("Create Folder");
	    delete=new Button("delete");
	    buttons.add(home);
	    buttons.add(up); // Add buttons to button box
	    buttons.add(download);
	   // buttons.add(reconnect);
	    buttons.add(delete);
	    buttons.add(newdir);
		
	    up.addActionListener(this);
	    download.addActionListener(this);
	    home.addActionListener(this);
	    newdir.addActionListener(this);
	    //reconnect.addActionListener(this);
	    delete.addActionListener(this);

	    JToolBar jt=new JToolBar();
	    jt.add(home);
	   // jt.add(reconnect);
	    jt.addSeparator();
	    jt.add(up);
	    jt.add(download);
	    jt.add(delete);
	    jt.add(newdir);
	    
	   

	   // jp.add(buttons,"Center");
	    jp.add(details,"North");
	    jp.add(jt,"South");
	    
	    
	    
	    
	   
	    
	    sftpChannel.cd(sftpChannel.getHome());
         filelist = sftpChannel.ls(sftpChannel.getHome());
         
       ImageIcon io=new ImageIcon("images/folder.png");
        for(int i=1; i<filelist.size();i++){
            LsEntry entry = (LsEntry) filelist.get(i);
            SftpATTRS attr = entry.getAttrs();
            boolean isDir = attr.isDir();
            details.setText(sftpChannel.pwd());
            System.out.println(entry.getFilename());
            if(isDir)
            {
            list.add(entry.getFilename()); 
            }
            else {
            	list.add("-"+entry.getFilename()); 
			}
        }
	    
   
	    this.add(list,"Center"); // Add stuff to the window
	   // this.add(jt);
	   this.add(properties,"South");
	    this.add(jp,"North");
	    this.setSize(500, 350);
	    this.setVisible(true);
	    
	    
	    
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
		    
	}
	 
	
	
	
	
	
	
	public serverOpen(String username,String servername,int port,String key) {
		// TODO Auto-generated constructor stub
		try{
		this.username=username;
		this.key=key;
		this.servername=servername;
		this.port=port;
		
		jsch.addIdentity(key);
		session = jsch.getSession(username, servername, port);
        session.setConfig("StrictHostKeyChecking", "no");
      
        session.connect();
        
        Channel channel = session.openChannel("sftp");
        channel.connect();
       sftpChannel = (ChannelSftp) channel;
        
       this.SFTPWORKINGDIR=sftpChannel.getHome(); 
       WORKINGDIR=SFTPWORKINGDIR;
		
	    list = new List(12, false); // Set up the list
	    list.setFont(new Font("MonoSpaced", Font.PLAIN, 14));
	    list.addActionListener(this);
	    list.addItemListener(this);
	    

	    details = new TextField(); // Set up the details area
	    details.setFont(new Font("MonoSpaced", Font.PLAIN, 12));
	    details.setEditable(false);
	    details.setText(sftpChannel.getHome());
	    JPanel jp=new JPanel();
	    jp.setLayout(new BorderLayout());
	    
	    properties = new TextField(); // Set up the details area
	    properties.setFont(new Font("MonoSpaced", Font.PLAIN, 12));
	    properties.setEditable(false);
	    
	    buttons = new Panel(); // Set up the button box
	    buttons.setLayout(new BorderLayout());
	    buttons.setFont(new Font("SansSerif", Font.BOLD, 14));

	    up = new Button("Upload"); // Set up the two buttons
	    download = new Button("Download");
	    reconnect=new Button("Reconnect");
	    home=new Button("home");
	    newdir=new Button("Create Folder");
	    delete=new Button("delete");
	    buttons.add(home);
	    buttons.add(up); // Add buttons to button box
	    buttons.add(download);
	    //buttons.add(reconnect);
	    buttons.add(delete);
	    buttons.add(newdir);
		
	    up.addActionListener(this);
	    download.addActionListener(this);
	    home.addActionListener(this);
	    newdir.addActionListener(this);
	   // reconnect.addActionListener(this);
	    delete.addActionListener(this);

	    JToolBar jt=new JToolBar();
	    jt.add(home);
	    //jt.add(reconnect);
	    jt.addSeparator();
	    jt.add(up);
	    jt.add(download);
	    jt.add(delete);
	    jt.add(newdir);
	    
	   

	   // jp.add(buttons,"Center");
	    jp.add(details,"North");
	    jp.add(jt,"South");
	    
	    
	    
	    
	   
	    
	    sftpChannel.cd(sftpChannel.getHome());
         filelist = sftpChannel.ls(sftpChannel.getHome());
         
       ImageIcon io=new ImageIcon("images/folder.png");
        for(int i=1; i<filelist.size();i++){
            LsEntry entry = (LsEntry) filelist.get(i);
            SftpATTRS attr = entry.getAttrs();
            boolean isDir = attr.isDir();
            details.setText(sftpChannel.pwd());
            System.out.println(entry.getFilename());
            if(isDir)
            {
            list.add(entry.getFilename()); 
            }
            else {
            	list.add("-"+entry.getFilename()); 
			}
        }
	    
   
	    this.add(list,"Center"); // Add stuff to the window
	   // this.add(jt);
	   this.add(properties,"South");
	    this.add(jp,"North");
	    this.setSize(500, 350);
	    this.setVisible(true);
	    
	    
	    
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
		    
	}
	
	
	
	
	
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		properties.setText("");
		
		int i = list.getSelectedIndex();
		LsEntry entry1 = (LsEntry) filelist.get(i);
        
        SftpATTRS attr = entry1.getAttrs();
        long length = attr.getSize();
        boolean isDir = attr.isDir();
        if(!isDir)
        {
        String hrSize = null;
        long Kb = length/1024;
        long Mb = Kb / 1024;
        long Gb = Mb / 1024;
        long Tb = Gb / 1024;
        DecimalFormat dec = new DecimalFormat("0.00");
        if ( Tb>1 ) {
            hrSize = dec.format(Tb).concat(" TB");
        } else if ( Gb>1 ) {
            hrSize = dec.format(Gb).concat(" GB");
        } else if ( Mb>1 ) {
            hrSize = dec.format(Mb).concat(" MB");
        } else if ( Kb>1 ) {
            hrSize = dec.format(Kb).concat(" KB");
        } else {
            hrSize = dec.format(length).concat(" Bytes");
        }

       
   properties.setText(hrSize);
        }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int len=0;
		System.out.println(e.getSource());
		// TODO Auto-generated method stub
		if (e.getSource() == list) { // Double click on an item
		      int i = list.getSelectedIndex()+1; // Check which item
		     
		          try
		          {
		        	  LsEntry entry1 = (LsEntry) filelist.get(i);
		          
		            SftpATTRS attr = entry1.getAttrs();
		            long length = attr.getSize();
		            boolean isDir = attr.isDir();
		          
		        
		        if (isDir){
		        len=entry1.getFilename().length();
		        	WORKINGDIR=SFTPWORKINGDIR;
		        	SFTPWORKINGDIR=SFTPWORKINGDIR+"/"+entry1.getFilename();
		        	System.out.println(SFTPWORKINGDIR);
		        	
		        	 sftpChannel.cd(SFTPWORKINGDIR);
		        	 details.setText(sftpChannel.pwd());
		        	 filelist = sftpChannel.ls(SFTPWORKINGDIR);
		        	 list.removeAll();
		          
		           for(int k=1; k<filelist.size();k++){
		               LsEntry entry2 = (LsEntry) filelist.get(k);
		               SftpATTRS attr2 = entry2.getAttrs();
		               boolean isDir2 = attr2.isDir();
		               System.out.println(entry2.getFilename());
		               if(isDir2)
		               {
		               list.add(entry2.getFilename()); 
		               }
		               else {
		               	list.add("-"+entry2.getFilename()); 
		   			}
		           }
		        	
		        }
		          }catch(Exception e1)
		          {
		        	  JOptionPane.showMessageDialog(null,e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		        	  SFTPWORKINGDIR=WORKINGDIR;
		          }
	

}
		
		
		
		
		
		
		if(e.getSource() == delete)
		{
		
			try
			{
				int i = list.getSelectedIndex();
				LsEntry entry1 = (LsEntry) filelist.get(i);
		        SftpATTRS attr = entry1.getAttrs();
	            boolean isDir = attr.isDir();
	    	if(isDir)
	    	{
	    		int dialogResult = JOptionPane.showConfirmDialog (null, "Confirm Delete "+list.getSelectedItem(),"Warning",JOptionPane.YES_NO_OPTION);
	    		if(dialogResult == JOptionPane.YES_OPTION){
	    			sftpChannel.rmdir(list.getSelectedItem().toString());
	    			JOptionPane.showMessageDialog(null,"Deleted","Sucess",JOptionPane.OK_OPTION);
	    		}
			
			
        	list.removeAll();
        	
        	sftpChannel.cd(SFTPWORKINGDIR);
            filelist = sftpChannel.ls(SFTPWORKINGDIR);
           System.out.println("entry.getFilename()");
           for(int k=1; k<filelist.size();k++){
               LsEntry entry2 = (LsEntry) filelist.get(k);
               SftpATTRS attr2 = entry2.getAttrs();
	            boolean isDir2 = attr2.isDir();
               System.out.println(entry2.getFilename());
               if(isDir2)
               {
               list.add(entry2.getFilename()); 
               }
               else {
               	list.add("-"+entry2.getFilename()); 
   			}
               
           }
	    	}
	    	else
	    	{
	    		int dialogResult = JOptionPane.showConfirmDialog (null, "Confirm Delete "+list.getSelectedItem(),"Warning",JOptionPane.YES_NO_OPTION);
	    		if(dialogResult == JOptionPane.YES_OPTION){
	    			sftpChannel.rm(list.getSelectedItem().toString());
					JOptionPane.showMessageDialog(null,"Deleted","Sucess",JOptionPane.OK_OPTION);
	    		}
			
	    		
				
	        	list.removeAll();
	        	
	        	sftpChannel.cd(SFTPWORKINGDIR);
	            filelist = sftpChannel.ls(SFTPWORKINGDIR);
	           System.out.println("entry.getFilename()");
	           for(int k=0; k<filelist.size();k++){
	               LsEntry entry2 = (LsEntry) filelist.get(k);
	               SftpATTRS attr2 = entry2.getAttrs();
		            boolean isDir2 = attr2.isDir();
	               System.out.println(entry2.getFilename());
	               if(isDir)
	               {
	               list.add(entry2.getFilename()); 
	               }
	               else {
	               	list.add("-"+entry2.getFilename()); 
	   			}
	    	}
	    	}
			}catch(Exception delex)
			{
				JOptionPane.showMessageDialog(null,delex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			}
		}
		
		
		
		if(e.getSource() == newdir)
		{
			
		
			try
			{
				
			sftpChannel.mkdir(JOptionPane.showInputDialog("Please enter folder name").toString());
			JOptionPane.showMessageDialog(null,"New Folder Created","Sucess",JOptionPane.INFORMATION_MESSAGE);
			
        	list.removeAll();
        	
        	sftpChannel.cd(SFTPWORKINGDIR);
            filelist = sftpChannel.ls(SFTPWORKINGDIR);
           System.out.println("entry.getFilename()");
           for(int k=0; k<filelist.size();k++){
               LsEntry entry2 = (LsEntry) filelist.get(k);
               SftpATTRS attr = entry2.getAttrs();
               boolean isDir = attr.isDir();
               System.out.println(entry2.getFilename());
               if(isDir)
               {
               list.add(entry2.getFilename()); 
               }
               else {
               	list.add("-"+entry2.getFilename()); 
   			}
           }
           
           
			}catch(Exception delex)
			{
				JOptionPane.showMessageDialog(null,delex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			}
		}
		
		if(e.getSource()==up)
		{
			 try {  
				 JFileChooser j = new JFileChooser();
					j.setFileSelectionMode(JFileChooser.FILES_ONLY);
					 j.showOpenDialog(this);
		           String uploadFile= j.getSelectedFile().getAbsolutePath();
		           System.out.println(uploadFile);
		            File file = new File(uploadFile); 
		            FileInputStream fileInputStream = new FileInputStream(file);
		            sftpChannel.put(fileInputStream, file.getName());  
		            fileInputStream.close();  
		            fileInputStream=null; 
		            JOptionPane.showMessageDialog(null,"Upload Complete","Sucess",JOptionPane.INFORMATION_MESSAGE);
		            list.removeAll();
		        	
		        	sftpChannel.cd(SFTPWORKINGDIR);
		            filelist = sftpChannel.ls(SFTPWORKINGDIR);
		           System.out.println("entry.getFilename()");
		           for(int k=0; k<filelist.size();k++){
		               LsEntry entry2 = (LsEntry) filelist.get(k);
		               SftpATTRS attr = entry2.getAttrs();
		               boolean isDir = attr.isDir();
		               System.out.println(entry2.getFilename());
		               if(isDir)
		               {
		               list.add(entry2.getFilename()); 
		               }
		               else {
		               	list.add("-"+entry2.getFilename()); 
		   			}
		           }
		        } catch (Exception eup) {  
		            eup.printStackTrace();  
		        }  
		}
		
		if(e.getSource()==download)
		{
			int i = list.getSelectedIndex();
			LsEntry entry1 = (LsEntry) filelist.get(i);
	        SftpATTRS attr = entry1.getAttrs();
            boolean isDir = attr.isDir();
    	if(!isDir)
    	{
			try {  
				JFileChooser j = new JFileChooser();
				j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				 j.showSaveDialog(this);
				 System.out.println(j.getSelectedFile()+"/"+list.getSelectedItem().substring(1, list.getSelectedItem().length()));
				 String saveFile=j.getSelectedFile()+"/"+list.getSelectedItem().substring(1, list.getSelectedItem().length());
				 File file = new File(saveFile);
				 System.out.println(saveFile);
	            FileOutputStream fileOutputStream =new FileOutputStream(file);  
	            sftpChannel.get(list.getSelectedItem().substring(1, list.getSelectedItem().length()), fileOutputStream );  
	            fileOutputStream.close();  
	            JOptionPane.showMessageDialog(null,"Download Complete","Sucess",JOptionPane.INFORMATION_MESSAGE);
	            fileOutputStream=null;  
	            file =null;  
	        } catch (Exception ex) {  
	        	JOptionPane.showMessageDialog(null,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
	        }  
    	}
    	else {
    		JOptionPane.showMessageDialog(null,"Cannot download ","Error",JOptionPane.ERROR_MESSAGE);
		}
		}
		
		if(e.getSource() == home)
		{
			try
			{
				list.removeAll();
				SFTPWORKINGDIR=sftpChannel.getHome();
			    sftpChannel.cd(sftpChannel.getHome());
			    System.out.println(sftpChannel.getHome());
		         filelist = sftpChannel.ls(sftpChannel.getHome());
		         
		         details.setText(sftpChannel.pwd());
		        for(int i=1; i<filelist.size();i++){
		            LsEntry entry = (LsEntry) filelist.get(i);
		            SftpATTRS attr = entry.getAttrs();
		            boolean isDir = attr.isDir();
		            
		            if(isDir)
		            {
		            list.add(entry.getFilename()); 
		            }
		            else {
		            	list.add("-"+entry.getFilename()); 
					}
		        }	
			}catch(Exception h1)
			{
				System.out.println(h1);
			}
			
		}
		
	}
}
