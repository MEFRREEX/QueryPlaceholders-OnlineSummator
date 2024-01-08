package com.mefrreex.queryplaceholders.onlinesummator;

import cn.nukkit.plugin.PluginBase;
import com.mefrreex.queryplaceholders.onlinesummator.listener.QueryListener;
import lombok.Getter;

import java.util.List;

@Getter
public class OnlineSummator extends PluginBase {

    private static OnlineSummator instance;

    private boolean sumAll;
    private boolean sumMaxOnline;
    private List<String> servers;

    @Override
    public void onLoad() {
        OnlineSummator.instance = this;
        this.saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        this.sumAll = this.getConfig().getBoolean("sum-all");
        this.sumMaxOnline = this.getConfig().getBoolean("sum-max-online");
        this.servers = this.getConfig().getStringList("servers");
        this.getServer().getPluginManager().registerEvents(new QueryListener(this), this);
    }

    public static OnlineSummator getInstance() {
        return instance;
    }
}
