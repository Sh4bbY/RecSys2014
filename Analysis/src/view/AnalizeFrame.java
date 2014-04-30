package view;

import helper.Statics;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
	
	private ActionListener loadDataBtnListener, printBtnListener, configDataBtnListener;
	private JTabbedPane chartTabs;
	private Analysis analysis;
	private ArrayList<ChartPanel> chartPanels;
	
	private JScrollPane logPane;
	private JTextArea logArea;
	private JPanel controlPanel;
	private JButton loadDataButton, configDataButton, printDataButton;
	private JFileChooser fileChooser;
	
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
		loadDataButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		configDataButton = new JButton("Configuration");
		configDataButton.addActionListener(configDataBtnListener);
		configDataButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		configDataButton.setVisible(true);
		
		printDataButton = new JButton("Print Chart");
		printDataButton.addActionListener(printBtnListener);
		printDataButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		printDataButton.setVisible(false);
		
		configDialog = new ConfigDialog(analysis.getConfig());
		
		logArea = new JTextArea();
		logArea.setEditable(false);
		
		logPane = new JScrollPane(logArea);
		logPane.setPreferredSize(new Dimension (width, 50));

		chartTabs = new JTabbedPane();
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
	}
	
	private void attachElements()
	{
		controlPanel.add(loadDataButton);
		controlPanel.add(configDataButton);
		controlPanel.add(printDataButton);
		
		this.add(controlPanel, BorderLayout.EAST);

		this.add(logPane, BorderLayout.SOUTH);
		this.add(chartTabs,BorderLayout.CENTER);		
	}
	
	public void addChart(ChartPanel cPanel)
	{
		cPanel.setName("Chart #"+(chartPanels.size()+1));
		chartPanels.add(cPanel);
		chartTabs.add(cPanel);
	}
}
