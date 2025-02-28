package it.mathsanalysis.auth.storage.database.core;

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

import it.mathsanalysis.auth.Auth;
import it.mathsanalysis.auth.manager.Manager;
import it.mathsanalysis.auth.storage.database.service.MongoService;
import it.mathsanalysis.auth.storage.database.service.MySQLService;
import it.mathsanalysis.auth.storage.database.structure.IDatabase;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager implements Manager {

    private static DatabaseManager INSTANCE;

    private List<IDatabase> databases;

    @Override
    public void start() {
        INSTANCE = this;

        registerDatabases();
    }

    @Override
    public void unregister() {
        databases.forEach(IDatabase::disconnect);
        INSTANCE = null;
    }

    private void registerDatabases(){
        databases = new ArrayList<>();

        databases.add(new MongoService());
        databases.add(new MySQLService());

        String type = Auth.get().getStorageConfig().getString("Database.Type", "MongoDB");
        for (IDatabase database : databases) {
            switch (type){
                case "MongoDB"->{
                    if (database instanceof MongoService service){
                        service.connect();
                    }
                }
                case "MySQL"->{
                    if (database instanceof MySQLService service){
                        service.connect();
                    }
                }
            }
        }
    }

    public static DatabaseManager get(){
        return INSTANCE;
    }

}