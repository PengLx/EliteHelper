package com.chsteam.penglx

import taboolib.common.platform.Plugin
import taboolib.common.platform.function.getDataFolder
import taboolib.common.platform.function.info
import taboolib.common.platform.function.releaseResourceFile
import taboolib.common.platform.function.warning
import taboolib.library.configuration.ConfigurationSection
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration
import taboolib.platform.BukkitPlugin
import taboolib.module.configuration.Configuration.Companion.loadFromFile
import java.io.File

object EliteHelper : Plugin() {

    @Config
    lateinit var conf: Configuration
        private set

    val plugin by lazy {
        BukkitPlugin.getInstance()
    }

    val items = hashMapOf<String, ConfigurationSection>()
    val lootTables = hashMapOf<String, MutableList<Pair<String, ConfigurationSection>>>()
    lateinit var stats : List<String>

    override fun onEnable() {
        info("Successfully running EliteHelper!")
        load()
    }

    fun load() {
        lootTables.clear()
        items.clear()
        stats = conf.getStringList("ava-stats")
        itemsload()
        lootLoad()
    }

    private fun lootLoad() {
        val file = File(getDataFolder(),"loot")
        if(!file.exists()) {
            releaseResourceFile("loot/example.yml", true)
        }
        loadLoot(file)
    }

    private fun loadLoot(file: File) {
        when {
            file.isDirectory -> file.listFiles()?.forEach { loadLoot(it) }
            file.name.endsWith(".yml") -> loadLoot(loadFromFile(file))
        }
    }

    private fun loadLoot(config: Configuration) {
        config.getKeys(false).forEach { name ->
            config.getConfigurationSection(name)?.let {
                if(lootTables[config.getString("$name.mobs")!!] == null) {
                    lootTables[config.getString("$name.mobs")!!] = mutableListOf()
                }
                lootTables[config.getString("$name.mobs")!!]!!.add(Pair(name, it))
            }
        }
    }

    private fun itemsload() {
        val file = File(getDataFolder(),"items")
        if(!file.exists()) {
            releaseResourceFile("items/example.yml", true)
        }
        load(file)
    }

    private fun load(file: File) {
        when {
            file.isDirectory -> file.listFiles()?.forEach { load(it) }
            file.name.endsWith(".yml") -> load(loadFromFile(file))
        }
    }

    private fun load(config: Configuration) {
        config.getKeys(false).forEach { name ->
            if(!items.containsKey(name)) {
                config.getConfigurationSection(name)?.let {
                    items[name] = it
                }
            } else {
                warning("物品Key重复 请检查 $name")
            }
        }
    }
}