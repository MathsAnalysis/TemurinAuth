package it.mathsanalysis.auth.config;

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

import it.mathsanalysis.auth.Auth;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigFile extends YamlConfiguration {

    private final File file;


    public ConfigFile(@NotNull String nameFile){
        this.file = new File(nameFile + ".yml");

        if(!file.exists()){
            try {
                createConfigFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.loadFile();
    }


    private void loadFile(){
        try {

            if(!file.exists()){
                createConfigFile();
            }

            this.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveFile() {
        try {
            if(!file.exists()){
                createConfigFile();
            }

            this.save(this.file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        try {
            if(!file.exists()){
                createConfigFile();
            }

            loadFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getString(@NotNull String path, String def) {
        return super.getString(path, def);
    }

    private void createConfigFile() throws IOException {
        Path configFilePath = Path.of(Auth.get().getPlugin().getDataFolder().getPath(), file.getName());
        Files.createFile(configFilePath);
    }

}