package view;

import helper.Statics;
import helper.TextAreaAppender;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import main.Analysis;

import org.jfree.chart.ChartPanel;

public class AnalizeFrame extends JFrame
{
	private static final long	serialVersionUID = 1L;

	private final String windowTitle = "Chart Frame"; 
	private final String loadFilePath = "/assets/test.dat";
	private final int width = 800, height = 500;
	
	private ActionListener loadDataBtnListener, printBtnListener, configDataBtnListener, createSolutionBtnListener, compareSolutionBtnListener;
	private JTabbedPane chartTabs;
	private Analysis analysis;
	private ArrayList<ChartPanel> chartPanels;
	
	private JScrollPane logPane;
	private JTextArea logArea;
	private JPanel controlPanel;
	private JButton loadDataButton, configDataButton, printDataButton, createSolutionButton, compareSolutionButton;
	private JFileChooser fileChooser;
	private MouseAdapter tabListener;
	
	private ConfigDialog configDialog;
	
	public AnalizeFrame(Analysis analysis)
	{
		this.analysis = analysis;
		this.chartPanels = new ArrayList<ChartPanel>();
		this.fileChooser = new JFileChooser(new File("").getAbsolutePath() + loadFilePath);
		
		createListeners();
		initialize();
		createElements();
		attachElements();
		
		this.setVisible(true);
	}
	
	private void initialize()
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(width, height);
		this.setTitle(windowTitle);
		this.setLocation(Statics.getCenterLocation(this.getWidth(), this.getHeight()));
		this.setLayout(new BorderLayout());
	}
	
	private void createElements()
	{		
		controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
				
		loadDataButton = new JButton("Load Data");
		loadDataButton.addActionListener(loadDataBtnListener);
		
		configDataButton = new JButton("Configuration");
		configDataButton.addActionListener(configDataBtnListener);
		configDataButton.setVisible(true);

		printDataButton = new JButton("Print Chart");
		printDataButton.addActionListener(printBtnListener);
		printDataButton.setVisible(false);
		
		createSolutionButton = new JButton("Create Solution");
		createSolutionButton.addActionListener(createSolutionBtnListener);
		createSolutionButton.setVisible(false);
		
		compareSolutionButton = new JButton("Compare Solution");
		compareSolutionButton.addActionListener(compareSolutionBtnListener);
		compareSolutionButton.setVisible(false);
		
		configDialog = new ConfigDialog(analysis.getConfig());
		
		logArea = new JTextArea();
		logArea.setEditable(false);
		TextAreaAppender.setTextArea(logArea);
		
		logPane = new JScrollPane(logArea);
		logPane.setPreferredSize(new Dimension (width, 50));

		chartTabs = new JTabbedPane();
		chartTabs.addMouseListener(tabListener);
	}
	
	private void createListeners()
	{
		loadDataBtnListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//String s = (String)JOptionPane.showInputDialog("FileName:",loadFilePath);
				int fcResult = fileChooser.showOpenDialog((JButton)e.getSource());
				if(fcResult == JFileChooser.APPROVE_OPTION)
				{
					analysis.readData(fileChooser.getSelectedFile());
					printDataButton.setVisible(true);
					createSolutionButton.setVisible(true);
					compareSolutionButton.setVisible(true);
				}
			}		
		};
		
		configDataBtnListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				configDialog.setVisible(true);
			}		
		};
		
		printBtnListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				analysis.drawChart();
			}		
		};		
		
		tabListener = new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e) 
		    {		      
		        if (e.getClickCount() == 2) 
		        {
			    	int index = chartTabs.indexAtLocation(e.getX(), e.getY());
			    	
			    	chartTabs.remove(index);
			    	chartPanels.remove(index);
		        }
		    }
		};

		createSolutionBtnListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				analysis.createSolution();
			}		
		};		
		
		compareSolutionBtnListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				analysis.compareSolution();
			}		
		};		
	}
	
	private void attachElements()
	{
		controlPanel.add(loadDataButton);
		controlPanel.add(configDataButton);
		controlPanel.add(printDataButton);
		controlPanel.add(createSolutionButton);
		controlPanel.add(compareSolutionButton);
		
		this.add(controlPanel, BorderLayout.EAST);

		this.add(logPane, BorderLayout.SOUTH);
		this.add(chartTabs,BorderLayout.CENTER);		
	}
	
	public void addChart(ChartPanel cPanel)
	{
		cPanel.setName("Chart #"+(chartPanels.size()+1));
		chartPanels.add(cPanel);
		chartTabs.add(cPanel);
		chartTabs.setSelectedIndex(chartTabs.getTabCount()-1);
	}
}
