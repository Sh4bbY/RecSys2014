package view;

import helper.Statics;

import java.awt.BorderLayout;
import java.awt.Component;
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

import main.Analysis;

import org.jfree.chart.ChartPanel;

public class AnalizeFrame extends JFrame
{
	private static final long	serialVersionUID	= 1L;

	private final String windowTitle = "Chart Frame"; 
	private final int width = 800, height = 500;
	private List<Integer> seriesSelections;
	private ActionListener loadDataBtnListener, printBtnListener, orderDataBtnListener, filterDataBtnListener;
	private MouseAdapter listClickListener;
	private Analysis analysis;
	
	private JLabel status;
	private JPanel controlPanel;
	private JButton loadDataButton, orderDataButton, filterDataButton, printDataButton;
	private JList<String> seriesList;
	private FilterDialog filterDialog;
	private OrderDialog orderDialog;
	
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
		
		filterDataButton = new JButton("Filter Data");
		filterDataButton.addActionListener(filterDataBtnListener);
		filterDataButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		filterDataButton.setVisible(false);
		
		orderDataButton = new JButton("Order Data");
		orderDataButton.addActionListener(orderDataBtnListener);
		orderDataButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		orderDataButton.setVisible(false);
		
		printDataButton = new JButton("Print Data");
		printDataButton.addActionListener(printBtnListener);
		printDataButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		printDataButton.setVisible(false);
		
		filterDialog = new FilterDialog(analysis);
		orderDialog = new OrderDialog(analysis);
		
		status = new JLabel("test");
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
				orderDataButton.setVisible(true);
				printDataButton.setVisible(true);
				filterDataButton.setVisible(true);
				seriesList.setVisible(true);
			}		
		};
		
		filterDataBtnListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				filterDialog.setVisible(true);
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
				analysis.drawChart(series);
			}		
		};
		
		orderDataBtnListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				orderDialog.setVisible(true);			
			}		
		};
		
		listClickListener = new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent evt) 
			{
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
		        }
		        
		        list.repaint();
		    }
		};
		
	}
	
	private void attachElements()
	{
		controlPanel.add(loadDataButton);
		controlPanel.add(seriesList);
		controlPanel.add(orderDataButton);
		controlPanel.add(filterDataButton);
		controlPanel.add(printDataButton);
		
		this.add(controlPanel, BorderLayout.EAST);
		this.add(status, BorderLayout.SOUTH);
		
	}
	
	public void setStatus(String status)
	{
		this.status.setText(status);
	}
	
	public void setChart(ChartPanel cPanel)
	{
		this.add(cPanel,BorderLayout.CENTER);
		this.setVisible(true);
	}
}
