package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import helper.Statics;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import charting.ChartConfiguration;

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
				config.setRatingAttributes(axisPanel.getRatingAttributes());
				config.setUserAttributes(axisPanel.getUserAttributes());
				config.setMovieAttributes(axisPanel.getMovieAttributes());
				config.setOrderASC(orderPanel.isOrderASC());
				//config.setSortingAttribute(orderPanel.isOrderASC());
				//config.setOrderAttribute(orderPanel.getOrderAttribute());
				//config.setFilter(filterPanel.getFilter());
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
				fileChooser.showOpenDialog((JButton)e.getSource());
			}		
		};
		
		saveConfigBtnListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				fileChooser.showSaveDialog((JButton)e.getSource());
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
	
}
