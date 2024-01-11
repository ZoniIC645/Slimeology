package com.zoniic645.slimeology.client.models;

import com.zoniic645.slimeology.SlimeologyMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers{
	public static final ModelLayerLocation SMART_SLIME_INNER_LAYER = new ModelLayerLocation(new ResourceLocation(SlimeologyMod.MODID, "slime_layer"), "inner");
	public static final ModelLayerLocation SMART_SLIME_OUTER_LAYER = new ModelLayerLocation(new ResourceLocation(SlimeologyMod.MODID, "slime_layer"), "outer");
}
