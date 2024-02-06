package com.chsteam.penglx

import com.chsteam.penglx.math.formula.DynamicFormula
import io.lumine.mythic.lib.api.player.MMOPlayerData
import net.Indyuce.mmoitems.ItemStats
import net.Indyuce.mmoitems.MMOItems
import net.Indyuce.mmoitems.api.Type
import net.Indyuce.mmoitems.stat.data.DoubleData
import net.Indyuce.mmoitems.stat.type.ItemStat
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.common.util.random


object Util {
    fun spawnItem(key: String, level: Int) : ItemStack? {
        val itemConfig = EliteHelper.items[key] ?: return null
        val type = itemConfig.getString("MATERIAL")
        val name = itemConfig.getString("MMOItemsID")
        val item = MMOItems.plugin.getMMOItem(Type.get(type), name) ?: return null
        val builder = item.newBuilder()
        var itemLevel = level
        if(itemLevel > itemConfig.getInt("level.max") && itemConfig.getInt("level.max") != -1) {
            itemLevel = itemConfig.getInt("level.max")
        }
        else if((itemLevel < itemConfig.getInt("level.min")) && itemConfig.getInt("level.min") != -1) {
            itemLevel = itemConfig.getInt("level.min")
        }
        itemConfig.getKeys(false).forEach { key ->
            //处理数值组
            if(key.endsWith("-stats")) {
                val min = cal(itemConfig.getString("${key}.min")!!, level).toInt()
                val max = cal(itemConfig.getString("${key}.max")!!, level).toInt()
                var var1 = itemConfig.getString("${key}.var1")?.let { cal(it,level) } ?: 1.0
                var var2 = itemConfig.getString("${key}.var2")?.let { cal(it,level) } ?: 1.0
                var var3 = itemConfig.getString("${key}.var3")?.let { cal(it,level) } ?: 1.0
                var var4 = itemConfig.getString("${key}.var4")?.let { cal(it,level) } ?: 1.0
                var var5 = itemConfig.getString("${key}.var5")?.let { cal(it,level) } ?: 1.0


                //获得数值组中可用的数值和几率
                val statsHashMap = hashMapOf<String, Pair<String, String?>>()
                itemConfig.getConfigurationSection(key)!!.getKeys(false).forEach { stats ->
                    if(stats !in listOf("var1","var2","var3","var4","var5","min","max")) {
                        statsHashMap[stats] = Pair(itemConfig.getConfigurationSection("${key}.${stats}")!!.getString("chance")!! , itemConfig.getConfigurationSection("${key}.${stats}")!!.getString("chance-max"))
                    }
                }
                val needStats = needStatsList(min,max, statsHashMap, level)

                needStats.shuffled().forEach { statName ->
                    val stat = getStats(statName)
                    var needPlus = 0.0
                    if(builder.mmoItem.hasData(stat)) {
                        needPlus = (builder.mmoItem.getData(stat) as DoubleData).value
                        builder.mmoItem.removeData(stat)
                    }
                    val cal = calAmount(
                        level,
                        itemConfig.getConfigurationSection("${key}.${statName}")!!.getString("amount")!!,
                        itemConfig.getConfigurationSection("${key}.${statName}")!!.getString("min"),
                        itemConfig.getConfigurationSection("${key}.${statName}")!!.getString("max"),
                        itemConfig.getConfigurationSection("${key}.${statName}")!!.getString("done"),
                        itemConfig.getConfigurationSection("${key}.${statName}")!!.getString("up"),
                        var1,
                        var2,
                        var3,
                        var4,
                        var5
                    )

                    itemConfig.getConfigurationSection("${key}.${statName}")!!.getString("var1_Set")?.let {
                        var1 = cal(it, level, cal,var1,
                            var2,
                            var3,
                            var4,
                            var5 )
                    }

                    itemConfig.getConfigurationSection("${key}.${statName}")!!.getString("var2_Set")?.let {
                        var2 = cal(it, level, cal,var1,
                            var2,
                            var3,
                            var4,
                            var5)
                    }

                    itemConfig.getConfigurationSection("${key}.${statName}")!!.getString("var3_Set")?.let {
                        var3 = cal(it, level, cal,var1,
                            var2,
                            var3,
                            var4,
                            var5)
                    }

                    itemConfig.getConfigurationSection("${key}.${statName}")!!.getString("var4_Set")?.let {
                        var4 = cal(it, level, cal,var1,
                            var2,
                            var3,
                            var4,
                            var5)
                    }

                    itemConfig.getConfigurationSection("${key}.${statName}")!!.getString("var5_Set")?.let {
                        var5 = cal(it, level, cal,var1,
                            var2,
                            var3,
                            var4,
                            var5)
                    }

                    builder.mmoItem.setData(stat, DoubleData(cal+needPlus))
                }
            }
        }
        return builder.build()
    }

