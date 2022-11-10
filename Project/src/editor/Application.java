package editor;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;

public class Application extends JFrame
{

    public static BufferedImage depthMap;


    static {
        try {
            depthMap = ImageIO.read(new File("rsrc/image.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void createComponents()
    {
        setSize(1024,800);
        setTitle("Application");
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        JPanel mainView = new JPanel(new GridLayout(2,2,1,1));

        JToolBar tb = new JToolBar();
        JButton test1 = new JButton("Open Depth Map");
        JButton writeout = new JButton("Write XYZ");

        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Please select depth map image...");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image file", "jpeg", "jpg", "png", "bmp", "gif");
        fc.addChoosableFileFilter(filter);

        JFileChooser fc1 = new JFileChooser();
        fc1.setDialogTitle("Please select file to write to");
        FileNameExtensionFilter filter1 = new FileNameExtensionFilter("3D file", "xyz");
        fc1.addChoosableFileFilter(filter1);


        tb.add(test1);
        tb.add(writeout);
        test1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int returnVal = fc.showOpenDialog(test1);
                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    File file = fc.getSelectedFile();
                    System.out.println("Reading: "+file.getName());
                    try
                    {
                        depthMap = ImageIO.read(file);

                    }catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
        });
        writeout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int returnVal = fc1.showSaveDialog(writeout);
                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    File file = fc1.getSelectedFile();
                    System.out.println(file.getAbsolutePath());
                    try (FileWriter writer = new FileWriter(file.getName());
                         BufferedWriter bw = new BufferedWriter(writer))
                    {

                        if(Application.depthMap != null)
                        {
                            for (int x = 0; x < Application.depthMap.getWidth(); x++)
                                for (int y = 0; y < Application.depthMap.getHeight(); y++)
                                {
                                    Color extract = new Color(Application.depthMap.getRGB(x,y));
                                    int depth = extract.getRed();
                                    bw.write(x+" "+y+" "+depth+"\n");
                                }
                        }
                    } catch (IOException ea)
                    {
                        System.err.format("IOException: %s%n", ea);
                    }
                }
            }
        });


        JPanel view,view2,view3;
        view = new Editor();
        view.setPreferredSize(new Dimension(200,200));
        view2 = new PerspectiveView();
        view2.setPreferredSize(new Dimension(200,200));
        view3 = new MeshView();
        view3.setPreferredSize(new Dimension(200,200));


        view.setBorder(BorderFactory.createLineBorder(Color.black));
        view2.setBorder(BorderFactory.createLineBorder(Color.black));
        view3.setBorder(BorderFactory.createLineBorder(Color.black));

        mainView.add(view2);
        mainView.add(view);
        mainView.add(view3);


        this.add(tb,BorderLayout.NORTH);
        this.add(mainView,BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args)
    {
        System.setProperty("sun.java2d.opengl", "true");
        Application application = new Application();
        System.out.println("Generating views");
        application.createComponents();
    }
}
