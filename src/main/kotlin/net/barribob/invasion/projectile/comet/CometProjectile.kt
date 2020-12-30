package net.barribob.invasion.projectile.comet

import net.barribob.invasion.mob.Entities
import net.barribob.invasion.projectile.BaseThrownItemEntity
import net.barribob.invasion.utils.AnimationUtils
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.projectile.thrown.ThrownItemEntity
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.EntityHitResult
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import software.bernie.geckolib3.core.IAnimatable
import software.bernie.geckolib3.core.controller.AnimationController
import software.bernie.geckolib3.core.manager.AnimationData
import software.bernie.geckolib3.core.manager.AnimationFactory

class CometProjectile : BaseThrownItemEntity, IAnimatable {
    var impacted: Boolean = false
    private var impactAction: ((Vec3d) -> Unit)? = null

    constructor(
        d: Double,
        e: Double,
        f: Double,
        world: World,
    ) : super(Entities.COMET, d, e, f, world)

    constructor(entityType: EntityType<out ThrownItemEntity>, world: World?) : super(entityType, world)

    constructor(livingEntity: LivingEntity, world: World, impactAction: (Vec3d) -> Unit) : super(
        Entities.COMET,
        livingEntity,
        world,
    ) {
        this.impactAction = impactAction
    }

    private fun onImpact() {
        if (impacted) return

        impacted = true
        val owner = owner
        if (owner != null && owner is LivingEntity) {
            impactAction?.let { it(pos) }
            remove()
        }
    }

    override fun onEntityHit(entityHitResult: EntityHitResult) {
        onImpact()
        super.onEntityHit(entityHitResult)
    }

    override fun onBlockHit(blockHitResult: BlockHitResult?) {
        onImpact()
        super.onBlockHit(blockHitResult)
    }

    override fun damage(source: DamageSource?, amount: Float): Boolean {
        onImpact()
        return super.damage(source, amount)
    }

    override fun collides(): Boolean {
        return true
    }

    override fun registerControllers(data: AnimationData) {
        data.addAnimationController(AnimationController(this, "spin", 0f, AnimationUtils.createIdlePredicate("spin")))
    }

    private val animationFactory = AnimationFactory(this)
    override fun getFactory(): AnimationFactory {
        return animationFactory
    }
}