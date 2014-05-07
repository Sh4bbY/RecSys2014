package helper;

import javax.swing.JTextArea;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(name = "TextFieldAppender", category = "Core", elementType = "appender")
public class TextAreaAppender extends AbstractAppender 
{
	private static JTextArea textArea;
	private String text;
	
    private TextAreaAppender(String name, Filter filter) 
    {
        super(name, filter, null);
        text = "";
    }

    public static void setTextArea(JTextArea textArea)
    {
    	TextAreaAppender.textArea = textArea;
    }
    
    public void append(LogEvent event) 
    {
    	if(event.getLevel() != Level.INFO)
    	{
    		return;
    	}
    	
    	text += event.getMessage().getFormattedMessage()+"\n";
    	
    	if(textArea != null)
    	{
    		textArea.setText(text);
    	}
    }

    @PluginFactory
    public static TextAreaAppender createAppender(
         @PluginAttribute("name") String name,
         @PluginElement("filters") Filter filter) 
    {
        return new TextAreaAppender(name, filter);
    }
}
