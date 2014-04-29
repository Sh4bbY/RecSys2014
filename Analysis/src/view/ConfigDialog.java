package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import helper.Statics;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import charting.ChartConfiguration;
import main.Analysis;

public class ConfigDialog extends JDialog
{
	private static final long	serialVersionUID	= 1L;
	
	private Analysis analysis;
	
	private FilterPanel filterPanel;
	private OrderPanel orderPanel;
	private AxisPanel axisPanel;

	private ActionListener okBtnListener, cancelBtnListener;
	private JButton okBtn, cancelBtn;
	private JPanel configPanel, btnPanel;
	private ChartConfiguration config;
	
	private int width = 600, height = 400;
	
	public ConfigDialog(Analysis analysis)
	{
		this.config = new ChartConfiguration();
		this.analysis = analysis;

		createListeners();
		initialize();
		createElements();
		attachElements();
	}
	private void initialize()
	{
		this.setSize(width, height);
		this.setLayout(new BorderLayout());
		this.setLocation(Statics.getCenterLocation(this.getWidth(), this.getHeight()));
	}
	
	private void createElements()
	{
		okBtn = new JButton("OK");	
		okBtn.addActionListener(okBtnListener);
		
		cancelBtn = new JButton("Cancel");	
		cancelBtn.addActionListener(cancelBtnListener);
		
		configPanel = new JPanel();
		configPanel.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		btnPanel = new JPanel();
		configPanel.setLayout(new FlowLayout());

		axisPanel = new AxisPanel();
		orderPanel = new OrderPanel();
		filterPanel = new FilterPanel();
	}

	private void createListeners()
	{		
		okBtnListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				config.setXAxis(axisPanel.getXAxis());
				config.setYAttributes(axisPanel.getYAttributes());
				config.setOrder(orderPanel.getOrder());
				config.setFilter(filterPanel.getFilter());
				
				setVisible(false);
			}		
		};
		
		cancelBtnListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
			}		
		};
	}
	
	private void attachElements()
	{
		configPanel.add(axisPanel);
		configPanel.add(orderPanel);
		configPanel.add(filterPanel);
		
		btnPanel.add(okBtn);
		btnPanel.add(cancelBtn);

		this.add(configPanel, BorderLayout.CENTER);
		this.add(btnPanel, BorderLayout.SOUTH);
	}
	
}
