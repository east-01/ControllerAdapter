package me.bowlerguy66.controlleradapter.layouts;

import java.io.File;
import java.util.List;

import me.bowlerguy66.controlleradapter.ControllerAdapter;

public class LayoutManager {

	private ControllerAdapter main;
	
	private List<Layout> layouts;
	private Layout currentLayout;

	public LayoutManager(ControllerAdapter main) {
		this.main = main;
		System.out.println("Initializing LayoutManager");
		reloadLayouts();		
		System.out.println("  Loaded layouts");
		if(layouts.size() > 0) setCurrentLayout(layouts.get(0), false);
		System.out.println("  Set the current layout to the first");
	}
	
	public void cycleLayout() {
		int nextLayout = layouts.indexOf(currentLayout) + 1;
		if(nextLayout >= layouts.size()) nextLayout = 0;
		setCurrentLayout(layouts.get(nextLayout));
	}
	
	public void reloadLayouts() {
		layouts = LayoutLoader.loadLayoutsInFolder(main, new File(ControllerAdapter.BASE_PATH + "/layouts"));
	}
	
	public List<Layout> getLayouts() {
		return layouts;
	}
	
	public Layout getLayout(String name) {
		for(Layout l : layouts) {
			if(l.getTitle().equals(name)) return l;
		}
		return null;
	}
	
	public Layout getCurrentLayout() {
		return currentLayout;
	}
	
	public void setCurrentLayout(Layout newLayout) {setCurrentLayout(newLayout, true);}
	public void setCurrentLayout(Layout newLayout, boolean forceUpdate) {
		if(currentLayout != null) main.getControllerManager().getController().removeListener(currentLayout.getListener());
		this.currentLayout = newLayout;
		main.getControllerManager().getController().addListener(currentLayout.getListener());
		if(forceUpdate) main.getDisplay().updateLayoutsBox();
		main.getInfoOverlay().updateText(newLayout.getTitle(), 60*5);
	}
	
	public void setCurrentLayout(String title) {setCurrentLayout(title, true);}
	public void setCurrentLayout(String title, boolean forceUpdate) {
		Layout newLayout = getLayout(title);
		if(newLayout != null) setCurrentLayout(newLayout, forceUpdate);
	}
	
	public boolean hasCurrentLayout() {
		return currentLayout != null;
	}
	
	public String[] getLayoutNames() {
		String[] names = new String[layouts.size()];
		for(int i = 0; i < layouts.size(); i++) {
			names[i] = layouts.get(i).getTitle();
		}
		return names;
	}
	
}
