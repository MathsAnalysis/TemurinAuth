package it.mathsanalysis.auth.storage.user.core;

/*
 * MIT License
 * Copyright (c) 2025 MathsAnalysis
 *
 * Created on 28/02/2025.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import it.mathsanalysis.auth.Auth;
import it.mathsanalysis.auth.storage.user.structure.AuthPlayer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
public class AuthPlayerManager {

    private static AuthPlayerManager INSTANCE;

    private Cache<UUID, AuthPlayer> AUTH_PLAYERS;
    private Map<UUID, AuthPlayer> REGISTERED_PLAYERS;


    public void start(){
        INSTANCE = this;

        this.AUTH_PLAYERS = Caffeine.newBuilder()
                .expireAfterAccess(Auth.get().getConfigFile().getInt("Settings.auth-session"), TimeUnit.SECONDS)
                .build();

        this.REGISTERED_PLAYERS = new ConcurrentHashMap<>();

    }

    public void stop() {
        this.AUTH_PLAYERS.invalidateAll();
        this.REGISTERED_PLAYERS.clear();
        INSTANCE = null;
    }

    public void registerPlayer(Player player){
        //todo implement the reqeust of database mojang
        //AuthPlayer authPlayer = new AuthPlayer(player.getUniqueId())
    }


    /**
     * Get a player that is currently registered
     * @param uuid UUID of the player
     * @return AuthPlayer
     */
    public AuthPlayer getRegisterPlayer(UUID uuid){
        return this.REGISTERED_PLAYERS.get(uuid);
    }


    /**
     * Get a player that is currently authenticated
     * @param uuid UUID of the player
     * @return AuthPlayer
     */
    public AuthPlayer getAuthPlayer(UUID uuid){
        return this.AUTH_PLAYERS.getIfPresent(uuid);
    }



    /**
     * Get all players that are currently registered
     * @return List of AuthPlayer
     */
    public List<AuthPlayer> getRegisteredPlayers(){
        return List.copyOf(this.REGISTERED_PLAYERS.values());
    }


    /**
     * Get all players that are currently authenticated
     * @return List of AuthPlayer
     */
    public List<AuthPlayer> getAuthPlayers(){
        return List.copyOf(this.AUTH_PLAYERS.asMap().values());
    }


    public static AuthPlayerManager getInstance(){
        return INSTANCE;
    }
}