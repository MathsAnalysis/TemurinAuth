package it.mathsanalysis.auth.storage.database.service;

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

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import it.mathsanalysis.auth.Auth;
import it.mathsanalysis.auth.storage.database.structure.IDatabase;
import lombok.Getter;
import org.bson.Document;

@Getter
public class MongoService implements IDatabase {

    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> users;

    @Override
    public void connect() {
        String URI = Auth.get().getStorageConfig().getString("cMongo.URI", "mongodb://localhost:27017");
        String database = Auth.get().getStorageConfig().getString("Database.Mongo.Database", "auth");

        this.client = MongoClients.create(URI);
        this.database = this.client.getDatabase(database);
        this.users = this.database.getCollection("users");
    }

    @Override
    public void disconnect() {
        if (this.client != null){
            this.database = null;
            this.users = null;
            this.client.close();
        }
    }
}