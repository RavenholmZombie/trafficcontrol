package com.clussmanproductions.trafficcontrol.event;

import com.clussmanproductions.trafficcontrol.ModTrafficControl;
import com.clussmanproductions.trafficcontrol.scanner.Scanner;

import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@EventBusSubscriber
public class WorldEventHandler {
	@SubscribeEvent
	public static void onLoad(WorldEvent.Load e)
	{
		if (e.getWorld().isRemote || !ModTrafficControl.IR_INSTALLED)
		{
			return;
		}
		
		try
		{
			Scanner thread = new Scanner(e.getWorld());
			Scanner.ScannersByWorld.put(e.getWorld().provider.getDimension(), thread);
		}
		catch(Exception ex)
		{
			ModTrafficControl.logger.error("Could not start Scanner Thread!  Either could not replace Entity Tracker or Chunk Loader: " + ex.toString());
		}
	}
	
	@SubscribeEvent
	public static void onUnload(WorldEvent.Unload e)
	{
		if (e.getWorld().isRemote || !ModTrafficControl.IR_INSTALLED)
		{
			return;
		}
		
		Scanner.ScannersByWorld.remove(e.getWorld().provider.getDimension());
	}
	
	@SubscribeEvent
	public static void onTick(TickEvent.WorldTickEvent e)
	{
		if (e.world.isRemote || !ModTrafficControl.IR_INSTALLED || e.type != TickEvent.Type.WORLD || e.phase != TickEvent.Phase.END)
		{
			return;
		}
		
		Scanner thread = Scanner.ScannersByWorld.get(e.world.provider.getDimension());
		if (thread != null)
		{
			thread.tick(e.world);
		}
	}
}
