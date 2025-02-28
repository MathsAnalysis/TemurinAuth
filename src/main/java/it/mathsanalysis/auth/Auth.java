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

import com.velocitypowered.api.proxy.ProxyServer;
import it.mathsanalysis.auth.config.ConfigFile;
import it.mathsanalysis.auth.manager.Manager;
import it.mathsanalysis.auth.premium.PremiumManager;
import it.mathsanalysis.auth.storage.database.core.DatabaseManager;
import it.mathsanalysis.auth.storage.user.core.AuthPlayerManager;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Auth {

    private static Auth INSTANCE;

    private ProxyServer proxyServer;
    private Logger logger;

    private List<Manager> managers;

    private Path dataDirectory;
    private ConfigFile storageConfig, configFile;

    public Auth(ProxyServer proxyServer, Logger logger, Path dataDirectory) {
        this.proxyServer = proxyServer;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
    }

    public void start(){
        INSTANCE = this;

        registerConfig();
        registerAllManager();
    }

    public void stop(){
        managers.forEach(Manager::unregister);
        managers.clear();
        INSTANCE = null;
    }

    private void registerConfig() {
        this.storageConfig = new ConfigFile(dataDirectory, "storage");
        this.configFile = new ConfigFile(dataDirectory, "config");
    }

    private void registerAllManager(){
        managers = new ArrayList<>();

        managers.add(new DatabaseManager());
        managers.add(new AuthPlayerManager());
        managers.add(new PremiumManager());

        managers.forEach(Manager::start);
    }

    public static Auth get(){
        return INSTANCE;
    }
}