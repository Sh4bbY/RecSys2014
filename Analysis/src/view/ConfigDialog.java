package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import helper.DataManager;
import helper.Statics;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import charting.ChartConfiguration;
import charting.attributes.XAxis;

public class ConfigDialog extends JDialog
{
	private static final long	serialVersionUID	= 1L;
	
	private FilterPanel filterPanel;
	private OrderPanel orderPanel;
	private AxisPanel axisPanel;

	private ActionListener okBtnListener, cancelBtnListener, loadConfigBtnListener, saveConfigBtnListener;
	private JButton okBtn, cancelBtn, loadConfigBtn, saveConfigBtn;
	private JPanel configPanel, btnPanel;
	private ChartConfiguration config;
	private JFileChooser fileChooser;
	
	private int width = 600, height = 400;
	
	public ConfigDialog(ChartConfiguration config)
	{
		this.config = config;
		fileChooser = new JFileChooser(new File("").getAbsolutePath());
		
		createListeners();
		initialize();
		createElements();
		attachElements();
		

		axisPanel.setXAxis(XAxis.Rating);
		okBtn.doClick();
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
		
		loadConfigBtn = new JButton("Load Config");	
		loadConfigBtn.addActionListener(loadConfigBtnListener);
		
		saveConfigBtn = new JButton("Save Config");	
		saveConfigBtn.addActionListener(saveConfigBtnListener);
		
		configPanel = new JPanel();
		configPanel.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		btnPanel = new JPanel();
		configPanel.setLayout(new FlowLayout());

		axisPanel = new AxisPanel(this);
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
				applyConfig(config);
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
		
		loadConfigBtnListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int result = fileChooser.showOpenDialog((JButton)e.getSource());
				
				if(result == JFileChooser.APPROVE_OPTION)
				{
					ChartConfiguration config = (ChartConfiguration)DataManager.loadFromFile(fileChooser.getSelectedFile().getAbsolutePath());
					applyConfigToGUI(config);
				}
			}		
		};
		
		saveConfigBtnListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int result = fileChooser.showSaveDialog((JButton)e.getSource());
				
				if(result == JFileChooser.APPROVE_OPTION)
				{
					DataManager.storeToFile(fileChooser.getSelectedFile().getAbsolutePath(), applyConfig(new ChartConfiguration()));
				}
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
		btnPanel.add(loadConfigBtn);
		btnPanel.add(saveConfigBtn);

		this.add(configPanel, BorderLayout.CENTER);
		this.add(btnPanel, BorderLayout.SOUTH);
	}
	
	public void xAxisUpdate(XAxis xAxis)
	{
		orderPanel.updateXAxis(xAxis);
		filterPanel.updateXAxis(xAxis);
	}

	public static void setComponentMaxHeight(JComponent jc)
	{
		Dimension max = jc.getMaximumSize();
		max.height = jc.getPreferredSize().height;
		
		jc.setMaximumSize(max); 
	}
	
	private ChartConfiguration applyConfig(ChartConfiguration config)
	{
		config.setXAxis(axisPanel.getXAxis());
		config.setSelectedAttributes(axisPanel.getSelectedAttributes());
		config.setOrderASC(orderPanel.isOrderASC());
		config.setSortingAttrIndex(orderPanel.getOrderAttrIndex());
		config.setFilters(filterPanel.getFilters());
		return config;
	}
	
	private void applyConfigToGUI(ChartConfiguration config)
	{
		axisPanel.setXAxis(config.getXAxis());
		axisPanel.setSelectedAttributes(config.getSelectedAttributes());
		orderPanel.setOrderASC(config.isSortingASC());
		orderPanel.setOrderAttrIndex(config.getSortingAttrIndex());
		filterPanel.setFilters(config.getFilters());
	}
}
