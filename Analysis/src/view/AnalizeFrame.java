package view;

import helper.Statics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import main.Analysis;

import org.jfree.chart.ChartPanel;

public class AnalizeFrame extends JFrame
{
	private static final long	serialVersionUID	= 1L;

	private final String windowTitle = "Chart Frame"; 
	private final int width = 800, height = 500;
	private List<Integer> seriesSelections;
	private ActionListener loadDataBtnListener, printBtnListener, configDataBtnListener;
	private MouseAdapter listClickListener;
	private JTabbedPane chartTabs;
	private Analysis analysis;
	private ChartPanel currentChart;
	
	private JScrollPane logPane;
	private JTextArea logArea;
	private JPanel controlPanel;
	private JButton loadDataButton, configDataButton, printDataButton;
	private JList<String> seriesList;
	
	private ConfigDialog configDialog;
	
	public AnalizeFrame(Analysis analysis)
	{
		this.analysis = analysis;
		
		seriesSelections = new ArrayList<Integer>();
		
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
		
		DefaultListModel<String> seriesModel = new DefaultListModel<String>();
		for(String name : Analysis.ATTRIBUTES)
		{
			seriesModel.addElement(name);
		}
		seriesList = new JList<String>(seriesModel);
		seriesList.setAlignmentX(Component.LEFT_ALIGNMENT);
		seriesList.addMouseListener(listClickListener);
		seriesList.setCellRenderer(new SeriesListCellRenderer(seriesSelections));
		seriesList.setVisible(false);

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
		
		configDialog = new ConfigDialog(analysis);
		
		logArea = new JTextArea();
		logArea.setEditable(false);
		
		logPane = new JScrollPane(logArea);
		logPane.setPreferredSize(new Dimension (width, 50));

		chartTabs = new JTabbedPane();
				
		//logPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		//logPane.setSize(width, 100);
	}
	
	private void createListeners()
	{
		loadDataBtnListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String s = (String)JOptionPane.showInputDialog("FileName:","/assets/training.dat");
				
				if(s == null)
				{
					return;
				}

				analysis.readData(s);
				printDataButton.setVisible(true);
				configDataButton.setVisible(false);
				seriesList.setVisible(true);
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
				int seriesAmount = seriesSelections.size();
				String[] series = new String[seriesAmount];
				for(int i =0; i<seriesAmount; i++)
				{
					series[i] = Analysis.ATTRIBUTES[seriesSelections.get(i)];
				}
				analysis.drawChart();
			}		
		};
		
		listClickListener = new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent evt) 
			{
		        @SuppressWarnings("unchecked")
				JList<String> list = (JList<String>)evt.getSource();
		        int selectionIndex = -1;
		        int listIndex;
		        
		        if (evt.getClickCount() == 2) 
		        {
		        	selectionIndex = list.locationToIndex(evt.getPoint());
		        	listIndex = seriesSelections.indexOf(selectionIndex);
		            
		            if(listIndex != -1)
		            {
		            	seriesSelections.remove(listIndex);
		            }
		            else
		            {
		            	seriesSelections.add(selectionIndex);
		            }
			        list.repaint();
		        }
		        
		    }
		};
		
	}
	
	private void attachElements()
	{
		controlPanel.add(loadDataButton);
		controlPanel.add(seriesList);
		controlPanel.add(configDataButton);
		controlPanel.add(printDataButton);
		
		this.add(controlPanel, BorderLayout.EAST);

		this.add(logPane, BorderLayout.SOUTH);
		this.add(chartTabs,BorderLayout.CENTER);		
	}
	
	public void addChart(ChartPanel cPanel)
	{
		if(currentChart != null)
		{
			chartTabs.remove(currentChart);
		}
		
		chartTabs.add(cPanel);
		currentChart = cPanel;
	}
}