    private fun getStats(name: String): ItemStat<*,*> {
        return when(name) {
            "ATTACK_DAMAGE" -> ItemStats.ATTACK_DAMAGE
            "ATTACK_SPEED" -> ItemStats.ATTACK_SPEED
            "CRITICAL_STRIKE_CHANCE" -> ItemStats.CRITICAL_STRIKE_CHANCE
            "CRITICAL_STRIKE_POWER" -> ItemStats.CRITICAL_STRIKE_POWER
            "SKILL_CRITICAL_STRIKE_CHANCE" -> ItemStats.SKILL_CRITICAL_STRIKE_CHANCE
            "SKILL_CRITICAL_STRIKE_POWER" -> ItemStats.SKILL_CRITICAL_STRIKE_POWER
            "BLOCK_POWER" -> ItemStats.BLOCK_POWER
            "BLOCK_RATING" -> ItemStats.BLOCK_RATING
            "BLOCK_COOLDOWN_REDUCTION" -> ItemStats.BLOCK_COOLDOWN_REDUCTION
            "DODGE_RATING" -> ItemStats.DODGE_RATING
            "DODGE_COOLDOWN_REDUCTION" -> ItemStats.DODGE_COOLDOWN_REDUCTION
            "PARRY_RATING" -> ItemStats.PARRY_RATING
            "PARRY_COOLDOWN_REDUCTION" -> ItemStats.PARRY_COOLDOWN_REDUCTION
            "COOLDOWN_REDUCTION" -> ItemStats.COOLDOWN_REDUCTION
            "RANGE" -> ItemStats.RANGE
            "MANA_COST" -> ItemStats.MANA_COST
            "STAMINA_COST" -> ItemStats.STAMINA_COST
            "ARROW_VELOCITY" -> ItemStats.ARROW_VELOCITY
            "PVE_DAMAGE" -> ItemStats.PVE_DAMAGE
            "PVP_DAMAGE" -> ItemStats.PVP_DAMAGE
            "BLUNT_POWER" -> ItemStats.BLOCK_POWER
            "BLUNT_RATING" -> ItemStats.BLUNT_RATING
            "WEAPON_DAMAGE" -> ItemStats.WEAPON_DAMAGE
            "PROJECTILE_DAMAGE" -> ItemStats.PROJECTILE_PARTICLES
            "SKILL_DAMAGE" -> ItemStats.SKILL_DAMAGE
            "MAGIC_DAMAGE" -> ItemStats.MAGIC_DAMAGE
            "PHYSICAL_DAMAGE" -> ItemStats.PHYSICAL_DAMAGE
            "DEFENSE" -> ItemStats.DEFENSE
            "DAMAGE_REDUCTION" -> ItemStats.DAMAGE_REDUCTION
            "FALL_DAMAGE_REDUCTION" -> ItemStats.FALL_DAMAGE_REDUCTION
            "PROJECTILE_DAMAGE_REDUCTION" -> ItemStats.PROJECTILE_DAMAGE_REDUCTION
            "PHYSICAL_DAMAGE_REDUCTION" -> ItemStats.PHYSICAL_DAMAGE_REDUCTION
            "FIRE_DAMAGE_REDUCTION" -> ItemStats.FIRE_DAMAGE_REDUCTION
            "MAGIC_DAMAGE_REDUCTION" -> ItemStats.MAGIC_DAMAGE_REDUCTION
            "PVE_DAMAGE_REDUCTION" -> ItemStats.PVE_DAMAGE_REDUCTION
            "PVP_DAMAGE_REDUCTION" -> ItemStats.PVP_DAMAGE_REDUCTION
            "UNDEAD_DAMAGE" -> ItemStats.UNDEAD_DAMAGE
            "LIFESTEAL" -> ItemStats.LIFESTEAL
            "SPELL_VAMPIRISM" -> ItemStats.SPELL_VAMPIRISM
            "ARMOR" -> ItemStats.ARMOR
            "ARMOR_TOUGHNESS" -> ItemStats.ARMOR_TOUGHNESS
            "MAX_HEALTH" -> ItemStats.MAX_HEALTH
            "MAX_MANA" -> ItemStats.MAX_MANA
            "ITEM_COOLDOWN" -> ItemStats.ITEM_COOLDOWN
            "NOTE_WEIGHT" -> ItemStats.NOTE_WEIGHT
            "KNOCKBACK" -> ItemStats.KNOCKBACK
            "RECOIL" -> ItemStats.RECOIL
            "DEATH_DOWNGRADE_CHANCE" -> ItemStats.DOWNGRADE_ON_DEATH_CHANCE
            "REQUIRED_LEVEL" -> ItemStats.REQUIRED_LEVEL
            else -> ItemStats.ATTACK_DAMAGE
        }
    }

