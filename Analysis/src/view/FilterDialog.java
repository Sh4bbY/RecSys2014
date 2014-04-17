package view;

import helper.Statics;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import main.Analysis;

public class FilterDialog extends JDialog
{
	private static final long	serialVersionUID	= 1L;

	private Analysis analysis;
	
	private JButton filterBtn, cancelBtn;
	private JPanel btnPanel;
	private JComboBox<String> filterAttribute;
	
	private ActionListener filterBtnListener, cancelBtnListener;
	
	private int width = 400, height = 200;
	
	public FilterDialog(Analysis analysis)
	{
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
		filterAttribute = new JComboBox<String>(Analysis.ATTRIBUTES);
		btnPanel = new JPanel();
		
		filterBtn = new JButton("Filter");	
		filterBtn.addActionListener(filterBtnListener);
		
		cancelBtn = new JButton("Cancel");	
		cancelBtn.addActionListener(cancelBtnListener);
	}
	
	private void createListeners()
	{		
		filterBtnListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//analysis.setFilter(false);
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
		btnPanel.add(filterBtn);
		btnPanel.add(cancelBtn);
		this.add(btnPanel, BorderLayout.SOUTH);
	}
}
