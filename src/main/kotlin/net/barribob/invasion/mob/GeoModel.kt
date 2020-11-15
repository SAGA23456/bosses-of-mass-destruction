package net.barribob.invasion.mob

import net.minecraft.util.Identifier
import software.bernie.geckolib3.core.IAnimatable
import software.bernie.geckolib3.model.AnimatedGeoModel

class GeoModel<T : IAnimatable>(
    private val modelLocation: Identifier,
    private val textureLocation: Identifier,
    private val animationLocation: Identifier
) : AnimatedGeoModel<T>() {
    override fun getModelLocation(animatable: T): Identifier = modelLocation
    override fun getTextureLocation(animatable: T): Identifier = textureLocation
    override fun getAnimationFileLocation(animatable: T): Identifier = animationLocation
}