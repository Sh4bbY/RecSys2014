package view;

import java.awt.Checkbox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import charting.attributes.Attribute;
import charting.attributes.MovieAttr;
import charting.attributes.RatingAttr;
import charting.attributes.UserAttr;

public class AxisPanel extends JPanel
{
	private static final long	serialVersionUID	= 1L;
	private final String RATING = "Rating", USER = "User", MOVIE = "Movie"; 
	
	private JRadioButton[] xAttributes;
	private JCheckBox[][] yAttributes;
	private JPanel[] attributePanel;
	private JLabel xTitle,yTitle;
	private ButtonGroup bGroup;
	private ActionListener radioBtnListener;
	
	public AxisPanel()
	{
		initialize();
		createListeners();
		createElements();
		attachElements();
	}
	
	private void initialize()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new TitledBorder("Axis"));
		setAlignmentY(JPanel.TOP_ALIGNMENT);
	}
	
	private void createListeners()
	{
		radioBtnListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String btnText = ((JRadioButton)e.getSource()).getText();	
				
				switch(btnText)
				{
					case RATING:	showAttributePanel(0); break;
					case USER:		showAttributePanel(1); break;
					case MOVIE:		showAttributePanel(2); break;
				}
			}
		};
	}
	
	private void showAttributePanel(int i)
	{
		for(JPanel panel : attributePanel)
		{
			panel.setVisible(false);
		}
		attributePanel[i].setVisible(true);
	}
	
	private void createElements()
	{
		xTitle = new JLabel("X-Axis");
		yTitle = new JLabel("Y-Axis");
		
		bGroup = new ButtonGroup();
		

		xAttributes = new JRadioButton[3];
		attributePanel = new JPanel[3];
		Attribute[] attrArr = null;

		yAttributes = new JCheckBox[3][];
		
		for(int i=0; i<3; i++)
		{
			attributePanel[i] = new JPanel();
			attributePanel[i].setVisible(false);
			attributePanel[i].setLayout(new BoxLayout(attributePanel[i], BoxLayout.Y_AXIS));
			
			xAttributes[i] = new JRadioButton();
			xAttributes[i].addActionListener(radioBtnListener);
			bGroup.add(xAttributes[i]);
			
			switch(i)
			{
				case 0: attrArr = RatingAttr.values(); 	xAttributes[i].setText(RATING); break;
				case 1: attrArr = UserAttr.values(); 	xAttributes[i].setText(USER); 	break;
				case 2: attrArr = MovieAttr.values(); 	xAttributes[i].setText(MOVIE); 	break;
			}
			

			yAttributes[i] = new JCheckBox[attrArr.length];
			
			for(int o=0; o<attrArr.length; o++)
			{
				yAttributes[i][o] = new JCheckBox(attrArr[o].toString());
				attributePanel[i].add(yAttributes[i][o]);
			}
		}
	}
	
	private void attachElements()
	{
		add(xTitle);
		
		for(JRadioButton radioBtn : xAttributes)
		{
			add(radioBtn);
		}
		
		add(yTitle);
		
		for(JPanel attrPanel : attributePanel)
		{
			add(attrPanel);
		}
		
	}
}
