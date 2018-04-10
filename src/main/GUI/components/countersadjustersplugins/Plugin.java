package main.GUI.components.countersadjustersplugins;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.GUI.components.logandinput.Input;
import main.GUI.components.logandinput.Log;
import main.GUI.components.misc.CustomButton;
import main.actions.Action;
import main.misc.Handler;
import main.plugin.StreamSAKPlugin;
import main.plugin.types.StreamSAKAdvancedPlugin;
import main.plugin.types.StreamSAKSimplePlugin;

public class Plugin {

	public static Color pluginForegroundColor = new Color(138, 219, 190);
	
	private StreamSAKPlugin plugin;
	
	public Plugin(StreamSAKPlugin plugin) {
		this.plugin = plugin;
	}
	
	public JButton[] generate() {
		JButton[] components = { generateButton() };
		return components;
	}
	
	public String getName() {
		return plugin.getName();
	}
	
	public StreamSAKPlugin getPlugin() {
		return plugin;
	}
	
	public void callPlugin() {
		if(plugin instanceof StreamSAKAdvancedPlugin) {
			StreamSAKAdvancedPlugin ap = (StreamSAKAdvancedPlugin)plugin;
			
			if(ap.getInput().getRequired()) {
				Handler.doOnInput(new Action() {
					@Override
					public void run() throws Exception {
						ap.getInput().setData(Input.getLastInput());
						plugin.doOnPress();
						
						if(ap.getLogEntry().getRequired())
							Log.write(ap.getLogEntry().getEntry());
					}
				}, ap.getInput().getMessage());
			} else {
				plugin.doOnPress();
				
				if(ap.getLogEntry().getRequired())
					Log.write(ap.getLogEntry().getEntry());
			}
		} else if (plugin instanceof StreamSAKSimplePlugin) {
			plugin.doOnPress();
		}
	}
	
	private JButton generateButton() {
		JButton b = new CustomButton(plugin.getName(), pluginForegroundColor);
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { callPlugin(); }
		});
		
		return b;
	}

}
