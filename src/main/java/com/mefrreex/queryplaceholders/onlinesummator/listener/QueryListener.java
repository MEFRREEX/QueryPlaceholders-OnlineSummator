package com.mefrreex.queryplaceholders.onlinesummator.listener;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.QueryRegenerateEvent;
import com.mefrreex.queryplaceholders.QueryPlaceholders;
import com.mefrreex.queryplaceholders.onlinesummator.OnlineSummator;
import com.mefrreex.queryplaceholders.query.BedrockQueryResponse;

import java.util.Collection;
import java.util.function.Function;

public class QueryListener implements Listener {
    
    private final OnlineSummator main;
    private final QueryPlaceholders queryPlaceholders;

    public QueryListener(OnlineSummator main) {
		this.main = main;
        this.queryPlaceholders = QueryPlaceholders.getInstance();
	}

    @EventHandler
    public void onQuery(QueryRegenerateEvent event) {
        Collection<String> servers = main.isSumAll() ? queryPlaceholders.getServers().keySet() : main.getServers();

        int online = this.getTotalOnline(servers, query -> query.playerCount());
        int maxOnline = this.getTotalOnline(servers, query -> query.maxPlayers());

        event.setPlayerCount(online);

        if (main.isSumMaxOnline()) {
            event.setMaxPlayerCount(maxOnline);
        }
    }

    /**
     * Counting all servers online
     * @param servers  Collection<String>
     * @param function Function<BedrockQueryResponse, Integer>
     * @return Online of all servers
     */
    private int getTotalOnline(Collection<String> servers, Function<BedrockQueryResponse, Integer> function) {
        return servers.stream()
            .map(server -> queryPlaceholders.getQuery(server))
            .mapToInt(query -> function.apply(query))
            .sum();
    }
}
