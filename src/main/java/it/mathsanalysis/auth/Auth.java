package it.mathsanalysis.auth;

/*
 * MIT License
 * Copyright (c) 2025 MathsAnalysis
 *
 * Created on 27/02/2025.
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

import it.mathsanalysis.auth.config.ConfigFile;
import it.mathsanalysis.auth.premium.provider.PremiumProvider;
import it.mathsanalysis.auth.storage.provider.StoragerProvider;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
@Setter
public class Auth {

    private static Auth INSTANCE;
    private JavaPlugin plugin;

    private StoragerProvider storagerProvider;
    private PremiumProvider premiumProvider;

    private ConfigFile storageConfig, configFile;

    public Auth(JavaPlugin plugin){
        this.plugin = plugin;
    }

    public void start(){
        INSTANCE = this;

        registerConfig();
        registerProvider();
    }

    public void stop(){
        this.storagerProvider.stop();
        this.premiumProvider.stop();
        INSTANCE = null;
    }

    private void registerConfig() {
        this.storageConfig = new ConfigFile("storage");
        this.configFile = new ConfigFile("config");
    }

    private void registerProvider(){
        this.storagerProvider = new StoragerProvider();
        this.storagerProvider.start();

        this.premiumProvider = new PremiumProvider();
        this.premiumProvider.start();
    }

    public static Auth get(){
        return INSTANCE;
    }
}