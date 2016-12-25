/**
 *      SandstoneBukkit - Bukkit implementation of SandstoneCommon
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2016 Sandstone <https://github.com/ProjectSandstone/>
 *      Copyright (c) contributors
 *
 *
 *      Permission is hereby granted, free of charge, to any person obtaining a copy
 *      of this software and associated documentation files (the "Software"), to deal
 *      in the Software without restriction, including without limitation the rights
 *      to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *      copies of the Software, and to permit persons to whom the Software is
 *      furnished to do so, subject to the following conditions:
 *
 *      The above copyright notice and this permission notice shall be included in
 *      all copies or substantial portions of the Software.
 *
 *      THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *      IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *      FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *      AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *      LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *      OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *      THE SOFTWARE.
 */
package com.github.projectsandstone.bukkit.converter

import com.github.jonathanxd.adapterhelper.Adapter
import com.github.jonathanxd.adapterhelper.AdapterManager
import com.github.jonathanxd.adapterhelper.Converter
import com.github.projectsandstone.api.Server
import com.github.projectsandstone.api.entity.Damageable
import com.github.projectsandstone.api.entity.Entity
import com.github.projectsandstone.api.entity.living.*
import com.github.projectsandstone.api.entity.living.animal.*
import com.github.projectsandstone.api.entity.living.monster.*
import com.github.projectsandstone.api.entity.living.player.Player
import com.github.projectsandstone.api.entity.living.player.User
import com.github.projectsandstone.api.world.World
import com.github.projectsandstone.bukkit.util.alias.*
import com.github.projectsandstone.bukkit.util.biMapOf

object ClassConverter : Converter<Class<*>, Class<*>> {

    private val converters = biMapOf(
            BukkitServer::class.java to Server::class.java,
            BukkitWorld::class.java to World::class.java,

            // Entity
            BukkitEntity::class.java to Entity::class.java,
            BukkitDamageable::class.java to Damageable::class.java,
            BukkitLivingEntity::class.java to LivingEntity::class.java,

            // Animal
            BukkitAnimals::class.java to Animal::class.java,
            BukkitChicken::class.java to Chicken::class.java,
            BukkitCow::class.java to Cow::class.java,
            BukkitHorse::class.java to Horse::class.java,
            BukkitMushroomCow::class.java to MushroomCow::class.java,
            BukkitOcelot::class.java to Ocelot::class.java,
            BukkitPig::class.java to Pig::class.java,
            BukkitPolarBear::class.java to PolarBear::class.java,
            BukkitRabbit::class.java to Rabbit::class.java,
            BukkitSheep::class.java to Sheep::class.java,
            BukkitWolf::class.java to Wolf::class.java,

            // Monster
            BukkitMonster::class.java to Monster::class.java,
            BukkitBlaze::class.java to Blaze::class.java,
            BukkitCaveSpider::class.java to CaveSpider::class.java,
            BukkitCreeper::class.java to Creeper::class.java,
            BukkitEnderman::class.java to Enderman::class.java,
            BukkitEndermite::class.java to Endermite::class.java,
            BukkitGhast::class.java to Ghast::class.java,
            BukkitGiant::class.java to Giant::class.java,
            BukkitGuardian::class.java to Guardian::class.java,
            BukkitMagmaCube::class.java to MagmaCube::class.java,
            BukkitSilverfish::class.java to Silverfish::class.java,
            BukkitSkeleton::class.java to Skeleton::class.java,
            BukkitSlime::class.java to Slime::class.java,
            BukkitSpider::class.java to Spider::class.java,
            BukkitWitch::class.java to Witch::class.java,
            BukkitWither::class.java to Wither::class.java,
            BukkitZombie::class.java to Zombie::class.java,
            BukkitPigZombie::class.java to ZombiePigman::class.java,

            // User
            BukkitOfflinePlayer::class.java to User::class.java,
            BukkitHumanEntity::class.java to Human::class.java,
            BukkitPlayer::class.java to Player::class.java,

            // Others
            BukkitAmbient::class.java to Ambient::class.java,
            BukkitAgeable::class.java to Ageable::class.java,
            BukkitBat::class.java to Bat::class.java,
            BukkitCreature::class.java to Creature::class.java,
            BukkitSquid::class.java to Squid::class.java,
            BukkitVillager::class.java to Villager::class.java
    )

    fun hasSandstoneEquivalent(klass: Class<*>) = converters.containsKey(klass)
    fun hasBukkitEquivalent(klass: Class<*>) = converters.inverse().containsKey(klass)
    fun hasEquivalent(klass: Class<*>) = this.hasSandstoneEquivalent(klass) || this.hasBukkitEquivalent(klass)

    fun getEquivalent(klass: Class<*>): Class<*> {
        return converters.getOrElse(klass, { converters.inverse()[klass] }) ?: throw IllegalArgumentException("Cannot convert input class: '$klass'!")
    }

    fun getSandstoneEquivalent(klass: Class<*>): Class<*> {
        return converters[klass] ?: throw IllegalArgumentException("Cannot get Sandstone equivalent class of: '$klass'!")
    }

    fun getBukkitEquivalent(klass: Class<*>): Class<*> {
        return converters.inverse()[klass] ?: throw IllegalArgumentException("Cannot get Bukkit equivalent class of: '$klass'!")
    }

    override fun convert(input: Class<*>, adapter: Adapter<*>?, manager: AdapterManager): Class<*> = this.getEquivalent(input)

}