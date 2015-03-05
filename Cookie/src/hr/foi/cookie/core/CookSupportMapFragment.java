package hr.foi.cookie.core;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;

public class CookSupportMapFragment extends SupportMapFragment {
	private View originalView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		originalView = super.onCreateView(inflater, container, savedInstanceState);
		return originalView;
	}
	
	@Override
	public View getView() {
		return originalView;
	}
	/*
	// 1. lista pretplatnika
	private List<OnMyLongClickListener> listeners;
	
	// 2. metoda za prijavu pretplatnika
	public void setOnMyLongClickListener(OnMyLongClickListener l)
	{
		if (listeners == null)
			listeners = new ArrayList<OnMyLongClickListener>();
		listeners.add(l);
	}
	// 3. metoda/e za odjavi pretplatnika
	public void removeOnMyLongClickListerner(OnMyLongClickListener l)
	{
		if (listeners != null)
			if (listeners.contains(l))
				listeners.remove(l);
		
		if (listeners.size() == 0)
			listeners = null;
	}
	
	// 4. metoda za okidanje dogaðaja
	private void onMyLongClick(MotionEvent event)
	{
		if (listeners != null)
		{
			for (OnMyLongClickListener l : listeners)
			{
				l.onMyLongClick(event);
			}
		}
		
	}*/
	
}
