package view;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import charting.ChartFilter;
import charting.attributes.Attribute;
import charting.attributes.MovieAttr;
import charting.attributes.RatingAttr;
import charting.attributes.UserAttr;
import charting.attributes.XAxis;

public class FilterPanel extends JPanel
{
	private static final long	serialVersionUID	= 1L;
	
	private JComboBox<String> filterAttribute;
	private JComboBox<String> operator;
	private JTextField valueField;
	private JButton addFilterBtn;
	private ActionListener addFilterBtnListener;
	private ArrayList<ArrayList<ChartFilter>> chartFilters;
	private JList<String> filterList;
	private DefaultListModel<String> listModel;
	private MouseAdapter listClickListener;
	private int currentXaxisIdx;
	
	public FilterPanel()
	{
		chartFilters = new ArrayList<ArrayList<ChartFilter>>();
		
		ArrayList<ChartFilter> defaultFilter = new ArrayList<ChartFilter>();
		defaultFilter.add(new ChartFilter(10, ChartFilter.LTE, RatingAttr.Rating));
		defaultFilter.add(new ChartFilter(0, ChartFilter.GTE, RatingAttr.Rating));
		
		chartFilters.add(defaultFilter);
		chartFilters.add(new ArrayList<ChartFilter>());
		chartFilters.add(new ArrayList<ChartFilter>());
		
		
				
		createListeners();
		initialize();
		createElements();
		attachElements();
	}
	
	private void initialize()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new TitledBorder("Filter"));
		setAlignmentY(JPanel.TOP_ALIGNMENT);
		setPreferredSize(new Dimension(150, 300));
	}
	
	private void createElements()
	{
		filterAttribute = new JComboBox<String>();
		ConfigDialog.setComponentMaxHeight(filterAttribute);
		
		listModel = new DefaultListModel<String>();
		filterList = new JList<String>(listModel);
		filterList.addMouseListener(listClickListener);
		
		
		operator = new JComboBox<String>();
		operator.addItem("<");
		operator.addItem("\u2264");
		operator.addItem("=");
		operator.addItem("\u2265");
		operator.addItem(">");
		ConfigDialog.setComponentMaxHeight(operator);
		
		valueField = new JTextField();
		ConfigDialog.setComponentMaxHeight(valueField);
		
		addFilterBtn = new JButton("Add Filter");
		addFilterBtn.addActionListener(addFilterBtnListener);
	}
	
	private void createListeners()
	{		
		addFilterBtnListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int attrIdx = filterAttribute.getSelectedIndex();
				int operatorIdx = operator.getSelectedIndex();
				
				chartFilters.get(currentXaxisIdx).add(new ChartFilter(Double.parseDouble(valueField.getText()), operatorIdx, attrIdx));
				listModel.addElement(filterAttribute.getItemAt(attrIdx)+" "+operator.getItemAt(operatorIdx)+" "+valueField.getText());
			}
		};
		
		listClickListener = new MouseAdapter() 
		{
		    public void mouseClicked(MouseEvent e) 
		    {		      
		        if (e.getClickCount() == 2) 
		        {
			    	int index = filterList.locationToIndex(e.getPoint());
			    	
			    	listModel.remove(index);
			    	chartFilters.remove(index);
		        }
		    }
		};
	}
	
	private void attachElements()
	{
		add(filterAttribute);
		add(operator);
		add(valueField);
		add(addFilterBtn);
		add(filterList);
	}
	
	public void updateXAxis(XAxis xAxis)
	{
		filterAttribute.removeAllItems();
		
		listModel.removeAllElements();
		
		Attribute[] attributes = null;
		
		switch(xAxis)
		{
			case Rating: attributes = RatingAttr.values(); 	currentXaxisIdx = 0;break;
			case User: attributes = UserAttr.values(); 		currentXaxisIdx = 1;break;
			case Movie: attributes = MovieAttr.values(); 	currentXaxisIdx = 2;break;
		}
		
		for(int i=0;i<attributes.length;i++)
		{
			filterAttribute.addItem(attributes[i].toString());
		}
		
		for(ChartFilter filter : chartFilters.get(currentXaxisIdx))
		{
			listModel.addElement(filterAttribute.getItemAt(filter.getAttrIndex())+" "+operator.getItemAt(filter.getOperator())+" "+filter.getValue());
		}	
	}

	public ChartFilter[] getFilters()
	{
		ChartFilter[] filterArray = new ChartFilter[chartFilters.get(currentXaxisIdx).size()];
		
		return chartFilters.get(currentXaxisIdx).toArray(filterArray);
	}
	
	public void setFilters(ChartFilter[] filters)
	{
		chartFilters.get(currentXaxisIdx).clear();
		listModel.clear();
		
		ChartFilter filter;
		
		for(int i=0;i<filters.length;i++)
		{
			filter = filters[i];
			chartFilters.get(currentXaxisIdx).add(filter);
			listModel.addElement(filterAttribute.getItemAt(filter.getAttrIndex())+" "+operator.getItemAt(filter.getOperator())+" "+filter.getValue());
		}
	}
}
