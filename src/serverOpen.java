import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Vector;

import javax.swing.JFrame;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class serverOpen extends JFrame implements ActionListener, ItemListener {

	TextArea textarea; 
	List list;
	Panel buttons;
	TextField details;
	Button up, close;
	String username,servername,password, SFTPWORKINGDIR;
	int port;
	String[] files;
	File currentDir;
	
	 JSch jsch = new JSch();
	 Session session = null;
	public serverOpen(String username,String servername,int port,String password,String SFTPWORKINGDIR) {
		// TODO Auto-generated constructor stub
		try{
		this.username=username;
		this.password=password;
		this.servername=servername;
		this.port=port;
		session = jsch.getSession(username, servername, port);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setPassword(password);
        session.connect();

        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftpChannel = (ChannelSftp) channel;
        
        sftpChannel.cd(SFTPWORKINGDIR);
        Vector filelist = sftpChannel.ls(SFTPWORKINGDIR);
        for(int i=0; i<filelist.size();i++){
            LsEntry entry = (LsEntry) filelist.get(i);
            System.out.println(entry.getFilename());
            
        }
        
		}catch(Exception e)
		{
			System.out.println(e);
		}
	    list = new List(12, false); // Set up the list
	    list.setFont(new Font("MonoSpaced", Font.PLAIN, 14));
	    list.addActionListener(this);
	    list.addItemListener(this);

	    details = new TextField(); // Set up the details area
	    details.setFont(new Font("MonoSpaced", Font.PLAIN, 12));
	    details.setEditable(false);

	    buttons = new Panel(); // Set up the button box
	    buttons.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 5));
	    buttons.setFont(new Font("SansSerif", Font.BOLD, 14));

	    up = new Button("Up a Directory"); // Set up the two buttons
	    close = new Button("Close");
	    up.addActionListener(this);
	    close.addActionListener(this);

	    buttons.add(up); // Add buttons to button box
	    buttons.add(close);

	    this.add(list, "Center"); // Add stuff to the window
	    this.add(details, "North");
	    this.add(buttons, "South");
	    this.setSize(500, 350);
	    this.setVisible(true);
	    listDirectory();
		    
	}
	  public void listDirectory() {
		    // Convert the string to a File object, and check that the dir exists
		    File dir = new File(SFTPWORKINGDIR);
		    if (!dir.isDirectory())
		      throw new IllegalArgumentException("FileLister: no such directory");

		    // Get the (filtered) directory entries
		    files = dir.list();

		    // Sort the list of filenames.
		    java.util.Arrays.sort(files);

		    // Remove any old entries in the list, and add the new ones
		    list.removeAll();
		    list.add("[Up to Parent Directory]"); // A special case entry
		    for (int i = 0; i < files.length; i++)
		      list.add(files[i]);

		    // Display directory name in window titlebar and in the details box
		    this.setTitle(SFTPWORKINGDIR);
		    details.setText(SFTPWORKINGDIR);

		    // Remember this directory for later.
		    currentDir = dir;
		  }
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	/*	if (e.getSource() == list) { // Double click on an item
		      int i = list.getSelectedIndex(); // Check which item
		      if (i == 0)
		        
		      else { // Otherwise, get filename
		        String name = files[i - 1];
		        File f = new File(currentDir, name); // Convert to a File
		        String fullname = f.getAbsolutePath();
		        if (f.isDirectory())
		          listDirectory(fullname); // List dir
		        else
		          new FileViewer(fullname).show(); // display file*/
	}

}