    private fun calAmount(level: Int,amount: String, min: String?, max: String?,up: String?, down: String?, var1: Double ,var2: Double, var3: Double, var4: Double,var5: Double) : Double {
        var itemAmount = cal(amount, level,var1,var2,var3,var4,var5)
        if(up != null && down != null) {
            val iup = cal(up,level,var1,var2,var3,var4,var5) + itemAmount
            val idown = itemAmount- cal(down, level,var1,var2,var3,var4,var5)
            itemAmount = random(iup, idown)
        }
        min?.let {
            if(cal(min, level,var1,var2,var3,var4,var5) > itemAmount) {
                itemAmount = cal(min, level,var1,var2,var3,var4,var5)
            }
        }

        max?.let {
            if(itemAmount > cal(max, level,var1,var2,var3,var4,var5)) {
                itemAmount = cal(max, level,var1,var2,var3,var4,var5)
            }
        }

        return itemAmount
    }

    private fun needStatsList(min: Int, max: Int,statsHashMap: HashMap<String, Pair<String, String?>>,level: Int) : List<String> {
        val need = mutableListOf<String>()
        var done = 0
        if(min == 0) {
            statsHashMap.forEach {
                var chance = cal(it.value.first, level)
                it.value.second?.let {
                    if(chance > cal(it, level)) {
                        chance = cal(it, level)
                    }
                }
                if(done >= max) return need
                if(chance > random(0.0, 1.0) && !need.contains(it.key)) {
                    done++
                    need.add(it.key)
                }
            }
        } else {
            while(done < min) {
                statsHashMap.forEach {
                    var chance = cal(it.value.first, level)
                    it.value.second?.let {
                        if (chance > cal(it, level)) {
                            chance = cal(it, level)
                        }
                    }
                    if (done >= max) return need
                    if (chance > random(0.0, 1.0) && !need.contains(it.key)) {
                        done++
                        need.add(it.key)
                    }
                }
            }
        }
        return need
    }

    fun cal(string: String, lvl: Int) : Double {
        val newString = string.replace("lvl", lvl.toString())
        return DynamicFormula(newString).evaluate()
    }

    fun cal(string: String, lvl: Int, var1: Double ,var2: Double, var3: Double, var4: Double,var5: Double) : Double {
        val newString = string
            .replace("lvl", lvl.toString())
            .replace("var1", var1.toString())
            .replace("var2", var2.toString())
            .replace("var3", var3.toString())
            .replace("var4", var4.toString())
            .replace("var5", var5.toString())
        return DynamicFormula(newString).evaluate()
    }

    fun cal(string: String, lvl: Int, stat: Double, var1: Double ,var2: Double, var3: Double, var4: Double,var5: Double) : Double {
        val newString = string
            .replace("lvl", lvl.toString())
            .replace("var1", var1.toString())
            .replace("var2", var2.toString())
            .replace("var3", var3.toString())
            .replace("var4", var4.toString())
            .replace("var5", var5.toString())
            .replace("value", stat.toString())
        return DynamicFormula(newString).evaluate()
    }

    fun getZDL(string: String, player: Player) : Double {
        val playerData: MMOPlayerData = MMOPlayerData.get(player)
        val statMap = playerData.statMap
        var newString = string

        EliteHelper.stats.forEach {
            newString = newString.replace(it, statMap.getInstance(it).total.toString())
        }


        return DynamicFormula(newString).evaluate()
    }
}