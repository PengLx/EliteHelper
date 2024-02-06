package com.chsteam.penglx

import com.chsteam.penglx.Util.cal
import com.magmaguy.elitemobs.api.EliteMobDeathEvent
import com.magmaguy.elitemobs.api.EliteMobSpawnEvent
import com.magmaguy.elitemobs.api.internal.RemovalReason
import com.magmaguy.elitemobs.entitytracker.EntityTracker
import com.magmaguy.elitemobs.mobconstructor.EliteEntity
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.scheduler.BukkitRunnable
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.util.random

object Listener {

    private val list = EliteHelper.conf.getStringList("world")
    private val white = EliteHelper.conf.getBoolean("white-list")

    private val radius = EliteHelper.conf.getDouble("radius")
    private val random = EliteHelper.conf.getBoolean("random")
    private val max = EliteHelper.conf.getInt("max-cal")
    private val power = EliteHelper.conf.getString("power")


    @SubscribeEvent(EventPriority.LOWEST)
    fun e(e: EliteMobSpawnEvent) {

        if(e.reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG) return

        if(white && e.entity.world.name !in list) return
        if(!white && e.entity.world.name in list) return

        object: BukkitRunnable() {

            override fun run() {
                val nearByPlayer = Nearby.getNearPlayer(e.entity.location, radius)
                var level = 0.0

                if(random) {
                    nearByPlayer.shuffled()
                }

                var done = 0

                nearByPlayer.forEach {
                    if(done==max) return@forEach
                    level += Util.getZDL(power!!, it)
                    done++
                }

                EntityTracker.unregisterEliteEntity(e.entity, RemovalReason.REMOVE_COMMAND)
                val lastLevel = random(level*0.7, level*1.3).toInt()
                val eliteMob = EliteEntity(e.entity, lastLevel, CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)
                eliteMob.setLivingEntity(e.entity, CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)
            }
        }.runTaskAsynchronously(EliteHelper.plugin)

    }


    @SubscribeEvent
    fun e(e: EliteMobDeathEvent) {
        val level = e.eliteEntity.level
        val name = e.eliteEntity.name.replace("[^\\u4E00-\\u9FA5]".toRegex(), "")
        val loc = e.entity.location
        val world = e.entity.world

        e.eliteEntity.damagers

        EliteHelper.lootTables[name]?.let {
            it.forEach { pair ->
                val min = cal(pair.second.getString("min")!!, level).toInt()
                val max = cal(pair.second.getString("max")!!, level).toInt()
                val lootMap = hashMapOf<String, Double>()
                pair.second.getConfigurationSection("drops")!!.getKeys(false).forEach {
                    lootMap[it] = cal(pair.second.getString("drops.${it}")!!,level)
                }

                val drops = needDrops(min,max, lootMap)

                drops.forEach {name ->
                    Util.spawnItem(name, level)?.let {
                        world.dropItem(loc, it)
                    }
                }
            }
        }
    }

    private fun needDrops(min: Int, max: Int, lootMap : HashMap<String, Double>) : List<String> {
        val need = mutableListOf<String>()
        var done = 0
        if(min == 0) {
            lootMap.forEach {
                if(it.value > random(0.0,1.0)) {
                    need.add(it.key)
                    done++
                }
            }
        } else {
            while(done < min) {
                lootMap.forEach {
                    if(done > max) {
                        return need
                    }

                    if(it.value > random(0.0, 1.0)) {
                        done++
                        need.add(it.key)
                    }
                }
            }
        }

        return need
    }
}