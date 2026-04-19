package com.clussmanproductions.trafficcontrol.tileentity;

import com.clussmanproductions.trafficcontrol.ModSounds;

import net.minecraft.util.SoundEvent;

public class WaysideHornTileEntity extends BellBaseTileEntity
{
	@Override
	protected SoundEvent getSoundEvent() 
	{
		return ModSounds.waysideHornEvent;
	}
}
